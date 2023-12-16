package org.example;
/**
 * Main application class that runs all the scrapers and inserts the data into the database.
 *
 * <p>Uses the following scrapers:
 * <ol>
 * <li>FootStore</li>
 * <li>Nike</li>
 * <li>Ultra Football</li>
 * <li>ProDirect</li>
 * <li>UniSportStore</li>
 * </ol>
 *
 * @author Maaz Chowdhry
 * @date 2023-12-13
 * @version 1.0
 */

// Importing the scrapers
import org.example.scrapers.*;
// Importing the JavascriptExecutor and WebDriver classes
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
// Importing the ChromeDriver and ChromeOptions classes
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
// Importing the concurrent package for multithreading
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApplication {
    /**
     * This method runs all the scrapers in parallel using multithreading
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        /*
         * Creating a fixed thread pool of size 5
         * Each scraper is submitted to the executor service
         * and is run in parallel
         */
        // Creating a fixed thread pool of size 5
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Submitting each scraper task to the executor service
        executorService.submit(() -> runScraper(1));
        executorService.submit(() -> runScraper(2));
        executorService.submit(() -> runScraper(3));
        executorService.submit(() -> runScraper(4));
        executorService.submit(() -> runScraper(5));

        // Shutdown the executor service once all the tasks are completed
        executorService.shutdown();
    }
    /**
     * This method runs the scraper based on the scraper choice
     * @param scraperChoice
     * 1 - FootStore
     * 2 - Nike
     * 3 - Ultra Football
     * 4 - ProDirect
     * 5 - UniSportStore
     */
    public static void runScraper(int scraperChoice){
        // Setting the path of the ChromeDriver
        System.out.println("Setting the path of the ChromeDriver");
        System.setProperty("webdriver.chrome.driver", "/Users/maazchowdhry/Downloads/chromedriver/mac_arm-119.0.6045.105/chromedriver-mac-arm64/chromedriver");
        System.setProperty("webdriver.chrome.whitelistedIps", "");
        // Create an instance of ChromeOptions
        System.out.println("Create an instance of ChromeOptions");
        ChromeOptions options = new ChromeOptions();
        // Set the path of the Chrome for testing
        System.out.println("Set the path of the Chrome for testing");
        options.setBinary("/Applications/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing");
        //initialising webdriver and javascript executor
        System.out.println("initialising webdriver and javascript executor");
        WebDriver driver = new ChromeDriver(options);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        DatabaseUtility dbUtil = new DatabaseUtility();

        switch (scraperChoice){
            case 1:
                //Run footStore scraper
                FootStore.runScraper(dbUtil,driver,js);
                break;
            case 2:
                //Run Nike Scraper
                NikeScript.runScraper(dbUtil,driver,js);
                break;
            case 3:
                //Run ultra football scraper
                ultraFootball.runScraper(dbUtil,driver,js);
                break;
            case 4:
                //Run ProDirect scraper
                ProDirect.runScraper(dbUtil,driver,js);
                break;
            case 5:
                //Run UniSportStore scraper
                UniSportStore.runScraper(dbUtil,driver,js);
                break;
            default:
                //Invalid scraper choice
                System.out.println("Invalid scraper choice");
        }
        // Close Hibernate session factory
        dbUtil.closeSessionFactory();

        // Quit WebDriver
        driver.quit();
    }
}
