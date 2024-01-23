package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MnemonicCardListPage;
import pages.MnemonicCardsPage;
import utils.runner.ProjectProperties;
import tests.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MnemonicCardsTest extends BaseTest {

    @Test(
            testName = "LMS-1355 Получение карточек. https://app.qase.io/plan/LMS/1?case=1355",
            description = "TC1355-01 - User can run any available Mnemonic cards pack."
    )
    @Description("Objective: To verify that the user can successfully access and open any available Mnemonic cards pack.")
    @Story("Mnemonic Cards")
    @TmsLink("j6g9c58ocvl0")
    public void testUserCanRunAnyAvailableMnemonicCardsPack() {

        MnemonicCardListPage mnemonicCardListPage =
                new HomePage(getPage()).init()
                        .clickMnemonicCardsMenu();

        final String expectedStackName = mnemonicCardListPage.getExpectedStackName();
        final String expectedStackQuantity = mnemonicCardListPage.getExpectedStackQuantity();

        MnemonicCardsPage mnemonicCardsPage =
                mnemonicCardListPage
                        .clickRandomMnemonicCardsStack();

        final String actualStackName = mnemonicCardsPage.getMnemonicCardHeaderName();
        final String expectedUrlPart = ProjectProperties.BASE_URL + TestData.MNEMONIC_CARDS_END_POINT;
        final String actualUrl = getPage().url();
        final Locator actualStackQuantity = mnemonicCardsPage.getMnemonicCardTotalQuantity();

        Assert.assertTrue(
                actualUrl.contains(expectedUrlPart),
                "If FAIL: The page URL " + actualUrl + " does NOT contains the url part: " + expectedUrlPart + ".\n"
        );
        Assert.assertTrue(
                expectedStackName.contains(actualStackName),
                "If FAIL: The expectedStackName " + expectedStackName
                        + " does NOT contains the actualStackName " + actualStackName+ ".\n"
        );
        assertThat(actualStackQuantity).hasText(expectedStackQuantity);
    }
}
