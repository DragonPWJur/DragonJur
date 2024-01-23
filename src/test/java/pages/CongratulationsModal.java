package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

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

    public String getCongratulationPointsText() {

        return congratulationPoints.innerText();
    }

    public int getCongratulationPoints() {
        String pointsText = getCongratulationPointsText();

        return parseInt(pointsText);
    }

    public String getTestProgressbarPointsText() {

        return testProgressbarPoints.innerText();
    }

    public int getTestProgressbarPointsNumber() {
        String pointsText = getTestProgressbarPointsText();

        return parseInt(pointsText);
    }
}
