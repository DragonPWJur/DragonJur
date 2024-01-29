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
            System.out.println("1. stripeDialog is NOT visible");

            int count = 3;
            while (count > 0 && !getDialog().isVisible()) {
                System.out.println("2. stripeDialog is NOT visible");
                waitForLocator(stripeDialog, 4000);

                count --;
                if (count == 0) {
                    System.out.println("3. stripeDialog is NOT visible");
                }
            }
            waitForLocator(stripeElement, 4000);

            if (stripeElement.isVisible()) {
                System.out.println(stripeElement + " is visible - 1");
                waitForLocator(stripeModalHeader, 4000);
                System.out.println(stripeModalHeader);
            }
        }
        System.out.println("stripeDialog.isVisible()");
        waitForLocator(stripeElement, 4000);
        if (stripeElement.isVisible()) {
            System.out.println(stripeElement + "is visible - 2");
        }
        waitForLocator(stripeModalHeader, 4000);
        if (stripeModalHeader.isVisible()) {
            System.out.println(stripeModalHeader + " is visible - 3");
        }

        System.out.println(stripeModalHeader);

        return stripeModalHeader;
    }

    public Locator getStripeElement() {

        return stripeElement;
    }
}
