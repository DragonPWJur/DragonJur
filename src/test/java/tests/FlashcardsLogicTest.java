package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.FlashcardPacksPage;
import pages.FlashcardsPackIDPage;
import pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FlashcardsLogicTest extends BaseTest {
    @Severity(SeverityLevel.NORMAL)
    @Story("FlashcardsLogic")
    @TmsLink("")
    @Description("LMS-1391 Логика Yes 2. https://app.qase.io/plan/LMS/1?case=1391"
            + "Objective: Verify that the user can successfully leave a 'Yes' mark and next " +
            "flashcard would be shown if there more cards in the pack.")
    @Test(description = "TC1391-01 - Possibility to leave a “Yes” mark and see the next card.")
    public void testYes2Logic() {
        HomePage homePage = new HomePage(getPage()).init();

        FlashcardsPackIDPage flashcardsPackIDPage =
                homePage
                        .clickFlashcardsMenu()
                        .clickAutotestFlashcardsPack()
                        .clickGotItButton();

        assertThat(getPage().getByText("soup")).isVisible();

        flashcardsPackIDPage
                .clickShowAnswerButton()
                .clickYesMarkButton();

        assertThat(flashcardsPackIDPage.getShowAnswerButton()).isVisible();
        assertThat(getPage().getByText("Panton")).isVisible();
    }



}
