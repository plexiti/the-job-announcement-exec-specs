package com.plexiti.showcase.jobannouncement.validation.ui;

import com.plexiti.showcase.jobannouncement.validation.support.SauceLabsWebDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.formatter.JUnitFeatureAndScenarioAwareFormatter;

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
public class CucumberSeleniumHooks {

    private final SauceLabsWebDriver driver;

    /*
     * TODO: We should use the interface WebDriver as a parameter and not a specific implementation
     * so we can use a local driver for development. But before that we need to re-evaluate the use of
     * PicoContainer as discussed here https://groups.google.com/forum/?fromgroups=#!topic/cukes/v6L8i37v9bw
     */
    public CucumberSeleniumHooks(SauceLabsWebDriver webDriver) {
        this.driver = webDriver;
    }

    @Before
    public void startScenarioTest(Scenario scenario) throws IOException {
        // driver.initLocal();
        driver.initRemote();
    }

    @After
    public void stopScenarioTest(Scenario scenario) throws IOException {
        gherkin.formatter.model.Feature featureModel = JUnitFeatureAndScenarioAwareFormatter.getCurrentFeature();
        gherkin.formatter.model.Scenario scenarioModel = JUnitFeatureAndScenarioAwareFormatter.getCurrentScenario();
        driver.updateRemoteJobAndQuit(featureModel, scenarioModel, scenario.isFailed());
        //driver.quit();
    }
}
