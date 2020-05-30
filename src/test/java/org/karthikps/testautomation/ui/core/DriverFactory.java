package org.karthikps.testautomation.ui.core;

import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class DriverFactory {
    private static Logger logger = LogManager.getLogger(DriverFactory.class.getName());
    protected WebDriver webDriver;

    @Parameters({"browser","headless"})
    @BeforeClass
    public void setup(String browser, String headless) {
        logger.info("Setting up browser");
        switch (browser) {
            case "chrome":
                webDriver = new ChromeDriver(getChromeOptions(headless.equals("true") ? true : false));
        }
    }

    private ChromeOptions getChromeOptions(boolean headless) {
        setChromeDriverBasedOnOS();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(headless);
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("enable-automation");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-browser-side-navigation");
        chromeOptions.addArguments("--disable-gpu");
        return chromeOptions;
    }

    private void setChromeDriverBasedOnOS()
    {
        String chromeDriverEnvPath = System.getenv("CHROMEDRIVER_PATH");
        if (SystemUtils.IS_OS_WINDOWS) {
            System.setProperty("webdriver.chrome.driver", chromeDriverEnvPath != null ? chromeDriverEnvPath : "Driver/win/chromedriver.exe");
        } else if (SystemUtils.IS_OS_LINUX) {
            System.setProperty("webdriver.chrome.driver", chromeDriverEnvPath != null ? chromeDriverEnvPath : "Driver/linux/chromedriver");
        } else if (SystemUtils.IS_OS_MAC_OSX) {
            System.setProperty("webdriver.chrome.driver", chromeDriverEnvPath != null ? chromeDriverEnvPath : "Driver/mac/chromedriver");
        }
    }

    @AfterClass
    public void tearDown()
    {
        logger.info("Tearing down");
        if(webDriver != null) {
            webDriver.quit();
        }
    }
}
