package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import tests.helpers.TestData;
import utils.runner.ProjectProperties;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class StudyThisTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("StudyThis")
    @TmsLink("zhdhkv1f6nk7")
    @Description("LMS-1342 https://app.qase.io/plan/LMS/1?case=1342" +
            "To verify the modal window is open when clicking the Study This button.")
    @Test(description = "TC1342-01 The modal window is open when clicking the Study This button")
    public void testModalWindowStudyIsOpened() {
        StudyThisModal studyThisModal =
                new HomePage(getPage()).init()
                        .clickStudyThisButton();

        Allure.step("Assert that the modal window is opened");
        assertThat(studyThisModal.getDialog()).isVisible();

        final Locator weakestExamAreasHeader = studyThisModal.getWeakestExamAreasHeader();
        final Locator weakestExamAreasMessage = studyThisModal.getWeakestExamAreasMessage();

        Allure.step("Assert that the modal window header '" + TestData.WEAKEST_EXAM_AREAS + "' is visible.");
        assertThat(weakestExamAreasHeader).isVisible();

        Allure.step("Assert that the weakest exam areas message is visible.");
        assertThat(weakestExamAreasMessage).isVisible();

        Allure.step("Assert that the weakest exam areas message has text '"
                + TestData.YOU_HAVE_NOT_STUDIED_ENOUGH + "'.");
        assertThat(weakestExamAreasMessage).hasText(TestData.YOU_HAVE_NOT_STUDIED_ENOUGH);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("StudyThis")
    @TmsLink("pt150jap2ioc")
    @Description("LMS-1342 Отработка секции study this. https://app.qase.io/plan/LMS/1?case=1342" +
            "Objective: To verify the user's ability to select 1-3 domains/chapters from the list " +
            "when their account contains the three least proficient knowledge domains/chapters.")
    @Test(description = "TC1342-02 The User can select 1-3 domains/chapters to study when his account " +
            "has 3 worst domains/chapters.")
    public void testAbilityToChooseWeakestAreas() {
        new PreconditionPage(getPage()).init()
                .startDomainsTestAndAnswerIncorrect(TestData.QUESTIONS_PER_DOMAIN, TestData.QUESTIONS_PER_TEST);

        HomePage homePage = new HomePage(getPage()).init();

        homePage.waitForAPIPrecondition(5000);

        StudyThisModal studyThisModal =
                homePage
                        .clickStudyThisButton();

        final Locator weakestAreaHeader = studyThisModal.getWeakestExamAreasHeader();
        final List<Locator> weakestAreasCheckboxes = studyThisModal.getWeakestAreasCheckboxes();

        Allure.step("Assert that the modal window header '" + TestData.WEAKEST_EXAM_AREAS + "' is visible.");
        assertThat(weakestAreaHeader).isVisible();

        Allure.step("Assert that 3 weakest areas are displayed on the page.");
        Assert.assertEquals(weakestAreasCheckboxes.size(), 3);
        assertThat(weakestAreasCheckboxes.get(0)).isVisible();
        assertThat(weakestAreasCheckboxes.get(1)).isVisible();
        assertThat(weakestAreasCheckboxes.get(2)).isVisible();

        studyThisModal
                .selectWeakestAreas(0, 2);

        Allure.step("Assert that weakest area '" + weakestAreasCheckboxes.get(0).innerText() + "' is selected.");
        assertThat(weakestAreasCheckboxes.get(0)).isChecked();

        Allure.step("Assert that weakest area '" + weakestAreasCheckboxes.get(2).innerText() + "' is selected.");
        ;
        assertThat(weakestAreasCheckboxes.get(2)).isChecked();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("StudyThis")
    @TmsLink("qbgvf8do0237")
    @Description("LMS-1342 Отработка секции study this. https://app.qase.io/plan/LMS/1?case=1342" +
            "Objective: To verify the user's ability to select 1-3 domains/chapters from the list " +
            "when their account contains the three least proficient knowledge domains/chapters.")
    @Test(description = "TC1342-03 The User can study 1-3  domains/chapters when his account has 3 worst domains/chapters.")
    public void testAbilityToStudyWeakestAreas() {
        new PreconditionPage(getPage()).init()
                .startDomainsTestAndAnswerIncorrect(TestData.QUESTIONS_PER_DOMAIN, TestData.QUESTIONS_PER_STUDY);

        HomePage homePage = new HomePage(getPage()).init();

        homePage.waitForAPIPrecondition(5000);

        TestTutorPage testTutorPage =
                homePage
                        .clickStudyThisButton()
                        .selectWeakestAreas(0, 2)
                        .clickStudyWeakestAreasButton();

        final String expectedUrl = ProjectProperties.BASE_URL + TestData.TEST_TUTOR_END_POINT;
        final Locator question = testTutorPage.getTestQuestion();
        final List<Locator> answers = testTutorPage.getRadioButtons();

        Allure.step("Assert that user was redirected to '" + TestData.TEST_TUTOR_END_POINT + "' page.");
        assertThat(getPage()).hasURL(expectedUrl);

        Allure.step("Assert that user can see the test question.");
        assertThat(question).isVisible();

        Allure.step("Assert that question text contains question mark.");
        assertThat(question).containsText(TestData.QUESTION_MARK);

        Allure.step("Assert that at least one answer is shown.");
        Assert.assertTrue(
                answers.size() >= 1,
                "If FAIL: There are no answers for the question."
        );
    }
}
