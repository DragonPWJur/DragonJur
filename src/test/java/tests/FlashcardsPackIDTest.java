package tests;

import org.testng.annotations.Test;
import pages.FlashcardPacksPage;
import pages.HomePage;
import utils.TestData;
import utils.TestUtils;

public class FlashcardsPackIDTest extends BaseTest {

    @Test
    public void test_StartFlashCardPack() {
        FlashcardPacksPage flashcardPacksPage = new HomePage(getPage(), getPlaywright())
                .clickFlashcardsMenu();

        int randomIndex = TestUtils.getRandomNumber(flashcardPacksPage.getFlashcardsPacksToLearn());

        String questionHeading = flashcardPacksPage
                .clickRandomFlashcardPack(randomIndex)
                .startFlashcardsPackForTheFirstTimeAndGoBack()
                .clickRandomFlashcardPack(randomIndex)
                .clickGotButton()
                .questionHeadingText();

        assert(getPage().url()).contains(TestData.FLASHCARDS_PACK_ID_END_POINT);
        assert(questionHeading).equals(TestData.QUESTION);
    }
}
