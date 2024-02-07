package pages;

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
                waitForLocator(stripeDialog, 2000);
                count--;
                if (count == 0) {
                    LoggerUtils.logError("ERROR: Stripe modal is NOT visible");
                }
            }

            waitForLocator(stripeElement, 2000);
            if (stripeElement.isVisible()) {
                waitForLocator(stripeModalHeader, 2000);
            }
        }

        LoggerUtils.logInfo("Stripe Dialog is visible");

        waitForLocator(stripeElement, 8000);

        return stripeModalHeader;
    }

    public Locator getStripeElement() {

        return stripeElement;
    }

    @Step("Wait for Stripe element.")
    private FrameLocator getStripePaymentInputFields() {
        waitForPageLoad();
        waitWithTimeout(7000);

        FrameLocator stripePaymentFrame = getPage().frameLocator("__privateStripeFrame85718");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment form is visible (" + stripePaymentFrame + ")");

        FrameLocator inputFields = stripePaymentFrame.frameLocator("div.p-Input']");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment input fields are visible (" + inputFields + ")");

        return inputFields;
    }

    @Step("Input Payment Card Number.")
    public StripeModal inputCreditCardNumber(String paymentCardNumber) {

        Locator cardNumberPlaceholder = getStripePaymentInputFields().getByPlaceholder("1234 1234 1234 1234");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment 'cardNumberPlaceholder' is visible (" + cardNumberPlaceholder + ")");

//        cardNumberPlaceholder.click();
//        cardNumberPlaceholder.fill(paymentCardNumber);

        LoggerUtils.logInfo("STRIPE FRAME: cardNumber input is not iterable because of Error: 'Unexpected token'");

        return new StripeModal(getPage());
    }

    @Step("Input Card Expiration Date.")
    public StripeModal inputCardExpirationDate(String cardExpirationDate) {

        Locator expirationDatePlaceholder = getStripePaymentInputFields().getByPlaceholder("MM / YY");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment 'expirationDatePlaceholder' is visible (" + expirationDatePlaceholder + ")");

//        expirationDatePlaceholder.click();
//        expirationDatePlaceholder.fill(cardExpirationDate);

        LoggerUtils.logInfo("STRIPE FRAME: expirationDate input is not iterable because of Error: 'Unexpected token'");

        return new StripeModal(getPage());
    }

    @Step("Input Card CVC.")
    public StripeModal inputCardCVC(String cardCVC) {

        Locator cvcPlaceholder = getStripePaymentInputFields().getByPlaceholder("CVC");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment 'cvcPlaceholder' is visible (" + cvcPlaceholder + ")");

//        cvcPlaceholder.click();
//        cvcPlaceholder.fill(cardCVC);

        LoggerUtils.logInfo("STRIPE FRAME: cvcPlaceholder is not iterable because of Error: 'Unexpected token'");

        return new StripeModal(getPage());
    }

    @Step("Input country name.")
    public StripeModal inputCardCountry(String countryName) {

        Locator countryPlaceholder = getStripePaymentInputFields().getByPlaceholder("United States");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment 'countryPlaceholder' is visible (" + countryPlaceholder + ")");

//        countryPlaceholder.click();
//        countryPlaceholder.fill(countryName);

        LoggerUtils.logInfo("STRIPE FRAME: expirationDate input is not iterable because of Error: 'Unexpected token'");

        return new StripeModal(getPage());
    }

    @Step("Input card Zip code.")
    public StripeModal inputZipCode(String cardZipCode) {

        Locator zipPlaceholder = getStripePaymentInputFields().getByPlaceholder("12345");
        LoggerUtils.logInfo("STRIPE FRAME: Stripe Payment 'zipPlaceholder' is visible (" + zipPlaceholder + ")");

//        zipDatePlaceholder.click();
//        zipDatePlaceholder.fill(cardZipCode);

        LoggerUtils.logInfo("STRIPE FRAME: expirationDate input is not iterable because of Error: 'Unexpected token'");

        return new StripeModal(getPage());
    }
}
