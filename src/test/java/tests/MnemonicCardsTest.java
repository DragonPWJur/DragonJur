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

    @Severity(SeverityLevel.NORMAL)
    @Story("Mnemonic Cards")
    @TmsLink("j6g9c58ocvl0")
    @Description("LMS-1355 Получение карточек. https://app.qase.io/plan/LMS/1?case=1355" +
            "Objective: To verify that the user can successfully access and open any available Mnemonic cards pack.")
    @Test(description = "TC1355-01 - User can run any available Mnemonic cards pack.")
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
        final String actualStackName = mnemonicCardsPage.getMnemonicCardsStackName();
        final String actualStackQuantity = mnemonicCardsPage.getMnemonicCardTotalQuantity();

        Allure.step("Assert that the actual url has expected part '" + TestData.MNEMONIC_CARDS_END_POINT + "'");
        Assert.assertTrue(
                actualUrl.contains(expectedUrlPart),
                "If FAIL: The page URL " + actualUrl + " does NOT contains the url part: " + expectedUrlPart + ".\n"
        );

        Allure.step("Assert that the Mnemonic cards Stack Name the same as on the list af all stacks.");
        Assert.assertTrue(
                expectedStackName.contains(actualStackName),
                "If FAIL: The expectedStackName " + expectedStackName
                        + " does NOT contains the actualStackName " + actualStackName + ".\n"
        );

        Allure.step("Assert that the Mnemonic cards Stack Quantity (" + actualStackQuantity + ")" +
                " is equal to expected quantity (" + expectedStackQuantity + ") on the list af all stacks.");
        Assert.assertEquals(
                actualStackQuantity, expectedStackQuantity,
                "If FAIL: The actual stack quantity (" + actualStackQuantity +
                        ") does NOT equals expected stack quantity: (" + expectedStackQuantity + ").\n"
        );
    }
}
