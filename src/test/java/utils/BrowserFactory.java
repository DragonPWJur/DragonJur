package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public enum BrowserFactory {

    CHROMIUM {
        @Override
        public Browser createInstance(Playwright playwright) {
            return playwright.chromium().launch(options);
        }
    },

    FIREFOX {
        @Override
        public Browser createInstance(Playwright playwright) {
            return playwright.firefox().launch(options);
        }
    },

    WEBKIT {
        @Override
        public Browser createInstance(Playwright playwright) {
            return playwright.webkit().launch(options);
        }
    };

    public final BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
            .setHeadless(ProjectProperties.IS_HEADLESS)
            .setSlowMo(ProjectProperties.IS_SLOW);

    public abstract Browser createInstance(Playwright playwright);
}
