package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
import tests.helpers.TestData;
import utils.api.APIUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class StudyThisTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("StudyThis")
    @TmsLink("zhdhkv1f6nk7")
    @Description("LMS-1342 https://app.qase.io/plan/LMS/1?case=1342" +
            "To verify the modal window is open when clicking the Study This button.")
    @Test(description = "TC1342-01 The modal window is open when clicking the Study This button")
    public void testModalWindowStudyIsOpened() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickStudyThisButton();

        Allure.step("Assert that the modal window is opened");
        assertThat(homePage.getDialog()).isVisible();

        final Locator weakestExamAreasHeader = homePage.getWeakestExamAreasHeader();
        final Locator weakestExamAreasMessage = homePage.getWeakestExamAreasMessage();

        Allure.step("Assert that the modal window header has " + TestData.WEAKEST_EXAM_AREAS + " text.");
        assertThat(weakestExamAreasHeader).hasText(TestData.WEAKEST_EXAM_AREAS);

        Allure.step("Assert that the weakest exam areas message is visible.");
        assertThat(weakestExamAreasMessage).isVisible();

        Allure.step("Assert that the weakest exam areas message has text '"
                + TestData.YOU_HAVE_NOT_STUDIED_ENOUGH + "'.");
        assertThat(weakestExamAreasMessage).hasText(TestData.YOU_HAVE_NOT_STUDIED_ENOUGH);
    }

    @Test(
            testName = "LMS-1342 Отработка секции study this. https://app.qase.io/plan/LMS/1?case=1342",
            description = "TC1342-03 The User can select 1-3 domains/chapters to study when his account has 3 worst domains/chapters"
    )
    @Description("Objective: To verify the user's ability to select 1-3 domains/chapters from the list when their account contains the three least proficient knowledge domains/chapters.")
    @Story("Home Page")
    @TmsLink("pt150jap2ioc")
    public void testAbilityToSeeWeakestAreas() throws InterruptedException {

        new PreconditionPage(getPage()).init()
                .startDomainsTestAndAnswerIncorrect(TestData.QUESTIONS_PER_DOMAIN, TestData.QUESTIONS_AMOUNT);

        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickStudyThisButton();

        Thread.sleep(5000);
    }


}
