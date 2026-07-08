package com.prestashop.pages;

import com.prestashop.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage extends BasePage {

    private final By personalInformationTitle =
            By.xpath("//*[contains(normalize-space(),'Personal Information')]");

    private final By firstNameInput =
            By.cssSelector("#customer-form input[name='firstname']");

    private final By lastNameInput =
            By.cssSelector("#customer-form input[name='lastname']");

    private final By emailInput =
            By.cssSelector("#customer-form input[name='email']");

    private final By birthdayInput =
            By.cssSelector("#customer-form input[name='birthday']");

    private final By customerPrivacyCheckbox =
            By.cssSelector("#customer-form input[name='customer_privacy']");

    private final By gdprCheckbox =
            By.cssSelector("#customer-form input[name='psgdpr']");

    private final By personalInfoContinueButton =
            By.cssSelector("#customer-form button[name='continue']");

    private final By addressInput =
            By.cssSelector("#delivery-address input[name='address1']");

    private final By cityInput =
            By.cssSelector("#delivery-address input[name='city']");

    private final By postcodeInput =
            By.cssSelector("#delivery-address input[name='postcode']");

    private final By addressContinueButton =
            By.cssSelector("button[name='confirm-addresses']");

    private final By shippingContinueButton =
            By.xpath("//button[@name='confirmDeliveryOption' or contains(normalize-space(),'Continue to Payment')]");

    private final By paymentPageTitle =
            By.xpath("//h1[contains(normalize-space(),'Payment')]");

    private final By cashOnDeliveryRadio =
            By.xpath("//input[@name='payment-option' and @data-module-name='ps_cashondelivery']");

    private final By termsCheckbox =
            By.xpath("//input[@name='conditions_to_approve[terms-and-conditions]']");

    private final By placeOrderButton =
            By.xpath("//button[contains(normalize-space(),'Place Order') or contains(normalize-space(),'Place order')]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void waitForPersonalInformationPage() {
        wait.until(driver ->
                driver.getPageSource().contains("Personal Information")
                        || driver.findElements(personalInformationTitle).size() > 0
                        || driver.findElements(firstNameInput).size() > 0
        );
    }

    public void enterGuestPersonalInformation(String firstName, String lastName, String email, String birthday) {
        waitForPersonalInformationPage();

        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(emailInput, email);
        type(birthdayInput, birthday);

        clickUsingJavaScript(customerPrivacyCheckbox);
        clickUsingJavaScript(gdprCheckbox);
    }

    public void clickPersonalInformationContinue() {
        waitForPersonalInformationPage();

        scrollToElement(personalInfoContinueButton);
        clickUsingJavaScript(personalInfoContinueButton);
    }

    public void fillGuestPersonalInformation(String firstName, String lastName, String email, String birthday) {
        enterGuestPersonalInformation(firstName, lastName, email, birthday);
        clickPersonalInformationContinue();
    }

    public boolean isEmailFieldInvalid() {
        waitForPersonalInformationPage();

        WebElement email = waitForVisible(emailInput);

        Boolean isValid = (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].checkValidity();", email);

        return !isValid;
    }

    public String getEmailValidationMessage() {
        waitForPersonalInformationPage();

        WebElement email = waitForVisible(emailInput);

        return (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validationMessage;", email);
    }

    public void fillAddressInformation(String address, String city, String postcode) {
        type(addressInput, address);
        type(cityInput, city);
        type(postcodeInput, postcode);

        scrollToElement(addressContinueButton);
        clickUsingJavaScript(addressContinueButton);
    }

    public void continueWithShippingMethod() {
        scrollToElement(shippingContinueButton);
        clickUsingJavaScript(shippingContinueButton);

        waitForVisible(paymentPageTitle);
    }

    public boolean isPaymentStepDisplayed() {
        return isDisplayed(paymentPageTitle);
    }

    public void selectCashOnDeliveryOnly() {
        waitForVisible(paymentPageTitle);

        scrollToElement(cashOnDeliveryRadio);
        clickUsingJavaScript(cashOnDeliveryRadio);
    }

    public void selectCashOnDeliveryAndAgreeTerms() {
        selectCashOnDeliveryOnly();

        scrollToElement(termsCheckbox);
        clickUsingJavaScript(termsCheckbox);
    }

    public boolean isPlaceOrderButtonDisplayed() {
        return isDisplayed(placeOrderButton);
    }

    public boolean isPlaceOrderButtonEnabled() {
        return waitForVisible(placeOrderButton).isEnabled();
    }
}