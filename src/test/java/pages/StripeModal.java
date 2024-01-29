package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class StripeModal extends BaseModal<StripeModal>{
    private final Locator stripeModalHeader = text("Add a payment method");
    private final Locator stripeElement = locator("div.StripeElement");

    StripeModal(Page page) {

        super(page);
    }

    @Override
    public StripeModal init() {

        return new StripeModal(getPage());
    }

    public Locator getStripeModalHeader() {
        waitForLocator(stripeModalHeader, 2000);

        return stripeModalHeader;
    }

    public Locator getStripeElement(){

        return stripeElement;
    }
}
