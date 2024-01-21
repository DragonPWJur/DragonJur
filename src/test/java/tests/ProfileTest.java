package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import utils.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProfileTest extends BaseTest {

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


    @Test
    public void testOpenChooseAProductByClickAGetButton() {
        new HomePage(getPage(), getPlaywright())
                .clickProfileMenu()
                .clickAddANewCourseButton()
                .clickGetButton();

        assertThat(getPage().getByText("Choose a product")).isVisible();
    }

    @Test
    public void testClickOnTheLifeTimeButton(){

        new HomePage(getPage(), getPlaywright())
                .clickProfileMenu()
                .clickAddANewCourseButton()
                .clickGetButton()
                .clickLifeTimeButton()
                .visibleHeadingChooseAProduct();

        assertThat(getPage().getByText("Life time"));
        assertThat(getPage().getByText("Gold")).isVisible();
        assertThat(getPage().getByText("Silver")).not().isVisible();
        assertThat(getPage().getByText("Bronze")).not().isVisible();

    }
}
