package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.TestData.expectedOneCheckboxProgressPoints;

public class HomeTest extends BaseTest {

    @Test
    public void testLoginNavigation() {

        assertThat(getPage()).hasURL(ProjectProperties.BASE_URL + "/home");
    }

    @DataProvider
    public Object[][] sideMenuItems() {
        return new Object[][]{
                {"Home", ProjectProperties.BASE_URL + "/home"},
                {"Study guide", ProjectProperties.BASE_URL + "/study-guide"},
                {"Tests", ProjectProperties.BASE_URL + "/test-list"},
                {"Flashcards", ProjectProperties.BASE_URL + "/flashcard-packs"},
                {"Mnemonic cards", ProjectProperties.BASE_URL + "/mnemoniccard-list"},
                {"Performance", ProjectProperties.BASE_URL + "/performance"},
                {"Profile", ProjectProperties.BASE_URL + "/profile"}
        };
    }

    @Test(dataProvider = "sideMenuItems")
    public void testNavigateToSubMenuItems(String locator, String expectedUrl) {
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(locator)).click();

        assertThat(getPage()).hasURL(expectedUrl);
    }

    @Test
    public void testLocators() {
        HomePage homePage = new HomePage(getPage(), getPlaywright());
        assertThat(homePage.getStudyThisButton()).isVisible();
        homePage.getStudyThisButton().click();
    }

    @Test
    public void testUponClickingCheckboxPointCountIncreases() throws InterruptedException {
        HomePage homePage = new HomePage(getPage(), getPlaywright())
                .clickHomeMenu()
                .focusWeek1Header()
                .verifyWeek1FirstCheckboxUnchecked();

        assertThat(homePage.getProgressbarPoints()).hasText("0");
        assertThat(homePage.getProgressbarSideMenuPoints()).hasText("0");

        homePage
                .clickWeek1FirstCheckbox();

        assertThat(homePage.getWeek1FirstCheckbox()).isChecked();
        assertThat(homePage.getProgressbarPoints()).hasText(expectedOneCheckboxProgressPoints);
        assertThat(homePage.getProgressbarSideMenuPoints()).hasText(expectedOneCheckboxProgressPoints);
    }
}