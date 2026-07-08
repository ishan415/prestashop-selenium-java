package com.prestashop.tests;

import com.prestashop.base.BaseTest;
import com.prestashop.pages.ClothesPage;
import com.prestashop.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DiscountFilterTest extends BaseTest {

    @Test
    public void verifyDiscountFilterDisplaysDiscountedProducts() {
        HomePage homePage = new HomePage(driver);
        ClothesPage clothesPage = new ClothesPage(driver);

        homePage.openWebsite();
        homePage.goToClothesCategory();

        clothesPage.applyDiscountedFilter();

        Assert.assertTrue(
                clothesPage.isDiscountFilterApplied(),
                "Discount filter was not applied."
        );

        Assert.assertTrue(
                clothesPage.areDiscountBadgesDisplayed(),
                "Discount badges were not displayed after applying Discounted filter."
        );
    }
}