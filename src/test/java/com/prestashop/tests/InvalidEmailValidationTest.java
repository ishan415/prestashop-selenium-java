package com.prestashop.tests;

import com.prestashop.base.BaseTest;
import com.prestashop.pages.CartPage;
import com.prestashop.pages.CheckoutPage;
import com.prestashop.pages.ClothesPage;
import com.prestashop.pages.HomePage;
import com.prestashop.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InvalidEmailValidationTest extends BaseTest {

    @Test
    public void verifyInvalidEmailValidationInGuestCheckout() {
        CheckoutPage checkoutPage = openCheckoutPersonalInformationPage();

        checkoutPage.enterGuestPersonalInformation(
                "Ishan",
                "Premathilaka",
                "test1gmail.com",
                "06/23/2002"
        );

        Assert.assertTrue(
                checkoutPage.isEmailFieldInvalid(),
                "Invalid email was accepted as valid."
        );

        Assert.assertFalse(
                checkoutPage.getEmailValidationMessage().isEmpty(),
                "Email validation message was not generated."
        );
    }

    private CheckoutPage openCheckoutPersonalInformationPage() {
        HomePage homePage = new HomePage(driver);
        ClothesPage clothesPage = new ClothesPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        String productName = "Hummingbird printed t-shirt";

        homePage.openWebsite();
        homePage.goToClothesCategory();
        clothesPage.applyDiscountedFilter();

        clothesPage.setProductQuantity(productName, 1);
        clothesPage.addProductToCart(productName);

        productPage.waitForCartModal();
        productPage.proceedToCheckoutFromModal();

        cartPage.proceedToCheckout();

        return new CheckoutPage(driver);
    }
}