package com.plexiti.showcase.jobannouncement.validation.support;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.runner.Description;

/*
 * The reason for this class is that we want to be able to update the status of a SauceLabs job
 * from the CucumberSeleniumHooks.@After method and the needed methods in the SauceOnDemandTestWatcher class
 * are protected. Thus we make them public in this class.
 */
public class SauceOnDemandCucumberTestWatcher extends SauceOnDemandTestWatcher {
    public SauceOnDemandCucumberTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider) {
        super(sessionIdProvider);
    }

    public SauceOnDemandCucumberTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider, SauceOnDemandAuthentication authentication) {
        super(sessionIdProvider, authentication);
    }

    public SauceOnDemandCucumberTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider, String username, String accessKey) {
        super(sessionIdProvider, username, accessKey);
    }

    public void failed(Throwable e, Description description) {
        super.failed(e, description);
    }

    public void succeeded(Description description) {
        super.succeeded(description);
    }
}