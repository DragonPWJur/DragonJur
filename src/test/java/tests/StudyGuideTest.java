package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import pages.TestTutorPage;
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

    @Severity(SeverityLevel.NORMAL)
    @Story("Study Guide")
    @TmsLink("ufe2bohbd0sy")
    @Description("LMS-1364 Запуск тестов. https://app.qase.io/case/LMS-1364" +
            "   Objective: To confirm that users with a Gold subscription can successfully run a test in the Study Guide section.")
    @Test(description = "TC1364-01 - Running Test in Study Guide with Gold Subscription")
    public void testTestIsRunWhenOpenFromStudyGuide() {
        TestTutorPage testTutorPage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .scrollToPageBottom()
                        .clickYesButton();

        final String actualUrl = getPage().url();
        final String expectedUrlPart = TestData.TEST_TUTOR_CHAPTER_INDEX_0_END_POINT;
        final Locator question = testTutorPage.getTestQuestion();

        Allure.step("Assert that user was redirected to '" + TestData.TEST_TUTOR_END_POINT + "' page.");
        Assert.assertTrue(
                actualUrl.contains(expectedUrlPart),
                "If FAIL: actual url '" + actualUrl + "' does NOT contains the '" + expectedUrlPart + "' end point."
        );

        Allure.step("Assert that user can see the test question.");
        assertThat(question).isVisible();

        Allure.step("Assert that question text contains question mark.");
        assertThat(question).containsText(TestData.QUESTION_MARK);

        Allure.step("Assert that at least one answer is shown.");
        Assert.assertTrue(
                testTutorPage.getAnswersCount() > 0,
                "If FAIL: The multiple-choice test should contain at least one answer.\n");

        Allure.step("Assert that chapter test page has buttons " + TestData.LIST_OF_TUTOR_TEST_FOOTER_BUTTONS);
        Assert.assertEquals(
                TestData.LIST_OF_TUTOR_TEST_FOOTER_BUTTONS, testTutorPage.listOfButtonNamesInFooter(),
                "If FAIL: Chapter 1 test tutor page does NOT has buttons "
                        + TestData.LIST_OF_TUTOR_TEST_FOOTER_BUTTONS + " on the footer."
        );
    }
}