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
import static junit.framework.Assert.fail;

@Ignore
public class EndToEndTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */

    protected String accessKey;
    protected String username;
    public SauceOnDemandAuthentication authentication;

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule SauceOnDemandTestWatcher resultReportingTestWatcher;

    /**
     * JUnit Rule which will record the test name of the current test.  This is referenced when creating the {@link DesiredCapabilities},
     * so that the Sauce Job is created with the test name.
     */
    public @Rule TestName testName= new TestName();

    private WebDriver driver;

    private String sessionId;

    private String appUrl;

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
        username = System.getProperty("sauceLabsUsername");
        if (username == null) {
            fail("You need to pass the Sauce Labs username as a property with '-DsauceLabsUsername=<username>'");
        }

        accessKey = System.getProperty("sauceLabsAccessKey");
        if (accessKey == null) {
            fail("You need to pass the Sauce Labs access key as a property with '-DsauceLabsAccessKey=<access key>'");
        }

        appUrl = System.getProperty("applicationUrl");
        if (appUrl == null) {
            fail("You need to pass the application URL as a property with '-DapplicationUrl=<url>'");
        }

        authentication = new SauceOnDemandAuthentication(username, accessKey);
        resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

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
