package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import java.util.List;

public final class PreconditionPage extends BasePage {

    private int flashcardsPackRandomIndex;
    private String flashcardsPackName;
    private String flashcardsPackCardsAmount;


    public int getFlashcardsPackRandomIndex() {
        return flashcardsPackRandomIndex;
    }

    public String getFlashcardsPackName() {
        return flashcardsPackName;
    }

    public String getFlashcardsPackCardsAmount() {
        return flashcardsPackCardsAmount;
    }

    public PreconditionPage(Page page) {
        super(page);
    }

    @Step("Precondition: Save the initial amount of 'Marked for re-checking' cards.")
    public String getInitialAmountOfCardsMarkedForRechecking() {
        String amountMarkedForRechecking = new HomePage(getPage())
                .clickFlashcardsMenu()
                .getAmountOfCardsMarkedForRechecking();

        new TestsPage(getPage()).clickHomeMenu();

        return amountMarkedForRechecking;
    }

//    public void endTest() {
//        new TestTutorPage(getPage())
//                .clickEndButton()
//                .clickYesButton()
//                .clickSkipButton()
//                .clickCloseTheTestButton();
//    }
//
    @Step("Precondition: Start random domain test with {number} question(s).")
    public TestTutorPage startRandomDomainTest(String number) {
        new HomePage(getPage())
                .clickHomeMenu()
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButtonIfNotActive()
                .clickRandomCheckbox()
                .inputNumberOfQuestions(number)
                .clickGenerateAndStartButton();

        return new TestTutorPage(getPage());
    }

    public void collectRandomFlashcardPackInfo() {
        FlashcardPacksPage flashcardPacksPage = new HomePage(getPage()).clickFlashcardsMenu();

        this.flashcardsPackRandomIndex =  flashcardPacksPage.getRandomPackIndex();
        this.flashcardsPackCardsAmount = flashcardPacksPage.getAmountOfCardsInPack();
        this.flashcardsPackName = flashcardPacksPage.getFlashcardsPackName();

        flashcardPacksPage.clickHomeMenu();
    }

    public List<Locator> getAllCheckBoxes() {

        return new HomePage(getPage()).getAllCheckboxes();
    }

    public boolean areAllCheckBoxesUnchecked() {
        return new HomePage(getPage()).areAllCheckBoxesUnchecked();
    }


//    public boolean checkIfListCheckBoxesIsNotEmptyAndOneIsChecked() {
//
//        HomePage homePage = new HomePage(getPage(), getPlaywright());
//        if (homePage.isListCheckBoxesNotEmpty()) {
//            homePage.clickRandomCheckBox();
//
//            return homePage.getListCheckedCheckBoxes().size() == 1;
//        }
//        return false;
//    }
}
