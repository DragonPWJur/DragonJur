package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FlashcardsPackIDPage;
import pages.HomePage;
import pages.PreconditionPage;
import tests.helpers.TestData;
import tests.helpers.TestUtils;
import utils.runner.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class FlashcardPacksTest extends BaseTest {

    @Test(
            testName = "TC1349-01 User can mark cards for re-checking (“Add to flashcards”).",
            description = "LMS-1349 https://app.qase.io/plan/LMS/1?case=1349"
    )
    public void testAddToFlashCard() {
        PreconditionPage precondition = new PreconditionPage(getPage());

        final String initialCardsAmount = precondition.getInitialAmountOfCardsMarkedForRechecking();
        final String expectedCardsAmount = TestUtils.add(initialCardsAmount, 1);
        final String actualCardsAmount =
                precondition
                        .startRandomDomainTest(TestData.ONE_QUESTION)
                        .clickAddToFlashCardButton()
                        .clickEndButton()
                        .clickYesButton()
                        .clickSkipButton()
                        .clickCloseTheTestButton()
                        .clickFlashcardsMenu()
                        .getAmountOfCardsMarkedForRechecking();

        Assert.assertEquals(
                actualCardsAmount, expectedCardsAmount,
                "The expected amount marked for re-checking cards (" + expectedCardsAmount
                        + ") is NOT equal to the actual amount (" + actualCardsAmount + ")."
        );
    }

    @Test(
            testName = "TC1367-01 Verify the user can start a flashcard pack.",
            description = "LMS-1367 https://app.qase.io/plan/LMS/1?case=1367"
    )
    public void testStartRandomFlashCardPack() {
        PreconditionPage precondition = new PreconditionPage(getPage());

        precondition.collectRandomFlashcardPackInfo();
        final int packIndex = precondition.getFlashcardsPackRandomIndex();
        final String packName = precondition.getFlashcardsPackName();
        final String cardsInPackAmount = precondition.getFlashcardsPackCardsAmount();
        final String expectedUrlPart = ProjectProperties.BASE_URL + TestData.FLASHCARDS_PACK_ID_END_POINT;

        FlashcardsPackIDPage flashcardsPackIDPage = new HomePage(getPage())
                        .clickFlashcardsMenu()
                        .clickNthFlashcardPack(packIndex)
                        .clickGotItButton();
        final String pageUrl = getPage().url();

        Assert.assertTrue(
                pageUrl.contains(expectedUrlPart),
                "The page URL " + pageUrl + " does NOT contain: " + expectedUrlPart
        );
        assertThat(flashcardsPackIDPage.getPackName()).hasText(packName);
        assertThat(flashcardsPackIDPage.cardsTotalAmount(cardsInPackAmount)).isVisible();
        assertThat(flashcardsPackIDPage.getQuestionHeading()).isVisible();
        assertThat(flashcardsPackIDPage.getShowAnswerButton()).isVisible();
    }

    @Test(
            testName = "TC1368-01 Flashcard turned when clicking the “Show Answer” button.",
            description = "LMS-1368 https://app.qase.io/plan/LMS/1?case=1368"
    )
    public void testFlashCardTurnedAfterClickingShowAnswerButton() {
        PreconditionPage precondition = new PreconditionPage(getPage());

        precondition.collectRandomFlashcardPackInfo();
        final int packIndex = precondition.getFlashcardsPackRandomIndex();

        FlashcardsPackIDPage flashcardsPackIDPage = new HomePage(getPage())
                .clickFlashcardsMenu()
                .clickNthFlashcardPack(packIndex)
                .clickGotItButton();

        assertThat(flashcardsPackIDPage.getQuestionHeading()).isVisible();
        assertThat(flashcardsPackIDPage.getAnswerHeading()).not().isVisible();
        assertThat(flashcardsPackIDPage.getNoButton()).not().isVisible();
        assertThat(flashcardsPackIDPage.getKindaButton()).not().isVisible();
        assertThat(flashcardsPackIDPage.getYesButton()).not().isVisible();
        assertThat(flashcardsPackIDPage.getShowAnswerButton()).isVisible();

        flashcardsPackIDPage.clickShowAnswerButton();

        assertThat(flashcardsPackIDPage.getQuestionHeading()).isVisible();
        assertThat(flashcardsPackIDPage.getAnswerHeading()).isVisible();
        assertThat(flashcardsPackIDPage.getNoButton()).isVisible();
        assertThat(flashcardsPackIDPage.getKindaButton()).isVisible();
        assertThat(flashcardsPackIDPage.getYesButton()).isVisible();
        assertThat(flashcardsPackIDPage.getShowAnswerButton()).not().isVisible();
    }

    @Test(
            testName = "TC1368-02 Possibility to leave a “Yes” mark.",
            description = "LMS-1368 https://app.qase.io/plan/LMS/1?case=1368"
    )
    public void testUserCanLeaveYesMark() {
        PreconditionPage precondition = new PreconditionPage(getPage());

        precondition.collectRandomFlashcardPackInfo();
        final int packIndex = precondition.getFlashcardsPackRandomIndex();

        FlashcardsPackIDPage flashcardsPackIDPage = new HomePage(getPage())
                .clickFlashcardsMenu()
                .clickNthFlashcardPack(packIndex)
                .clickGotItButton()
                .clickShowAnswerButton();
        final String yesCardsAmountBeforeClick = flashcardsPackIDPage.getYesCardsAmount();
        final String expectedYesCardsAmount = TestUtils.add(yesCardsAmountBeforeClick, 1);

        assertThat(flashcardsPackIDPage.getResetResultsButton()).not().isVisible();

        flashcardsPackIDPage.clickYesMarkButton();
        final String yesCardsAmountAfterClick = flashcardsPackIDPage.getYesCardsAmount();

        Assert.assertEquals(
                yesCardsAmountAfterClick, expectedYesCardsAmount,
                "Expected 'Yes Mark' number to increase by 1 after clicking the 'Yes Mark' button."
        );
        assertThat(flashcardsPackIDPage.getResetResultsButton()).isVisible();
    }
}
