package com.prestashop.tests;

import com.prestashop.base.BaseTest;
import com.prestashop.pages.HomePage;
import com.prestashop.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UnavailableProductSearchTest extends BaseTest {

    @Test
    public void verifyUnavailableProductSearchShowsNoResults() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);

        homePage.openWebsite();
        homePage.searchProduct("xyznotavailable123");

        Assert.assertTrue(
                searchResultsPage.isNoResultDisplayed(),
                "No result message was not displayed for unavailable product search."
        );
    }
}