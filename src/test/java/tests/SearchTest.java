package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchTest extends BaseTest{

    @Test
    public void testSearchByNotExistingKeyWord() {
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Study guide")).click();

        getPage().getByPlaceholder("Search").fill("abc");

        assertThat(getPage().getByText("Nothing found. Try to use other key words")).isVisible();

        assertThat(getPage()
                .locator("div:has(input[placeholder='Search']) + div>div"))
                .hasText("Nothing found. Try to use other key words");
    }
}