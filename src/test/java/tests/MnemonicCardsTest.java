//package tests;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import pages.HomePage;
//import pages.MnemonicCardListPage;
//import pages.MnemonicCardsPage;
//import utils.runner.ProjectProperties;
//import tests.helpers.TestData;
//
//import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
//
//public class MnemonicCardsTest extends BaseTest {
//
//    @Test
//    public void testUserCanRunAnyAvailableMnemonicCardsPack() {
//
//        MnemonicCardListPage mnemonicCardListPage = new HomePage(getPage(), getPlaywright())
//                .clickMnemonicCardsMenu();
//
//        String expectedStackName = mnemonicCardListPage.getExpectedStackName();
//        String expectedStackQuantity = mnemonicCardListPage.getExpectedStackQuantity();
//
//        mnemonicCardListPage.clickRandomMnemonicCardsStack();
//
//        MnemonicCardsPage mnemonicCardsPage = new MnemonicCardsPage(getPage(), getPlaywright());
//
//        String actualStackName = mnemonicCardsPage.getMnemonicCardHeaderText();
//
//        Assert.assertTrue(getPage().url().contains(ProjectProperties.BASE_URL + TestData.MNEMONIC_CARDS_END_POINT));
//        assertThat(mnemonicCardsPage.getMnemonicCardHeader()).containsText(actualStackName);
//        Assert.assertTrue(expectedStackName.contains(actualStackName),
//                "expectedStackName is " + expectedStackName + ", but actualStackName " + actualStackName
//        );
//        assertThat(mnemonicCardsPage.getMnemonicCardTotalQuantity()).hasText(expectedStackQuantity);
//    }
//}
