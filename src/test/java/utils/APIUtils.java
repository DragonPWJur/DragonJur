package utils;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import utils.api.entity.Chapter;
import utils.api.entity.GuideTable;
import utils.api.entity.Unit;

public class APIUtils {

    public static  void changeChapter1Unit1TextViaAPI(String testText, boolean isAdd, APIRequestContext requestContext) {
        GuideTable guideTable = APIServises.getStudyGuideTable(requestContext);
        Chapter chapter1 = guideTable.getChapters().get(0);
        chapter1 = APIServises.getUnitByGuideIdAndChapterId(requestContext, guideTable.getId(), chapter1.getId());
        Unit unit1 = chapter1.getUnits().get(0);

        String text = unit1.getContent().getBlocks().get(0).getData().getText();
        if (isAdd) {
            text = testText + text;
        } else {
            text = text.substring(text.indexOf(testText) + testText.length());
        }

        unit1.getContent().getBlocks().get(0).getData().setText(text);

        APIServises.editUnitByGuideIdAndChapterId(requestContext, unit1);
    }
}
