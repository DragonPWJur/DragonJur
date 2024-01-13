package pages;

import com.microsoft.playwright.Dialog;
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

    protected Locator radio() {
        return getPage().getByRole(AriaRole.RADIO);
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

    protected Locator waitForListOfElementsLoaded(String string) {
        Locator list = getPage().locator(string);
        list.last().waitFor();
        return list;
    }

    public Locator waitForListLoadedGetByText(String string) {
        Locator list = getPage().getByText(string);
        list.last().waitFor();

        return list;
    }

    protected Locator dialog() {
        return getPage().getByRole(AriaRole.DIALOG);
    }

    protected  void cancelDialog() {
        if (dialog().isVisible()) {
            getPage().onDialog(Dialog::dismiss);
            button("Cancel").click();
        }
    }

    protected Locator locator(String string) {
        return getPage().locator(string);
    }
}