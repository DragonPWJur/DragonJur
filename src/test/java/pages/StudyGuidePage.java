package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import io.qameta.allure.Step;
import pages.constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class StudyGuidePage extends BaseFooter<StudyGuidePage> implements IRandom{
    private final Locator projectionsFirstWord = text("Projections").first();
    private final Locator projectionsFirstButton = button("Projections").first();
    private final Locator noteTextAria = locator("//textarea");
    private final Locator saveButton = button("Save");
    private final Locator highlightsAndNotesButton = button("Highlights and notes");
    private final Locator searchField = placeholder(Constants.SEARCH);
    private final Locator nothingFoundMessage = text(Constants.NOTHING_FOUND);
    private final Locator searchResultTextbox = locator("div:has(input[placeholder='Search']) + div>div");
    private final Locator longBonesFirstText = text("Long bones").first();
    private final Locator unit1Text = locator("#body .ce-block__content").first();
    private final Locator yesButton = locator("button:not(:has(> *))").first();
    private final Locator searchResult = locator("div:has(button > span) > button:not(:has(> *))");

    StudyGuidePage(Page page) {
        super(page);
    }

    @Override
    public StudyGuidePage init() {

        return createPage(new StudyGuidePage(getPage()), Constants.STUDY_GUIDE_END_POINT);
    }

    public Locator getNoteTextAria() {

        return noteTextAria;
    }

    public Locator getProjectionsFirstWord() {
        projectionsFirstWord.waitFor();

        return projectionsFirstWord;
    }

    public Locator getNothingFoundMessage() {

        return nothingFoundMessage;
    }

    public Locator getSearchResultMessage() {

        return searchResultTextbox;
    }

    public StudyGuidePage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public StudyGuidePage clickNoteSaveButton() {
        saveButton.click();

        return this;
    }

    public StudyGuidePage inputNoteText(String text) {
        noteTextAria.fill(text);

        return this;
    }

    public StudyGuidePage doubleClickOnWord() {
        getProjectionsFirstWord().dblclick();

        return this;
    }

    public StudyGuidePage clickHighlightsAndNotesButton() {
        highlightsAndNotesButton.click();

        return this;
    }

    public String getWordText() {

        return getProjectionsFirstWord().textContent();
    }

    public StudyGuidePage inputRandomStringInSearchField(String text) {
        searchField.fill(text);

        return this;
    }

    public StudyGuidePage highlightWords() {
        longBonesFirstText.hover();
        BoundingBox box = longBonesFirstText.boundingBox();

        getPage().mouse().move(box.x, box.y + 10);
        getPage().mouse().down();
        getPage().mouse().move(box.x + box.width, box.y + 10);
        getPage().mouse().up();

        return this;
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

    public StudyGuidePage inputSearchWord(String word) {
        searchField.fill(word);

        return this;
    }

    public List<String> getSearchResultText() {
        waitWithTimeout(2000);
        List<Locator> searchResultsList = allItems("div:has(button > span) > button:not(:has(> *))");

        List<String> resultsText = new ArrayList<>();
        for(Locator result : searchResultsList) {
            resultsText.add(result.innerText());
        }

        return resultsText;
    }

//    public List<String> getAllSearchResultText() {
//
//      searchResult.innerText();
//
//    }

}
