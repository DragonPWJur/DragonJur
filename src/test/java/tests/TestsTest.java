package tests;

import com.microsoft.playwright.Locator;

import org.testng.annotations.Test;
import pages.FlashcardsPage;

import pages.TestTutorPage;
import pages.TestsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.lang.Integer.parseInt;

public class TestsTest extends BaseTest {

    public String preconditions_UserCanMarkCardsForRechecking() {
        String numberOfCardsForReChecking = new FlashcardsPage(getPage(), getPlaywright())
                .getNumberOfCardsForReChecking();
        new TestsPage(getPage(), getPlaywright()).initiateTest();

        return numberOfCardsForReChecking;
    }

    @Test
    public void test_UserCanMarkCardsForRechecking() {

        String numberOfCardsForReChecking = preconditions_UserCanMarkCardsForRechecking();

        new TestTutorPage(getPage(), getPlaywright())
                .clickAddToFlashCardButton()
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTheTestButton();

        Locator newNumberOfCardsForReChecking = new FlashcardsPage(getPage(), getPlaywright()).clickFlashcardsMenu().getNumberMarkedForRechecking();

        assertThat(newNumberOfCardsForReChecking).hasText(Integer.toString(parseInt(numberOfCardsForReChecking) + 1));
    }

    @Test
    public void test_UserCanMarkTheCard() {

        new TestsPage(getPage(), getPlaywright()).initiateTest();

        TestTutorPage testTutorPage = new TestTutorPage(getPage(), getPlaywright());

        testTutorPage.clickMarkForReview();

        assertThat(testTutorPage.getMarkForReviewButton()).not().isVisible();;
        assertThat(testTutorPage.getRemoveFromMarkedButton()).isVisible();
    }

    public String preconditions_AfterMarkingTheCardTheNumberOfMarkedCardsIncreasedBy1() {

        String numberMarkedQuestionMode = new TestsPage(getPage(), getPlaywright())
                .getNumberMarked();

        new TestsPage(getPage(), getPlaywright()).initiateTest();

        return numberMarkedQuestionMode;
    }

    @Test
    public void test_AfterMarkingTheCardTheNumberOfMarkedCardsIncreasedBy1() {

        String numberMarkedQuestionMode = preconditions_AfterMarkingTheCardTheNumberOfMarkedCardsIncreasedBy1();

        new TestTutorPage(getPage(), getPlaywright())
                .clickMarkForReview()
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTheTestButton();

        Locator newNumberMarkedQuestionMode = new TestsPage(getPage(), getPlaywright()).getNumberMarkedQuestionMode();

        assertThat(newNumberMarkedQuestionMode).hasText(Integer.toString(parseInt(numberMarkedQuestionMode) + 1));
    }
}
