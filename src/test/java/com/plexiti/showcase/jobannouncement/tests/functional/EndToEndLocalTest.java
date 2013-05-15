package com.plexiti.showcase.jobannouncement.tests.functional;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;

@Ignore
public class EndToEndLocalTest {

    private WebDriver driver;

    private String appUrl = "http://localhost:8080/the-job-announcement/";

    public void setDriver(WebDriver theDriver) {
        this.driver = theDriver;
    }

    public void setAppUrl(String url) {
        this.appUrl = url;
    }

    @Test
    public void fullProcessTest() throws Exception {
        driver.get(appUrl);
        assertThat(driver.getTitle()).contains("Index");

        driver.findElement(By.linkText("Start the role play!")).click();
        assertThat(driver.getTitle()).contains("The control center");

        switchToUser("Gonzo The Great");

        // Click on the "Request new announcement" button
        driver.findElement(By.cssSelector("input[value='Request new announcement']")).click();
        assertThat(driver.getTitle()).endsWith("New job anouncement");

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
        assertThat(driver.getTitle()).contains("The control center");

        switchToUser("Fozzie The Bear");

        // Switch to the "To describe" tab
        switchToTab("To describe");

        driver.findElement(By.cssSelector("input[value='Describe']")).click();
        assertThat(driver.getTitle()).contains("Describe job announcement");

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
        assertThat(driver.getTitle()).contains("Review job announcement");

        WebElement gonzoComment = driver.findElement(By.cssSelector("textarea[name*='comment']"));
        gonzoComment.sendKeys("Looks great! Publish it!");

        // Click on button "Approve for publication"
        driver.findElement(By.cssSelector("input[value='Approve for publication']")).click();

        switchToUser("Fozzie The Bear");

        switchToTab("To publish");

        // Click on "Publish" button
        driver.findElement(By.cssSelector("input[name*='publish']")).click();
        assertThat(driver.getTitle()).contains("Publish job announcement");

        // Click on "Publish" button
        driver.findElement(By.cssSelector("input[value='Publish']")).click();

        switchToTab("Published");

        // Click on the "View" button
        driver.findElement(By.cssSelector("input[name*='published']")).click();
        assertThat(driver.getTitle()).contains("Published job announcement");
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
        setDriver(new FirefoxDriver());
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
