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
                //.interceptAPIStudyGuideTable();

        Assert.assertFalse(studyGuidePage.getUnit1Text().contains(TestData.WORD_TEST),
                "Expected text to contain '" + TestData.WORD_TEST + "' but it is: " + studyGuidePage.getUnit1Text());

        studyGuidePage.changeChapter1Unit1TextViaAPI(TestData.WORD_TEST);

        studyGuidePage
                .reload()
                .clickStudyGuide();

        Assert.assertTrue(studyGuidePage.getUnit1Text().contains(TestData.WORD_TEST),
                "Expected text doesn't contain '" + TestData.WORD_TEST + "' but it is: " + studyGuidePage.getUnit1Text());

        studyGuidePage.restoreChapter1Unit1Text(TestData.WORD_TEST);
    }
}