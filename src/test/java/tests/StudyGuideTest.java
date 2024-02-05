package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;
import tests.helpers.TestUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class StudyGuideTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Study Guide")
    @TmsLink("f8cnmz6sy744")
    @Description("LMS-1363 Выделение части текста. https://app.qase.io/case/LMS-1363" +
            "   To confirm the user's ability to successfully highlight a word")
    @Test(description = "TC1363-01 - Executing Word Highlighting by Double Click")
    public void testExecutingWordHighlightingByDoubleClick() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .doubleClickOnWord(TestData.PROJECTIONS);

        final Locator noteTextAria = studyGuidePage.getNoteTextAria();
        final Locator word = studyGuidePage.getWord(TestData.PROJECTIONS);

        Allure.step("Assert that text aria to put the note is visible.");
        assertThat(noteTextAria).isVisible();

        Allure.step("Assert that selected word '" + TestData.PROJECTIONS + "' is highlighted.");
        assertThat(word).hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Study Guide")
    @TmsLink("yerkt67nt8zq")
    @Description("LMS-1363 Выделение части текста. https://app.qase.io/case/LMS-1363" +
            "   To confirm that the user can successfully highlight multiple words")
    @Test(description = "TC1363-02 - Highlighting Multiple Words")
    public void testHighlightingMultipleWords() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .highlightWords(TestData.PHALANGES_IN_THE_FINGERS);

        final Locator noteTextAria = studyGuidePage.getNoteTextAria();
        final Locator words = studyGuidePage.getWords(TestData.PHALANGES_IN_THE_FINGERS);

        Allure.step("Assert that text aria to put the note is visible.");
        assertThat(noteTextAria).isVisible();

        Allure.step("Assert that selected words " + TestData.PHALANGES_IN_THE_FINGERS + " are highlighted.");
        assertThat(words).hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Study Guide")
    @TmsLink("qqog7vjki13b")
    @Description("LMS-1362 Создание заметок. https://app.qase.io/case/LMS-1362" +
            "  To verify that the User can successfully create a note")
    @Test(description = "TC1362-01 - Creating a Note")
    public void testCreatingANote() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .doubleClickOnWord(TestData.PROJECTIONS)
                        .inputNoteText(TestUtils.getRandomString(10))
                        .clickNoteSaveButton();

        final Locator noteTextAria = studyGuidePage.getNoteTextAria();
        final Locator word = studyGuidePage.getWord(TestData.PROJECTIONS);

        Allure.step("Assert that text aria to put the note is Not visible.");
        assertThat(noteTextAria).not().isVisible();

        Allure.step("Assert that selected word '" + TestData.PROJECTIONS + "' is highlighted.");
        assertThat(word).hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);

        studyGuidePage
                .clickHighlightsAndNotesButton();

        final Locator wordNoteButton = studyGuidePage.getHighlightedWordButton();

        Allure.step("Assert that text aria to put the note is visible.");
        assertThat(wordNoteButton).isVisible();

        Allure.step("Assert that Note button is created on the right side menu.");
        assertThat(wordNoteButton).hasCount(1);

        Allure.step("Assert that Note button is visible on the right side menu.");
        assertThat(wordNoteButton).isVisible();

        Allure.step("Assert that Note button has text '" + TestData.PROJECTIONS + "'.");
        assertThat(wordNoteButton).hasText(TestData.PROJECTIONS);
    }
}