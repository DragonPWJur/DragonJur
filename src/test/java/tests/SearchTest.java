//package tests;
//
//import com.microsoft.playwright.Locator;
//import com.microsoft.playwright.Page;
//import com.microsoft.playwright.options.AriaRole;
//import org.testng.annotations.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
//
//public class SearchTest extends BaseTest {
//
//    private final String EXISTING_KEYWORD = "stand";
//
//    @Test
//    public void testSearchByNotExistingKeyWord() {
//        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Study guide")).click();
//
//        getPage().getByPlaceholder("Search").fill("abc");
//
//        assertThat(getPage().getByText("Nothing found. Try to use other key words")).isVisible();
//
//        assertThat(getPage()
//                .locator("div:has(input[placeholder='Search']) + div>div"))
//                .hasText("Nothing found. Try to use other key words");
//    }
//
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
//}
package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchTest extends BaseTest {

//    private final String EXISTING_KEYWORD = "stand";
//
//    @Test
//    public void testSearchByNotExistingKeyWord() {
//        StudyGuidePage studyGuidePage = new HomePage(getPage(), getPlaywright())
//                .clickStudyGuide()
//                .inputRandomStringInSearchField();
//
//        assertThat(studyGuidePage.getNothingFoundMessage()).isVisible();
//        assertThat(studyGuidePage.getSearchResultField()).hasText(TestData.NOTHING_FOUND_MESSAGE);
//    }
//
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