package com.prestashop.tests;

import com.prestashop.base.BaseTest;
import com.prestashop.pages.CartPage;
import com.prestashop.pages.CheckoutPage;
import com.prestashop.pages.ClothesPage;
import com.prestashop.pages.HomePage;
import com.prestashop.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TermsCheckboxValidationTest extends BaseTest {

    @Test
    public void verifyPlaceOrderDisabledWithoutAgreeingToTerms() {
        CheckoutPage checkoutPage = openPaymentPage();

        Assert.assertTrue(
                checkoutPage.isPaymentStepDisplayed(),
                "Payment page was not displayed."
        );

        checkoutPage.selectCashOnDeliveryOnly();

        Assert.assertFalse(
                checkoutPage.isPlaceOrderButtonEnabled(),
                "Place Order button should be disabled when terms are not accepted."
        );
    }

    private CheckoutPage openPaymentPage() {
        HomePage homePage = new HomePage(driver);
        ClothesPage clothesPage = new ClothesPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        String productName = "Hummingbird printed t-shirt";
        String uniqueEmail = "test" + System.currentTimeMillis() + "@gmail.com";

        homePage.openWebsite();
        homePage.goToClothesCategory();
        clothesPage.applyDiscountedFilter();

        clothesPage.setProductQuantity(productName, 1);
        clothesPage.addProductToCart(productName);

        productPage.waitForCartModal();
        productPage.proceedToCheckoutFromModal();

        cartPage.proceedToCheckout();

        checkoutPage.fillGuestPersonalInformation(
                "Ishan",
                "Premathilaka",
                uniqueEmail,
                "06/23/2002"
        );

        checkoutPage.fillAddressInformation(
                "No 10 Main Street",
                "Paris",
                "75001"
        );

        checkoutPage.continueWithShippingMethod();

        return checkoutPage;
    }
}