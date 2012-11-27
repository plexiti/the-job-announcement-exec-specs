package com.plexiti.showcase.jobannouncement.web.acceptance.tests;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Date;

import static junit.framework.Assert.assertTrue;

public class EndToEndTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("plexiti", "2d003a41-7fc8-4dc3-848f-eb3fbdc8ef65");

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * JUnit Rule which will record the test name of the current test.  This is referenced when creating the {@link DesiredCapabilities},
     * so that the Sauce Job is created with the test name.
     */
    public @Rule TestName testName= new TestName();

    private WebDriver driver;

    private String sessionId;

    private String appUrl = "http://the-job-announcement.com"; // "http://localhost:8080/the-job-announcement/";


    @Test
    public void fullProcessTest() throws Exception {
        driver.get(appUrl);
        assertTrue(driver.getTitle().contains("Index"));

        driver.findElement(By.linkText("Start the role play!")).click();
        assertTrue(driver.getTitle().contains("The control center"));

        switchToUser("Gonzo The Great");

        // Click on the "Request new announcement" button
        driver.findElement(By.cssSelector("input[value='Request new announcement']")).click();
        assertTrue(driver.getTitle().endsWith("New job anouncement"));

        // Fill in the "I need" text area
        WebElement iNeedField = driver.findElement(By.tagName("textarea"));
        iNeedField.sendKeys("An experienced Java software developer for our next product!");

        // Fill in the "job title" field
        WebElement jobTitleField = driver.findElement(By.cssSelector("input[name*='title']"));
        String jobTitle = "A great Java developer wanted";
        jobTitleField.sendKeys(jobTitle);

        // Submit the new request
        driver.findElement(By.cssSelector("input[value='Request description']")).click();

        // Check that we are back in the list page
        assertTrue(driver.getTitle().contains("The control center"));

        switchToUser("Fozzie Bear");

        // Switch to the "To describe" tab
        switchToTab("To describe");

        driver.findElement(By.cssSelector("input[value='Describe']")).click();
        assertTrue(driver.getTitle().contains("Describe job announcement"));

        WebElement jobDescription = driver.findElement(By.cssSelector("textarea[name*='description']"));
        jobDescription.sendKeys(
                "- Java developer with 10+ years experience\n" +
                "- Good knowledge of open source frameworks\n" +
                "- Communication skills"
        );

        WebElement tweet = driver.findElement(By.cssSelector("textarea[name*='tweet']"));
        // Add the current date to the tweet to avoid rejection from the Twitter API
        Date now = new Date();
        tweet.sendKeys(jobTitle + " (" + now.toString() + ")");

        WebElement fozzieComment = driver.findElement(By.cssSelector("textarea[name*='comment']"));
        fozzieComment.sendKeys("What do you think about the description?!");

        // Click the "Request review" button
        driver.findElement(By.cssSelector("input[value='Request review']")).click();

        switchToUser("Gonzo The Great");

        // Switch to the "To review" tab
        switchToTab("To review");

        // Click on the "Request review"
        WebElement button = driver.findElement(By.cssSelector("input[name*='review']"));
        button.click();
        assertTrue(driver.getTitle().contains("Review job announcement"));

        WebElement gonzoComment = driver.findElement(By.cssSelector("textarea[name*='comment']"));
        gonzoComment.sendKeys("Looks great! Publish it!");

        // Click on button "Approve for publication"
        driver.findElement(By.cssSelector("input[value='Approve for publication']")).click();

        switchToUser("Fozzie Bear");

        switchToTab("To publish");

        // Click on "Publish" button
        driver.findElement(By.cssSelector("input[name*='publish']")).click();
        assertTrue(driver.getTitle().contains("Publish job announcement"));

        // Click on "Publish" button
        driver.findElement(By.cssSelector("input[value='Publish']")).click();

        switchToTab("Published");

        // Click on the "View" button
        driver.findElement(By.cssSelector("input[name*='published']")).click();
        assertTrue(driver.getTitle().contains("Published job announcement"));
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

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("platform", "Windows 2012");
        capabilities.setCapability("version", "17");
        capabilities.setCapability("name", testName.getMethodName());
        capabilities.setCapability("tags", new String[]{"web", "firefox", "windows"});

        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
