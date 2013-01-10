package com.plexiti.showcase.jobannouncement.tests.functional;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Date;

import static junit.framework.Assert.assertTrue;

@Ignore
public class EndToEndTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("plexiti", "2d003a41-7fc8-4dc3-848f-eb3fbdc8ef65");

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * JUnit Rule which will record the test name of the current test.  This is referenced when creating the {@link DesiredCapabilities},
     * so that the Sauce Job is created with the test name.
     */
    public @Rule TestName testName= new TestName();

    private WebDriver driver;

    private String sessionId;

    private String appUrl = "http://the-job-announcement.com"; // "http://localhost:8080/the-job-announcement/";

    @Test
    @Ignore
    public void fullProcessTest() throws Exception {
        EndToEndLocalTest test = new EndToEndLocalTest();
        test.setAppUrl(appUrl);
        test.setDriver(driver);
        test.fullProcessTest();
    }

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("platform", "Windows 2012");
        capabilities.setCapability("version", "17");
        capabilities.setCapability("name", testName.getMethodName());
        capabilities.setCapability("tags", new String[]{"web", "firefox", "windows"});

        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
