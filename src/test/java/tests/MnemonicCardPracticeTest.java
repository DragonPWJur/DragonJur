package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MnemonicCardPracticePage;
import utils.runner.ProjectProperties;
import tests.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MnemonicCardPracticeTest extends BaseTest {

    @Test(
            testName = "LMS-1355 Получение карточек. https://app.qase.io/plan/LMS/1?case=1355",
            description = "TC1355-02 - Opening Mnemonic Card after Starting Practice."
    )
    @Description("Objective: To verify that the user can successfully open any Mnemonic Card after clicking the Start Practice button.")
    @Story("Mnemonic Cards")
    @TmsLink("bhhgxa6zrj99")
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
