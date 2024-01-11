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
}
