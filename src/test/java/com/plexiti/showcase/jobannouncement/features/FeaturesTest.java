package com.plexiti.showcase.jobannouncement.features;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

/**
 * Feature validation
 */
@RunWith(Cucumber.class)
@Cucumber.Options(
        features = "src/test/resources/features",
        format = { "pretty", "html:target/cucumber-html-report", "json-pretty:target/cucumber-report.json" }
)
public class FeaturesTest {
}
