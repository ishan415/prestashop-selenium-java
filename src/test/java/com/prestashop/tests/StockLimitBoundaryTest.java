package com.prestashop.tests;

import com.prestashop.base.BaseTest;
import com.prestashop.pages.ClothesPage;
import com.prestashop.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StockLimitBoundaryTest extends BaseTest {

    @Test
    public void verifyQuantityAboveStockLimitIsRejected() {
        HomePage homePage = new HomePage(driver);
        ClothesPage clothesPage = new ClothesPage(driver);

        String productName = "Hummingbird printed t-shirt";

        homePage.openWebsite();
        homePage.goToClothesCategory();
        clothesPage.applyDiscountedFilter();

        clothesPage.setProductQuantity(productName, 298);
        clothesPage.addProductToCart(productName);

        Assert.assertTrue(
                clothesPage.isStockLimitErrorDisplayed(),
                "Stock limit validation message was not displayed for quantity 298."
        );
    }
}