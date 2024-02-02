package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MnemonicCardListPage;
import pages.MnemonicCardsPage;
import tests.helpers.TestData;
import utils.runner.ProjectProperties;

public class MnemonicCardsTest extends BaseTest {

    @Test(
            testName = "LMS-1355 Получение карточек. https://app.qase.io/plan/LMS/1?case=1355",
            description = "TC1355-01 - User can run any available Mnemonic cards pack.")
    @Description("Objective: To verify that the user can successfully access and open any available Mnemonic cards pack.")
    @Story("Mnemonic Cards")
    @TmsLink("j6g9c58ocvl0")
    @Severity(SeverityLevel.NORMAL)
    public void testUserCanRunAnyAvailableMnemonicCardsPack() {

        MnemonicCardListPage mnemonicCardListPage =
                new HomePage(getPage()).init()
                        .clickMnemonicCardsMenu();

        final String expectedStackName = mnemonicCardListPage.getRandomStackName();
        final String expectedStackQuantity = mnemonicCardListPage.getRandomStackCardsAmount();

        MnemonicCardsPage mnemonicCardsPage =
                mnemonicCardListPage
                        .clickRandomMnemonicCardsStack();

        final String expectedUrlPart = ProjectProperties.BASE_URL + TestData.MNEMONIC_CARDS_END_POINT;
        final String actualUrl = getPage().url();
        final String actualStackName = mnemonicCardsPage.getMnemonicCardName();
        final String actualStackQuantity = mnemonicCardsPage.getMnemonicCardTotalQuantity();

        Assert.assertTrue(
                actualUrl.contains(expectedUrlPart),
                "If FAIL: The page URL " + actualUrl + " does NOT contains the url part: " + expectedUrlPart + ".\n"
        );
        Assert.assertTrue(
                expectedStackName.contains(actualStackName),
                "If FAIL: The expectedStackName " + expectedStackName
                        + " does NOT contains the actualStackName " + actualStackName + ".\n"
        );
        Assert.assertEquals(
                actualStackQuantity, expectedStackQuantity,
                "If FAIL: The actual stack quantity (" + actualStackQuantity +
                        ") does NOT equals expected stack quantity: (" + expectedStackQuantity + ").\n"
        );
    }
}
