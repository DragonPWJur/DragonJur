package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class StudyGuideTest extends BaseTest {

    @Test
    public void testCreatingANote() {
        StudyGuidePage studyGuidePage = new HomePage(getPage(), getPlaywright())
                .clickStudyGuide()
                .doubleClickOnWord()
                .inputNoteText()
                .clickNoteSaveButton()
                .clickHighlightsAndNotesButton();

        assertThat(studyGuidePage.getNoteTextAria()).not().isVisible();
        assertThat(studyGuidePage.getWord())
                .not().hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_0_0_0_0);
        assertThat(studyGuidePage.getWord())
                .hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
        assertThat(studyGuidePage.getNoteButtonForWord()).isVisible();
    }

    @Test
    public void testExecutingWordHighlightingByDoubleClick() {
        StudyGuidePage studyGuidePage = new HomePage(getPage(), getPlaywright())
                .clickStudyGuide()
                .doubleClickOnWord();

        assertThat(studyGuidePage.getNoteTextAria()).isVisible();
        assertThat(studyGuidePage.getWord())
                .not().hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_0_0_0_0);
        assertThat(studyGuidePage.getWord())
                .hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
    }

    @Test
    public void testHighlightingMultipleWords() {
        StudyGuidePage studyGuidePage = new HomePage(getPage(), getPlaywright())
                .clickStudyGuide()
                .highlightWords();


        assertThat(studyGuidePage.getNoteTextAria()).isVisible();
        assertThat(studyGuidePage.getMultipleWords())
                .not().hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_0_0_0_0);
        assertThat(studyGuidePage.getMultipleWords())
                .hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
    }

    @Test
    public void testTextContentChanges() {
        StudyGuidePage studyGuidePage = new HomePage(getPage(), getPlaywright())
                .clickStudyGuide();

        Assert.assertFalse(studyGuidePage.getUnit1Text().contains(TestData.UNIT_1_CONTENT_TEST), "Text expected doesnt contain a word 'test' but it is");

        studyGuidePage.changeChapter1Unit1NameViaAPI(TestData.UNIT_1_CONTENT_TEST);

        studyGuidePage
                .clickHomeMenu()
                .clickStudyGuide();

        Assert.assertTrue(studyGuidePage.getUnit1Text().contains(TestData.UNIT_1_CONTENT_TEST), "Text expected contain a word 'test' but its not");

        studyGuidePage.changeChapter1Unit1NameViaAPI(TestData.UNIT_1_CONTENT);
    }
}