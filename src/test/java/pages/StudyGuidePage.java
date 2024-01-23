package pages;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.BoundingBox;
import utils.APIServises;
import utils.TestData;
import utils.TestUtils;
import utils.api.obj.Chapter;
import utils.api.obj.GuideTable;
import utils.api.obj.Unit;

public class StudyGuidePage extends BaseSideMenu {
    private final Locator noteTextAria = locator("//textarea");
    private final Locator saveButton = button("Save");
    private final Locator highlightsAndNotesButton = button("Highlights and notes");
    private final Locator searchField = placeholder("Search");
    private final Locator nothingFoundMessage = text("Nothing found. Try to use other key words");
    private final Locator searchResultField = locator("div:has(input[placeholder='Search']) + div>div");
    private final Locator unit1Text = locator("#body .ce-block__content").first();
    private GuideTable guideTablePage;

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

    public void restoreChapter1Unit1Text() {
        changeChapter1Unit1TextViaAPI(TestData.WORD_TEST, false);
    }

    public void changeChapter1Unit1TextViaAPI(String testText, boolean isAdd) {
        GuideTable guideTable = APIServises.getStudyGuideTable(getPlaywright());
        Chapter chapter1 = guideTable.getChapters().get(0);
        chapter1 = APIServises.getUnitByGuideIdAndChapterId(getPlaywright(), guideTable.getId(), chapter1.getId());
        Unit unit1 = chapter1.getUnits().get(0);

        String text = unit1.getContent().getBlocks().get(0).getData().getText();
        if (isAdd) {
            text = testText + text;
        } else {
            text = text.substring(text.indexOf(testText) + testText.length());
        }

        unit1.getContent().getBlocks().get(0).getData().setText(text);

        APIServises.editUnitByGuideIdAndChapterId(getPlaywright(), unit1);
    }

    public StudyGuidePage interceptAPIStudyGuideTable() {
        getPage().route("**/table-of-content", route -> {
            APIResponse response = route.fetch();
            setGuideTable(APIServises.getStudyGuideTable(response));
            route.resume();
        });
        return this;
    }

    public void setGuideTable(GuideTable guideTablePage) {
        this.guideTablePage = guideTablePage;
    }

    public GuideTable getGuideTable() {

        return guideTablePage;
    }

    public String getUnit1Text() {

        return unit1Text.innerText();
    }

    public StudyGuidePage  reload() {
        getPage().reload();

        return this;
    }
}
