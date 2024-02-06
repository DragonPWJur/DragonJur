package tests;

import com.microsoft.playwright.Locator;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FlashcardsPackIDPage;
import pages.HomePage;
import pages.PreconditionPage;
import tests.helpers.TestData;
import tests.helpers.TestUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class FlashcardPacksTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Flashcards")
    @TmsLink("rr1rrsnttopz")
    @Description("LMS-1349 Возможность для юзера добавлять во флешкарточки. https://app.qase.io/plan/LMS/1?case=1349 "
            + "Objective: To confirm the user's ability to mark cards for re-checking"
            + " and verify the increased amount within the 'Marked for re-checking' flashcard section.")
    @Test(description = "TC1349-01 - User can mark cards for re-checking (“Add to flashcards”).")
    public void testAddToFlashCard() {
        PreconditionPage precondition = new PreconditionPage(getPage()).init();

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

        Allure.step("Verify that within the 'Marked for re-checking' info, the count of cards has increased by 1.");
        Assert.assertEquals(
                actualCardsAmount, expectedCardsAmount,
                "If FAIL: The expected amount marked for re-checking cards (" + expectedCardsAmount
                        + ") is NOT equal to the actual amount (" + actualCardsAmount + ").\n"
        );
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Flashcards")
    @TmsLink("5w1lt3377dz3")
    @Description("LMS-1367 Запуск стопки. https://app.qase.io/plan/LMS/1?case=1367  "
            + "Objective: To confirm that users can successfully initiate a flashcard pack for studying.")
    @Test(description = "TC1367-01 - Verify that the user can start a flashcard pack.")
    public void testStartRandomFlashCardPack() {
        PreconditionPage precondition = new PreconditionPage(getPage()).init();
        precondition
                .collectRandomFlashcardPackInfo();

        final int packIndex = precondition.getFlashcardsPackRandomIndex();
        final String expectedPackName = precondition.getFlashcardsPackName();
        final String expectedPackAmount = precondition.getFlashcardsPackCardsAmount();
        final String expectedUrlPart = TestData.FLASHCARDS_PACK_ID_END_POINT;

        FlashcardsPackIDPage flashcardsPackIDPage =
                new HomePage(getPage()).init()
                        .clickFlashcardsMenu()
                        .clickNthFlashcardPack(packIndex)
                        .clickGotItButton();

        final String actualPageUrl = getPage().url();
        final String actualVisiblePackName = flashcardsPackIDPage.getPackName();
        final Locator cardsTotalText = flashcardsPackIDPage.cardsTotalText(expectedPackAmount);
        final Locator questionHeading = flashcardsPackIDPage.getQuestionHeading();

        Allure.step("Assert that user was redirected to '" + expectedUrlPart + "' end point.");
        Assert.assertTrue(
                actualPageUrl.contains(expectedUrlPart),
                "If FAIL: The page URL " + actualPageUrl + " does NOT contains the url part: " + expectedUrlPart + ".\n"
        );

        Allure.step("Assert that saved flashcards pack Name contains visible pack name'" + actualVisiblePackName + "'.");
        Assert.assertTrue(
                expectedPackName.contains(actualVisiblePackName),
                "If FAIL: The expected pack name " + expectedPackName + "does NOT contains actual pack name " + actualVisiblePackName + ".\n"
        );

        Allure.step("Assert that '" + cardsTotalText.innerText() + "' is visible.");
        assertThat(cardsTotalText).isVisible();

        Allure.step("Assert that '" + questionHeading.innerText() + "' is visible.");
        assertThat(questionHeading).isVisible();

        Allure.step("Assert that '" + cardsTotalText.innerText() + "' is equals '" + expectedPackAmount + "'.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Flashcards")
    @TmsLink("8kke54otuh6c")
    @Description("LMS-1368 Возможность оставлять пометки yes. https://app.qase.io/plan/LMS/1?case=1368  " +
            "Objective: Verify that the user can see the Answer when the flashcard is turned.")
    @Test(description = "TC1368-01 - Flashcard turned when clicking the “Show Answer” button.")
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

        Allure.step("Assert that '" + questionHeading.innerText() + "' is visible.");
        assertThat(questionHeading).isVisible();

        Allure.step("Assert that '" + showAnswerButton.innerText() + "' button is visible.");
        assertThat(showAnswerButton).isVisible();

        flashcardsPackIDPage
                .clickShowAnswerButton();

        Allure.step("Assert that '" + questionHeading.innerText() + "' is visible.");
        assertThat(questionHeading).isVisible();

        Allure.step("Assert that '" + answerHeading.innerText() + "' is visible.");
        assertThat(answerHeading).isVisible();

        Allure.step("Assert that '" + noButton.innerText() + "' is visible.");
        assertThat(noButton).isVisible();

        Allure.step("Assert that '" + kindaButton.innerText() + "' is visible.");
        assertThat(kindaButton).isVisible();

        Allure.step("Assert that '" + yesButton.innerText() + "' is visible.");
        assertThat(yesButton).isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Flashcards")
    @TmsLink("xgp7wuhi782")
    @Description("LMS-1368 Возможность оставлять пометки yes. https://app.qase.io/plan/LMS/1?case=1368"
            + "Objective: Verify that the user can successfully leave a 'Yes' mark on a flashcard"
            + " when the card is turned.")
    @Test(description = "TC1368-02 - Possibility to leave a “Yes” mark.")
    public void testUserCanLeaveYesMark() {
        FlashcardsPackIDPage flashcardsPackIDPage =
                new HomePage(getPage()).init()
                        .clickFlashcardsMenu()
                        .clickAutotestFlashcardsPack()
                        .clickGotItButton()
                        .clickShowAnswerButton();

        final String yesCardsAmountBeforeClick = flashcardsPackIDPage.getYesCardsAmount();
        final String expectedYesCardsAmount = TestUtils.add(yesCardsAmountBeforeClick, 1);

        flashcardsPackIDPage
                .clickYesMarkButton();

        final Locator resetResultsButton = flashcardsPackIDPage.getResetResultsButton();
        final String yesCardsAmountAfterClick = flashcardsPackIDPage.getYesCardsAmount();

        Allure.step("Assert that '" + resetResultsButton.innerText() + "' is visible.");
        assertThat(resetResultsButton).isVisible();

        Allure.step("Assert that 'Yes' card number (" + yesCardsAmountAfterClick + ")" +
                " equals expected number " + expectedYesCardsAmount + ".");
        Assert.assertEquals(
                yesCardsAmountAfterClick, expectedYesCardsAmount,
                "If FAIL: Expected 'Yes' cards amount does NOT increased by 1 after clicking the 'Yes' button.\n"
        );
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Flashcards")
    @TmsLink("65ov9eivu5o5")
    @Description("LMS-1373 Возможность оставлять пометки kinda. https://app.qase.io/plan/LMS/1?case=1373"
            + "Objective: Verify that the user can successfully leave a 'Kinda' mark on a flashcard when the card is turned.")
    @Test(description = "TC1373-01 - Possibility to leave a “Kinda” mark.")
    public void testUserCanLeaveKindaMark() {
        FlashcardsPackIDPage flashcardsPackIDPage =
                new HomePage(getPage()).init()
                        .clickFlashcardsMenu()
                        .clickAutotestFlashcardsPack()
                        .clickGotItButton()
                        .clickShowAnswerButton();

        final String kindaCardsAmountBeforeClick = flashcardsPackIDPage.getKindaCardsAmount();
        final String expectedKindaCardsAmount = TestUtils.add(kindaCardsAmountBeforeClick, 1);


        flashcardsPackIDPage
                .clickKindaMarkButton();

        final Locator resetResultsButton = flashcardsPackIDPage.getResetResultsButton();
        final String kindaCardsAmountAfterClick = flashcardsPackIDPage.getKindaCardsAmount();

        Allure.step("Assert that '" + resetResultsButton.innerText() + "' is visible.");
        assertThat(resetResultsButton).isVisible();

        Allure.step("Assert that '" + kindaCardsAmountAfterClick + "' equals '" + expectedKindaCardsAmount + "'.");
        Assert.assertEquals(
                kindaCardsAmountAfterClick, expectedKindaCardsAmount,
                "If FAIL: Expected 'Kinda' cards amount does NOT increased by 1 after clicking the 'Kinda' button.\n"
        );
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Flashcards")
    @TmsLink("8aif3r2l9kd2")
    @Description("LMS-1374 Возможность оставлять пометки no. https://app.qase.io/plan/LMS/1?case=1374  "
            + "Objective: Verify that the user can successfully leave a 'No' mark on a flashcard when the card is turned.")
    @Test(description = "TC1374-01 - Possibility to leave a “No” mark.")
    public void testUserCanLeaveNoMark() {
        FlashcardsPackIDPage flashcardsPackIDPage =
                new HomePage(getPage()).init()
                        .clickFlashcardsMenu()
                        .clickAutotestFlashcardsPack()
                        .clickGotItButton()
                        .clickShowAnswerButton();

        final String noCardsAmountBeforeClick = flashcardsPackIDPage.getNoCardsAmount();
        final String expectedNoCardsAmount = TestUtils.add(noCardsAmountBeforeClick, 1);

        flashcardsPackIDPage
                .clickNoMarkButton();

        final Locator resetResultsButton = flashcardsPackIDPage.getResetResultsButton();
        final String noCardsAmountAfterClick = flashcardsPackIDPage.getNoCardsAmount();

        Allure.step("Assert that '" + resetResultsButton.innerText() + "' is visible.");
        assertThat(resetResultsButton).isVisible();

        Allure.step("Assert that '" + noCardsAmountAfterClick + "' equals '" + expectedNoCardsAmount + "'.");
        Assert.assertEquals(
                noCardsAmountAfterClick, expectedNoCardsAmount,
                "If FAIL: Expected 'No' cards amount does NOT increased by 1 after clicking the 'No' button.\n"
        );
    }
}
