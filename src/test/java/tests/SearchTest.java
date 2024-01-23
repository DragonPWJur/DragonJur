package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchTest extends BaseTest {

    @Test(
            testName = "TC1361-01 - Typing of not found text gives 'Nothing found. Try to use other keywords” message'.",
            description = "LMS-1361 https://app.qase.io/project/LMS?suite=184&case=1361"
    )
    public void testSearchByNotExistingKeyWord() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuide()
                        .inputRandomStringInSearchField();

        assertThat(studyGuidePage.getNothingFoundMessage()).isVisible();
        assertThat(studyGuidePage.getSearchResultField()).hasText(TestData.NOTHING_FOUND_MESSAGE);
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