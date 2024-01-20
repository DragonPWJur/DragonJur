package pages;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;

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
    protected Locator exactHeading(String text) {
        return getPage().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(text).setExact(true));
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

    protected Locator exactText(String text) {

        return getPage().getByText(text, new Page.GetByTextOptions().setExact(true));
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

    protected Locator dialog() {

        return getPage().getByRole(AriaRole.DIALOG);
    }

    protected Locator buttonInBanner(String text) {
        return getPage().getByRole(AriaRole.BANNER)
                .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(text));
    }

    protected Locator locator(String css) {

        return getPage().locator(css);
    }

    protected Locator checkBoxImage(int number) {

        return locator("label:has(input):nth-child(" + (number)  + ") svg");
    }

    protected List<Locator> checkBoxesAll(String css) {

        locator(css).first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        locator(css).last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

        return locator(css).all();
    }

    protected List<Locator> radioButtonsAll() {
        radio().first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        radio().last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

        return radio().all();
    }

    protected void cancelDialog() {
        if (dialog().isVisible() && button("Cancel").isVisible()) {
            getPage().onDialog(Dialog::dismiss);
            button("Cancel").click();
        }
    }

    protected Locator waitForListLoadedGetByText(String string) {
        Locator list = getPage().getByText(string);
        list.last().waitFor();

        return list;
    }

    protected Locator waitForListOfElementsLoaded(Locator locator) {
        locator.last().waitFor();
        return locator;
    }

    protected Locator numberMarked() {
        return text("Marked").getByText("1");
    }

    protected Locator textbox() {
        return getPage().getByRole(AriaRole.TEXTBOX);
    }

    protected Locator placeholder(String text) {
        return getPage().getByPlaceholder(text);
    }
}