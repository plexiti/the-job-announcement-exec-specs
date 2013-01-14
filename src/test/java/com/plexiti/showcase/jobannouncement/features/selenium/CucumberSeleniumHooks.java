package com.plexiti.showcase.jobannouncement.features.selenium;

import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.io.IOException;

public class CucumberSeleniumHooks {

    @Before
    public void startFeatureTest() throws IOException {
        System.out.print("startFeatureTest()");
    }

    @After
    public void stopFeatureTest() throws IOException {
        System.out.print("stopFeatureTest()");
    }
}
