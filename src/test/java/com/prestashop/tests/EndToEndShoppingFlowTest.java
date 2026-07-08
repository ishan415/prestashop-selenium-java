package com.prestashop.tests;

import com.prestashop.base.BaseTest;
import com.prestashop.pages.CartPage;
import com.prestashop.pages.CheckoutPage;
import com.prestashop.pages.ClothesPage;
import com.prestashop.pages.HomePage;
import com.prestashop.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EndToEndShoppingFlowTest extends BaseTest {

    @Test
    public void verifyFullShoppingFlowWithTwoDiscountedProducts() {

        HomePage homePage = new HomePage(driver);
        ClothesPage clothesPage = new ClothesPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        String productOne = "Hummingbird printed t-shirt";
        String productTwo = "Hummingbird printed sweater";

        // Open website
        homePage.openWebsite();

        // Go to Clothes category
        homePage.goToClothesCategory();

        // Apply Discounted filter
        clothesPage.applyDiscountedFilter();

        Assert.assertTrue(
                clothesPage.isDiscountFilterApplied(),
                "Discount filter was not applied successfully."
        );

        Assert.assertTrue(
                clothesPage.isProductDisplayed(productOne),
                "T-shirt is not displayed after applying Discounted filter."
        );

        Assert.assertTrue(
                clothesPage.isProductDisplayed(productTwo),
                "Sweater is not displayed after applying Discounted filter."
        );

        // Increase t-shirt quantity to 3 using plus button
        clothesPage.setProductQuantity(productOne, 3);

        Assert.assertEquals(
                clothesPage.getProductQuantity(productOne),
                "3",
                "T-shirt quantity was not increased to 3."
        );

        // Add t-shirt to cart
        clothesPage.addProductToCart(productOne);
        productPage.waitForCartModal();

        Assert.assertTrue(
                productPage.isCartModalDisplayed(),
                "Cart modal was not displayed after adding t-shirt."
        );

        // Continue shopping
        productPage.continueShopping();

        // Increase sweater quantity to 4 using plus button
        clothesPage.setProductQuantity(productTwo, 4);

        Assert.assertEquals(
                clothesPage.getProductQuantity(productTwo),
                "4",
                "Sweater quantity was not increased to 4."
        );

        // Add sweater to cart
        clothesPage.addProductToCart(productTwo);
        productPage.waitForCartModal();

        Assert.assertTrue(
                productPage.isCartModalDisplayed(),
                "Cart modal was not displayed after adding sweater."
        );

        // Go to cart page
        productPage.proceedToCheckoutFromModal();

        // Verify products in cart
        Assert.assertTrue(
                cartPage.isProductDisplayed(productOne),
                "T-shirt is not displayed in the cart."
        );

        Assert.assertTrue(
                cartPage.isProductDisplayed(productTwo),
                "Sweater is not displayed in the cart."
        );

        // Verify quantities in cart
        Assert.assertEquals(
                cartPage.getProductQuantity(productOne),
                "3",
                "T-shirt quantity is incorrect in cart."
        );

        Assert.assertEquals(
                cartPage.getProductQuantity(productTwo),
                "4",
                "Sweater quantity is incorrect in cart."
        );

        // Verify subtotal
        Assert.assertTrue(
                cartPage.getSubtotal().contains("206.69"),
                "Cart subtotal is incorrect. Actual subtotal: " + cartPage.getSubtotal()
        );

        // Proceed to checkout
        cartPage.proceedToCheckout();

        // Fill guest personal information
        checkoutPage.fillGuestPersonalInformation(
                "Ishan",
                "Premathilaka",
                "test1@gmail.com",
                "06/23/2002"
        );

        // Fill address
        checkoutPage.fillAddressInformation(
                "No 10 Main Street",
                "Paris",
                "75001"
        );

        // Continue shipping
        checkoutPage.continueWithShippingMethod();

        // Select Cash on Delivery and agree terms
        checkoutPage.selectCashOnDeliveryAndAgreeTerms();

        // Verify payment/order summary page reached
        Assert.assertTrue(
                checkoutPage.isPlaceOrderButtonDisplayed(),
                "Payment/order summary page was not reached."
        );

        
    }
}