package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;
import tests.helpers.TestUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class StudyGuideTest extends BaseTest {

    @Test(
            testName = "LMS-1363 Выделение части текста. https://app.qase.io/case/LMS-1363",
            description = "TC1363-01 - Executing Word Highlighting by Double Click")
    @Description("To confirm the user's ability to successfully highlight a word")
    @Story("Study Guide")
    @TmsLink("f8cnmz6sy744")
    @Severity(SeverityLevel.NORMAL)
    public void testExecutingWordHighlightingByDoubleClick() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .doubleClickOnWord(TestData.PROJECTIONS);

        final Locator noteTextAria = studyGuidePage.getNoteTextAria();
        final Locator word = studyGuidePage.getWord(TestData.PROJECTIONS);

        assertThat(noteTextAria).isVisible();
        assertThat(word).hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
    }

    @Ignore
    @Test(
            testName = "LMS-1363 Выделение части текста. https://app.qase.io/case/LMS-1363",
            description = "TC1363-02 - Highlighting Multiple Words")
    @Description("To confirm that the user can successfully highlight multiple words")
    @Story("Study Guide")
    @TmsLink("yerkt67nt8zq")
    @Severity(SeverityLevel.NORMAL)
    public void testHighlightingMultipleWords() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .highlightWords(TestData.LONG_BONES);

        final Locator noteTextAria = studyGuidePage.getNoteTextAria();
        final Locator word = studyGuidePage.getWord(TestData.PROJECTIONS);

        assertThat(noteTextAria).isVisible();
        assertThat(word).hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
    }

    @Test(
            testName = "LMS-1362 Создание заметок. https://app.qase.io/case/LMS-1362",
            description = "TC1362-01 - Creating a Note")
    @Description("To verify that the User can successfully create a note")
    @Story("Study Guide")
    @TmsLink("qqog7vjki13b")
    @Severity(SeverityLevel.NORMAL)
    public void testCreatingANote() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .doubleClickOnWord(TestData.PROJECTIONS)
                        .inputNoteText(TestUtils.getRandomString(10))
                        .clickSaveButton();

        final Locator noteTextAria = studyGuidePage.getNoteTextAria();
        final Locator word = studyGuidePage.getWord(TestData.PROJECTIONS);

        assertThat(noteTextAria).not().isVisible();
        assertThat(word).hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);

        studyGuidePage
                .clickHighlightsAndNotesButton();

        final Locator wordNoteButton = studyGuidePage.getHighlightedWordButton();

        assertThat(wordNoteButton).isVisible();
        assertThat(wordNoteButton).hasText(TestData.PROJECTIONS);
        assertThat(wordNoteButton).hasCount(1);
    }
}