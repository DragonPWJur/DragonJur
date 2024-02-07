package tests;

import com.microsoft.playwright.Page;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CongratulationsModal;
import pages.FlashcardPacksPage;
import pages.FlashcardsPackIDPage;
import pages.HomePage;
import tests.helpers.TestData;
import tests.helpers.TestUtils;
import utils.runner.ProjectProperties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FlashcardsLogicTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("FlashcardsLogic")
    @TmsLink("")
    @Description("LMS-1390 Логика Yes 1. https://app.qase.io/plan/LMS/1?case=1390"
            + "Нажать Yes. При завершении стопки начисляются поинты.")
    @Test(description = "TC1390-01 - Possibility to leave a “Yes” mark and get points.")
    public void testYes1Logic() {
        HomePage homePage = new HomePage(getPage()).init();

        final int mainSectionPointsBefore = homePage.getMainSectionPoints();

        Allure.step("Assert that Main Section Points before (" + mainSectionPointsBefore + ") are equal to 0.");
        Assert.assertEquals(
                mainSectionPointsBefore, 0,
                "If FAIL: Points on Main Section (" + mainSectionPointsBefore + ") are NOT equals to 0.\n"
        );

        CongratulationsModal congratulationsModal =
                homePage
                        .clickFlashcardsMenu()
                        .clickAutotestFlashcardsPack()
                        .clickGotItButton()
                        .clickShowAnswerButton()
                        .clickYesMarkButton()
                        .clickEndButtonToCongratulationsModal()
                        .clickYesToCongratulationButton();

        final int pointsOnModalAfter = congratulationsModal.getCongratulationPoints();

        Allure.step("Assert that points on 'Congratulations' modal window (" + pointsOnModalAfter + ") after test " +
                " are greater or equals to Main Section Points before (" + mainSectionPointsBefore + ").");
        Assert.assertTrue(
                mainSectionPointsBefore <= pointsOnModalAfter,
                "If FAIL: On Congratulation pop-up, expected points after does NOT increased."
        );

        congratulationsModal
                .clickNextButton();

        final int secondModalPointsAfter = congratulationsModal.getPoints();

        Allure.step("Assert that points on second 'Congratulations' modal window (" + secondModalPointsAfter
                + ") after  " + " are greater or equals to Main Section Points before  ("
                + mainSectionPointsBefore + ").");
        Assert.assertTrue(mainSectionPointsBefore <= secondModalPointsAfter,
                "If FAIL: On Congratulation second pop-up, expected points after  does NOT increased."
        );

        congratulationsModal
                .clickOkToFlashcardsButton()
                .clickHomeMenu();

        final int mainSectionPointsAfter = homePage.getMainSectionPoints();

        Allure.step("Assert that Main Section Points after (" + mainSectionPointsAfter + ") are greater then " +
                "Main Section Points before (" + mainSectionPointsBefore + ").");
        Assert.assertTrue(
                mainSectionPointsAfter > mainSectionPointsBefore,
                "If FAIL: Points on Main Section after (" + mainSectionPointsAfter + ") are NOT increased.\n"
        );

        Allure.step("Assert that Points on main section after (" + mainSectionPointsAfter + ")" +
                " are equals to Congratulations modal points (" + pointsOnModalAfter + ").");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("FlashcardsLogic")
    @TmsLink("")
    @Description("LMS-1391 Логика Yes 2. https://app.qase.io/plan/LMS/1?case=1391"
            + "LMS-1395 Логика Kinda 1. https://app.qase.io/plan/LMS/1?case=1395"
            + "LMS-1399 Логика No 1. https://app.qase.io/plan/LMS/1?case=1399"
            + "Objective: Verify that the user can successfully leave a 'Yes/Kinda/No' mark and next " +
            "flashcard would be shown if there more cards in the pack.")
    @Test(description = "TC1391-1395-1399 - Possibility to leave a “Yes/Kinda/No” mark and see the next card.",
            dataProvider = "yesKindaNoButtons", dataProviderClass = TestData.class)
    public void testYes2Kinda1No1Logic(String buttonName) {
        HomePage homePage = new HomePage(getPage()).init();

        FlashcardsPackIDPage flashcardsPackIDPage =
                homePage
                        .clickFlashcardsMenu()
                        .clickAutotestFlashcardsPack()
                        .clickGotItButton();

        Allure.step("Assert that the first flashcard with text '" + TestData.FIRST_CARD_TEXT + "' is shown.");
        assertThat(getPage().getByText(TestData.FIRST_CARD_TEXT)).isVisible();

        flashcardsPackIDPage
                .clickShowAnswerButton();

        switch (buttonName) {
            case "Yes" -> flashcardsPackIDPage.clickYesMarkButton();
            case "Kinda" -> flashcardsPackIDPage.clickKindaMarkButton();
            case "No" -> flashcardsPackIDPage.clickNoMarkButton();
        }

        Allure.step("Assert that 'Show Answer' button is  visible.");
        assertThat(flashcardsPackIDPage.getShowAnswerButton()).isVisible();

        Allure.step("Assert that the first flashcard with text '" + TestData.FIRST_CARD_TEXT + "' is Not shown.");
        assertThat(getPage().getByText(TestData.FIRST_CARD_TEXT)).not().isVisible();

        Allure.step("Assert that the next flashcard with text '" + TestData.NEXT_CARD_TEXT + "' is shown.");
        assertThat(getPage().getByText(TestData.NEXT_CARD_TEXT)).isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("FlashcardsLogic")
    @TmsLink("")
    @Description("LMS-1392 Логика Yes 3. https://app.qase.io/plan/LMS/1?case=1392"
            + "Нажать Yes. При завершении стопки количество Learned увеличивается на 1.")
    @Test(description = "TC1392-01 - 'Learned' should increased by 1 after leaving a “Yes” mark.")
    public void testYes3Logic() {
        HomePage homePage = new HomePage(getPage()).init();

        FlashcardPacksPage flashcardPacksPage =
                homePage
                        .clickFlashcardsMenu();

        final String learnedAmountBefore = flashcardPacksPage.getLearnedCardsAmount();
        final String expectedLearnedAmountAfter = TestUtils.add(learnedAmountBefore, 1);

        flashcardPacksPage
                .clickAutotestFlashcardsPack()
                .clickGotItButton()
                .clickShowAnswerButton()
                .clickYesMarkButton()
                .clickEndButtonToCongratulationsModal()
                .clickYesToCongratulationButton()
                .clickNextButton()
                .clickOkToFlashcardsButton();

        final String learnedAmountAfter = flashcardPacksPage.getLearnedCardsAmount();

        Allure.step("Assert that Learned cards amount before (" + learnedAmountBefore + ") increased by 1 (" +
                learnedAmountAfter + ").");
        Assert.assertEquals(
                learnedAmountAfter, expectedLearnedAmountAfter,
                "If FAIL: Learned cards amount before (" + learnedAmountBefore + ") NOT increased.\n"
        );
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("FlashcardsLogic")
    @TmsLink("")
    @Description("LMS-1393 Логика Yes 4. https://app.qase.io/plan/LMS/1?case=1393"
            + "LMS-1396 Логика Kinda 2. https://app.qase.io/plan/LMS/1?case=1396"
            + "LMS-1400 Логика No 2. https://app.qase.io/plan/LMS/1?case=1400"
            + "Нажать 'Yes/Kinda/No'. В 'Show all flashcards'/'Yes/Kinda/No' отображается отмеченная карточка.")
    @Test(description = "TC1393-1396-1400 - В 'Show all flashcards'/'Yes/Kinda/No' отображается отмеченная карточка.",
            dataProvider = "yesKindaNoButtons", dataProviderClass = TestData.class)
    public void testYes4Kinda2No2Logic(String buttonName) {
        HomePage homePage = new HomePage(getPage()).init();

        FlashcardsPackIDPage flashcardsPackIDPage =
                homePage
                        .clickFlashcardsMenu()
                        .clickAutotestFlashcardsPack()
                        .clickGotItButton()
                        .clickShowAnswerButton();

        Allure.step("Assert that '" + TestData.FIRST_CARD_TEXT + "' is visible.");
        assertThat(getPage().getByText(TestData.FIRST_CARD_TEXT)).isVisible();

        switch (buttonName) {
            case "Yes" -> flashcardsPackIDPage.clickYesMarkButton();
            case "Kinda" -> flashcardsPackIDPage.clickKindaMarkButton();
            case "No" -> flashcardsPackIDPage.clickNoMarkButton();
        }

        flashcardsPackIDPage
                .clickShowAllFlashcardsButton();

        switch (buttonName) {
            case "Yes" -> flashcardsPackIDPage.clickYesToFlashcardsModalButton();
            case "Kinda" -> flashcardsPackIDPage.clickKindaToFlashcardsModalButton();
            case "No" -> flashcardsPackIDPage.clickNoToFlashcardsModalButton();
        }

        Allure.step("Assert that only '1' card in '(" + buttonName + ")' section is visible");
        assertThat(getPage().getByText("1", new Page.GetByTextOptions().setExact(true))).isVisible();
        assertThat(getPage().getByText("2", new Page.GetByTextOptions().setExact(true))).not().isVisible();

        Allure.step("Assert that '" + TestData.FIRST_CARD_TEXT + "' is visible.");
        assertThat(getPage().getByText(TestData.FIRST_CARD_TEXT)).isVisible();
    }


    @Severity(SeverityLevel.NORMAL)
    @Story("FlashcardsLogic")
    @TmsLink("")
    @Description("LMS-1398 Логика Yes 5. https://app.qase.io/plan/LMS/1?case=1398"
            + "LMS-1397 Логика Kinda 3. https://app.qase.io/plan/LMS/1?case=1397"
            + "LMS-1401 Логика No 3. https://app.qase.io/plan/LMS/1?case=1401"
            + "Нажать Yes/Kinda/No. В разделе Home в блоке Performance количество Flashcards отмеченных 'Yes/Kinda/No' увеличилось на 1.")
    @Test(description = "TC1398-1397-1401 - В разделе Home в блоке Performance количество Flashcards " +
            "отмеченных 'Yes/Kinda/No' увеличилось на 1.",
            dataProvider = "yesKindaNoButtons", dataProviderClass = TestData.class)
    public void testYes5Kinda3No3Logic(String buttonName) {
        HomePage homePage = new HomePage(getPage()).init();

        String yesAmountBefore = "", kindaAmountBefore = "", noAmountBefore = "";
        String expectedYesAmountAfter = "", expectedKindaAmountAfter = "", expectedNoAmountAfter = "";
        String yesAmountAfter = "", kindaAmountAfter = "", noAmountAfter = "";

        switch (buttonName) {
            case "Yes" -> {
                yesAmountBefore = homePage.getYesPerformanceAmount();
                expectedYesAmountAfter = TestUtils.add(yesAmountBefore, 1);
            }
            case "Kinda" -> {
                kindaAmountBefore = homePage.getKindaPerformanceAmount();
                expectedKindaAmountAfter = TestUtils.add(kindaAmountBefore, 1);
            }
            case "No" -> {
                noAmountBefore = homePage.getNoPerformanceAmount();
                expectedNoAmountAfter = TestUtils.add(noAmountBefore, 1);
            }
        }

        FlashcardsPackIDPage flashcardsPackIDPage =
                homePage
                        .clickFlashcardsMenu()
                        .clickAutotestFlashcardsPack()
                        .clickGotItButton()
                        .clickShowAnswerButton();

        switch (buttonName) {
            case "Yes" -> flashcardsPackIDPage
                    .clickYesMarkButton()
                    .clickEndButtonToCongratulationsModal()
                    .clickYesToCongratulationButton()
                    .clickNextButton()
                    .clickOkToFlashcardsButton()
                    .clickHomeMenu();
            case "Kinda" -> flashcardsPackIDPage
                    .clickKindaMarkButton()
                    .clickEndButtonToFlashcardsPackID()
                    .clickYesToFlashcardsButton()
                    .clickHomeMenu();
            case "No" -> flashcardsPackIDPage
                    .clickNoMarkButton()
                    .clickEndButtonToFlashcardsPackID()
                    .clickYesToFlashcardsButton()
                    .clickHomeMenu();
        }

        switch (buttonName) {
            case "Yes" -> {
                yesAmountAfter = homePage.getYesPerformanceAmount();
            }
            case "Kinda" -> {
                kindaAmountAfter = homePage.getKindaPerformanceAmount();
            }
            case "No" -> {
                noAmountAfter = homePage.getNoPerformanceAmount();
            }
        }

        switch (buttonName) {
            case "Yes" -> {
                Allure.step("Assert that '" + buttonName + "' Performance Amount before (" + yesAmountBefore + ") increased by 1 (" +
                        yesAmountAfter + ").");
                Assert.assertEquals(
                        yesAmountAfter, expectedYesAmountAfter,
                        "If FAIL: Learned cards amount before (" + yesAmountBefore + ") NOT increased.\n"
                );
            }
            case "Kinda" -> {
                Allure.step("Assert that '" + buttonName + "' Performance Amount before (" + kindaAmountBefore + ") increased by 1 (" +
                        kindaAmountAfter + ").");
                Assert.assertEquals(
                        kindaAmountAfter, expectedKindaAmountAfter,
                        "If FAIL: Learned cards amount before (" + kindaAmountBefore + ") NOT increased.\n"
                );
            }
            case "No" -> {
                Allure.step("Assert that '" + buttonName + "' Performance Amount before (" + noAmountBefore + ") increased by 1 (" +
                        noAmountAfter + ").");
                Assert.assertEquals(
                        noAmountAfter, expectedNoAmountAfter,
                        "If FAIL: Learned cards amount before (" + noAmountBefore + ") NOT increased.\n"
                );
            }

        }

    }


}
