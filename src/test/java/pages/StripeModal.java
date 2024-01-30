package pages;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utils.reports.LoggerUtils;

public final class StripeModal extends BaseModal<StripeModal> {
    private final Locator stripeDialog = dialog();
    private final Locator stripeModalHeader = text("Add a payment method");
    private final Locator stripeElement = locator("div.StripeElement");

    StripeModal(Page page) {

        super(page);
    }

    @Override
    public StripeModal init() {
        LoggerUtils.logInfo("On modal 'Stripe Payment'");

        return new StripeModal(getPage());
    }

    public Locator getStripeModalHeader() {
        if (!stripeDialog.isVisible()) {
            LoggerUtils.logInfo("Stripe Dialog is NOT visible");
            int count = 3;
            while (count > 0 && !getDialog().isVisible()) {
                waitForLocator(stripeDialog, 4000);
                count--;
                if (count == 0) {
                    LoggerUtils.logError("ERROR: Stripe modal is NOT visible");
                }
            }

            waitForLocator(stripeElement, 4000);
            if (stripeElement.isVisible()) {
                waitForLocator(stripeModalHeader, 4000);
            }
        }
        LoggerUtils.logInfo("Stripe Dialog is visible");
        waitForLocator(stripeElement, 4000);
        waitForLocator(stripeModalHeader, 4000);

        return stripeModalHeader;
    }

    public Locator getStripeElement() {

        return stripeElement;
    }

    private FrameLocator getStripePaymentInputFields() {
        waitForPageLoad();
        waitWithTimeout(20000);

        FrameLocator stripePaymentFrame = getPage().frameLocator("__privateStripeFrame85718");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment form is visible (" + stripePaymentFrame + ")");

        FrameLocator inputFields = stripePaymentFrame.frameLocator("div.p-Input']");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment input fields are visible (" + inputFields + ")");

        return inputFields;
    }

    @Step("Input Payment Card Number {paymentCardNumber}.")
    public StripeModal inputCreditCardNumber(String paymentCardNumber) {

        Locator cardNumberPlaceholder = getStripePaymentInputFields().getByPlaceholder("1234 1234 1234 1234");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment 'cardNumberPlaceholder' is visible (" + cardNumberPlaceholder + ")");

//        cardNumberPlaceholder.click();
//        cardNumberPlaceholder.fill(paymentCardNumber);

        LoggerUtils.logInfo("STRIPE FRAME: cardNumber input is not iterable because of Error: 'Unexpected token'");

        return new StripeModal(getPage());
    }
    @Step("Input Card Expiration Date {cardExpirationDate}.")
    public StripeModal inputCardExpirationDate(String cardExpirationDate) {

        Locator expirationDatePlaceholder = getStripePaymentInputFields().getByPlaceholder("MM / YY");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment 'expirationDatePlaceholder' is visible (" + expirationDatePlaceholder + ")");

//        expirationDatePlaceholder.click();
//        expirationDatePlaceholder.fill(cardExpirationDate);

        LoggerUtils.logInfo("STRIPE FRAME: expirationDate input is not iterable because of Error: 'Unexpected token'");

        return new StripeModal(getPage());
    }

    @Step("Input Card Expiration Date {cardExpirationDate}.")
    public StripeModal inputCardCVCDate(String cardCVCDate) {

        Locator cvcDatePlaceholder = getStripePaymentInputFields().getByPlaceholder("CVC");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment 'expirationDatePlaceholder' is visible (" + cvcDatePlaceholder + ")");

//        cvcDatePlaceholder.click();
//        cvcDatePlaceholder.fill(cardCVCDate);

        LoggerUtils.logInfo("STRIPE FRAME: expirationDate input is not iterable because of Error: 'Unexpected token'");

        return new StripeModal(getPage());
    }

    @Step("Input Card Expiration Date {cardExpirationDate}.")
    public StripeModal inputCardCountryDate(String cardCountryDate) {

        Locator countryDatePlaceholder = getStripePaymentInputFields().getByPlaceholder("United States");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment 'expirationDatePlaceholder' is visible (" + countryDatePlaceholder + ")");

//        countryDatePlaceholder.click();
//        countryDatePlaceholder.fill(cardCountryDate);

        LoggerUtils.logInfo("STRIPE FRAME: expirationDate input is not iterable because of Error: 'Unexpected token'");

        return new StripeModal(getPage());
    }

    @Step("Input Card Expiration Date {cardExpirationDate}.")
    public StripeModal inputCardZipCodeDate(String cardZipDate) {

        Locator zipDatePlaceholder = getStripePaymentInputFields().getByPlaceholder("12345");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment 'expirationDatePlaceholder' is visible (" + zipDatePlaceholder + ")");

//        zipDatePlaceholder.click();
//        zipDatePlaceholder.fill(cardZipDate);

        LoggerUtils.logInfo("STRIPE FRAME: expirationDate input is not iterable because of Error: 'Unexpected token'");

        return new StripeModal(getPage());
    }
}
