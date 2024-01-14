package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PreconditionPage;
import pages.TestTutorPage;
import utils.TestData;
import utils.TestUtils;

public class FlashcardPacksTest extends BaseTest {

    @Test
    public void testUserCanMarkCardsForRechecking() {

        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());

        preconditionPage.startTestDomain2(TestData.ONE);
        preconditionPage.clickRemoveFromFlashcardsButtonIfVisible();
        preconditionPage.endTest();

        String numberOfCardsForReCheckingBefore = preconditionPage.getCurrentNumberOfCardForRechecking();

        preconditionPage.startTestDomain2(TestData.ONE);

        String numberOfCardsForReCheckingAfter = new TestTutorPage(getPage(), getPlaywright())
                .clickAddToFlashCardButton()
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTheTestButton()
                .clickFlashcardsMenu()
                .getNumberMarkedForRechecking();

        Assert.assertEquals(numberOfCardsForReCheckingAfter, TestUtils.addNumber(numberOfCardsForReCheckingBefore, 1));
    }
}
