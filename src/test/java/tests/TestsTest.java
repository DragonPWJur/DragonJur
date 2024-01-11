package tests;

import com.microsoft.playwright.Locator;

import org.testng.annotations.Test;
import pages.FlashcardsPage;

import pages.TestTutorPage;
import pages.TestsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.lang.Integer.parseInt;


public class TestsTest extends BaseTest {

    @Test
    public void test_UserCanMarkCardsForRechecking() {

        String numberOfCardsForReChecking = new FlashcardsPage(getPage(), getPlaywright())
                .getNumberOfCardsForReChecking();
        new TestsPage(getPage(), getPlaywright()).initiateTest();

        new TestTutorPage(getPage(), getPlaywright())
                .clickAddToFlashCardButton()
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTheTestButton();

        Locator newNumberOfCardsForReChecking = new FlashcardsPage(getPage(), getPlaywright()).clickFlashcardsMenu()
                .getNumberMarkedForRechecking();

        String expectedResult = Integer.toString(parseInt(numberOfCardsForReChecking) + 1);

        assertThat(newNumberOfCardsForReChecking).hasText(expectedResult);
    }

    @Test
    public void test_UserCanMarkTheCard() {

        new TestsPage(getPage(), getPlaywright()).initiateTest();

        TestTutorPage testTutorPage = new TestTutorPage(getPage(), getPlaywright());

        testTutorPage.clickMarkForReview();

        assertThat(testTutorPage.getMarkForReviewButton()).not().isVisible();;
        assertThat(testTutorPage.getRemoveFromMarkedButton()).isVisible();
    }

    @Test
    public void test_AfterMarkingTheCardTheNumberOfMarkedCardsIncreasedBy1() {

        String numberMarkedQuestionMode = new TestsPage(getPage(), getPlaywright())
                .getNumberMarked();
        new TestsPage(getPage(), getPlaywright()).initiateTest();

        new TestTutorPage(getPage(), getPlaywright())
                .clickMarkForReview()
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTheTestButton();

        Locator newNumberMarkedQuestionMode = new TestsPage(getPage(), getPlaywright())
                .getNumberMarkedQuestionMode();

        String expectedResult = Integer.toString(parseInt(numberMarkedQuestionMode) + 1);

        assertThat(newNumberMarkedQuestionMode).hasText(expectedResult);
    }
}
