package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class ReportAProblemModal extends BaseModal<ReportAProblemModal> implements IRandom {
    private final Locator describeTheProblemTextarea = textbox();
    private final Locator sendButton = button("Send");
    private final Locator reportMessage = locator(".ReactModalPortal div:has(svg) span");
    ReportAProblemModal(Page page) {
        super(page);
    }

    @Override
    public ReportAProblemModal init() {

        return new ReportAProblemModal(getPage());
    }

    public ReportAProblemModal inputText(String text) {
        if (describeTheProblemTextarea.isVisible()) {
            describeTheProblemTextarea.fill(text);
        }

        return this;
    }

    public ReportAProblemModal clickSendButton() {
        sendButton.click();

        return new ReportAProblemModal(getPage());
    }

    public Locator getReportMessage() {
        waitWithTimeout(2000);

        return reportMessage;
    }

    public Locator getDescribeTheProblemTextarea() {
        return describeTheProblemTextarea;
    }

    public Locator getSendButton() {
        return sendButton;
    }
}
