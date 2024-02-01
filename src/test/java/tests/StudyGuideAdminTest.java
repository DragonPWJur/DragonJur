package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.StudyGuidePage;
import tests.helpers.TestData;
import utils.api.APIUtils;


@Ignore
public class StudyGuideAdminTest extends BaseTest {


    @Test(
            testName = "LMS-TC1360-01 User is able to see the Study Guide text. https://app.qase.io/plan/LMS/1?case=1360",
            description = "TC1360-01 - The user sees that the changes made in the admin site Study Guide appear on the user website."
    )
    @Description("Objective:  To verify that user is able to see the study guide text.")
    @Story("StudyGuide")
    @TmsLink("rxlgx1r82okx")
    public void testTextContentChanges() {
        StudyGuidePage studyGuidePage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu();

        Assert.assertFalse(
                studyGuidePage.getUnit1Text().contains(TestData.TEST),
                "If FAIL: The expected text '" + TestData.TEST + "' was found within the content of the unit.\n"
        );

        APIUtils
                .adminChangeChapter1Unit1Text(TestData.TEST, "add");

        studyGuidePage
                .reload();

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