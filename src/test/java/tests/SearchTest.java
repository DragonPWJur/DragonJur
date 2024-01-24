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
                        .inputRandomStringInSearchField(TestData.SEARCH_WORD);

        final Locator nothingFoundMessage = studyGuidePage.getNothingFoundMessage();
        final Locator searchResultMessage = studyGuidePage.getSearchResultMessage();

        assertThat(nothingFoundMessage).isVisible();
        assertThat(searchResultMessage).hasText(TestData.NOTHING_FOUND);
    }

//    @Test
//    public void testSearchByExistingKeyWord() {
//        List<Locator> listOfMatches = new ArrayList<>();
//
//        listOfMatches.add(getPage().locator("button:not(:has(> *))"));
//
//        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Study guide")).click();
//
//        getPage().getByPlaceholder("Search").fill(EXISTING_KEYWORD);
//
//        for (int i = 3; i <= listOfMatches.size(); i++) {
//            assertThat(getPage()
//                    .locator("listOfMatches.get(i)"))
//                    .hasText(EXISTING_KEYWORD);
//        }
//    }
}