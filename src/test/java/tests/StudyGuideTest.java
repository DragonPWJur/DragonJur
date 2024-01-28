package tests;

import com.microsoft.playwright.Locator;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;
import tests.helpers.TestUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class StudyGuideTest extends BaseTest {

    @Test
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
    @Test
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

    @Ignore
    @Test
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