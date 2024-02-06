package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import io.qameta.allure.Step;
import pages.constants.Constants;

import java.util.List;

public final class StudyGuidePage extends BaseFooter<StudyGuidePage> implements IRandom {
    private final Locator noteTextAria = locator("//textarea");
    private final Locator saveButton = button("Save");
    private final Locator highlightsAndNotesButton = button("Highlights and notes");
    private final Locator highlightedWordButton = locator("div>button>span");
    private final Locator searchField = placeholder(Constants.SEARCH);
    private final Locator nothingFoundMessage = text(Constants.NOTHING_FOUND);
    private final Locator searchResultTextbox = locator("div:has(input[placeholder='Search']) + div>div");
    private final Locator unit1Text = locator("#body .ce-block__content").first();
    private final String match = "div:has(button > span) > button:not(:has(> *))";
    private final Locator yesButton = exactButton("Yes");

    StudyGuidePage(Page page) {
        super(page);
    }

    @Override
    public StudyGuidePage init() {

        return createPage(new StudyGuidePage(getPage()), Constants.STUDY_GUIDE_END_POINT);
    }

    public Locator getWord(String word) {

        return text(word).nth(1);
    }

    @Step("Double click on the word '{word}'")
    public StudyGuidePage doubleClickOnWord(String word) {
        getWord(word).dblclick();

        return this;
    }

    @Step("Enter text '{text}' into the note modal.")
    public StudyGuidePage inputNoteText(String text) {
        noteTextAria.fill(text);

        return this;
    }

    public Locator getNoteTextAria() {

        return noteTextAria;
    }

    @Step("Click the 'Highlights and Notes' button")
    public void clickHighlightsAndNotesButton() {
        highlightsAndNotesButton.click();
    }

    public Locator getHighlightedWordButton() {

        return highlightedWordButton;
    }

    @Step("Enter text {text} into the search field")
    public StudyGuidePage inputStringIntoSearchField(String text) {
        searchField.fill(text);

        return this;
    }

    @Step("Get a list of matching words.")
    public List<Locator> getMatchesList() {

        return allItems(match);
    }

    @Step("Click the 'Save' button inside the note modal")
    public StudyGuidePage clickNoteSaveButton() {
        saveButton.click();

        return this;
    }

    @Step("Highlight words '{string}' by moving mouse.")
    public StudyGuidePage highlightWords(String string) {
        final Locator words = exactText(string);

        words.hover();
        BoundingBox box = words.boundingBox();

        getPage().mouse().move(box.x, box.y + 5);
        getPage().mouse().down();
        getPage().mouse().move(box.x + box.width - 5, box.y + 5);
        getPage().mouse().up();

        return this;
    }

    @Step("Get 'Nothing found. Try to use other key words' message")
    public Locator getNothingFoundMessage() {

        return nothingFoundMessage;
    }

    @Step("Get search result message")
    public Locator getSearchResultMessage() {

        return searchResultTextbox;
    }

    public String getUnit1Text() {

        return unit1Text.innerText();
    }

    @Step("Reload current page.")
    public void reload() {
        getPage().reload();
        this.waitForPageLoad();

        new StudyGuidePage(getPage()).init();
    }

    @Step("Scroll to the bottom of the StudyGuide page")
    public StudyGuidePage scrollToPageBottom() {
        waitForPageLoad();
        yesButton.scrollIntoViewIfNeeded();
        return this;
    }

    @Step("Click 'Yes' button.")
    public TestTutorPage clickYesButton() {
        waitForLocator(yesButton, 3000);
        yesButton.click();
        return new TestTutorPage(getPage()).init();
    }

    @Step("Input search word {text} in the search field")
    public StudyGuidePage inputSearchWordInSearchField(String text) {
        searchField.fill(text);

        return this;
    }

    public Locator getWords(String words) {

        return text(words);
    }
}
