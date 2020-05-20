import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as WebElement

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

TestData products = findTestData('products')
prices = []

// iterate product search list
for (def currentProductNo : (0..products.getRowNumbers() - 1)) {
    String searchKeywords = products.internallyGetValue('search keywords', currentProductNo)

    String productOption = products.internallyGetValue('product option', currentProductNo)

    // search for product keywords
    WebUI.setText(findTestObject('1_relaxdays_shopping_cart/input_Suche_q'), searchKeywords)

    WebUI.sendKeys(findTestObject('1_relaxdays_shopping_cart/input_Suche_q'), Keys.chord(Keys.ENTER))

    WebUI.waitForPageLoad(10)

    boolean productMatches = false

    TestObject productItemsTo = findTestObject('1_relaxdays_shopping_cart/list_product_item')

    WebUI.scrollToElement(productItemsTo, 10)

    List<WebElement> productItems = WebUiCommonHelper.findWebElements(productItemsTo, 10)

    if (productItems != null) {
        Integer productItemsCount = productItems.size()

        if (productItemsCount > 0) {
            // search hits has been found
            String searchKeywordsNmzd = CustomKeywords.'common.utils.normalizeString'(searchKeywords).toLowerCase()

            searchKeywordsNmzdList = searchKeywordsNmzd.split('[\\W]')

            String productOptionNmzd = (productOption != null) && (productOption.size() > 0) ? CustomKeywords.'common.utils.normalizeString'(
                productOption).toLowerCase() : ''

            // iterate available products
            for (def productItemNo : (1..productItemsCount)) {
                TestObject productItem = findTestObject('1_relaxdays_shopping_cart/dyn_div_product-item', [('productItemNo') : productItemNo])

                TestObject productItemLink = findTestObject('1_relaxdays_shopping_cart/dyn_a_product-item-link', [('productItemNo') : productItemNo])

                String productItemLinkText = WebUI.getText(productItemLink)

                String productItemLinkTextNmzd = CustomKeywords.'common.utils.normalizeString'(productItemLinkText).toLowerCase().trim()

                List<WebElement> productItemOptElms = WebUiCommonHelper.findWebElements(findTestObject('1_relaxdays_shopping_cart/dyn_div_swatch-option_list', 
                        [('productItemNo') : productItemNo]), 10)

                productItemOptCount = productItemOptElms != null ? productItemOptElms.size() : 0

                if (searchKeywordsNmzdList.every({ 
                        productItemLinkTextNmzd.contains(it)
                    })) {
                    // search keywords are matching product description
                    KeywordUtil.logInfo("### Found matching product for search keywords '$searchKeywords': '$productItemLinkText'.")

                    if (productOptionNmzd != '') {
                        // product should match an option to be choosed
                        if (productItemOptCount > 0) {
                            // iterate available product options
                            for (def currentOptNo : (1..productItemOptCount)) {
                                TestObject productItemOptTo = findTestObject('1_relaxdays_shopping_cart/dyn_div_swatch-option', 
                                    [('productItemNo') : productItemNo, ('currentOptNo') : currentOptNo])

                                String currentOptLabelStr = WebUI.getAttribute(productItemOptTo, 'option-label')

                                String currentOptLabelNmzd = CustomKeywords.'common.utils.normalizeString'(currentOptLabelStr).toLowerCase().trim()

                                if (productOptionNmzd == currentOptLabelNmzd) {
                                    KeywordUtil.logInfo("### Expected product option '$productOption' is available as: '$currentOptLabelStr'.")
									
									List<String> optCssClasses = WebUI.getAttribute(productItemOptTo, 'class').split(/\s+/)
									if (optCssClasses.contains('selected')) {
										KeywordUtil.logInfo("### The desired product option is already pre-selected and should not be clicked to avoid being redirected to the product detail page.")
									}
									else {
										KeywordUtil.logInfo("### The desired product option is not pre-selected and must therefore be clicked.")
										WebUI.click(productItemOptTo)
									}

                                    productMatches = true

                                    break
                                }
                                
                                KeywordUtil.markWarning("### Expected option '$productOption' is not available for this product.") // end for (iterate available product options)
                            }
                        } else {
                            KeywordUtil.markWarning("### Expected option '$productOption' is not available due to no options beeing available.")
                        }
                        // product does not have to match an option to be choosed
                    } else {
                        KeywordUtil.logInfo('### Current product does match search keywords without the need to check any options.')

                        productMatches = true
                    }
                    
                    if (productMatches) {
						price = WebUI.getText(findTestObject('1_relaxdays_shopping_cart/dyn_div_price-box', [('productItemNo') : productItemNo]))
						prices << price
						
                        KeywordUtil.logInfo("### Put product '$productItemLinkText' into shopping cart and remember the price '${price}'.")

                        TestObject toCartButton = findTestObject('1_relaxdays_shopping_cart/dyn_button_tocart', [('productItemNo') : productItemNo])

                        WebUI.mouseOver(toCartButton)

                        WebUI.waitForElementClickable(toCartButton, 5)

                        WebUI.click(toCartButton)
						
						WebUI.delay(1)
						WebUI.takeScreenshot()
						WebUI.delay(1)
						
                        WebUI.waitForElementClickable(findTestObject('1_relaxdays_shopping_cart/button_modal-450_action-close'), 
                            5)

                        WebUI.click(findTestObject('1_relaxdays_shopping_cart/button_modal-450_action-close'))

                        break
                    }
                }
                
                KeywordUtil.logInfo("### Current product '$productItemLinkText' does not match search keywords '$searchKeywords' and, if applicable, expected option '$productOption'.") // end for (iterate available products)
            }
            
            if (!(productMatches)) {						
				WebUI.takeScreenshot()
                KeywordUtil.markWarning("### No product on first results page does match search keywords '$searchKeywords' and, if applicable, expected option '$productOption'.")
            }
        }
    } // end for (iterate product search list)
}

WebUI.callTestCase(findTestCase('1_test_shopping_flow/1_relaxdays_shopping_cart/2_check_subtotal'), [('prices') : prices], FailureHandling.STOP_ON_FAILURE)
