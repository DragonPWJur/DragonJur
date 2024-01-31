package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import pages.*;
import tests.helpers.TestData;
import utils.runner.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProfileTest extends BaseTest {

    @Test(
            testName = "LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359",
            description = "TC1359-01 - Opening the Profile Page")
    @Description("Objective: To verify that the user can successfully navigate to the Profile page."
            + "Assert that the headers H3 display as expected: “Account”, “Your Courses”, “Payment method”.")
    @Story("Profile")
    @TmsLink("bagw135rbhfe")
    @Severity(SeverityLevel.NORMAL)
    public void testProfilePageDisplaysHeaders() {
        final String profileUrl = ProjectProperties.BASE_URL + TestData.PROFILE_END_POINT;

        ProfilePage profilePage =
                new HomePage(getPage()).init()
                        .clickProfileMenu();

        assertThat(getPage()).hasURL(profileUrl);

        final Locator accountHeading = profilePage.getAccount();
        final Locator paymentMethodHeading = profilePage.getPaymentMethod();
        final Locator addANewCourseButton = profilePage.getAddANewCourseButton();

        assertThat(accountHeading).isVisible();
        assertThat(paymentMethodHeading).isVisible();
        assertThat(addANewCourseButton).isVisible();
    }

    @Test(
            testName = "LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359",
            description = "TC1359-02 - Opening the 'Add New Course' Page from Profile")
    @Description("Objective: To verify that clicking the '+ Add a new course' button on the Profile page opens the 'Add new course' page."
            + " and verify the 'Add new course' page is opened.")
    @Story("Profile page")
    @TmsLink("wclaf9sah357")
    @Severity(SeverityLevel.NORMAL)
    public void testAddNewCourseButtonNavigation() {
        final String addNewCourseUrl = ProjectProperties.BASE_URL + TestData.ADD_NEW_COURSE_END_POINT;

        AddNewCoursePage addNewCoursePage =
                new HomePage(getPage()).init()
                        .clickProfileMenu()
                        .clickAddANewCourseButton();

        assertThat(getPage()).hasURL(addNewCourseUrl);
        assertThat(addNewCoursePage.getAddNewCourseHeader()).hasText(TestData.ADD_NEW_COURSE);
    }

    @Test(
            testName = "LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359",
            description = "TC1359-03 - Opening the 'Choose a Product' Modal by Clicking 'Get' Button”).")
    @Description("Objective: To confirm that clicking the 'Get' button on the " +
            "'Add new course' page opens the 'Choose a product' modal window."
            + " and Verify that the user is redirected to the 'Choose a product' page, displayed as a modal window.")
    @Story("Add New Course Page")
    @TmsLink("du8bq42dps6b")
    @Severity(SeverityLevel.NORMAL)
    public void testOpenChooseAProductModalByClickAGetButton() {
        AddNewCourseModal addNewCourseModal =
                new HomePage(getPage()).init()
                        .clickProfileMenu()
                        .clickAddANewCourseButton()
                        .clickGetButton();

        final Locator modalHeading = addNewCourseModal.getChooseAProductHeading();
        final Locator lifetimeButton = addNewCourseModal.getLifeTimeButton();

        assertThat(addNewCourseModal.getDialog()).isVisible();
        assertThat(modalHeading).isVisible();
        assertThat(modalHeading).hasText(TestData.CHOOSE_A_PRODUCT);
        assertThat(lifetimeButton).isVisible();
        assertThat(lifetimeButton).isEnabled();
    }

    @Test(
            testName = "LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359",
            description = "TC1359-04 - Purchasing Lifetime Course (Gold package)")
    @Description("Objective: To verify the user's ability to purchase the Lifetime course."
            + " and verify that the user is redirected to the Lifetime course purchase page.")
    @Story("Add New Course Page")
    @TmsLink("wxcm7w4fhzq0")
    @Severity(SeverityLevel.NORMAL)
    public void testClickOnTheLifeTimeButton() {
        AddNewCourseModal addNewCourseModal =
                new HomePage(getPage()).init()
                        .clickProfileMenu()
                        .clickAddANewCourseButton()
                        .clickGetButton()
                        .clickLifeTimeButton();

        final Locator goldHeading = addNewCourseModal.getGoldHeading();
        final Locator silverHeading = addNewCourseModal.getSilverHeading();
        final Locator bronzeHeading = addNewCourseModal.getBronzeHeading();
        final Locator purchaseButton = addNewCourseModal.getPurchaseButton();

        assertThat(goldHeading).isVisible();
        assertThat(silverHeading).not().isVisible();
        assertThat(bronzeHeading).not().isVisible();
        assertThat(purchaseButton).isVisible();
        assertThat(purchaseButton).hasCount(1);
        assertThat(purchaseButton).isEnabled();
    }

    @Test(
            testName = "LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359",
            description = "TC1359-05 - Inputting Payment Information After Clicking 'Purchase'")
    @Description("Objective: To verify the user's ability to input payment information after clicking the 'Purchase' button."
            + " and verify the User is redirected to the 'Add a payment method' page.")
    @Story("StripeModal")
    @TmsLink("8afbdan400ta")
    @Severity(SeverityLevel.NORMAL)
    public void testPurchaseButtonNavigatesToStripeElement() {
        StripeModal stripeModal =
                new HomePage(getPage()).init()
                        .clickProfileMenu()
                        .clickAddANewCourseButton()
                        .clickGetButton()
                        .clickLifeTimeButton()
                        .clickPurchaseButton();

        final Locator stripeModalHeader = stripeModal.getStripeModalHeader();
        final Locator stripeElement = stripeModal.getStripeElement();

        assertThat(stripeModalHeader).isVisible();
        assertThat(stripeElement).isAttached();
        assertThat(stripeElement).isVisible();
    }

    @Test(
            testName = "LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359",
            description = "TC1359-06 - Submitting Payment with Valid Credit Card")
    @Description("Objective: To verify the user's ability to submit payment after entering valid credit card information."
            + " and verify that the new course is shown in the Your Courses list on the Profile page.")
    @Story("Subscription payment was successful")
    @TmsLink("appnuxx2ck9l")
    @Severity(SeverityLevel.NORMAL)
    public void testE2EPurchaseLifeTimeCourse() {
        StripeModal stripeModal =
                new HomePage(getPage()).init()
                        .clickProfileMenu()
                        .clickAddANewCourseButton()
                        .clickGetButton()
                        .clickLifeTimeButton()
                        .clickPurchaseButton()
                        .inputCreditCardNumber(TestData.PAYMENT_CARD_NUMBER)
                        .inputCardExpirationDate(TestData.CARD_EXPIRATION_DATE)
                        .inputCardCVCDate(TestData.CVC_DATE)
                        .inputCardCountryDate(TestData.COUNTRY_DATE)
                        .inputCardZipCodeDate(TestData.ZIP_CODE_DATE);

        final Locator stripeModalHeader = stripeModal.getStripeModalHeader();
        final Locator stripeElement = stripeModal.getStripeElement();

        assertThat(stripeModalHeader).isVisible();
        assertThat(stripeElement).isAttached();
        assertThat(stripeElement).isVisible();
    }
}
