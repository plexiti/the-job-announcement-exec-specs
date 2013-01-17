package com.plexiti.showcase.jobannouncement.validation.ui;

import com.plexiti.showcase.jobannouncement.validation.support.SauceLabsWebDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.formatter.JUnitFeatureAndScenarioAwareFormatter;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private final SauceLabsWebDriver driver;

    public CucumberSeleniumHooks(SauceLabsWebDriver driver) {
        this.driver = driver;
    }

    @Before
    public void startScenarioTest(Scenario scenario) throws IOException {
        System.err.println("@Before hook called");
        driver.initLocal();
    }

    @After
    public void stopScenarioTest(Scenario scenario) throws IOException {
        gherkin.formatter.model.Feature featureModel = JUnitFeatureAndScenarioAwareFormatter.getCurrentFeature();
        gherkin.formatter.model.Scenario scenarioModel = JUnitFeatureAndScenarioAwareFormatter.getCurrentScenario();
        this.driver.renameRemoteJobReportResultAndQuit(featureModel, scenarioModel, scenario.isFailed());
    }
}
