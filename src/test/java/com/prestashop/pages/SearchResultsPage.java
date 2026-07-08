package com.prestashop.pages;

import com.prestashop.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage extends BasePage {

    private final By productCards =
            By.cssSelector("article.product-miniature, article.product");

    private final By noResultMessage =
            By.xpath("//*[contains(normalize-space(.),'No matches were found') " +
                    "or contains(normalize-space(.),'No results') " +
                    "or contains(normalize-space(.),'There are 0 products')]");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isNoResultDisplayed() {
        return driver.findElements(noResultMessage).size() > 0
                || driver.findElements(productCards).size() == 0;
    }
}