package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class StudyGuideTest extends BaseTest {

    @Test
    public void testCreatingANote() {
        String sscKey = "background-color";
        String sscValue = "rgba(62, 48, 179, 0.2)";
        String fullyTransparentColor = "rgba(0, 0, 0, 0)";

        StudyGuidePage studyGuidePage = new HomePage(getPage(), getPlaywright())
                .clickStudyGuide()
                .doubleClickWord()
                .inputNoteText()
                .clickNoteSaveButton()
                .clickHighlightsAndNotesButton();

        assertThat(studyGuidePage.getNoteTextAria()).not().isVisible();
        assertThat(studyGuidePage.getWordFromList()).not().hasCSS(sscKey, fullyTransparentColor);
        assertThat(studyGuidePage.getWordFromList()).hasCSS(sscKey, sscValue);
        assertThat(studyGuidePage.getNoteButton()).isVisible();
    }
}