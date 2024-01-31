package tests;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.reports.ExceptionListener;
import utils.runner.GmailUtils;

import static utils.api.APINewCustomerUtils.EMAIL_END_PART;
import static utils.runner.ProjectProperties.COMMON_EMAIL_PART;

@Listeners(ExceptionListener.class)
public final class GmailTest {

    @Ignore
    @Test
    public void testExtractGmailPasswordOauth2() throws Exception {
        String expectedPassword = "MjXQ350#@&";

        String actualPassword = GmailUtils.extractPasswordFromEmail(GmailUtils.getGmailService(), COMMON_EMAIL_PART + 1021 + EMAIL_END_PART);

        Assert.assertEquals(actualPassword, expectedPassword);
    }
}