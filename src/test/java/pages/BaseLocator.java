package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

abstract class BaseLocator extends BasePage {

    protected BaseLocator(Page page, Playwright playwright) {
        super(page, playwright);
    }

    protected Locator button(String text) {
        return getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator exactButton(String text) {
        return getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(text).setExact(true));
    }

    protected Locator link(String text) {
        return getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator exactLink(String text) {
        return getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text).setExact(true));
    }

    protected Locator heading(String text) {
        return getPage().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator label(String text) {
        return getPage().getByLabel(text);
    }

    protected Locator dataId(String text) {
        getPlaywright().selectors().setTestIdAttribute("data-id");
        return getPage().getByTestId(text);
    }

    protected Locator text(String text) {
        return getPage().getByText(text);
    }

    protected Locator radio(String text) {
        return getPage().getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator alert() {
        return getPage().getByRole(AriaRole.ALERT);
    }

    protected Locator image(String text) {
        return getPage().getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName(text));
    }

    protected void clickButton(String text) {
        button(text).click();
    }
}