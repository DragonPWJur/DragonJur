package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import io.qameta.allure.Step;
import pages.constants.Constants;
import tests.helpers.TestData;

import java.util.ArrayList;
import java.util.List;

public final class PerformancePage extends BaseSideMenu<PerformancePage> {
    private final Locator overallButton = button("Overall");
    private final Locator testsButtonInBanner = buttonInBanner("Tests");
    private final Locator overallButtonInBanner = buttonInBanner("Overall").nth(1);
    private final Locator allFlashcardsButtonInBanner = buttonInBanner("All flashcards");
    private final Locator dropdownLocator = locator(".bIQFHP");
    private final Locator totalTests = text("Tests").locator("~span");
    private final Locator totalFlashcards = text("Flashcards").locator("~span");
    private final Locator correctPercentageText = exactText("Total:").first().locator("~div>span:first-child");
    private final Locator correctNumberText = exactText("Total:").first().locator("~div>span:last-child");
    private final Locator incorrectPercentageText = exactText("Total:").last().locator("~div>span:first-child");
    private final Locator incorrectNumberText = exactText("Total:").last().locator("~div>span:last-child");
    private final Locator lastTestLocator = locator(".eaNKjL>div:nth-child(2)");
    private final Locator yesPercentageText = exactText("Total:").first().locator("~div>span:first-child");
    private final Locator yesNumberText = exactText("Total:").first().locator("~div>span:last-child");
    private final Locator kindaPercentageText = exactText("Total:").nth(1).locator("~div>span:first-child");
    private final Locator kindaNumberText = exactText("Total:").nth(1).locator("~div>span:last-child");
    private final Locator noPercentageText = exactText("Total:").nth(2).locator("~div>span:first-child");
    private final Locator noNumberText = exactText("Total:").nth(2).locator("~div>span:last-child");
    private final Locator unusedPercentageText = exactText("Total:").last().locator("~div>span:first-child");
    private final Locator unusedNumberText = exactText("Total:").last().locator("~div>span:last-child");

    private final List<Locator> stacks = getStackList();

    PerformancePage(Page page) {
        super(page);
    }

    @Override
    public PerformancePage init() {

        return createPage(new PerformancePage(getPage()), Constants.PERFORMANCE_END_POINT);
    }

    @Step("Click on “Overall”.")
    public PerformancePage clickOverallDropdown() {
        overallButton.click();

        return this;
    }

    @Step("Click dropdown from Overall")
    public PerformancePage clickDropdown() {
        dropdownLocator.click();

        return this;
    }

    @Step("Click on “Tests” in the drop-down menu")
    public PerformancePage clickTestsButtonInBanner() {
        testsButtonInBanner.click();

        return this;
    }

    public Locator getDropdownFilter() {

        return dropdownLocator;
    }

    public int getNumberOfQuestions() {

        return Integer.parseInt(totalTests.innerText().replaceAll("[^0-9]", ""));
    }

    public int getNumberOfFlashcards() {

        return Integer.parseInt(totalFlashcards.innerText().replaceAll("[^0-9]", ""));
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

    public double getYesPercentageText() {

        return Double.parseDouble(yesPercentageText.innerText().replace("%", ""));
    }

    public int getYesNumberText() {

        return Integer.parseInt(yesNumberText.innerText());
    }

    public double getKindaPercentageText() {

        return Double.parseDouble(kindaPercentageText.innerText().replace("%", ""));
    }

    public int getKindaNumberText() {

        return Integer.parseInt(kindaNumberText.innerText());
    }

    public double getNoPercentageText() {

        return Double.parseDouble(noPercentageText.innerText().replace("%", ""));
    }

    public int getNoNumberText() {

        return Integer.parseInt(noNumberText.innerText());
    }

    public double getUnusedPercentageText() {

        return Double.parseDouble(unusedPercentageText.innerText().replace("%", ""));
    }

    public int getUnusedNumberText() {

        return Integer.parseInt(unusedNumberText.innerText());
    }

    public List<Locator> getStacks() {

        return stacks;
    }

    public List<Locator> getStackList() {
        List<Locator> stackList = new ArrayList<>();

        for (String stackName : TestData.STACKS_NAMES) {
            stackList.add(exactText(stackName).locator(".."));
        }

        return stackList;
    }

    public PerformancePage setLastTest() {
        lastTestLocator.click();

        return this;
    }

    public Locator getTestsButtonInBanner() {

        return testsButtonInBanner;
    }

    public Locator getOverallButtonInBanner() {

        return overallButtonInBanner;
    }

    public Locator getAllFlashcardsButtonInBanner() {

        return allFlashcardsButtonInBanner;
    }

    @Step("Click 'All flashcards' filter.")
    public void clickAllFlashcardsButtonInBanner() {
        allFlashcardsButtonInBanner.click();
        waitForPageLoad();
        waitWithTimeout(10000);
    }
}
