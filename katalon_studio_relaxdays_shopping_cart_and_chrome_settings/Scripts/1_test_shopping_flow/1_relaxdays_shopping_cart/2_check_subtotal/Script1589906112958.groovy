import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.click(findTestObject('1_relaxdays_shopping_cart/a_showcart'))

List<Double> prices_dbl = prices.collect{
	Double.parseDouble(it.replace(',', '.').replaceAll(/[^\d\.]/, ''))
}

Double subtotal_expected_dbl = prices_dbl.sum()

String subtotal_actual = WebUI.getText(findTestObject('1_relaxdays_shopping_cart/subtotal_price')).replace(',', '.').replaceAll(/[^\d\.]/, '')

subtotal_actual_dbl = Double.parseDouble(subtotal_actual)

WebUI.verifyEqual(subtotal_actual_dbl, subtotal_expected_dbl)

WebUI.takeScreenshot()
WebUI.delay(1)
