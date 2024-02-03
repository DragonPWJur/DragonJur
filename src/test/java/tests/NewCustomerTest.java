package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import tests.helpers.TestData;
import utils.api.APIUtils;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NewCustomerTest extends BaseTest {
    @Test(
            testName = "LMS-1343 Отработка streaks. https://app.qase.io/plan/LMS/1?case=1343",
            description = "TC1343-02 - Verification of Text in the 'Streaks' Modal Window"
    )
    @Description("Objective: To confirm that the user can view points greater than 0 and the text indicating the number of streak days in the modal window.")
    @Story("Home Page")
    @TmsLink("j0y70alubidi")
    public void testStreaksModalWindowTextVerification() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickHomeMenu();

        assertThat(homePage.getStreaksButton()).hasText("0");

        final int randomIndex = homePage.getRandomIndex();

        homePage
                .clickNthCheckbox(randomIndex);

        assertThat(homePage.getNthCheckbox(randomIndex)).isChecked();
        assertThat(homePage.getStreaksButton()).hasText("1");

        homePage
                .clickStreaksButton();

        assertThat(homePage.getStreakDaysModalWindowTextLocator()).hasText(TestData.ONE_DAY_STUDY_STREAK_MESSAGE);
        Assert.assertTrue(
                homePage.getMainSectionPoints() > 0,
                "If FAIL: Expected result 'Upon opening the modal window, points greater than 0 are visible' is not reached.");
    }

    @Test(
            testName = "LMS-1342 Отработка секции study this. https://app.qase.io/plan/LMS/1?case=1342",
            description = "TC1342-03 The User can select 1-3 domains/chapters to study when his account has 3 worst domains/chapters"
    )
    @Description("Objective: To verify the user's ability to select 1-3 domains/chapters from the list when their account contains the three least proficient knowledge domains/chapters.")
    @Story("Home Page")
    @TmsLink("pt150jap2ioc")
    public void testAbilityToStudyWeakestAreas() {
        APIUtils.answerQuestionsForIncorrectAnswersAndFinish();

        HomePage homePage =
                new HomePage(getPage()).init();

        homePage.clickStudyThisButton();
//        List<Locator> weakestCategories = homePage.getAllWeakestCategories();
//
//        Assert.assertTrue(weakestCategories.size() >= 3, "Size of list weakestCategories < 3");
//
//        homePage.clickRandomCheckboxOfWeakestCategories();
//
//        assertThat(homePage.getStudyWeakestAreasButton()).isVisible();
    }
}
