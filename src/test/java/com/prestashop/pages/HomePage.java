package com.prestashop.pages;

import com.prestashop.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private final String url = "https://demo.prestashop.com/#/en/front";

    private final By clothesCategoryLink =
            By.xpath("(//a[contains(@href,'3-clothes') and contains(normalize-space(),'Clothes')])[1]");

    private final By clothesPageTitle =
            By.xpath("//h1[contains(normalize-space(),'Clothes')]");

    private final By searchInput =
            By.cssSelector("input[name='s']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openWebsite() {
        driver.get(url);
        switchToStoreFrameIfAvailable();
    }

    private void switchToStoreFrameIfAvailable() {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("framelive")));
        } catch (TimeoutException e) {
            // Sometimes the demo opens without iframe
        }
    }

    public void goToClothesCategory() {
        // ensure the link is clickable before attempting to click
        waitForClickable(clothesCategoryLink);
        clickUsingJavaScript(clothesCategoryLink);

        wait.until(ExpectedConditions.visibilityOfElementLocated(clothesPageTitle));
    }

    public void searchProduct(String keyword) {
        WebElement searchBox = waitForVisible(searchInput);
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);

        wait.until(driver ->
                driver.getCurrentUrl().toLowerCase().contains("search")
                        || driver.getPageSource().toLowerCase().contains(keyword.toLowerCase())
        );
    }
}