package com.plexiti.showcase.jobannouncement.validation.ui;

import com.plexiti.showcase.jobannouncement.validation.support.SauceLabsWebDriver;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.fest.assertions.api.Assertions.*;

public class RequestNewAnnouncementStepDefs {

    private final WebDriver driver;
    private String title;
    private String need;

    public RequestNewAnnouncementStepDefs(SauceLabsWebDriver driver) {
        this.driver = driver;
        WebDriver.Options opts = driver.manage();
        opts.timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @When("^I request a new job announcement$")
    public void I_request_a_new_job_announcement() throws Throwable {
        driver.findElement(By.linkText("Start the role play!")).click();
        //WebDriverWait wait = new WebDriverWait(driver, 5);
        String requestNewAnnouncementCssSelector = "input[value='Request new announcement']";
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(requestNewAnnouncementCssSelector)));
        // Click on the "Request new announcement" button
        driver.findElement(By.cssSelector(requestNewAnnouncementCssSelector)).click();
    }

    @When("^I use the job announcement title \"([^\"]*)\"$")
    public void I_use_the_job_announcement_title(String title) throws Throwable {
        // Fill in the "job title" field
        WebElement jobTitleField = driver.findElement(By.cssSelector("input[name*='title']"));
        jobTitleField.sendKeys(title);
        this.title = title;
    }

    @When("^I use the job announcement need$")
    public void I_use_the_job_announcement_need(String need) throws Throwable {
        // Fill in the "I need" text area
        WebElement iNeedField = driver.findElement(By.tagName("textarea"));
        iNeedField.sendKeys(need);
        this.need = need;
    }

    @Then("^A new job announcement with these attributes exists$")
    public void A_new_job_announcement_with_these_attributes_exists() throws Throwable {
        // Submit the new request
        driver.findElement(By.cssSelector("input[value='Request description']")).click();

        // Switch to the "To describe" tab
        driver.findElement(By.partialLinkText("To describe")).click();

        /*
         * Check that the announcement is there by making sure that there is at least one announcement
         * with the same title
         */
        List<WebElement> tds = driver.findElements(By.xpath("//td[contains(., '" + title + "')]"));
        assertThat(tds.size()).isGreaterThan(1);
    }

    @Then("^I cannot request a new job announcement$")
    public void I_cannot_request_a_new_job_announcement() throws Throwable {
        driver.findElement(By.linkText("Start the role play!")).click();
        /*
         * Make sure the button is disabled, i.e. cannot be found
         *
         * The method driver.findElement(...) throws an exception when the
         * element is not found. By using findElements() and asserting the
         * size of the returned list of elements we avoid having to deal
         * with an exception that will be reported as an error later.
         */
        List<WebElement> elements = driver.findElements(
                By.cssSelector("input[value='Request new announcement']"));
        assertThat(elements.size()).isEqualTo(0);
    }

    @Then("^A job announcement with these attributes exists that he can work on$")
    public void A_job_announcement_with_these_attributes_exists_that_he_can_work_on() throws Throwable {
        driver.findElement(By.linkText("Start the role play!")).click();

      // Switch to the "To describe" tab
      driver.findElement(By.partialLinkText("To describe")).click();

        /*
         * Check that the announcement is there by making sure that there is at least one announcement
         * with the same title
         */
      List<WebElement> tds = driver.findElements(By.xpath("//td[contains(., '" + title + "')]"));
      assertThat(tds.size()).isGreaterThan(1);
    }
}
