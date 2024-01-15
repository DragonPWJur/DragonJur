package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class StudyGuidePage extends SideMenuPage {
    private final Locator wordList = waitForListLoadedGetByText("Projections");
    private final Locator noteButton = button(getWordText());
    private final Locator noteTextAria = locator("//textarea");
    private final Locator saveButton = button("Save");
    private final Locator highlightsAndNotesButton = button("Highlights and notes");

    public StudyGuidePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getNoteButton() {
        return noteButton;
    }

    public Locator getNoteTextAria() {
        return noteTextAria;
    }

    public Locator getWordFromList() {
        return wordList.nth(1);
    }

    public StudyGuidePage clickNoteSaveButton() {
        saveButton.click();

        return this;
    }

    public StudyGuidePage inputNoteText() {
        noteTextAria.fill("s");

        return this;
    }


    public StudyGuidePage doubleClickWord() {
        wordList.nth(1).dblclick();

        return this;
    }

    public StudyGuidePage clickHighlightsAndNotesButton() {
        highlightsAndNotesButton.click();

        return this;
    }

    public String getWordText() {
        return wordList.nth(1).textContent();
    }
}
