package com.plexiti.showcase.jobannouncement.features.selenium.definitions;

import com.plexiti.showcase.jobannouncement.features.selenium.support.SharedWebDriver;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.fest.assertions.api.Assertions.assertThat;

public class ApplicationNavigationStepDefs {
    private final WebDriver driver;
    private String appUrl = "http://the-job-announcement.com/";

    public ApplicationNavigationStepDefs(SharedWebDriver webDriver) {
        this.driver = webDriver;
    }

    @Given("^I am logged in as \"([^\"]*)\"$")
    public void I_am_logged_in_as(String username) throws Throwable {
        driver.get(appUrl);
        assertThat(driver.getTitle()).contains("Index");
        switchToUser("Gonzo The Great");
    }

    @Given("^I am on the \"([^\"]*)\" page$")
    public void I_am_on_the_page(String pageTitle) throws Throwable {
        driver.findElement(By.linkText("Start the role play!")).click();
        assertThat(driver.getTitle()).contains(pageTitle);
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
