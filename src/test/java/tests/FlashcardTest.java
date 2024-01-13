package tests;

import com.microsoft.playwright.Locator;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.lang.Integer.parseInt;

public class FlashcardTest extends BaseTest {


    @Ignore
    @Test
    public void testUserCanMarkCardsForRechecking() {

        String numberOfCardsForReChecking = new HomePage(getPage(), getPlaywright())
                .clickFlashcardsMenu()
                .getNumberMarkedForRechecking().innerText();

        String expectedResult = Integer.toString(parseInt(numberOfCardsForReChecking) + 1);

        Locator actual = new HomePage(getPage(), getPlaywright())
                .initiateTest()
                .clickAddToFlashCardButtonIfVisible()
                .clickEndButton()
                .endTestIfVisible()
                .clickSkipButton()
                .clickCloseTheTestButton()
                .getHomePage()
                .clickFlashcardsMenu()
                .getNumberMarkedForRechecking();

        assertThat(actual).hasText(expectedResult);
    }
}
