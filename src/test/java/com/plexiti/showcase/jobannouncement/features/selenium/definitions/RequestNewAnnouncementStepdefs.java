package com.plexiti.showcase.jobannouncement.features.selenium.definitions;

import com.plexiti.showcase.jobannouncement.features.selenium.support.SharedWebDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.fest.assertions.api.Assertions.assertThat;

public class RequestNewAnnouncementStepDefs {

    private final WebDriver driver;

    public RequestNewAnnouncementStepDefs(SharedWebDriver driver) {
        this.driver = driver;
    }

    @When("^I request an announcement with title \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void I_request_an_announcement_with_title_and_description(String title, String description) throws Throwable {
        // Click on the "Request new announcement" button
        driver.findElement(By.cssSelector("input[value='Request new announcement']")).click();
        assertThat(driver.getTitle()).endsWith("New job anouncement");

        // Fill in the "I need" text area
        WebElement iNeedField = driver.findElement(By.tagName("textarea"));
        iNeedField.sendKeys(description);

        // Fill in the "job title" field
        WebElement jobTitleField = driver.findElement(By.cssSelector("input[name*='title']"));
        jobTitleField.sendKeys(title);

        // Submit the new request
        driver.findElement(By.cssSelector("input[value='Request description']")).click();

        // Check that we are back in the list page
        assertThat(driver.getTitle()).contains("The control center");
    }

    @Then("^A new job announcement with these attributes exists$")
    public void A_new_job_announcement_with_these_attributes_exists() throws Throwable {
        // Express the Regexp above with the code you wish you had
        //throw new PendingException();
    }
}
