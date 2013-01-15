package com.plexiti.showcase.jobannouncement.validation.ui;

import com.plexiti.showcase.jobannouncement.validation.support.SauceLabsWebDriver;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.fest.assertions.api.Assertions.assertThat;

public class ApplicationNavigationStepDefs {
    private final WebDriver driver;
    private String appUrl;

    public ApplicationNavigationStepDefs(SauceLabsWebDriver webDriver) {
        this.driver = webDriver;
        this.appUrl = webDriver.getApplicationUrl();
    }

    @Given("^I am logged in as \"([^\"]*)\"$")
    public void I_am_logged_in_as(String username) throws Throwable {
        driver.get(appUrl);
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
