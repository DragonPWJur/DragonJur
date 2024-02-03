package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Search")
    @TmsLink("m9rydpfuvuw6")
    @Description("LMS-1361 Поиск по конкретному слову. https://app.qase.io/plan/LMS/1?case=1361"
            + "Objective: To confirm the display of the 'Nothing found. Try to use other keywords' message when" +
            " a non-existent keyword is typed.")
    @Test(description = "TC1361-01 - Typing of not found text gives 'Nothing found. Try to use other keywords' message.")
    public void testSearchByNotExistingWord() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .inputStringIntoSearchField(TestData.SEARCH_WORD);

        final Locator nothingFoundMessage = studyGuidePage.getNothingFoundMessage();
        final Locator searchResultMessage = studyGuidePage.getSearchResultMessage();

        Allure.step("Assert that '" + TestData.NOTHING_FOUND + "' message is visible.");
        assertThat(nothingFoundMessage).isVisible();

        Allure.step("Assert that '" + TestData.NOTHING_FOUND + "' message has text '" + TestData.NOTHING_FOUND + "'.");
        assertThat(searchResultMessage).hasText(TestData.NOTHING_FOUND);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Search")
    @TmsLink("vdluszdw85zh")
    @Description("LMS-1361 Поиск по конкретному слову. https://app.qase.io/plan/LMS/1?case=1361"
            + "   Objective: To confirm that searching a keyword displays all partial or full matches under" +
            " the Search field.")
    @Test(description = "TC1361-02 - Displaying All Matches for the Searched Keyword.")
    public void testSearchByExistingWord() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .inputSearchWordInSearchField(TestData.STAND);

        final List<Locator> matchesList = studyGuidePage.getMatchesList();

        Allure.step( "Assert that each matching word from the list contains the search word '" + TestData.STAND + "'");
        for (Locator match : matchesList) {
            assertThat(match).containsText(TestData.STAND);
        }
    }
}