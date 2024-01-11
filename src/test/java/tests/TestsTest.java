package tests;

import com.microsoft.playwright.Locator;

import org.testng.annotations.Test;
import pages.FlashcardsPage;

import pages.TestTutorPage;
import pages.TestsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.lang.Integer.parseInt;

public class TestsTest extends BaseTest {

    public String preconditions_1349() {
        String numberOfCardsForReChecking = new FlashcardsPage(getPage(), getPlaywright())
                .getNumberOfCardsForReChecking();
        new TestsPage(getPage(), getPlaywright()).initiateTest();

        return numberOfCardsForReChecking;
    }

    @Test
    public void test_1349() {

        String numberOfCardsForReChecking = preconditions_1349();

        new TestTutorPage(getPage(), getPlaywright())
                .clickAddToFlashCard()
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTestButton();

        Locator newNumberOfCardsForReChecking = new FlashcardsPage(getPage(), getPlaywright()).clickFlashcardsMenuButton().numberMarkedForRechecking;

        assertThat(newNumberOfCardsForReChecking).hasText(Integer.toString(parseInt(numberOfCardsForReChecking) + 1));
    }

    @Test
    public void test_1350_1() {

        new TestsPage(getPage(), getPlaywright()).initiateTest();

        TestTutorPage testTutorPage = new TestTutorPage(getPage(), getPlaywright());

        testTutorPage.clickMarkForReview();

        assertThat(testTutorPage.markForReview).not().isVisible();;
        assertThat(testTutorPage.removeFromMarked).isVisible();
    }

    public String preconditions_1350_2() {

        String numberMarkedQuestionMode = new TestsPage(getPage(), getPlaywright())
                .getNumberMarked();

        new TestsPage(getPage(), getPlaywright()).initiateTest();

        return numberMarkedQuestionMode;
    }

    @Test
    public void test_1350_2() {

        String numberMarkedQuestionMode = preconditions_1350_2();

        new TestTutorPage(getPage(), getPlaywright())
                .clickMarkForReview()
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTestButton();

        Locator newNumberMarkedQuestionMode = new TestsPage(getPage(), getPlaywright()).numberMarkedQuestionMode;

        assertThat(newNumberMarkedQuestionMode).hasText(Integer.toString(parseInt(numberMarkedQuestionMode) + 1));
    }
}
