package pages;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

abstract class BaseModal extends BaseLocator{

    private final Locator dialog = dialog();
    private final Locator cancelButton = exactButton("Cancel");
    private final Locator gotItButton = exactButton("Got it");
    private final Locator yesButton = exactButton("Yes");
    private final Locator skipButton = exactButton("Skip");
    private final Locator backButton = exactButton("Back");

    protected BaseModal(Page page) {
        super(page);
    }

    protected Locator getDialog() {

        return dialog;
    }

    @Step("Click 'Cansel' button to cancel dialog.")
    protected void cancelDialog() {
        if (dialog.isVisible() && cancelButton.isVisible()) {
            getPage().onDialog(Dialog::dismiss);
            cancelButton.click();
        }
    }

    @Step("Click 'Got' button on dialog window if visible.")
    public FlashcardsPackIDPage clickGotItButton() {
        if(dialog.isVisible() && gotItButton.isVisible())  {
            gotItButton.click();
        }

        return new FlashcardsPackIDPage(getPage());
    }

    @Step("Click 'Yes' button on dialog window.")
    public TestTutorPage clickYesButton() {
        if(dialog.isVisible() && yesButton.isVisible()) {
            yesButton.click();
        }

        return new TestTutorPage(getPage());
    }

    @Step("Click 'Skip' button on dialog window.")
    public TestResultPage clickSkipButton() {
        if(skipButton.isVisible()) {
            skipButton.click();
        } else {
            skipButton.waitFor();
            skipButton.click();
        }

        return new TestResultPage(getPage());
    }

    @Step("Click 'Back' button on dialog window.")
    protected TestResultPage clickBackButton() {
        if(dialog.isVisible() && backButton.isVisible()) {
            backButton.click();
        }

        return new TestResultPage(getPage());
    }
}
