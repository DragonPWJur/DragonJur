package pages;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

abstract class BaseModal<TPage> extends BaseLocator<TPage> {
    private final Locator dialog = dialog();
    private final Locator cancelButton = exactButton("Cancel");
    private final Locator gotItButton = exactButton("Got it");
    private final Locator yesButton = exactButton("Yes");
    private final Locator skipButton = exactButton("Skip");
    private final Locator backButton = exactButton("Back");
    private final Locator closeButton = exactButton("Close");
    private final Locator okButton = exactButton("Ok");
    private final Locator nextButton = exactButton("Next");
    private final Locator weakestExamAreasHeader = dialog.locator("span");
    private final Locator weakestExamAreasMessage = dialog.getByText(
            "You have not studied enough in order for us to calculate your weakest areas. Keep Studying \uD83D\uDE03"
    );

    BaseModal(Page page) {
        super(page);
    }

    protected Locator getDialog() {

        return dialog;
    }

    public Locator getCloseButton() {
        return closeButton;
    }

    @Step("Click 'Cansel' button to cancel dialog if visible.")
    protected void cancelDialog() {
        if (dialog.isVisible() && cancelButton.isVisible()) {
            getPage().onDialog(Dialog::dismiss);
            cancelButton.click();
        }
    }

    @Step("Click 'Got It' button on dialog window if visible.")
    public FlashcardsPackIDPage clickGotItButton() {
        gotItButton.click();

        return new FlashcardsPackIDPage(getPage());
    }

    @Step("Click 'Yes' button on dialog window if visible.")
    public TestTutorPage clickYesButton() {
        if(dialog.isVisible() && yesButton.isVisible()) {
            yesButton.click();
        }

        return new TestTutorPage(getPage());
    }

    @Step("Click 'Skip' button on dialog window if visible.")
    public TestResultPage clickSkipButton() {
        if(skipButton.isVisible()) {
            skipButton.click();
        } else {
            skipButton.waitFor();
            skipButton.click();
        }

        return new TestResultPage(getPage()).init();
    }

    @Step("Click 'Back' button on dialog window if visible.")
    protected TPage clickBackButton() {
        if(dialog.isVisible() && backButton.isVisible()) {
            backButton.click();
        }

        return init();
    }

    public TPage clickNextButton() {
        nextButton.click();

        return init();
    }

    public TPage clickOkButton() {
        okButton.click();

        return init();
    }
}
