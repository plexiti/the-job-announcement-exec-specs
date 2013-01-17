package com.plexiti.showcase.jobannouncement.validation.ui;

import com.plexiti.showcase.jobannouncement.validation.support.SauceLabsWebDriver;
import cucumber.api.java.en.Given;
import cucumber.runtime.formatter.JUnitFeatureAndScenarioAwareFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import static org.fest.assertions.api.Assertions.assertThat;

public class ApplicationNavigationStepDefs {

    @Autowired
    private final SauceLabsWebDriver driver;

    public ApplicationNavigationStepDefs(SauceLabsWebDriver webDriver) {
        this.driver = webDriver;
    }

    @Given("^I am logged in as \"([^\"]*)\"$")
    public void I_am_logged_in_as(String username) throws Throwable {
        driver.get(driver.getApplicationUrl());
        assertThat(driver.getTitle()).contains("Index");
        switchToUser(username);
    }

    private void switchToTab(String tabTitle) {
        driver.findElement(By.partialLinkText(tabTitle)).click();
    }

    private void switchToUser(String userName) {
        /*
         * Switch to user "Gonzo the Great"
         */
        driver.findElement(By.className("dropdown-toggle")).click();
        driver.findElement(By.linkText(userName)).click();
        // TODO: check that the switch was successful!
    }
}
