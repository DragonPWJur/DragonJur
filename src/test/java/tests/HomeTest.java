package tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
import utils.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
    @Ignore
    @Test
    public void testUponClickingCheckboxPointCountIncreases() {
        HomePage homePage = new HomePage(getPage(), getPlaywright())
                .clickHomeMenu()
                .clickTwoWeeksButton()
                .focusWeek1Header();

        int beforeCountPoints = homePage.getProgressbarPointsNumber();
        int beforeCountSideMenuPoints = homePage.getProgressbarSideMenuPointsNumber();

        Assert.assertEquals(beforeCountSideMenuPoints, beforeCountPoints);

        homePage
                .clickWeek1FirstCheckbox();

        getPage().waitForTimeout(2000);

        int afterCountPoints = homePage.getProgressbarPointsNumber();
        int afterCountSideMenuPoints = homePage.getProgressbarSideMenuPointsNumber();

        assertThat(homePage.getWeek1FirstCheckbox()).isChecked();

        Assert.assertTrue(beforeCountPoints < afterCountPoints);
        Assert.assertTrue(beforeCountSideMenuPoints < afterCountSideMenuPoints);
        assertThat(homePage.getProgressbarPoints()).hasText(String.valueOf(beforeCountPoints + 818));
        Assert.assertEquals(homePage.getProgressbarPointsText(), homePage.getProgressbarSideMenuPointsText());
    }

    @Test
    public void testStreaksModalWindowIsAppeared() {
        HomePage homePage = new HomePage(getPage(), getPlaywright())
                .clickHomeMenu()
                .clickStreaksButton();

        assertThat(homePage.getStreaksModalWindow()).isVisible();
    }

    @Test
    public void testTheSingleNonActiveCheckboxCanBeChecked() {
        Assert.assertTrue(new PreconditionPage(getPage(), getPlaywright())
                .checkIfListCheckBoxesIsNotEmptyAndAllUnchecked(), "Precondition is not reached.");

        HomePage homePage = new HomePage(getPage(), getPlaywright());

        boolean isCheckBoxChecked = homePage
                .clickRandomCheckBox()
                .isCheckBoxChecked();

        Locator checkboxImage = homePage.getCheckboxImage();

        Assert.assertTrue(isCheckBoxChecked, "Randomly checked checkbox is expected to be checked, but unchecked.");
        assertThat(checkboxImage).hasCount(1);
        assertThat(checkboxImage).isVisible();
    }
}
