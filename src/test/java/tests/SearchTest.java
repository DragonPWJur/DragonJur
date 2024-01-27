package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchTest extends BaseTest {

    @Test(
            testName = "LMS-1361 Поиск по конкретному слову. https://app.qase.io/plan/LMS/1?case=1361",
            description = "TC1361-01 - Typing of not found text gives 'Nothing found. Try to use other keywords' message."
    )
    @Description("Objective: To confirm the display of the 'Nothing found. Try to use other keywords' message when" +
            " a non-existent keyword is typed.")
    @Story("Search")
    @TmsLink("m9rydpfuvuw6")
    public void testSearchByNotExistingKeyWord() {

        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .inputStringIntoSearchField(TestData.SEARCH_WORD);

        final Locator nothingFoundMessage = studyGuidePage.getNothingFoundMessage();
        final Locator searchResultMessage = studyGuidePage.getSearchResultMessage();

        assertThat(nothingFoundMessage).isVisible();
        assertThat(searchResultMessage).hasText(TestData.NOTHING_FOUND);
    }

//    @Test
//    public void testSearchByExistingKeyWord() {
//
//        StudyGuidePage studyGuidePage =
//                new HomePage(getPage()).init()
//                        .clickStudyGuideMenu()
//                        .inputSearchWord(TestData.BONE);
//
//        final List<String> searchResultText = studyGuidePage.getSearchResultText();
//
//        System.out.println(searchResultText);
//
//        for (String result : searchResultText) {
//            Assert.assertTrue(
//                    result.toLowerCase().contains(TestData.BONE),
//                    "If FAIL: Search result '" + result + "' does NOT contains the search word '" + TestData.BONE + "'.\n"
//            );
//        }
//    }
}