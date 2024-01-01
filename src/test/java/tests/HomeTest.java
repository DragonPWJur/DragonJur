package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomeTest extends BaseTest {

    @Test
    public void testLoginNavigation() {

        assertThat(getPage()).hasURL(getBaseUrl() + "/home");
    }

    @DataProvider
    public Object[][] sideMenuItems() {
        return new Object[][]{
                {"Home", getBaseUrl() + "/home"},
                {"Study guide", getBaseUrl() + "/study-guide"},
                {"Tests", getBaseUrl() + "/test-list"},
                {"Flashcards", getBaseUrl() + "/flashcard-packs"},
                {"Mnemonic cards", getBaseUrl() + "/mnemoniccard-list"},
                {"Performance", getBaseUrl() + "/performance"},
                {"Profile", getBaseUrl() + "/profile"}
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
        assertThat(homePage.studyThisButton).isVisible();
        homePage.studyThisButton.click();
    }
}