package com.prestashop.pages;

import com.prestashop.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private final By proceedToCheckoutButtons =
            By.xpath("//a[contains(normalize-space(.),'Proceed to checkout')] | //button[contains(normalize-space(.),'Proceed to checkout')]");

    private final By cartSubtotal =
            By.xpath("(//*[contains(normalize-space(),'items')]/following::*[contains(normalize-space(),'€')][1])[1]");

    private final By personalInformationTitle =
            By.xpath("//*[contains(normalize-space(),'Personal Information')]");

    private final By firstNameInput =
            By.cssSelector("#customer-form input[name='firstname']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private By cartItemByProductName(String productName) {
        return By.xpath("//div[contains(@class,'cart-item')][.//*[contains(normalize-space(),'" + productName + "')]]");
    }

    private By quantityByProductName(String productName) {
        return By.xpath("//div[contains(@class,'cart-item')][.//*[contains(normalize-space(),'" + productName + "')]]//input[@name='product-quantity-spin' or contains(@class,'js-cart-line-product-quantity')]");
    }

    public boolean isProductDisplayed(String productName) {
        return isDisplayed(cartItemByProductName(productName));
    }

    public String getProductQuantity(String productName) {
        return getAttribute(quantityByProductName(productName), "value");
    }

    public String getSubtotal() {
        return getText(cartSubtotal);
    }

    public void proceedToCheckout() {
        wait.until(driver -> {
            try {
                List<WebElement> buttons = driver.findElements(proceedToCheckoutButtons);

                for (WebElement button : buttons) {
                    if (button.isDisplayed() && button.getText().trim().contains("Proceed to checkout")) {

                        ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].scrollIntoView({block:'center'});",
                                button
                        );

                        ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].click();",
                                button
                        );

                        return true;
                    }
                }

                return false;

            } catch (StaleElementReferenceException e) {
                return false;
            }
        });

        wait.until(driver ->
                driver.getPageSource().contains("Personal Information")
                        || driver.findElements(personalInformationTitle).size() > 0
                        || driver.findElements(firstNameInput).size() > 0
        );
    }
}