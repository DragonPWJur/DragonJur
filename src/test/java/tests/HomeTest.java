package tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ProjectProperties;
import utils.TestUtils;

import javax.swing.text.Utilities;
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

    @Ignore
    @Test
    public void testTheSingleNonActiveCheckboxCanBeChecked() throws InterruptedException {

//    Objective: To verify that a non-active checkbox can be successfully checked.
//
//    Preconditions:
//    User is logged in to the application.
//    User has navigated to the Home page.
//    At least one checkbox is shown under the Learning Scheduler section
//    No checkboxes are currently selected.
//    Test Steps:
//    Randomly select the single checkbox.
//    Click on the checkbox.
//    Expected Results:
//    After selecting the unchecked checkbox, it should transition to a “checked” state.
//1. Assert that it has a “checked” state after clicking on the unchecked checkbox.
//2. Assert that the “checked” image is displayed inside the box.


       Locator checkbox = new HomePage(getPage(), getPlaywright())
                .getCheckboxUnderLearningSchedulerSection();

        Assert.assertTrue(checkbox.count() > 0);

        boolean allUnchecked = checkbox.all().stream().anyMatch(Locator::isChecked);

        Assert.assertFalse(allUnchecked);

        int number = TestUtils.getRandomNumber(checkbox);

        new HomePage(getPage(), getPlaywright())
//                .clickCheckbox(checkbox, number)
                .clickCheckbox(checkbox, 1)

        ;
        Thread.sleep(3000);

        Assert.assertFalse(allUnchecked);

    }
}