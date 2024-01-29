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
        if (!getDialog().isVisible()) {
            waitForLocator(getDialog(), 4000);
            System.out.println(stripeElement);
            if (getDialog().isVisible()) {
                waitForLocator(stripeModalHeader, 4000);
                System.out.println(stripeModalHeader);
            }
        }
        waitForLocator(stripeModalHeader, 4000);
        System.out.println(stripeElement);
        System.out.println(stripeModalHeader);

        return stripeModalHeader;
    }

    public Locator getStripeElement() {

        return stripeElement;
    }
}
