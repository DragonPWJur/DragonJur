package pages;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

abstract class BaseModal<TPage> extends BaseLocator<TPage> {
    private final Locator dialog = dialog();
    private final Locator cancelButton = exactButton("Cancel");
    private final Locator gotItButton = exactButton("Got it");
    private final Locator yesButton = exactButton("Yes");
    private final Locator skipButton = exactButton("Skip");
    private final Locator backButton = exactButton("Back");
    private final Locator okButton = exactButton("Ok");
    private final Locator nextButton = exactButton("Next");
    private final Locator weakestExamAreasHeader = dialog.locator("span");
    private final Locator weakestExamAreasMessage = dialog.getByText(Constants.YOU_HAVE_NOT_STUDIED_ENOUGH);

    BaseModal(Page page) {
        super(page);
    }

    public Locator getDialog() {

        return dialog;
    }

    @Step("Click 'Cansel' button to cancel dialog if visible.")
    public void cancelDialog() {
        if (dialog.isVisible() && cancelButton.isVisible()) {
            getPage().onDialog(Dialog::dismiss);
            cancelButton.click();
        }
    }

    @Step("Click 'Got It' button on dialog window.")
    public FlashcardsPackIDPage clickGotItButton() {
        gotItButton.click();

        return new FlashcardsPackIDPage(getPage());
    }

    @Step("Click 'Yes' button on dialog window if visible.")
    public TestTutorPage clickYesButton() {
        if (dialog.isVisible() && yesButton.isVisible()) {
            yesButton.click();
        }

        return new TestTutorPage(getPage());
    }

    @Step("Click 'Yes' button on dialog window if visible.")
    public CongratulationsModal clickYesToCongratulationButton() {
        if (dialog.isVisible() && yesButton.isVisible()) {
            yesButton.click();
        }

        return new CongratulationsModal(getPage());
    }

    @Step("Click 'Skip' button on dialog window if visible.")
    public TestResultPage clickSkipButton() {
        if (skipButton.isVisible()) {
            skipButton.click();
        } else {
            skipButton.waitFor();
            skipButton.click();
        }

        return new TestResultPage(getPage()).init();
    }

    public Locator getWeakestExamAreasHeader() {

        return weakestExamAreasHeader;
    }

    public Locator getWeakestExamAreasMessage() {

        return weakestExamAreasMessage;
    }

    @Step("Click 'Back' button on dialog window if visible.")
    protected TPage clickBackButton() {
        if (dialog.isVisible() && backButton.isVisible()) {
            backButton.click();
        }

        return init();
    }

    @Step("Click 'Next' button on dialog pop-up.")
    public void clickNextButton() {
        nextButton.click();
    }

    @Step("Click 'Ok' button on dialog pop-up.")
    public TestResultPage clickOkButton() {
        okButton.click();

        return new TestResultPage(getPage()).init();
    }
}
