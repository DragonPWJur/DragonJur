package tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
import utils.ProjectProperties;
import utils.TestData;
import utils.TestUtils;

import java.util.List;

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
        new PreconditionPage(getPage(), getPlaywright())
                .checkIfListCheckBoxesIsNotEmptyAndAllUnchecked();

        List<Locator> listOfTimeButton = new HomePage(getPage(), getPlaywright())
                .getListOfTimeButton().all();
        int randomButton = TestUtils.getRandomInt(0, listOfTimeButton.size());
        new HomePage(getPage(), getPlaywright()).clickNthTimeButton(randomButton);

        List<Locator> checkBoxesList = new HomePage(getPage(), getPlaywright())
                .getListCheckboxes();

        int randomCheckbox = TestUtils.getRandomInt(0, checkBoxesList.size());
        Locator checkedCheckbox = new HomePage(getPage(), getPlaywright())
                .checkNthCheckbox(randomCheckbox)
                .getNthCheckbox(randomCheckbox);

        Locator checkedCheckboxImage = checkedCheckbox.locator(TestData.CHECKBOX_IMAGE_LOCATOR);

        assertThat(checkedCheckbox).isChecked();
        assertThat(checkedCheckboxImage).hasAttribute(TestData.ATTRIBUTE_FILL, TestData.COLOR_CHECKED_CHECKBOX);
    }
}
