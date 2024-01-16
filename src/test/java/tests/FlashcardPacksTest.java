package tests;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;
import utils.TestUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.ProjectProperties.BASE_URL;

public class FlashcardPacksTest extends BaseTest {

    @Test
    public void testUserCanMarkCardsForRechecking() {

        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());

        preconditionPage.startTestDomain2(TestData.ONE);
        preconditionPage.clickRemoveFromFlashcardsButtonIfVisible();
        preconditionPage.endTest();

        String numberOfCardsForReCheckingBefore = preconditionPage.getCurrentNumberOfCardForRechecking();

        preconditionPage.startTestDomain2(TestData.ONE);

        String numberOfCardsForReCheckingAfter = new TestTutorPage(getPage(), getPlaywright())
                .clickAddToFlashCardButton()
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTheTestButton()
                .clickFlashcardsMenu()
                .getNumberMarkedForRechecking();

        Assert.assertEquals(numberOfCardsForReCheckingAfter, TestUtils.addNumber(numberOfCardsForReCheckingBefore, 1));
    }

    @Test
    public void testUserCanMarkCardsForRecheckingRandom() {

        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());
        preconditionPage.resetCourseResults();
        new ProfilePage(getPage(), getPlaywright())
                .closeNotification();

        String numberOfCardsForReCheckingBefore = preconditionPage.getCurrentNumberOfCardForRechecking();
        preconditionPage.startTest(TestData.ONE);

        String numberOfCardsForReCheckingAfter = new TestTutorPage(getPage(), getPlaywright())
                .clickAddToFlashCardButton()
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTheTestButton()
                .clickFlashcardsMenu()
                .getNumberMarkedForRechecking();

        Assert.assertEquals(numberOfCardsForReCheckingAfter, TestUtils.addNumber(numberOfCardsForReCheckingBefore, 1));
    }

    @Test
    public void test_StartFlashCardPack() {

        FlashcardPacksPage flashcardPacksPage = new HomePage(getPage(), getPlaywright())
                .clickFlashcardsMenu();

        int randomIndex = TestUtils.getRandomNumber(flashcardPacksPage.getFlashcardsPacksToLearn());

        new PreconditionPage(getPage(), getPlaywright())
                .startFlashcardPack(randomIndex);

        flashcardPacksPage
                .clickRandomFlashcardPack(randomIndex)
                .clickGotButtonIfVisible();

        FlashcardsPackIDPage FlashcardsPackIDPage = new FlashcardsPackIDPage(getPage(), getPlaywright());

        Assert.assertTrue(getPage().url().contains(BASE_URL + TestData.FLASHCARDS_PACK_ID_END_POINT));
        assertThat(FlashcardsPackIDPage.getQuestionHeading()).hasText(TestData.QUESTION);
    }

}
