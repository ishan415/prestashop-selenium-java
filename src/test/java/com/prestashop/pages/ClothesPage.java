package com.prestashop.pages;

import com.prestashop.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClothesPage extends BasePage {

    private final By discountedFilterLink =
            By.xpath("(//a[contains(normalize-space(.),'Discounted')] | //label[contains(normalize-space(.),'Discounted')] | //span[contains(normalize-space(.),'Discounted')])[1]");

    private final By activeDiscountFilter =
            By.xpath("//*[contains(normalize-space(.),'Selections: Discounted') or contains(normalize-space(.),'Active filters')]");

    private final By discountBadge =
            By.xpath("//*[contains(normalize-space(.),'-20%')]");

    private final By stockLimitError =
            By.xpath("//*[contains(normalize-space(.),'You can only buy') and contains(normalize-space(.),'Please adjust')]");

    public ClothesPage(WebDriver driver) {
        super(driver);
    }

    public void applyDiscountedFilter() {
        scrollToElement(discountedFilterLink);
        clickUsingJavaScript(discountedFilterLink);

        wait.until(driver ->
                driver.getCurrentUrl().contains("q=Selections-Discounted")
                        || driver.getPageSource().contains("Selections: Discounted")
                        || driver.findElements(activeDiscountFilter).size() > 0
                        || driver.findElements(discountBadge).size() > 0
        );
    }

    public boolean isDiscountFilterApplied() {
        return driver.getCurrentUrl().contains("q=Selections-Discounted")
                || driver.getPageSource().contains("Selections: Discounted")
                || driver.findElements(activeDiscountFilter).size() > 0
                || driver.findElements(discountBadge).size() > 0;
    }

    public boolean areDiscountBadgesDisplayed() {
        return driver.findElements(discountBadge).size() >= 1;
    }

    private By productCard(String productName) {
        return By.xpath("//article[contains(@class,'product')][.//a[contains(normalize-space(.),'" + productName + "')]]");
    }

    private By productQuantityInput(String productName) {
        return By.xpath("//article[contains(@class,'product')][.//a[contains(normalize-space(.),'" + productName + "')]]//input[@name='qty']");
    }

    private By productAddToCartButton(String productName) {
        return By.xpath("//article[contains(@class,'product')][.//a[contains(normalize-space(.),'" + productName + "')]]//button[contains(@class,'add-to-cart') or @data-button-action='add-to-cart']");
    }

    public boolean isProductDisplayed(String productName) {
        return isDisplayed(productCard(productName));
    }

    public void setProductQuantity(String productName, int quantity) {
        By quantityLocator = productQuantityInput(productName);

        wait.until(driver -> {
            try {
                WebElement qtyInput = driver.findElement(quantityLocator);

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        qtyInput
                );

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[1];" +
                                "arguments[0].setAttribute('value', arguments[1]);" +
                                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        qtyInput,
                        String.valueOf(quantity)
                );

                String actualValue = (String) ((JavascriptExecutor) driver).executeScript(
                        "return arguments[0].value;",
                        qtyInput
                );
                return actualValue.equals(String.valueOf(quantity));

            } catch (StaleElementReferenceException | NoSuchElementException e) {
                return false;
            }
        });
    }

    public String getProductQuantity(String productName) {
        By quantityLocator = productQuantityInput(productName);

        return wait.until(driver -> {
            try {
                WebElement qtyInput = driver.findElement(quantityLocator);
                return qtyInput.getDomProperty("value");
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                return null;
            }
        });
    }

    public void addProductToCart(String productName) {
        By addToCartLocator = productAddToCartButton(productName);

        wait.until(driver -> {
            try {
                WebElement addToCartButton = driver.findElement(addToCartLocator);

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        addToCartButton
                );

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();",
                        addToCartButton
                );

                return true;

            } catch (StaleElementReferenceException | NoSuchElementException e) {
                return false;
            }
        });
    }

    public boolean isStockLimitErrorDisplayed() {
        return isDisplayed(stockLimitError);
    }
}