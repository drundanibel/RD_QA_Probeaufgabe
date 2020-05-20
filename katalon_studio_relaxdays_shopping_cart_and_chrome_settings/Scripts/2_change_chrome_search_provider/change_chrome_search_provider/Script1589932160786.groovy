import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverPath())
WebDriver driver = new ChromeDriver()
DriverFactory.changeWebDriver(driver)

for (def count : (1..quantity)) {
	CustomKeywords.'common.utils.pressShortCut'(['CONTROL', 'T'])
	CustomKeywords.'common.utils.switchToNewWindow'()
	WebUI.waitForPageLoad(10)
	
	String cursorKeyDirection = null
	
	if (WebUI.verifyElementPresent(findTestObject('2_change_chrome_settings/input_fakebox-input'), 5, FailureHandling.OPTIONAL)) {
		KeywordUtil.logInfo("### ${count}. Run: Google Search Provider is active and is now to be changed to Bing ...")
		cursorKeyDirection = 'DOWN'
	}
	else if (WebUI.verifyElementPresent(findTestObject('2_change_chrome_settings/input_sb_form_q'), 5, FailureHandling.OPTIONAL)) {
		KeywordUtil.logInfo("### ${count}. Run: Bing Search Provider is active and is now to be changed to Google ...")
		cursorKeyDirection = 'UP'
	}
	
	WebUI.takeScreenshot()
	WebUI.delay(1)
	
	if (cursorKeyDirection != null) {
		driver.get('chrome://settings')
		WebUI.waitForPageLoad(10)
		
		WebUI.delay(1)
		CustomKeywords.'common.utils.pressShortCut'(['TAB'], 21)
		WebUI.delay(1)
		CustomKeywords.'common.utils.pressShortCut'([cursorKeyDirection])
		KeywordUtil.logInfo("### ${count}. Run: Search Provider should have been changed.")
	}
	
	WebUI.takeScreenshot()
	WebUI.delay(1)
	
	CustomKeywords.'common.utils.closeActiveBrowserTab'()
}

CustomKeywords.'common.utils.pressShortCut'(['CONTROL', 'T'])
CustomKeywords.'common.utils.switchToNewWindow'()
WebUI.waitForPageLoad(10)

WebUI.takeScreenshot()
WebUI.delay(1)
