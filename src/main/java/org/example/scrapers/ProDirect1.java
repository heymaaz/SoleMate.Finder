package org.example.scrapers;

import org.example.ProductInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * This class scrapes the ProDirect website
 * It utilizes Selenium WebDriver for web navigation and data extraction.
 * It saves the product data in a List of ProductInfo objects and returns it.
 */
public class ProDirect1 {
    /**
     * Runs the scraper for the ProDirect website.
     * This method orchestrates the process of scraping product data from ProDirect,
     * and stores them in a List of ProductInfo objects.
     * It calls the ClickViewMoreButton method to click the "View More" button on the website
     * to load all the products on the page.
     * It then iterates through the products and extracts the title, URL, price, and image URL.
     * It saves the product data in a List of ProductInfo objects and returns it.
     *
     * @param driver The WebDriver instance for navigating and extracting data from the web.
     * @param js The JavascriptExecutor for executing JavaScript commands during scraping.
     * @return products The List of ProductInfo objects containing the scraped data.
     */
    public static List<ProductInfo> runScraper(WebDriver driver, JavascriptExecutor js) {
        List<ProductInfo> products = new ArrayList<>();
        try {
            // Navigate to the website
            driver.get("https://www.prodirectsport.com/soccer/l/adults/departments-boots/activity-football/brand-nike/");

            // Wait for the dynamic content to load or scroll down
            Thread.sleep(2000);

            ClickViewMoreButton(driver,js);

            // Now extract the data
            List<WebElement> productElements = driver.findElements(By.cssSelector("div._root_ehpbx_6"));
            for (WebElement productElement : productElements) {
                // Extract the title
                String title = productElement.findElement(By.className("_name_ehpbx_67")).getText();

                // Extract the URL
                String url = productElement.findElement(By.cssSelector("a._link_ehpbx_21")).getAttribute("href");

                // Extract the price
                String price = "";
                double selling_price=0.0;
                try {
                    price = productElement.findElement(By.className("_price_ehpbx_72")).getText();
                    Scanner sc = new Scanner(price.replaceAll("[a-zA-Z£:]|\\r?\\n]"," "));
                    while(sc.hasNextDouble())
                    {
                        selling_price=sc.nextDouble();
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Price not found for product: " + title);
                }

                // Extract the image URL
                String imageUrl = "";
                try {
                    imageUrl = productElement.findElement(By.cssSelector("img._image_ehpbx_32")).getAttribute("src");
                } catch (NoSuchElementException e) {
                    System.out.println("Image URL not found for product: " + title);
                }
                ProductInfo product = new ProductInfo();
                product.setTitle(title);
                product.setImageUrl(imageUrl);
                product.setUrl(url);
                // Print the details
                System.out.println("Title: " + title);
                System.out.println("URL: " + url);
                System.out.println("Price: " + price);
                if (selling_price != 0.0) {
                    System.out.println("Selling Price: " + selling_price);
                    price = "£"+Double.toString(selling_price);
                    product.setPrice(price);
                }
                System.out.println("Image URL: " + imageUrl);
                products.add(product);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    /**
     * This method clicks the "View More" button on the ProDirect website
     * to load all the products on the page.
     * It uses a while loop to keep clicking the button until it is no longer present.
     *
     * @param driver The WebDriver instance for navigating and extracting data from the web.
     * @param js The JavascriptExecutor for executing JavaScript commands during scraping.
     */
    public static void ClickViewMoreButton(WebDriver driver,JavascriptExecutor js) {
        while (true) {
            // Loop until the button is no longer present
            try {
                // Attempt to find the "View More" button

                WebElement viewMoreButton = driver.findElement(By.cssSelector(".product-listing__view-more button"));

                // Scroll to the button
                js.executeScript("arguments[0].scrollIntoView(true);", viewMoreButton);

                // Click the button
                viewMoreButton.click();

                // Wait for 2 seconds
                Thread.sleep(2000);
            } catch (NoSuchElementException e) {
                // If the button is not found, break the loop
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
