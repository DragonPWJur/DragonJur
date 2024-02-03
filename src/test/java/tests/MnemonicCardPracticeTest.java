package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MnemonicCardPracticePage;
import utils.runner.ProjectProperties;
import tests.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MnemonicCardPracticeTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Mnemonic Cards")
    @TmsLink("bhhgxa6zrj99")
    @Description("LMS-1355 Получение карточек. https://app.qase.io/plan/LMS/1?case=1355" +
            "  Objective: To verify that the user can successfully open any Mnemonic Card after " +
            "clicking the Start Practice button.")
    @Test(description = "TC1355-02 - Opening Mnemonic Card after Starting Practice.")
    public void testOpeningMnemonicCardAfterStartingPractice() {
        MnemonicCardPracticePage mnemonicCardPracticePage =
                new HomePage(getPage()).init()
                        .clickMnemonicCardsMenu()
                        .clickRandomMnemonicCardsStack()
                        .clickStartPracticeButton();

        final String expectedUrlPart = ProjectProperties.BASE_URL + TestData.MNEMONIC_CARD_PRACTICE_END_POINT;
        final String actualPageUrl = getPage().url();
        final Locator mnemonicCardPracticeHeader= mnemonicCardPracticePage.getMnemonicCardPracticeHeader();
        final Locator answers = mnemonicCardPracticePage.getAnswersToQuestion();
        final Locator words = mnemonicCardPracticePage.getMnemonicWords();

        Allure.step("Assert that the url contains the expected url part  '" + TestData.MNEMONIC_CARD_PRACTICE_END_POINT);
        Assert.assertTrue(
                actualPageUrl.contains(expectedUrlPart),
                "If FAIL: The page URL " + actualPageUrl + " does NOT contains the url part: " + expectedUrlPart + ".\n"
        );

        Allure.step("Assert that the mnemonic card practice header has text '" + TestData.PRACTICE + "'.");
        assertThat(mnemonicCardPracticeHeader).hasText(TestData.PRACTICE);

        Allure.step("Assert that the mnemonic card answers are visible.");
        assertThat(answers).isVisible();

        Allure.step("Assert that the mnemonic card words are visible.");
        assertThat(words).isVisible();
    }
}
