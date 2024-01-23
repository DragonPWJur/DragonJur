
//package tests;
//
//import io.qameta.allure.*;
//import com.microsoft.playwright.Locator;
//import org.testng.Assert;
//import org.testng.annotations.Ignore;
//import org.testng.annotations.Test;
//import pages.*;
//import pages.HomePage;
//import pages.TestListPage;
//import pages.TestTimedPage;
//import pages.TestsPage;
//import tests.helpers.TestData;
//
//import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
//import static utils.runner.ProjectProperties.BASE_URL;
//
//public class TestListTest extends BaseTest {
//
//    @Test(description = "TC1344-01 The single non-active Checkbox can be checked.")
//    @Description("Objective: To verify that a non-active checkbox ca be successfully checked.")
//    @Story("Tests")
//    @TmsLink("9lf328qwx4bv")
//    public void testTheSingleCheckboxCanBeChecked() {
//        String checkboxText = new HomePage(getPage(), getPlaywright())
//                        .clickTestsMenu()
//                        .cancelDialogIfVisible()
//                        .clickDomainsButton()
//                        .clickRandomCheckboxAndReturnItsName();
//
//        assertThat(new TestListPage(getPage(), getPlaywright()).checkIcon(checkboxText)).isVisible();
//    }
//
//    @Test(description = "TC1344-02 Execute Tutor Mode with a randomly selected checkbox in the Domain section.")
//    @Description("Objective: To verify that User can successfully activate Tutor mode by checking a random checkbox " +
//            "in the Domain section and entering valid data in the ‘Number of Questions’ field.")
//    @Story("Tests")
//    @TmsLink("9a0yelvj0jdh")
//    public void testTutorModeWithRandomCheckboxInDomain() {
//        TestsPage testsPage = new HomePage(getPage(), getPlaywright())
//                .clickTestsMenu()
//                .cancelDialogIfVisible()
//                .clickDomainsButton()
//                .clickRandomCheckbox()
//                .clickTutorButton()
//                .inputNumberOfQuestions(TestData.ONE)
//                .clickGenerateAndStartButton();
//
//        waitForPageLoad(TestData.TEST_TUTOR_END_POINT);
//
//        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
//        assertThat(testsPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
//        Assert.assertTrue(testsPage.countTestRadioButtons() >= 1);
//    }
//    @Ignore
//    @Test
//    public void testTutorModeWithRandomCheckboxInChapter() {
//        TestsPage testsPage = new HomePage(getPage(), getPlaywright())
//                .clickTestsMenu()
//                .cancelDialogIfVisible()
//                .clickChaptersButton()
//                .clickRandomCheckbox()
//                .clickTutorButton()
//                .inputNumberOfQuestions(TestData.ONE)
//                .clickGenerateAndStartButton();
//
//        waitForPageLoad(TestData.TEST_TUTOR_END_POINT);
//
//        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
//        assertThat(testsPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
//        Assert.assertTrue(testsPage.countTestRadioButtons() >= 1);
//    }
//
//    @Test
//    public void testRunTimedMode() {
//        TestTimedPage testTimedPage = new HomePage(getPage(), getPlaywright())
//                .clickTestsMenu()
//                .cancelDialogIfVisible()
//                .clickTimedButton()
//                .clickGenerateAndStartButton1()
//                .clickStartTestButton()
//                .clickStartButton();
//
//        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TIMED_END_POINT);
//        assertThat(testTimedPage.getTimer()).isVisible();
//        assertThat(testTimedPage.getQuestionMark()).containsText(TestData.QUESTION_MARK);
//        Assert.assertTrue(testTimedPage.getAnswersCount() > 0);
//    }
//
//    @Ignore
//    @Test
//    public void testAfterMarkingTheCardTheNumberOfMarkedCardsIncreasedBy1() {
//        new PreconditionPage(getPage(), getPlaywright())
//                .startTest(TestData.ONE);
//
//        Locator numberMarked = new TestTutorPage(getPage(), getPlaywright())
//                .clickMarkForReviewButton()
//                .clickEndButton()
//                .clickYesButton()
//                .clickSkipButton()
//                .clickCloseTheTestButton()
//                .getNumberMarked();
//
//        assertThat(numberMarked).isVisible();
//    }
//}
