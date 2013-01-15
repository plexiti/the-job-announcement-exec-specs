package com.plexiti.showcase.jobannouncement.validation.ui;

import com.plexiti.showcase.jobannouncement.validation.support.SauceLabsWebDriver;
import com.plexiti.showcase.jobannouncement.validation.support.SauceOnDemandCucumberTestWatcher;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.runner.Description;

import java.io.IOException;

/*
 * TODO: this should be moved out of this project to make it reusable
 *
 * If we try to make the hooks class extend a base class we get:
 * "cucumber.runtime.CucumberException: You're not allowed to extend classes that define Step Definitions or hooks.
 *   class com.plexiti.showcase.jobannouncement.validation.ui.RunCukesHooks extends
 *   class com.plexiti.showcase.jobannouncement.validation.support.CucumberSeleniumHooks"
 *
 */
public class CucumberSeleniumHooks implements SauceOnDemandSessionIdProvider {

    private final SauceLabsWebDriver driver;

    public SauceOnDemandCucumberTestWatcher resultReportingTestWatcher;

    /*
     * TODO: We should use the interface WebDriver as a parameter and not a specific implementation
     * so we can use a local driver for development. But before that we need to re-evaluate the use of
     * PicoContainer as discussed here https://groups.google.com/forum/?fromgroups=#!topic/cukes/v6L8i37v9bw
     */
    public CucumberSeleniumHooks(SauceLabsWebDriver webDriver) {
        this.driver = webDriver;
    }

    @Before
    public void startFeatureTest(Scenario scenario) throws IOException {
        // TODO: once we are able to retrieve the feature and scenario titles we can use it as the Sauce Labs job id
        driver.init("TODO: feature and scenario title");
        this.resultReportingTestWatcher = new SauceOnDemandCucumberTestWatcher(this, driver.getAuthentication());
    }

    @After
    public void stopFeatureTest(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            this.resultReportingTestWatcher.failed(null, Description.EMPTY);
        } else {
            this.resultReportingTestWatcher.succeeded(Description.EMPTY);
        }
        this.driver.quit();
    }

    @Override
    public String getSessionId() {
        return driver.getSessionId();
    }
}
