package tests.helpers;

import org.testng.annotations.DataProvider;
import utils.runner.ProjectProperties;

import java.util.Arrays;
import java.util.List;

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
    public static final String ADD_NEW_COURSE_END_POINT = "/add-new-course";

    //Data
    public static final String ONE_QUESTION = "1";
    public static final String BACKGROUND_COLOR = "background-color";
    public static final String RGBA_62_48_179_0_2 = "rgba(62, 48, 179, 0.2)";
    public static final String QUESTION_MARK = "?";
    public static final String EXPLANATION = "Explanation";
    public static final String CHECKBOX_POINTS = "833";
    public static final String PRACTICE = "Practice";
    public static final String NOTHING_FOUND = "Nothing found. Try to use other key words";
    public static final String PROJECTIONS = "Projections";
    public static final String PHALANGES_IN_THE_FINGERS = "Phalanges in the fingers";
    public static final String WEAKEST_EXAM_AREAS = "Weakest Exam Areas";
    public static final String YOU_HAVE_NOT_STUDIED_ENOUGH =
            "You have not studied enough in order for us to calculate your weakest areas. Keep Studying \uD83D\uDE03";
    public static final String SEARCH_WORD = TestUtils.getRandomString(10);
    public static final String TEST = "Test";
    public static final String HISTORY_AND_CIVILIZATION_FOR_STATS = "History and Civilization for Stats";
    public static final String FOUR_QUESTIONS = "4";
    public static final String AUTOMATION_TESTING_FOR_STATS = "Automation testing for stats";
    public static final String FIVE_QUESTIONS = "5";
    public static final String ALL_TESTS = "All tests";
    public static final String ADD_NEW_COURSE = "Add new course";
    public static final String CHOOSE_A_PRODUCT = "Choose a product";
    public static final String REMOVE_FROM_MARKED = "Remove from marked";
    public static final String THE_REPORT_HAS_BEEN_SENT_SUCCESSFULLY = "The report has been sent successfully";
    public static final String STAND = "stand";
    public static final List<String> LIST_OF_TUTOR_TEST_FOOTER_BUTTONS = Arrays.asList(
            "Report a problem", "Hint", "Mark for review", "Add to flashcard"
    );
    public static final String ALERT_NOT_ENOUGH_QUESTIONS = "There are not enough questions in the sections that you have chosen";
    public static final String PAYMENT_CARD_NUMBER = "4242424242424242";
    public static final String CARD_EXPIRATION_DATE = "12/26";
    public static final String CVC = "333";
    public static final String COUNTRY = "United States";
    public static final String ZIP_CODE = "07920";
    public static final String ONE_DAY_STUDY_STREAK_MESSAGE = "You are on a 1 day study streak!";
    public static final String TWO_WEEKS_PLAN = "2 Weeks";

    public static final String[] STACKS_NAMES = {
            "Rustic Granite Pants",
            "Rustic Wooden Bacon",
            "Lorem ipsum dolor sit amet",
            "Sleek Soft Keyboard"
    };

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
