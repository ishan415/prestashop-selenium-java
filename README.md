# PrestaShop Selenium Automation Assignment

This project contains Selenium WebDriver automation tests for the PrestaShop demo e-commerce website.

Website under test:  
https://demo.prestashop.com/#/en/front

The automation framework is developed using Java, Selenium WebDriver, TestNG, and Maven. The project follows the Page Object Model design pattern, where page locators and actions are separated from test methods.

---

## Tools and Technologies Used

- Java 17
- Selenium WebDriver
- TestNG
- Maven
- Google Chrome
- VS Code
- Page Object Model

---

## Project Structure

```text
prestashop-selenium-java
│
├── pom.xml
├── README.md
│
└── src
    └── test
        └── java
            └── com
                └── prestashop
                    ├── base
                    │   ├── BasePage.java
                    │   └── BaseTest.java
                    │
                    ├── pages
                    │   ├── HomePage.java
                    │   ├── ClothesPage.java
                    │   ├── ProductPage.java
                    │   ├── CartPage.java
                    │   ├── CheckoutPage.java
                    │   └── SearchResultsPage.java
                    │
                    └── tests
                        ├── EndToEndShoppingFlowTest.java
                        ├── DiscountFilterTest.java
                        ├── AddSingleProductToCartTest.java
                        ├── MinimumQuantityBoundaryTest.java
                        ├── MaximumValidQuantityBoundaryTest.java
                        ├── CartSubtotalSingleProductTest.java
                        ├── UnavailableProductSearchTest.java
                        ├── StockLimitBoundaryTest.java
                        ├── InvalidEmailValidationTest.java
                        └── TermsCheckboxValidationTest.java
```

---

## How to Run the Tests

### Prerequisites

Before running the tests, make sure the following are installed:

- Java JDK 17
- Maven
- Google Chrome
- VS Code or any Java IDE

Check Java installation:

```bash
java -version
```

Check Maven installation:

```bash
mvn -version
```

---

## Run All Tests

Open the terminal from the project root folder where `pom.xml` is located and run:

```bash
mvn test
```

---

## Run a Single Test Class

Example:

```bash
mvn test -Dtest=EndToEndShoppingFlowTest
```

Other examples:

```bash
mvn test -Dtest=DiscountFilterTest
mvn test -Dtest=MinimumQuantityBoundaryTest
mvn test -Dtest=UnavailableProductSearchTest
mvn test -Dtest=StockLimitBoundaryTest
```

---

## Automated Test Coverage

### 1. EndToEndShoppingFlowTest

This test covers the full shopping flow.

Covered steps:

- Open the PrestaShop demo store
- Navigate to the Clothes category
- Apply the Discounted filter
- Add `Hummingbird printed t-shirt` with quantity 3
- Continue shopping
- Add `Hummingbird printed sweater` with quantity 4
- Go to the cart page
- Verify both products are displayed in the cart
- Verify product quantities
- Verify cart subtotal
- Proceed to guest checkout
- Fill personal information
- Fill address details
- Continue through the shipping step
- Select Cash on Delivery
- Agree to terms
- Verify that the Payment / Order Summary page is reached

Note: This test stops at the Payment / Order Summary page and verifies that the Place Order button is displayed. It does not click the final Place Order button to avoid completing the order submission on the live demo website.

---

### 2. DiscountFilterTest

This test verifies the Discounted filter.

Covered steps:

- Open the PrestaShop demo store
- Navigate to the Clothes category
- Apply the Discounted filter
- Verify that the filter is applied
- Verify that discounted products are displayed

---

### 3. AddSingleProductToCartTest

This test verifies that a single product can be added to the cart.

Covered steps:

- Open the PrestaShop demo store
- Navigate to the Clothes category
- Apply the Discounted filter
- Select `Hummingbird printed t-shirt`
- Set quantity to 1
- Add the product to the cart
- Verify that the cart confirmation modal is displayed

---

### 4. MinimumQuantityBoundaryTest

This test verifies the minimum valid quantity boundary.

Covered steps:

- Open the PrestaShop demo store
- Navigate to the Clothes category
- Apply the Discounted filter
- Set product quantity to 1
- Add the product to the cart
- Verify that the product is displayed in the cart
- Verify that the cart quantity is 1

Boundary value covered:

```text
Quantity = 1
```

---

### 5. MaximumValidQuantityBoundaryTest

This test verifies the maximum valid quantity boundary.

Covered steps:

- Open the PrestaShop demo store
- Navigate to the Clothes category
- Apply the Discounted filter
- Set product quantity to 297
- Add the product to the cart
- Verify that the cart confirmation modal is displayed

Boundary value covered:

```text
Quantity = 297
```

---

### 6. CartSubtotalSingleProductTest

This test verifies cart subtotal calculation for a single product.

Covered steps:

- Open the PrestaShop demo store
- Navigate to the Clothes category
- Apply the Discounted filter
- Add one `Hummingbird printed t-shirt` to the cart
- Open the cart page
- Verify that the product is displayed
- Verify that the quantity is correct
- Verify that the cart subtotal is correct

---

### 7. UnavailableProductSearchTest

This test verifies the behavior when searching for an unavailable product.

Covered steps:

- Open the PrestaShop demo store
- Search for an unavailable product keyword
- Verify that no matching product result is displayed

Negative / edge case covered:

```text
Search keyword = xyznotavailable123
```

---

### 8. StockLimitBoundaryTest

This test verifies stock limit validation.

Covered steps:

- Open the PrestaShop demo store
- Navigate to the Clothes category
- Apply the Discounted filter
- Enter a quantity above the available stock limit
- Attempt to add the product to the cart
- Verify that the system displays a stock limit validation message

Edge case covered:

```text
Quantity above available stock
```

Note: This test may be flaky because the available stock quantity on the live demo website can change.

---

### 9. InvalidEmailValidationTest

This test verifies invalid email validation during guest checkout.

Covered steps:

- Add a product to the cart
- Proceed to checkout
- Enter invalid email format
- Verify that the email field is treated as invalid

Negative case covered:

```text
Invalid email = test1gmail.com
```

Note: This test may be affected by checkout page loading behavior on the live demo website.

---

### 10. TermsCheckboxValidationTest

This test verifies that the user cannot place an order without agreeing to the terms of service.

Covered steps:

- Add a product to the cart
- Proceed to checkout
- Fill guest personal information
- Fill address details
- Continue to the payment step
- Select Cash on Delivery
- Do not select the terms of service checkbox
- Verify that the Place Order action cannot be completed without accepting terms

Negative case covered:

```text
Terms and conditions checkbox not selected
```

Note: This test may be affected by checkout page loading behavior on the live demo website.

---

## Assertions Used

The automation tests include assertions to verify real outcomes, such as:

- Discount filter is applied
- Discounted products are displayed
- Product is added to cart
- Cart confirmation modal is displayed
- Product is displayed in the cart
- Product quantity is correct
- Cart subtotal is correct
- Payment page is displayed
- Place Order button is displayed
- Invalid email is rejected
- Stock limit validation message is displayed

---

## Wait Strategy

The framework uses explicit waits with `WebDriverWait`.

The tests wait for:

- Elements to be visible
- Elements to be clickable
- Elements to be present in the DOM
- Cart modal to appear
- Checkout steps to load
- Payment page to load

No hard-coded `Thread.sleep()` waits are used.

---

## Known Limitations and Flaky Areas

The PrestaShop demo website is a live public demo site. Because of this, some tests may behave differently between executions.

Known limitations:

- Product stock quantities may change or reset because the site is public.
- Exact stock boundary values such as 297 or 298 may become unstable if the available stock changes.
- Checkout pages may load slowly depending on demo site performance.
- Some checkout-related tests may be flaky because of dynamic rendering, iframe loading, and session/cart updates.
- The stock limit validation message may appear differently depending on the current product stock and cart state.
- Browser warnings related to SLF4J or Chrome CDP version may appear in the terminal, but they do not stop the tests from running.

For more stable stock limit validation, a clearly invalid quantity such as 500 or 999 can be used instead of a value close to the current stock limit.

---

## Important Note About Order Submission

The full end-to-end automation test reaches the Payment / Order Summary page and verifies that the Place Order button is displayed.

The test does not click the final Place Order button because the website is a live demo store and the assignment mentions not to complete real payment steps.

---

## Summary

This project demonstrates:

- Selenium WebDriver automation using Java
- TestNG test execution
- Maven project setup
- Page Object Model structure
- Explicit waits
- Assertions
- End-to-end e-commerce flow automation
- Filter validation
- Cart validation
- Boundary testing
- Negative testing