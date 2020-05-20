package common

import java.text.Normalizer

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities

import java.awt.Robot
import java.awt.event.KeyEvent
import java.lang.reflect.Field

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


public class utils {
	@Keyword
	static def String normalizeString(String inputStr) {
		String outputStr = inputStr
		for ( e in [('ä'):'ae', ('ö'):'oe', ('ü'):'ue', ('ß'):'ss', ('Ä'):'Ae', ('Ö'):'Oe', ('Ü'):'Ue'] ) {
			outputStr = outputStr.replaceAll(e.key, e.value)
		}
		outputStr = Normalizer.normalize(outputStr, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
		return outputStr
	}

	@Keyword
	public static void pressShortCut(List<String> keysPressed = [], int quantity = 1) {
		Robot robot = new Robot()
		for (def count : (1..quantity)) {
			keysPressed.eachWithIndex{ it, id ->
				String keyPressed = it.toUpperCase()
				Field f = KeyEvent.class.getField('VK_' + keyPressed)
				int keyEvent = f.getInt(null)
				if (id < keysPressed.size() - 1) {
					robot.keyPress(keyEvent)
				}
				else {
					robot.keyPress(keyEvent)
					robot.keyRelease(keyEvent)
				}
			}
			keysPressed.eachWithIndex{ it, id ->
				String keyPressed = it.toUpperCase()
				Field f = KeyEvent.class.getField('VK_' + keyPressed)
				int keyEvent = f.getInt(null)
				if (id < keysPressed.size() - 1) {
					robot.keyRelease(keyEvent)
				}
			}
		}
	}

	@Keyword
	static def switchToNewWindow() {
		def Integer originWindowId =  WebUI.getWindowIndex()
		def WebDriver driver = DriverFactory.getWebDriver()
		def Set<String> allHandles = driver.getWindowHandles()
		def Integer newWindowId = allHandles.size() - 1
		WebUI.switchToWindowIndex(newWindowId)
		return ['originWindowId' : originWindowId, 'newWindowId' : newWindowId]
	}

	@Keyword
	static def closeActiveBrowserTab(String switchToWindowHandle = null) {
		def WebDriver driver = DriverFactory.getWebDriver()
		driver.switchTo().window(driver.getWindowHandles().last())
		driver.close()
		driver.switchTo().window(switchToWindowHandle != null ? switchToWindowHandle : driver.getWindowHandles().last())
	}
}