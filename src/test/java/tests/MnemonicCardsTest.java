package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MnemonicCardListPage;
import pages.MnemonicCardsPage;
import utils.ProjectProperties;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MnemonicCardsTest extends BaseTest {

    @Test
    public void testUserCanRunAnyAvailableMnemonicCardsPack() {

        MnemonicCardListPage mnemonicCardListPage = new HomePage(getPage(), getPlaywright())
                .clickMnemonicCardsMenu();

        String[] expectedNameAndQuantityOfStack = mnemonicCardListPage.getRandomStackText();

        mnemonicCardListPage.clickStack(expectedNameAndQuantityOfStack[0]);
        MnemonicCardsPage mnemonicCardsPage = new MnemonicCardsPage(getPage(), getPlaywright());

        assertThat(mnemonicCardsPage.getMnemonicCardHeader()).hasText(expectedNameAndQuantityOfStack[0]);
        assertThat(mnemonicCardsPage.getMnemonicCardTotalQuantity()).hasText(expectedNameAndQuantityOfStack[1]);
        Assert.assertTrue(getPage().url().contains(ProjectProperties.BASE_URL + TestData.MNEMONIC_CARDS_END_POINT));
    }
}
