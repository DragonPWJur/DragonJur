package pages;

import com.microsoft.playwright.Page;
import pages.constants.Constants;

public final class TestTutorPage extends BaseTestsPage<TestTutorPage, TestTutorPage> {

    TestTutorPage(Page page) {
        super(page);
    }

    @Override
    public TestTutorPage init() {

        return createPage(new TestTutorPage(getPage()), Constants.TEST_TUTOR_END_POINT);
    }
}

