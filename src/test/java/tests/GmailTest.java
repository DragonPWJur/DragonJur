package tests;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.runner.GmailUtils;
import utils.reports.ExceptionListener;

@Listeners(ExceptionListener.class)
public final class GmailTest {

    @Ignore
    @Test
    public void testExtractGmailPasswordOauth2() throws Exception {
        String expectedPassword = "MjXQ350#@&";

        String actualPassword = GmailUtils.extractPasswordFromEmail(GmailUtils.getGmailService(), 1021);

        Assert.assertEquals(actualPassword, expectedPassword);
    }
}