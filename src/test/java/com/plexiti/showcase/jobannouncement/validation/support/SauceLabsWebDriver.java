package com.plexiti.showcase.jobannouncement.validation.support;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.common.Utils;
import com.saucelabs.saucerest.SauceREST;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.fail;

/**
 * Based on example from the cucumber-jvm project: http://bit.ly/Y4ZqCD
 *
 * <p>
 * Example of a WebDriver implementation that has delegates all methods to a static instance (REAL_DRIVER) that is only 
 * created once for the duration of the JVM. The REAL_DRIVER is automatically closed when the JVM exits. This makes 
 * scenarios a lot faster since opening and closing a browser for each scenario is pretty slow.
 * To prevent browser state from leaking between scenarios, cookies are automatically deleted before every scenario.
 * </p>
 * <p>
 * A new instance of SharedDriver is created for each Scenario and passed to yor Stepdef classes via Dependency Injection
 * </p>
 * <p>
 * As a bonus, screenshots are embedded into the report for each scenario. (This only works
 * if you're also using the HTML formatter).
 * </p>
 * <p>
 * A new instance of the SharedDriver is created for each Scenario and then passed to the Step Definition classes'
 * constructor. They all receive a reference to the same instance. However, the REAL_DRIVER is the same instance throughout
 * the life of the JVM.
 * </p>
 */
public class SauceLabsWebDriver implements SauceOnDemandSessionIdProvider, WebDriver {
    protected WebDriver driver;
    private SauceOnDemandAuthentication authentication;
    public SauceOnDemandCucumberTestWatcher resultReportingTestWatcher;

    protected String accessKey;
    protected String username;
    private String appUrl;
    private SauceREST sauceREST;

    public SauceLabsWebDriver() {
    }

    public void parseOptions() {
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
    }

    public void initLocal() {
        parseOptions();
        this.driver = new FirefoxDriver();
    }

    public void initRemote() throws MalformedURLException {
        parseOptions();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("platform", "Windows 2012");
        capabilities.setCapability("version", "17");
        capabilities.setCapability("tags", new String[]{"web", "firefox", "windows"});

        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        this.resultReportingTestWatcher = new SauceOnDemandCucumberTestWatcher(this, this.authentication);
        this.sauceREST = new SauceREST(username, accessKey);
    }

    public String getSessionId() {
        return ((RemoteWebDriver) driver).getSessionId().toString();
    }

    /*
     * Delegate all methods to the remote sauce labs web driver
     */
    public SauceOnDemandAuthentication getAuthentication() {
        return this.authentication;
    }

    public void updateRemoteJobAndQuit(Feature feature, Scenario scenario, boolean scenarioFailed) throws IOException {
        String jobName = feature.getName() + ": " + scenario.getName();
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("name", jobName);
        updates.put("passed", !scenarioFailed);
        sauceREST.updateJobInfo(this.getSessionId(), updates);

        this.driver.quit();
    }

    public String getApplicationUrl() {
        return this.appUrl;
    }

    @Override
    public void get(String s) {
        this.driver.get(s);
    }

    @Override
    public String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return this.driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return this.driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return this.driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return this.driver.getPageSource();
    }

    @Override
    public void close() {
        this.driver.close();
    }

    @Override
    public void quit() {
        this.driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return this.driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return this.driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return this.driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return this.driver.navigate();
    }

    @Override
    public Options manage() {
        return this.driver.manage();
    }
}
