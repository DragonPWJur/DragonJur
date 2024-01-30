package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public final class CongratulationsModal extends BaseModal<CongratulationsModal> {
    private final Locator congratulationPoints = locator("div[role='dialog']")
            .locator("span")
            .filter(new Locator.FilterOptions().setHasText(Pattern.compile("\\d+")));
    private final Locator testProgressbarPoints = locator("div>svg.CircularProgressbar+div>span").first();

    CongratulationsModal(Page page) {
        super(page);
    }

    @Override
    public CongratulationsModal init() {

        return new CongratulationsModal(getPage());
    }

    @Step("Get Points text on 'Congratulation' dialog pop-up.")
    public String getCongratulationPointsText() {

        return congratulationPoints.innerText();
    }

    @Step("Get Points number on the first 'Congratulation' dialog pop-up.")
    public int getCongratulationPoints() {
        String pointsText = getCongratulationPointsText();

        return parseInt(pointsText);
    }

    @Step("Get Points number on the second 'Congratulation' dialog pop-up.")
    public int getTestPoints() {

        return parseInt(testProgressbarPoints.innerText());
    }


}
