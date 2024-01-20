package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PerformancePage extends BaseSideMenu {
    private final Locator overallButton = button("Overall");
    private final Locator testsButtonInBanner = buttonInBanner("Tests");
    private final Locator dropdownLocator = locator(".bIQFHP");
    private final Locator totalTests = text("Tests").locator("~span");
    private final Locator correctPercentageText = exactText("Total:")
            .first()
            .locator("~div>span:first-child");
    private final Locator correctNumberText = exactText("Total:")
            .first()
            .locator("~div>span:last-child");
    private final Locator incorrectPercentageText = exactText("Total:")
            .last()
            .locator("~div>span:first-child");
    private final Locator incorrectNumberText = exactText("Total:")
            .last()
            .locator("~div>span:last-child");
    private final Locator lastTestLocator = locator(".eaNKjL>div:nth-child(2)");
    public PerformancePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public PerformancePage clickOverallDropdown() {
        overallButton.click();

        return this;
    }

    public PerformancePage clickDropdown() {
        dropdownLocator.click();

        return this;
    }

    public PerformancePage clickTestsButtonInBanner() {
        testsButtonInBanner.click();

        return  this;
    }

    public Locator getSettedFilter() {
        return dropdownLocator;
    }

    public int getNumberOfQuestions() {
        return Integer.parseInt(totalTests.innerText().replaceAll("[^0-9]", ""));
    }

    public double getCorrectPercentage() {
        return Double.parseDouble(correctPercentageText.innerText().replace("%", ""));
    }

    public int getCorrectNumbers() {
        return Integer.parseInt(correctNumberText.innerText());
    }

    public double getIncorrectPercentage() {
        return Double.parseDouble(incorrectPercentageText.innerText().replace("%", ""));
    }

    public int getIncorrectNumbers() {
        return Integer.parseInt(incorrectNumberText.innerText());
    }

    public PerformancePage setLastTest() {
        lastTestLocator.click();

        return this;
    }
}
