package com.prestashop.pages;

import com.prestashop.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    private final By cartModal = By.id("blockcart-modal");

    private final By continueShoppingButton =
            By.xpath("//button[contains(normalize-space(),'Continue shopping')]");

    private final By proceedToCheckoutButton =
            By.xpath("//a[contains(normalize-space(),'Proceed to checkout')]");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartModalDisplayed() {
        return isDisplayed(cartModal);
    }

    public void waitForCartModal() {
        waitForVisible(cartModal);
    }

    public void continueShopping() {
        click(continueShoppingButton);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cartModal));
    }

    public void proceedToCheckoutFromModal() {
        click(proceedToCheckoutButton);
    }
}