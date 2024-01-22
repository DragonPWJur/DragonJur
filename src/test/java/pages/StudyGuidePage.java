package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.BoundingBox;
import utils.TestData;
import utils.TestUtils;

public class StudyGuidePage extends BaseSideMenu {
    private final Locator noteTextAria = locator("//textarea");
    private final Locator saveButton = button("Save");
    private final Locator highlightsAndNotesButton = button("Highlights and notes");
    private final Locator searchField = placeholder("Search");
    private final Locator nothingFoundMessage = text("Nothing found. Try to use other key words");
    private final Locator searchResultField = locator("div:has(input[placeholder='Search']) + div>div");

    public StudyGuidePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getNoteButtonForWord() {
        return button(getWordText());
    }

    public Locator getNoteTextAria() {
        return noteTextAria;
    }

    public Locator getWord() {
        Locator list = getPage().getByText(TestData.PROJECTIONS);
        list.first().waitFor();
        return list.nth(1);
    }

    public Locator getNothingFoundMessage() {
        return nothingFoundMessage;
    }

    public Locator getSearchResultField() {
        return searchResultField;
    }

    public StudyGuidePage clickNoteSaveButton() {
        saveButton.click();

        return this;
    }

    public StudyGuidePage inputNoteText() {
        noteTextAria.fill("s");

        return this;
    }

    public StudyGuidePage doubleClickOnWord() {
        getWord().dblclick();

        return this;
    }

    public StudyGuidePage clickHighlightsAndNotesButton() {
        highlightsAndNotesButton.click();

        return this;
    }

    public String getWordText() {
        return getWord().textContent();
    }

    public StudyGuidePage inputRandomStringInSearchField() {
        searchField.fill(TestUtils.geteRandomString(10));

        return this;
    }

    public Locator getMultipleWords() {
        Locator list = getPage().getByText(TestData.LONG_BONES);
        list.first().waitFor();
        return list.nth(1);
    }

    public StudyGuidePage highlightWords() {
        getMultipleWords().hover();
        BoundingBox box = getMultipleWords().boundingBox();

        getPage().mouse().move(box.x, box.y + 10);
        getPage().mouse().down();
        getPage().mouse().move(box.x + box.width, box.y + 10);
        getPage().mouse().up();

        return this;
    }
}
