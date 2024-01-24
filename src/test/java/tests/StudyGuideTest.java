package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;

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
    @Test(
            testName = "LMS-TC1360-01 User is able to see the Study Guide text. https://app.qase.io/plan/LMS/1?case=1360",
            description = "TC1360-01 - The user sees that the changes made in the admin site Study Guide appear on the user website."
    )
    @Description("Objective:  To verify that user is able to see the study guide text.")
    @Story("StudyGuide")
    @TmsLink("rxlgx1r82okx")
    public void testTextContentChanges() {
        StudyGuidePage studyGuidePage = new HomePage(getPage()).init()
                .clickStudyGuideMenu();

        Assert.assertFalse(studyGuidePage.getUnit1Text().contains(TestData.WORD_TEST),
                "Expected text to contain '" + TestData.WORD_TEST + "' but it is: " + studyGuidePage.getUnit1Text());

        studyGuidePage.changeChapter1Unit1TextViaAPI(TestData.WORD_TEST);

        studyGuidePage
                .reload();

        Assert.assertTrue(studyGuidePage.getUnit1Text().contains(TestData.WORD_TEST),
                "Expected text doesn't contain '" + TestData.WORD_TEST + "' but it is: " + studyGuidePage.getUnit1Text());

        studyGuidePage.restoreChapter1Unit1TextViaAPI(TestData.WORD_TEST);
    }
}