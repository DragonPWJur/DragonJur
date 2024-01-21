package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.TestUtils;

public class ReportAProblemModal extends BaseModal {
    private final Locator describeTheProblemTextarea = textbox();
    private final Locator sendButton = button("Send");
    private final Locator reportSentSuccessfullyMessage = exactText("The report has been sent successfully");

    protected ReportAProblemModal(Page page) {
        super(page);
    }

    public ReportAProblemModal inputText() {
        if (describeTheProblemTextarea.isVisible()) {
            describeTheProblemTextarea.fill(TestUtils.geteRandomString(10));
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

    public Locator getReportAProblemModal() {
        return dialog();
    }

}
