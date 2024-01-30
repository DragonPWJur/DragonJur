package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
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
}
