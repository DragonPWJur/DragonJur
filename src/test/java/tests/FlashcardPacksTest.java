package tests;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.FlashcardsPackIDPage;
import pages.HomePage;
import pages.PreconditionPage;
import pages.TestTutorPage;
import utils.ProjectProperties;
import utils.TestData;
import utils.TestUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FlashcardPacksTest extends BaseTest {

    @Ignore
    @Test
    public void testUserCanMarkCardsForRecheckingRandom() {

        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());
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
        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());
        int currentRandomIndex = preconditionPage.getCurrentNumberOfFlashcardPack();
        preconditionPage.startFlashcardPackAndGoBack(currentRandomIndex);

        FlashcardsPackIDPage FlashcardsPackIDPage = new HomePage(getPage(), getPlaywright())
                .clickHomeMenu()
                .clickFlashcardsMenu()
                .clickNthFlashcardPack(currentRandomIndex)
                .clickGotButtonIfVisible();

        Assert.assertTrue(getPage().url().contains(ProjectProperties.BASE_URL + TestData.FLASHCARDS_PACK_ID_END_POINT));
        assertThat(FlashcardsPackIDPage.getQuestionHeading()).hasText(TestData.QUESTION);
    }
}
