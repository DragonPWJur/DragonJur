package tests;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
import pages.TestTutorPage;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestTutorTest extends BaseTest {

    @Test
    public void testUserCanMarkTheCard() {
        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());
        preconditionPage.startTest(TestData.ONE);

        Locator removeFromMarkedButton = new TestTutorPage(getPage(), getPlaywright())
                .clickMarkForReviewButton()
                .getRemoveFromMarkedButton();

        assertThat(removeFromMarkedButton).isVisible();
    }

    @Test
    public void testUserCanSeeTheRightAnswerAndTheExplanation() {

        TestTutorPage testTutorPage = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckbox()
                .clickTutorButton()
                .inputNumberOfQuestions(TestData.ONE)
                .clickGenerateAndStartButton2()
                .clickCorrectAnswerRadioButton()
                .clickConfirmButton();

        assertThat(testTutorPage.getCorrectAnswerBackgroundColor()).isVisible();
        assertThat(testTutorPage.getH3Header()).hasText(TestData.EXPLANATION);
        assertThat(testTutorPage.getH3HeaderExplanationText()).isVisible();
        Assert.assertFalse(testTutorPage.getExplanationText().isEmpty(), "Explanation text is empty");
    }

    @Test
    public void testSuccessfulReportSubmission() {
        new PreconditionPage(getPage(), getPlaywright()).startTest(TestData.ONE);

        TestTutorPage testTutorPage = new TestTutorPage(getPage(), getPlaywright())
                .clickReportButton()
                .inputSymbolsIntoReportAProblemTextarea()
                .clickSendButton();

        assertThat(testTutorPage.getReportAProblemModal()).isVisible();
        assertThat(testTutorPage.getReportSentSuccessfullyMessage()).hasText(TestData.REPORT_MESSAGE);
    }

    @Test
    public void getTestResult() {

        HomePage homePage = new HomePage(getPage(), getPlaywright());
        int beforeHomeCountPoints = homePage.getProgressbarPointsNumber();
        int beforeHomeCountSideMenuPoints = homePage.getProgressbarSideMenuPointsNumber();

        TestTutorPage testTutorPage = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckbox()
                .inputNumberOfQuestions(TestData.ONE)
                .clickGenerateAndStartButton2()
                .clickCorrectAnswerRadioButton()
                .clickConfirmButton()
                .clickEndButton()
                .clickYesButton();

        getPage().waitForTimeout(1000);

        int testCongratulationCountPoints = testTutorPage
                .getCongratulationPoints();

        System.out.println("test Congratulation CountPoints1 " + testCongratulationCountPoints);
        System.out.println("Congratulation points number " + testTutorPage.getCongratulationPointsText());

        Assert.assertTrue(beforeHomeCountPoints < testCongratulationCountPoints);

        int testCountPoints2 = testTutorPage
                .clickTestNextButton()
                .getTestProgressbarPointsNumber();

        System.out.println("test progressBarr CountPoints2: " + testCountPoints2);

        Assert.assertTrue(beforeHomeCountPoints < testCountPoints2);
        Assert.assertEquals(String.valueOf(testCongratulationCountPoints), testTutorPage.getTestProgressbarPointsText());

        testTutorPage
                .clickTestOkButton()
                .clickCloseTheTestButton()
                .clickHomeMenu();

        int afterHomeCountPoints = homePage.getProgressbarPointsNumber();
        int afterHomeCountSideMenuPoints = homePage.getProgressbarSideMenuPointsNumber();

        System.out.println("afterHomeCountPoints: " + afterHomeCountPoints);
        System.out.println("afterHomeCountSideMenuPoints: " + afterHomeCountPoints);

        Assert.assertTrue(beforeHomeCountPoints < afterHomeCountPoints);
        Assert.assertTrue(beforeHomeCountSideMenuPoints < afterHomeCountSideMenuPoints);
        assertThat(homePage.getProgressbarPoints()).hasText(TestData.CORRECT_ANSWER_POINTS);

        Assert.assertEquals(homePage.getProgressbarPointsText(), homePage.getProgressbarSideMenuPointsText());
        Assert.assertEquals(String.valueOf(testCongratulationCountPoints), homePage.getProgressbarPointsText());
        Assert.assertEquals(String.valueOf(testCountPoints2), homePage.getProgressbarPointsText());
    }
}
