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
    private final Locator kindaButton = exactButton("Kinda");
    private final Locator noButton = exactButton("No");
    private final Locator skipButton = exactButton("Skip");
    private final Locator backButton = exactButton("Back");
    private final Locator okButton = exactButton("Ok");
    private final Locator nextButton = exactButton("Next");

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

    @Step("Click 'Yes' button on dialog window.")
    public FlashcardsPackIDPage clickYesToFlashcardsModalButton() {
        if (dialog.isVisible() && yesButton.isVisible()) {
            yesButton.click();
        }

        return new FlashcardsPackIDPage(getPage());
    }

    @Step("Click 'Kinda' button on dialog window.")
    public FlashcardsPackIDPage clickKindaToFlashcardsModalButton() {
        if (dialog.isVisible() && kindaButton.isVisible()) {
            kindaButton.click();
        }

        return new FlashcardsPackIDPage(getPage());
    }

    @Step("Click 'No' button on dialog window.")
    public FlashcardsPackIDPage clickNoToFlashcardsModalButton() {
        if (dialog.isVisible() && noButton.isVisible()) {
            noButton.click();
        }

        return new FlashcardsPackIDPage(getPage());
    }

    @Step("Click 'Yes' button on dialog window if visible.")
    public CongratulationsModal clickYesToCongratulationButton() {
        if (dialog.isVisible() && yesButton.isVisible()) {
            yesButton.click();
        }

        return new CongratulationsModal(getPage());
    }

    @Step("Click 'Yes' button on dialog window if visible.")
    public FlashcardPacksPage clickYesToFlashcardsButton() {
        yesButton.click();

        return new FlashcardPacksPage(getPage());
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

    @Step("Click 'Back' button on dialog window if visible.")
    protected TPage clickBackButton() {
        if (dialog.isVisible() && backButton.isVisible()) {
            backButton.click();
        }

        return init();
    }

    @Step("Click 'Next' button on dialog pop-up.")
    public CongratulationsModal clickNextButton() {
        nextButton.click();

        return new CongratulationsModal(getPage());
    }

    @Step("Click 'Ok' button on dialog pop-up.")
    public TestResultPage clickOkButton() {
        okButton.click();

        return new TestResultPage(getPage()).init();
    }

    @Step("Click 'Ok' button on dialog pop-up.")
    public FlashcardPacksPage clickOkToFlashcardsButton() {
        okButton.click();

        return new FlashcardPacksPage(getPage()).init();
    }
}
