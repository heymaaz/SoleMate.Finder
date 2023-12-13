package org.example.scrapers;

import org.example.DatabaseUtility;
import org.openqa.selenium.*;

import java.util.List;

/**
 * The ultraFootball class is responsible for scraping product data from the ultraFootball website.
 * It utilizes Selenium WebDriver for web navigation and data extraction, and integrates with
 * the DatabaseUtility class for data persistence.
 */
public class ultraFootball {
    /**
     * Runs the scraper for the ultraFootball website.
     * This method orchestrates the process of scraping product data from ultraFootball,
     * including fetching product URLs, SKU details, and storing them in the database.
     *
     * @param dbUtil The DatabaseUtility instance for interacting with the database.
     * @param driver The WebDriver instance for navigating and extracting data from the web.
     * @param js The JavascriptExecutor for executing JavaScript commands during scraping.
     */
    public static void runScraper(DatabaseUtility dbUtil, WebDriver driver, JavascriptExecutor js){
        try {
            // Navigate to the website
            driver.get("https://www.ultrafootball.com/collections/footwear-nike?view=view-all");

            // Wait for the dynamic content to load or scroll down
            Thread.sleep(2000);


            // Now extract the data
            List<WebElement> gridItems = driver.findElements(By.cssSelector("div.grid__item"));

            for (WebElement item : gridItems) {
                try {
                    // Extract the title
                    WebElement titleElement = item.findElement(By.cssSelector("input[id='gtmImpressionTitle']"));
                    String title = titleElement.getAttribute("value");

                    // Extract the URL
                    WebElement urlElement = item.findElement(By.cssSelector("a[href*='/products/']"));
                    String url = urlElement.getAttribute("href");

                    // Extract the price
                    WebElement priceElement = item.findElement(By.cssSelector("div.product-thumb__price .money"));
                    String price = priceElement.getText();

                    String sku = title.substring(1+title.indexOf("(")).replaceAll("\\)","");
                    String sku_full = sku.toLowerCase().trim();
                    sku=sku_full.substring(0,sku_full.indexOf("-"));
                    String brand = "Nike";
                    String imageUrl ="";
                    title = title.substring(0,title.indexOf("("));
                    if(sku_full.contains(" ")){
                        sku_full=sku_full.substring(0,sku_full.indexOf(" "));
                    }
                    // Print the details
                    System.out.println("Title: " + title);
                    System.out.println("URL: " + url);
                    System.out.println("Price: " + price);
                    System.out.println("sku: " + sku);


                    if(!dbUtil.skuFullExists(sku_full)){
                        if(!dbUtil.skuExists(sku))
                            dbUtil.insertShoeModel(sku,brand);
                        dbUtil.insertShoes(sku_full,sku,title,imageUrl);
                    }
                    if(!dbUtil.urlExists(url)){
                        dbUtil.insertComparison(sku_full, url, price);
                    }

                } catch (NoSuchElementException e) {
                    //Skip to next item System.out.println("An element was not found for one of the items.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
