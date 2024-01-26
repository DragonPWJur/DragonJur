package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MnemonicCardPracticePage;
import utils.runner.ProjectProperties;
import tests.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MnemonicCardPracticeTest extends BaseTest {

    @Test
    public void testOpeningMnemonicCardAfterStartingPractice() {
        MnemonicCardPracticePage mnemonicCardPracticePage =
                new HomePage(getPage()).init()
                        .clickMnemonicCardsMenu()
                        .clickRandomMnemonicCardsStack()
                        .clickStartPracticeButton();

        final String expectedUrlPart = ProjectProperties.BASE_URL + TestData.MNEMONIC_CARD_PRACTICE_END_POINT;
        final String actualPageUrl = getPage().url();

        Assert.assertTrue(
                actualPageUrl.contains(expectedUrlPart),
                "If FAIL: The page URL " + actualPageUrl + " does NOT contains the url part: " + expectedUrlPart + ".\n"
        );
        assertThat(mnemonicCardPracticePage.getMnemonicCardPracticeHeader()).hasText(TestData.PRACTICE);
        assertThat(mnemonicCardPracticePage.getAnswersToQuestion()).isVisible();
        assertThat(mnemonicCardPracticePage.getMnemonicWords()).isVisible();
    }
}
