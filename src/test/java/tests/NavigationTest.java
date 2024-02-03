package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import tests.helpers.TestData;
import utils.runner.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NavigationTest extends BaseTest {

    @Severity(SeverityLevel.CRITICAL)
    @Story("Navigation")
    @Test(description = "TC0001 Verify that the user can land on the home page.")
    public void testLoginNavigation() {

        assertThat(getPage()).hasURL(ProjectProperties.BASE_URL + TestData.HOME_END_POINT);
    }

    @Severity(SeverityLevel.BLOCKER)
    @Story("Navigation")
    @Test(dataProvider = "sideMenuItems", dataProviderClass = TestData.class,
            description = "TC0002 Verify that the user can navigate within the application.")
    public void testNavigateToSubMenuItems(String menuName, String expectedUrl) {
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(menuName)).click();

        assertThat(getPage()).hasURL(expectedUrl);
    }
}
