//package tests;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import pages.HomePage;
//import pages.MnemonicCardPracticePage;
//import utils.runner.ProjectProperties;
//import tests.helpers.TestData;
//
//import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
//
//public class MnemonicCardPracticeTest extends BaseTest {
//
//    @Test
//    public void testOpeningMnemonicCardAfterStartingPractice() {
//        MnemonicCardPracticePage mnemonicCardPracticePage = new HomePage(getPage(), getPlaywright())
//                .clickMnemonicCardsMenu()
//                .clickRandomMnemonicCardsStackAndGo()
//                .clickStartPracticeButton();
//
//        Assert.assertTrue(getPage().url().contains(ProjectProperties.BASE_URL + TestData.MNEMONIC_CARD_PRACTICE_END_POINT));
//        assertThat(mnemonicCardPracticePage.getMnemonicCardPracticeHeader()).hasText(TestData.PRACTICE);
//        assertThat(mnemonicCardPracticePage.getAnswersToQuestion()).isVisible();
//        assertThat(mnemonicCardPracticePage.getMnemonicWords()).isVisible();
//    }
//}
