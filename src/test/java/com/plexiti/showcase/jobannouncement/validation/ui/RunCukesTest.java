package com.plexiti.showcase.jobannouncement.validation.ui;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

/**
 * Feature validation with Cucumber
 */
@RunWith(Cucumber.class)
@Cucumber.Options(
        features = "src/test/resources/features",
        glue = {
                "cucumber.runtime.java.spring.hooks",
                "com.plexiti.showcase.jobannouncement.validation.ui"
        },
        format = {
                "pretty",
                "html:target/cucumber-html-report",
                "json-pretty:target/cucumber-report.json",
                "cucumber.runtime.formatter.JUnitFeatureAndScenarioAwareFormatter:target/extended-cucumber-report"
        }
)
public class RunCukesTest {
}
