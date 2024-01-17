package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import utils.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProfileTest extends BaseTest{
    protected static final String expectedUrl = ProjectProperties.BASE_URL + "/profile";

    @Test
    public void testNavigationToTheProfilePage() {

        new HomePage(getPage(), getPlaywright())
                .clickProfileMenu();

        assertThat(getPage()).hasURL(expectedUrl);
    }
}
