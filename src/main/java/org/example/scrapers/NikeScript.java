package org.example.scrapers;

import org.example.DatabaseUtility;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Scanner;
/**
 * NikeScript is a class designed to interact with Nike's website for men's football shoes.
 * It utilizes Selenium WebDriver to navigate the website, handle dynamic content, and extract product information.
 */
public class NikeScript {
    /**
     * Checks for the presence of an 'Accept All' button on the page and clicks it if found.
     * This method is useful for handling cookie consent popups.
     *
     * @param driver The WebDriver instance used to interact with the web page.
     */
    public static void checkAndClickAcceptButton(WebDriver driver) {
        try {
            // Locate the button by its attributes (e.g., class name, data-testid, etc.)
            WebElement acceptButton = driver.findElement(By.cssSelector("button[data-testid='dialog-accept-button']"));

            // Check if the button is displayed and clickable
            if (acceptButton.isDisplayed()) {
                // Wait for 1 second before clicking
                Thread.sleep(1000);
                acceptButton.click();
                System.out.println("Clicked 'Accept All' button.");
            }
        } catch (NoSuchElementException e) {
            // Button not found, do nothing
            System.out.println("'Accept All' button not found.");
        } catch (InterruptedException e) {
            // Handle interrupted exception
            e.printStackTrace();
        }
    }
    /**
     * Executes the scraping process for Nike's website.
     * This method navigates to the Nike website for men's football shoes, extracts product information
     * such as title, URL, price, and image URL, and stores this information in the database.
     *
     * @param dbUtil The database utility used for data storage.
     * @param driver The WebDriver instance used for web scraping.
     * @param js The JavascriptExecutor instance used for executing JavaScript on the web page.
     */
    public static void runScraper(DatabaseUtility dbUtil, WebDriver driver, JavascriptExecutor js){
        try {
            // Navigate to the website
            driver.get("https://www.nike.com/gb/w/mens-football-shoes-1gdj0znik1zy7ok");
            //driver.get("https://www.nike.com/gb/w/football-shoes-1gdj0zy7ok");

            // Wait for the dynamic content to load or scroll down
            Thread.sleep(2000);

            // Example of scrolling down the page
            for (int i = 0; i < 15; i++) {
                checkAndClickAcceptButton(driver);
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                Thread.sleep(2000); // Wait for content to load
            }

            // Now extract the data
            List<WebElement> productElements = driver.findElements(By.className("product-card"));
            for (WebElement productElement : productElements) {
                // Extract the title
                String title = productElement.findElement(By.className("product-card__title")).getText().trim();

                // Extract the URL
                String url = productElement.findElement(By.className("product-card__link-overlay")).getAttribute("href");
                //Get product_id
                url = url.toLowerCase();
                if (Character.isDigit(url.charAt(1 + url.lastIndexOf('/')))) {
                    continue;
                }

                String sku_full = url.substring(1+url.lastIndexOf('/'));
                // Extract the price
                String price = productElement.findElement(By.className("product-price__wrapper")).getText();
                Scanner sc = new Scanner(price);
                price = sc.nextLine();
                /*sc = new Scanner(price.replaceAll("£",""));
                double full_price=0.0;
                while(sc.hasNextDouble())
                {
                    full_price=sc.nextDouble();
                }*/
                // Extract the image URL
                String imageUrl = productElement.findElement(By.className("product-card__hero-image")).getAttribute("src");

                // Print the details
                System.out.println("Title: " + title);
                System.out.println("sku_full: " + sku_full);
                System.out.println("URL: " + url);
                System.out.println("Price: " + price);
                //System.out.println("Full Price:"+full_price);
                System.out.println("Image URL: " + imageUrl);
                String sku = sku_full.substring(0,sku_full.indexOf("-"));
                System.out.println("sku: " + sku);

                String brand = title.substring(0,title.indexOf(" "));
                if(!dbUtil.skuFullExists(sku_full)){
                    if(!dbUtil.skuExists(sku))
                        dbUtil.insertShoeModel(sku,brand);
                    dbUtil.insertShoes(sku_full,sku,title,imageUrl);
                }
                if(!dbUtil.urlExists(url)){
                    dbUtil.insertComparison(sku_full, url, price);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
