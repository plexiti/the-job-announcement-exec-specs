package com.plexiti.showcase.jobannouncement.features.selenium.definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RequestNewAnnouncementSteps {
    @Given("^I am logged in as \"([^\"]*)\"$")
    public void I_am_logged_in_as(String arg1) throws Throwable {
        // Express the Regexp above with the code you wish you had
        //throw new PendingException();
    }

    @When("^I request an announcement with title \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void I_request_an_announcement_with_title_and_description(String arg1, String arg2) throws Throwable {
        // Express the Regexp above with the code you wish you had
        //throw new PendingException();
    }

    @Then("^A new job announcement with these attributes exists$")
    public void A_new_job_announcement_with_these_attributes_exists() throws Throwable {
        // Express the Regexp above with the code you wish you had
        //throw new PendingException();
    }
}
