package org.example;
/**
 * This is the main application class that runs all the scrapers
 * and inserts the data into the database
 */
/** Importing the scrapers */
import org.example.scrapers.*;
/** Importing the JavascriptExecutor and WebDriver classes */
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
/** Importing the ChromeDriver and ChromeOptions classes */
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
/** Importing the concurrent package for multithreading */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApplication {
    /**
     * This method runs all the scrapers in parallel using multithreading
     * @param args
     */
    public static void main(String[] args) {
        /**
         * Creating a fixed thread pool of size 5
         * Each scraper is submitted to the executor service
         * and is run in parallel
         */
        /** Creating a fixed thread pool */
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        /** Submitting each scraper task to the executor service */
        executorService.submit(() -> runScraper(1));
        executorService.submit(() -> runScraper(2));
        executorService.submit(() -> runScraper(3));
        executorService.submit(() -> runScraper(4));
        executorService.submit(() -> runScraper(5));

        /** Shutdown the executor service */
        executorService.shutdown();
    }
    public static void runScraper(int scraperChoice){
        /** Setting the path of the ChromeDriver */
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        System.setProperty("webdriver.chrome.whitelistedIps", "");
        // Create an instance of ChromeOptions
        ChromeOptions options = new ChromeOptions();
        // Set the path of the Chrome for testing
        options.setBinary("C:\\Program Files\\Google\\chrome-win64\\chrome.exe");
        //initialising webdriver and javascript executor
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
        // Close Hibernate session
        dbUtil.closeSessionFactory();

        // Quit WebDriver
        driver.quit();
    }
}
