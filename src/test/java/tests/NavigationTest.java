package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;
import tests.helpers.TestData;
import utils.runner.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NavigationTest extends BaseTest {

    @Test
    public void testLoginNavigation() {

        assertThat(getPage()).hasURL(ProjectProperties.BASE_URL + TestData.HOME_END_POINT);
    }

    @Test(dataProvider = "sideMenuItems", dataProviderClass = TestData.class)
    public void testNavigateToSubMenuItems(String menuName, String expectedUrl) {
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(menuName)).click();

        assertThat(getPage()).hasURL(expectedUrl);
    }
}
