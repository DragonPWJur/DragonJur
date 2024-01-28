package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;

import java.util.List;

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
    public void testSearchByNotExistingWord() {

        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .inputStringIntoSearchField(TestData.SEARCH_WORD);

        final Locator nothingFoundMessage = studyGuidePage.getNothingFoundMessage();
        final Locator searchResultMessage = studyGuidePage.getSearchResultMessage();

        assertThat(nothingFoundMessage).isVisible();
        assertThat(searchResultMessage).hasText(TestData.NOTHING_FOUND);
    }

    @Test(
            testName = "LMS-1361 Поиск по конкретному слову. https://app.qase.io/plan/LMS/1?case=1361",
            description = "TC1361-02 - Displaying All Matches for the Searched Keyword."
    )
    @Description("Objective: To confirm that searching a keyword displays all partial or full matches under" +
            " the Search field.")
    @Story("Search")
    @TmsLink("vdluszdw85zh")
    public void testSearchByExistingWord() {

        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .inputSearchWordInSearchField(TestData.STAND);

        final List<Locator> matchesList = studyGuidePage.getMatchesList();

        for (Locator match : matchesList) {
            assertThat(match).containsText(TestData.STAND);
        }
    }
}