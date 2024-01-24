package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import io.qameta.allure.Step;
import pages.constants.Constants;
import utils.api.APIUtils;
import utils.api.entity.GuideTable;

public final class StudyGuidePage extends BaseFooter<StudyGuidePage> implements IRandom {
    private final Locator projectionsFirstWord = text("Projections").first();
    private final Locator projectionsFirstButton = button("Projections").first();
    private final Locator noteTextAria = locator("//textarea");
    private final Locator saveButton = button("Save");
    private final Locator highlightsAndNotesButton = button("Highlights and notes");
    private final Locator searchField = placeholder("Search");
    private final Locator nothingFoundMessage = text("Nothing found. Try to use other key words");
    private final Locator searchResultField = locator("div:has(input[placeholder='Search']) + div>div");
    private final Locator longBonesFirstText = text("Long bones").first();
    private final Locator unit1Text = locator("#body .ce-block__content").first();
    private GuideTable guideTablePage;

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

    public Locator getSearchResultField() {

        return searchResultField;
    }

    public StudyGuidePage clickSaveButton() {
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

    public StudyGuidePage inputRandomStringInSearchField() {
        searchField.fill(getRandomString(10));

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

    @Step("Restore the text 'Unit 1 Chapter 1' in the admin part of the 'Study Guide'.")
    public void restoreChapter1Unit1Text(String word) {
        APIUtils.changeChapter1Unit1TextViaAPI(word, false, getPage().request());
    }

    @Step("Change the text 'Unit 1 Chapter 1' in the admin part of the 'Study Guide'.")
    public void changeChapter1Unit1TextViaAPI(String word) {
        APIUtils.changeChapter1Unit1TextViaAPI(word, true, getPage().request());
    }

//    public StudyGuidePage interceptAPIStudyGuideTable() {
//        getPage().route("**/table-of-content", route -> {
//            APIResponse response = route.fetch();
//            setGuideTable(APIServises.getStudyGuideTable(response));
//            route.resume();
//        });
//        return this;
//    }
//
//    public void setGuideTable(GuideTable guideTablePage) {
//        this.guideTablePage = guideTablePage;
//    }
//
//    public GuideTable getGuideTable() {
//
//        return guideTablePage;
//    }

    public String getUnit1Text() {

        return unit1Text.innerText();
    }

    @Step("Reload current page.")
    public void reload() {
        getPage().reload();
        this.waitForPageLoad();
    }
}