package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;
import utils.api.APIUtils;

public class StudyGuideAdminTest extends BaseTest {
    @Severity(SeverityLevel.NORMAL)
    @Story("StudyGuide")
    @TmsLink("rxlgx1r82okx")
    @Description("LMS-TC1360-01 User is able to see the Study Guide text. https://app.qase.io/plan/LMS/1?case=1360" +
            "   Objective:  To verify that user is able to see the study guide text.")
    @Test(description = "TC1360-01 - The user sees that the changes made in the admin site Study Guide appear on the user website.")
    public void testTextContentChanges() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu();

        Allure.step("Assert that Study Guide text does Not contains the word '" + TestData.TEST + "'.");
        Assert.assertFalse(
                studyGuidePage.getUnit1Text().contains(TestData.TEST),
                "If FAIL: The expected text '" + TestData.TEST + "' was found within the content of the unit.\n"
        );

        APIUtils
                .adminChangeChapter1Unit1Text(TestData.TEST, "add");

        studyGuidePage
                .reload();


        Allure.step("Assert that Study Guide text now contains the word " + TestData.TEST);
        Assert.assertTrue(studyGuidePage.getUnit1Text().contains(TestData.TEST),
                "If FAIL: The expected text '" + TestData.TEST + "'was NOT found within the content of the unit.\n"
        );
    }

    @AfterMethod
    public void restoreChapter1Unit1TextOnAdmin() {
        APIUtils
                .adminChangeChapter1Unit1Text(TestData.TEST, "remove");
    }
}