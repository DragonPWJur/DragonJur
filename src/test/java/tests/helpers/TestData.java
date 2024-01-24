package tests.helpers;

import org.testng.annotations.DataProvider;
import utils.runner.ProjectProperties;

public final class TestData {
    //Side Menu
    public static final String HOME_END_POINT = "/home";
    public static final String STUDY_GUIDE_END_POINT = "/study-guide";
    public static final String TEST_LIST_END_POINT = "/test-list";
    public static final String FLASHCARD_PACKS_END_POINT = "/flashcard-packs";
    public static final String MNEMONIC_CARDS_LIST_END_POINT = "/mnemoniccard-list";
    public static final String PERFORMANCE_END_POINT = "/performance";
    public static final String PROFILE_END_POINT = "/profile";

    //Pages
    public static final String TEST_TUTOR_END_POINT = "/test-tutor";
    public static final String TEST_TIMED_END_POINT = "/test-timed";
    public static final String MNEMONIC_CARDS_END_POINT = "/mnemonic-cards/";
    public static final String MNEMONIC_CARD_PRACTICE_END_POINT = "/mnemonic-card-practice/";
    public static final String FLASHCARDS_PACK_ID_END_POINT = "/flashcards?packId";

    //Data
    public static final String ONE_QUESTION = "1";
    public static final String FLASHCARDS = "Flashcards";
    public static final String GOT_IT = "Got it";
    public static final String YES = "Yes";
    public static final String QUESTION = "Question";
    public static final String BACKGROUND_COLOR = "background-color";
    public static final String RGBA_62_48_179_0_2 = "rgba(62, 48, 179, 0.2)";
    public static final String RGBA_0_0_0_0 = "rgba(0, 0, 0, 0)";
    public static final String QUESTION_MARK = "?";
    public static final String EXPLANATION = "Explanation";
    public static final String CHECKBOX_POINTS = "833";
    public static final String REPORT_MESSAGE = "The report has been sent successfully";
    public static final String PRACTICE = "Practice";
    public static final String NOTHING_FOUND = "Nothing found. Try to use other key words";
    public static final String CORRECT_ANSWER_POINTS = "38";
    public static final String PROJECTIONS = "Projections";
    public static final String LONG_BONES = "Long bones";
    public static final String STUDY_THIS_MODAL_HEADER = "Weakest Exam Areas";
    public static final String STUDY_THIS_MODAL_MESSAGE =
            "You have not studied enough in order for us to calculate your weakest areas. Keep Studying \uD83D\uDE03";
    public static final String SEARCH_WORD = TestUtils.getRandomString(10);

    @DataProvider
    public static Object[][] sideMenuItems() {
        return new Object[][]{
                {"Home", ProjectProperties.BASE_URL + HOME_END_POINT},
                {"Study guide", ProjectProperties.BASE_URL + STUDY_GUIDE_END_POINT},
                {"Tests", ProjectProperties.BASE_URL + TEST_LIST_END_POINT},
                {"Flashcards", ProjectProperties.BASE_URL + FLASHCARD_PACKS_END_POINT},
                {"Mnemonic cards", ProjectProperties.BASE_URL + MNEMONIC_CARDS_LIST_END_POINT},
                {"Performance", ProjectProperties.BASE_URL + PERFORMANCE_END_POINT},
                {"Profile", ProjectProperties.BASE_URL + PROFILE_END_POINT}
        };
    }
}
