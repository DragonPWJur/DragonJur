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

        assertThat(getPage()).hasURL(baseUrl + "/home");
    }

    @DataProvider
    public Object[][] sideMenuItems() {
        return new Object[][]{
                {"Home", baseUrl + "/home"},
                {"Study guide", baseUrl + "/study-guide"},
                {"Tests", baseUrl + "/test-list"},
                {"Flashcards", baseUrl + "/flashcard-packs"},
                {"Mnemonic cards", baseUrl + "/mnemoniccard-list"},
                {"Performance", baseUrl + "/performance"},
                {"Profile", baseUrl + "/profile"}
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
    public void test_TC1359_04_Purchasing_Lifetime_Course () {

        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                        .setName("Profile"))
                .click();
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                        .setName("Add a new course"))
                .click();
        getPage().locator("div:nth-child(3) > .sc-jKDlA-D > .sc-dkzDqf")
                .click();
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                        .setName("Life time"))
                .click();
        // getPage().getByRole(AriaRole.HEADING).isVisible();

        assertThat(getPage().getByText("Gold"));
        assertThat(getPage().getByText("Silver")).not().isVisible();
        assertThat(getPage().getByText("Bronze")).not().isVisible();

        // assertThat(getPage().getByText("Life time"));
        // assertThat(getPage().getByText("Most effective"));

    }
}