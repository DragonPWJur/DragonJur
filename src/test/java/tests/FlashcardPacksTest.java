package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FlashcardsPackIDPage;
import pages.HomePage;
import pages.PreconditionPage;
import utils.ProjectProperties;
import utils.TestData;
import utils.TestUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class FlashcardPacksTest extends BaseTest {

//    @Test(testName = "TC1349-01 LMS-1349 https://app.qase.io/plan/LMS/1?case=1349")
//    public void testAddToFlashCard() {
//        PreconditionPage precondition = new PreconditionPage(getPage());
//
//        final String initialCardsAmount = precondition.getInitialAmountOfCardsMarkedForRechecking();
//
//        final String expectedCardsAmount = TestUtils.add(initialCardsAmount, 1);
//
//        final String actualCardsAmount =
//                precondition.startRandomDomainTest(TestData.ONE_QUESTION)
//                        .clickAddToFlashCardButton()
//                        .clickEndButton()
//                        .clickYesButton()
//                        .clickSkipButton()
//                        .clickCloseTheTestButton()
//                        .clickFlashcardsMenu()
//                        .getAmountOfCardsMarkedForRechecking();
//
//        Assert.assertEquals(
//                actualCardsAmount, expectedCardsAmount,
//                "The expected amount marked for re-checking cards (" + expectedCardsAmount
//                        + ") is NOT equal to the actual amount (" + actualCardsAmount + ")."
//        );
//    }
//
//    @Test(testName = "TC1367-01 LMS-1367 https://app.qase.io/plan/LMS/1?case=1367")
//    public void testStartRandomFlashCardPack() {
//        PreconditionPage precondition = new PreconditionPage(getPage());
//
//<<<<<<< HEAD
//        precondition.collectRandomFlashcardPackInfo();
//        final int packIndex = precondition.getFlashcardsPackRandomIndex();
//        final String packName = precondition.getFlashcardsPackName();
//        final String cardsInPackAmount = precondition.getFlashcardsPackCardsAmount();
//=======
//        FlashcardsPackIDPage FlashcardsPackIDPage = new HomePage(getPage(), getPlaywright())
//                .clickHomeMenu()
//                .clickFlashcardsMenu()
//                .clickNthFlashcardPack(currentRandomIndex)
//                .clickGotItButtonIfVisible();
//>>>>>>> 8952b8e84f53dbc1d24376ded00a911bdb48372e
//
//        FlashcardsPackIDPage flashcardsPackIDPage =
//                new HomePage(getPage())
//                        .clickFlashcardsMenu()
//                        .clickNthFlashcardPack(packIndex)
//                        .clickGotItButton();
//
//        String pageUrl = getPage().url();
//        String expectedUrlPart = ProjectProperties.BASE_URL + TestData.FLASHCARDS_PACK_ID_END_POINT;
//
//        Assert.assertTrue(
//                pageUrl.contains(expectedUrlPart),
//                "The page URL " + pageUrl + " does NOT contain: " + expectedUrlPart
//        );
//        assertThat(flashcardsPackIDPage.packNameOnTopMenu(packName)).isVisible();
//        assertThat(flashcardsPackIDPage.cardsTotalAmount(cardsInPackAmount)).isVisible();
//        assertThat(flashcardsPackIDPage.getQuestionHeading()).isVisible();
//        assertThat(flashcardsPackIDPage.getShowAnswerButton()).isVisible();
//    }
//
//    @Test
//    public void testUserCanLeaveYesMark() {
//        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());
//        int currentRandomIndex = preconditionPage.getCurrentNumberOfFlashcardPack();
//
//        FlashcardsPackIDPage flashcardsPackIDPage = new HomePage(getPage(), getPlaywright())
//                .clickHomeMenu()
//                .clickFlashcardsMenu()
//                .clickNthFlashcardPack(currentRandomIndex)
//                .clickGotItButtonIfVisible()
//                .clickShowAnswerButton();
//
//        assertThat(flashcardsPackIDPage.getQuestionHeading()).isVisible();
//        assertThat(flashcardsPackIDPage.getAnswerHeading()).isVisible();
//
//        String numberOfYesMarksBefore = flashcardsPackIDPage.getNumberOfYesMarks().innerText();
//
//        flashcardsPackIDPage.clickYesMarkButton();
//        String numberOfYesMarksAfter = flashcardsPackIDPage.getNumberOfYesMarks().innerText();
//
//        Assert.assertEquals(numberOfYesMarksAfter, flashcardsPackIDPage.increaseByOne(numberOfYesMarksBefore),
//                "Expected 'Yes Mark' number to increase by 1 after clicking the 'Yes Mark' button.");
//        assertThat(flashcardsPackIDPage.getResetResultsButton()).isVisible();
//    }
}
