package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class ReportAProblemModal extends BaseModal<ReportAProblemModal> implements IRandom {
    private final Locator describeTheProblemTextarea = textbox();
    private final Locator sendButton = button("Send");
    private final Locator reportSentSuccessfullyMessage = exactText("The report has been sent successfully");

    ReportAProblemModal(Page page) {
        super(page);
    }

    @Override
    public ReportAProblemModal init() {

        return new ReportAProblemModal(getPage());
    }

    public ReportAProblemModal inputText() {
        if (describeTheProblemTextarea.isVisible()) {
            describeTheProblemTextarea.fill(getRandomString(10));
        }

        return this;
    }

    public ReportAProblemModal clickSendButton() {
        sendButton.click();

        return this;
    }

    public Locator getReportSentSuccessfullyMessage() {
        getCloseButton().waitFor();
        if (getCloseButton().isVisible()) {

            return reportSentSuccessfullyMessage;
        }

        return null;
    }

}
