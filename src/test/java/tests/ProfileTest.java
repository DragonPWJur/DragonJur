package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import utils.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProfileTest extends BaseTest{

    @Test
    public void testNavigationToTheProfilePage() {

        new HomePage(getPage(), getPlaywright())
                .clickProfileMenu();

        assertThat(getPage()).hasURL(ProjectProperties.BASE_URL + "/profile");
    }

    @Test
    public void testAddNewCourseButtonNavigation() {

        new HomePage(getPage(), getPlaywright())
                .clickProfileMenu()
                .clickAddANewCourseButton();

        assertThat(getPage()).hasURL(ProjectProperties.BASE_URL + "/add-new-course");
        assertThat(getPage().getByTitle("Add a new course"));
    }
}
