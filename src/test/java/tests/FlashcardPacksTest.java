package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.FlashcardsPackIDPage;
import pages.HomePage;
import pages.PreconditionPage;
import tests.helpers.TestData;
import tests.helpers.TestUtils;
import utils.runner.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class FlashcardPacksTest extends BaseTest {

    @Ignore
    @Test(
            testName = "LMS-1349 Возможность для юзера добавлять во флешкарточки. https://app.qase.io/plan/LMS/1?case=1349",
            description = "TC1349-01 - User can mark cards for re-checking (“Add to flashcards”)."
    )
    @Description("Objective: To confirm the user's ability to mark cards for re-checking"
            + " and verify the increase in the count within the 'Marked for re-checking' flashcard section.")
    @Story("Flashcards")
    @TmsLink("rr1rrsnttopz")
    public void testAddToFlashCard() {

        PreconditionPage precondition = new PreconditionPage(getPage()).init();
        precondition
                .collectRandomFlashcardPackInfo();

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
                "If FAIL: The expected amount marked for re-checking cards (" + expectedCardsAmount
                        + ") is NOT equal to the actual amount (" + actualCardsAmount + ").\n"
        );
    }

    @Test(
            testName = "LMS-1367 Запуск стопки. https://app.qase.io/plan/LMS/1?case=1367",
            description = "TC1367-01 - Verify the user can start a flashcard pack."
    )
    @Description("Objective: To confirm that users can successfully initiate a flashcard pack for studying.")
    @Story("Flashcards")
    @TmsLink("5w1lt3377dz3")
    public void testStartRandomFlashCardPack() {

        PreconditionPage precondition = new PreconditionPage(getPage()).init();
        precondition
                .collectRandomFlashcardPackInfo();

        final int packIndex = precondition.getFlashcardsPackRandomIndex();
        final String packName = precondition.getFlashcardsPackName();
        final String cardsInPackAmount = precondition.getFlashcardsPackCardsAmount();
        final String expectedUrlPart = ProjectProperties.BASE_URL + TestData.FLASHCARDS_PACK_ID_END_POINT;

        FlashcardsPackIDPage flashcardsPackIDPage =
                new HomePage(getPage()).init()
                        .clickFlashcardsMenu()
                        .clickNthFlashcardPack(packIndex)
                        .clickGotItButton();

        final String actualPageUrl = getPage().url();
        final String actualVisiblePackName = flashcardsPackIDPage.getPackName();
        final Locator cardsTotalText = flashcardsPackIDPage.cardsTotalText(cardsInPackAmount);
        final Locator questionHeading = flashcardsPackIDPage.getQuestionHeading();
        final Locator showAnswerButton = flashcardsPackIDPage.getShowAnswerButton();

        Assert.assertTrue(
                actualPageUrl.contains(expectedUrlPart),
                "If FAIL: The page URL " + actualPageUrl + " does NOT contains the url part: " + expectedUrlPart + ".\n"
        );
        Assert.assertTrue(
                packName.contains(actualVisiblePackName),
                "If FAIL: The expected pack name " + packName + "does NOT contains actual pack name " + actualVisiblePackName + ".\n"
        );
        assertThat(cardsTotalText).isVisible();
        assertThat(questionHeading).isVisible();
        assertThat(showAnswerButton).isVisible();
    }

    @Test(
            testName = "LMS-1368 Возможность оставлять пометки yes. https://app.qase.io/plan/LMS/1?case=1368",
            description = "TC1368-01 - Flashcard turned when clicking the “Show Answer” button."
    )
    @Description("Objective: Verify that the user can see the Answer when the flashcard is turned.")
    @Story("Flashcards")
    @TmsLink("8kke54otuh6c")
    public void testFlashCardTurnedAfterClickingShowAnswerButton() {

        PreconditionPage precondition = new PreconditionPage(getPage()).init();
        precondition
                .collectRandomFlashcardPackInfo();

        final int packIndex = precondition.getFlashcardsPackRandomIndex();

        FlashcardsPackIDPage flashcardsPackIDPage =
                new HomePage(getPage()).init()
                        .clickFlashcardsMenu()
                        .clickNthFlashcardPack(packIndex)
                        .clickGotItButton();

        final Locator questionHeading = flashcardsPackIDPage.getQuestionHeading();
        final Locator answerHeading = flashcardsPackIDPage.getAnswerHeading();
        final Locator noButton = flashcardsPackIDPage.getNoButton();
        final Locator kindaButton = flashcardsPackIDPage.getKindaButton();
        final Locator yesButton = flashcardsPackIDPage.getYesButton();
        final Locator showAnswerButton = flashcardsPackIDPage.getShowAnswerButton();

        assertThat(questionHeading).isVisible();
        assertThat(answerHeading).not().isVisible();
        assertThat(noButton).not().isVisible();
        assertThat(kindaButton).not().isVisible();
        assertThat(yesButton).not().isVisible();
        assertThat(showAnswerButton).isVisible();

        flashcardsPackIDPage
                .clickShowAnswerButton();

        assertThat(questionHeading).isVisible();
        assertThat(answerHeading).isVisible();
        assertThat(noButton).isVisible();
        assertThat(kindaButton).isVisible();
        assertThat(yesButton).isVisible();
        assertThat(showAnswerButton).not().isVisible();
    }

    @Test(
            testName = "LMS-1368 Возможность оставлять пометки yes. https://app.qase.io/plan/LMS/1?case=1368",
            description = "TC1368-02 - Possibility to leave a “Yes” mark."
    )
    @Description("Objective: Verify that the user can successfully leave a 'Yes' mark on a flashcard"
            + " when the card is turned.")
    @Story("Flashcards")
    @TmsLink("xgp7wuhi782")
    public void testUserCanLeaveYesMark() {

        PreconditionPage precondition = new PreconditionPage(getPage()).init();
        precondition
                .collectRandomFlashcardPackInfo();

        final int packIndex = precondition.getFlashcardsPackRandomIndex();

        FlashcardsPackIDPage flashcardsPackIDPage =
                new HomePage(getPage()).init()
                        .clickFlashcardsMenu()
                        .clickNthFlashcardPack(packIndex)
                        .clickGotItButton()
                        .clickShowAnswerButton();

        final String yesCardsAmountBeforeClick = flashcardsPackIDPage.getYesCardsAmount();
        final String expectedYesCardsAmount = TestUtils.add(yesCardsAmountBeforeClick, 1);
        final Locator resetResultsButton = flashcardsPackIDPage.getResetResultsButton();

        assertThat(resetResultsButton).not().isVisible();

        flashcardsPackIDPage
                .clickYesMarkButton();

        final String yesCardsAmountAfterClick = flashcardsPackIDPage.getYesCardsAmount();

        assertThat(resetResultsButton).isVisible();
        Assert.assertEquals(
                yesCardsAmountAfterClick, expectedYesCardsAmount,
                "If FAIL: Expected 'Yes Mark' number does NOT increased by 1 after clicking the 'Yes Mark' button.\n"
        );
    }
}
