package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import pages.*;
import tests.helpers.TestData;
import utils.runner.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PaymentTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Profile")
    @TmsLink("bagw135rbhfe")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "    Objective: To verify that the user can successfully navigate to the Profile page."
            + "Assert that the headers H3 display as expected: “Account”, “Your Courses”, “Payment method”.")
    @Test(description = "TC1359-01 - Profile Page has Course Purchase course options.")
    public void testProfilePageDisplaysBuyNewCourseHeaders() {
        final String profileUrl = ProjectProperties.BASE_URL + TestData.PROFILE_END_POINT;

        ProfilePage profilePage =
                new HomePage(getPage()).init()
                        .clickProfileMenu();

        Allure.step("Assert that user can land on Profile page (" + TestData.PROFILE_END_POINT + ").");
        assertThat(getPage()).hasURL(profileUrl);

        final Locator accountHeading = profilePage.getAccount();
        final Locator paymentMethodHeading = profilePage.getPaymentMethod();
        final Locator addANewCourseButton = profilePage.getAddANewCourseButton();

        Allure.step("Assert that 'Account' heading is visible.");
        assertThat(accountHeading).isVisible();

        Allure.step("Assert that 'Payment method' heading is visible.");
        assertThat(paymentMethodHeading).isVisible();

        Allure.step("Assert that 'Add a New Course' button is visible.");
        assertThat(addANewCourseButton).isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("AddNewCourse")
    @TmsLink("wclaf9sah357")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "Objective: To verify that clicking the '+ Add a new course' button on the Profile page opens the 'Add new course' page."
            + " and verify the 'Add new course' page is opened.")
    @Test(description = "TC1359-02 - Opening the 'Add New Course' Page from Profile")
    public void testAddNewCourseButtonNavigation() {
        final String addNewCourseUrl = ProjectProperties.BASE_URL + TestData.ADD_NEW_COURSE_END_POINT;

        AddNewCoursePage addNewCoursePage =
                new HomePage(getPage()).init()
                        .clickProfileMenu()
                        .clickAddANewCourseButton();

        Allure.step("Assert that user can land on Add new course page (" + TestData.ADD_NEW_COURSE_END_POINT + ").");
        assertThat(getPage()).hasURL(addNewCourseUrl);

        Allure.step("Assert that 'Add new course' page header is '" + TestData.ADD_NEW_COURSE + "'.");
        assertThat(addNewCoursePage.getAddNewCourseHeader()).hasText(TestData.ADD_NEW_COURSE);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("AddNewCourse")
    @TmsLink("du8bq42dps6b")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "Objective: To confirm that clicking the 'Get' button on the " +
            "'Add new course' page opens the 'Choose a product' modal window."
            + " and Verify that the user is redirected to the 'Choose a product' page, displayed as a modal window.")
    @Test(description = "TC1359-03 - Opening the 'Choose a Product' Modal by Clicking 'Get' Button”.")
    public void testOpenChooseAProductModalByClickAGetButton() {
        AddNewCourseModal addNewCourseModal =
                new HomePage(getPage()).init()
                        .clickProfileMenu()
                        .clickAddANewCourseButton()
                        .clickGetButton();

        final Locator modalHeading = addNewCourseModal.getChooseAProductHeading();
        final Locator lifetimeButton = addNewCourseModal.getLifeTimeButton();

        Allure.step("Assert that after clicking on 'Get' button, the new modal window is opened.");
        assertThat(addNewCourseModal.getDialog()).isVisible();

        Allure.step("Assert that the new modal window displays the heading.");
        assertThat(modalHeading).isVisible();

        Allure.step("Assert that the heading text is '" + TestData.CHOOSE_A_PRODUCT + "'.");
        assertThat(modalHeading).hasText(TestData.CHOOSE_A_PRODUCT);

        Allure.step("Assert that the 'Lifetime' button is visible.");
        assertThat(lifetimeButton).isVisible();

        Allure.step("Assert that the 'Lifetime' button is enabled.");
        assertThat(lifetimeButton).isEnabled();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("AddNewCourse")
    @TmsLink("wxcm7w4fhzq0")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "Objective: To verify the user's ability to purchase the Lifetime course."
            + " and verify that the user is redirected to the Lifetime course purchase page.")
    @Test(description = "TC1359-04 - Purchasing Lifetime Course (Gold package)")
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

//        Allure.step("Assert that the 'Gold' option is available.");
//        assertThat(goldHeading).isVisible();

        Allure.step("Assert that the 'Silver' option is not available.");
        assertThat(silverHeading).not().isVisible();

        Allure.step("Assert that the 'Bronze' option is not available.");
        assertThat(bronzeHeading).not().isVisible();

        Allure.step("Assert that the 'Purchase' button is visible.");
        assertThat(purchaseButton).isVisible();

        Allure.step("Assert that the only one 'Purchase' button is present.");
        assertThat(purchaseButton).hasCount(1);

        Allure.step("Assert that the 'Purchase' button is enabled.");
        assertThat(purchaseButton).isEnabled();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("StripeModal")
    @TmsLink("8afbdan400ta")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "Objective: To verify the user's ability to input payment information after clicking the 'Purchase' button."
            + " and verify the User is redirected to the 'Add a payment method' page.")
    @Test(description = "TC1359-05 - Inputting Payment Information After Clicking 'Purchase'")
    public void testPurchaseButtonOpensStripeElement() {
        StripeModal stripeModal =
                new HomePage(getPage()).init()
                        .clickProfileMenu()
                        .clickAddANewCourseButton()
                        .clickGetButton()
                        .clickLifeTimeButton()
                        .clickPurchaseButton();

        final Locator stripeModalHeader = stripeModal.getStripeModalHeader();
        final Locator stripeElement = stripeModal.getStripeElement();

        Allure.step("Assert that Stripe payment modal window is visible.");
        assertThat(stripeModalHeader).isVisible();

        Allure.step("Assert that Stripe payment element is attached.");
        assertThat(stripeElement).isAttached();

        Allure.step("Assert thet Stripe payment element is visible.");
        assertThat(stripeElement).isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("StripeModal")
    @TmsLink("appnuxx2ck9l")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "Objective: To verify the user's ability to submit payment after entering valid credit card information."
            + " and verify that the new course is shown in the Your Courses list on the Profile page.")
    @Test(description = "TC1359-06 - Submitting Payment with Valid Credit Card")
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
                        .inputCardCVC(TestData.CVC)
                        .inputCardCountry(TestData.COUNTRY)
                        .inputZipCode(TestData.ZIP_CODE);

        final Locator stripeModalHeader = stripeModal.getStripeModalHeader();
        final Locator stripeElement = stripeModal.getStripeElement();

        Allure.step("Assert that Stripe payment  form Header 'Add a payment method' is visible.");
        assertThat(stripeModalHeader).isVisible();

        Allure.step("Assert that Stripe payment element is attached.");
        assertThat(stripeElement).isAttached();

        Allure.step("Assert that Stripe payment element is visible.");
        assertThat(stripeElement).isVisible();
    }
}
