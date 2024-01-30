package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class AddNewCourseModal extends BaseModal<AddNewCourseModal> {
    private final Locator lifeTimeButton = button("Life time");
    private final Locator chooseAProductHeading = locator(".ReactModalPortal div:has(> div > svg) > span");
    private final Locator goldHeader = exactText("Gold");
    private final Locator silverHeader = exactText("Silver");
    private final Locator bronzeHeader = exactText("Bronze");
    private final Locator purchaseButton = exactButton("Purchase");

    AddNewCourseModal(Page page) {

        super(page);
    }

    @Override
    public AddNewCourseModal init() {

        return new AddNewCourseModal(getPage());
    }

    public AddNewCourseModal clickLifeTimeButton() {
        lifeTimeButton.click();

        return init();
    }

    public Locator getChooseAProductHeading() {

        return chooseAProductHeading;
    }

    public Locator getLifeTimeButton() {

        return lifeTimeButton;
    }

    public Locator getGoldHeading() {

        return goldHeader;
    }

    public Locator getSilverHeading() {

        return silverHeader;
    }

    public Locator getBronzeHeading() {

        return bronzeHeader;
    }

    public Locator getPurchaseButton() {

        return purchaseButton;
    }

    public StripeModal clickPurchaseButton() {
        purchaseButton.click();

        return new StripeModal(getPage()).init();
    }
}
