package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;
import utils.api.APIUtils;

public class StudyGuideTest extends BaseTest {
    //
//    @Test
//    public void testCreatingANote() {
//        StudyGuidePage studyGuidePage = new HomePage(getPage(), getPlaywright())
//                .clickStudyGuide()
//                .doubleClickOnWord()
//                .inputNoteText()
//                .clickNoteSaveButton()
//                .clickHighlightsAndNotesButton();
//
//        assertThat(studyGuidePage.getNoteTextAria()).not().isVisible();
//        assertThat(studyGuidePage.getWord())
//                .not().hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_0_0_0_0);
//        assertThat(studyGuidePage.getWord())
//                .hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
//        assertThat(studyGuidePage.getNoteButtonForWord()).isVisible();
//    }
//
//    @Test
//    public void testExecutingWordHighlightingByDoubleClick() {
//        StudyGuidePage studyGuidePage = new HomePage(getPage(), getPlaywright())
//                .clickStudyGuide()
//                .doubleClickOnWord();
//
//        assertThat(studyGuidePage.getNoteTextAria()).isVisible();
//        assertThat(studyGuidePage.getWord())
//                .not().hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_0_0_0_0);
//        assertThat(studyGuidePage.getWord())
//                .hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
//    }
//
//    @Test
//    public void testHighlightingMultipleWords() {
//        StudyGuidePage studyGuidePage = new HomePage(getPage(), getPlaywright())
//                .clickStudyGuide()
//                .highlightWords();
//
//
//        assertThat(studyGuidePage.getNoteTextAria()).isVisible();
//        assertThat(studyGuidePage.getMultipleWords())
//                .not().hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_0_0_0_0);
//        assertThat(studyGuidePage.getMultipleWords())
//                .hasCSS(TestData.BACKGROUND_COLOR, TestData.RGBA_62_48_179_0_2);
//    }
}