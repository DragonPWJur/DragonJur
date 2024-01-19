package tests;

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
                .doubleClickWord()
                .inputNoteText()
                .clickNoteSaveButton()
                .clickHighlightsAndNotesButton();

        assertThat(studyGuidePage.getNoteTextAria()).not().isVisible();
        assertThat(studyGuidePage.getWordFromList()).not().hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_0_0_0_0);
        assertThat(studyGuidePage.getWordFromList()).hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
        assertThat(studyGuidePage.getNoteButton()).isVisible();
    }
}