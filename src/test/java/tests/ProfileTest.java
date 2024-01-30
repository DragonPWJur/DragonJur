package tests;

import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;
import pages.*;
import tests.helpers.TestData;
import utils.runner.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProfileTest extends BaseTest {

    @Test
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

    @Test
    public void testAddNewCourseButtonNavigation() {
        final String addNewCourseUrl = ProjectProperties.BASE_URL + TestData.ADD_NEW_COURSE_END_POINT;

        AddNewCoursePage addNewCoursePage =
                new HomePage(getPage()).init()
                        .clickProfileMenu()
                        .clickAddANewCourseButton();

        assertThat(getPage()).hasURL(addNewCourseUrl);
        assertThat(addNewCoursePage.getAddNewCourseHeader()).hasText(TestData.ADD_NEW_COURSE);
    }


    @Test
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

    @Test
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

    @Test
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

    @Test
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
