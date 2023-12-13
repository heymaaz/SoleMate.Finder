package org.example.scrapers;

import org.example.DatabaseUtility;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class FootStore {
    public static void runScraper(DatabaseUtility dbUtil, WebDriver driver, JavascriptExecutor js) {
        try {
            // Navigate to the website
            driver.get("https://foot-store.com/boots/nike?limit=80");
            do {
                Thread.sleep(1000);
                acceptAllButton(driver);
                Thread.sleep(1000);
                // Now extract the data
                List<WebElement> productElements = driver.findElements(By.cssSelector("ul.products-grid li.item"));
                for (WebElement productElement : productElements) {
                    // Extract the title
                    String title = productElement.findElement(By.cssSelector(".product-name a")).getAttribute("title");
                    title = title.substring(5+title.indexOf("Nike "));
                    // Extract the URL
                    String url = productElement.findElement(By.cssSelector(".product-name a")).getAttribute("href");

                    // Extract the price
                    String price = "";
                    try {
                        price = productElement.findElement(By.cssSelector(".price-box .price")).getText();
                    } catch (NoSuchElementException e) {
                        System.out.println("Price not found for product: " + title);
                    }

                    // Extract the image URL


                    // Print the details
                    System.out.println("Title: " + title);
                    System.out.println("URL: " + url);
                    System.out.println("Price: " + price);
                    String productCode = "";
                    if (url != null && !url.isEmpty()) {
                        int lastSlashIndex = url.lastIndexOf('/');
                        int hyphenIndex = url.indexOf('-', lastSlashIndex);
                        hyphenIndex = url.indexOf('-', hyphenIndex+1);
                        if (lastSlashIndex != -1 && hyphenIndex != -1) {
                            productCode = url.substring(lastSlashIndex + 1, hyphenIndex).toUpperCase();
                            System.out.println("Product Code: " + productCode);
                            String sku_full = productCode.toLowerCase().trim();
                            String sku = sku_full.substring(0,sku_full.indexOf("-"));
                            String brand = "Nike";
                            String imageUrl ="";
                            if(!dbUtil.skuFullExists(sku_full)){
                                if(!dbUtil.skuExists(sku))
                                    dbUtil.insertShoeModel(sku,brand);
                                dbUtil.insertShoes(sku_full,sku,title,imageUrl);
                            }
                            if(!dbUtil.urlExists(url)){
                                dbUtil.insertComparison(sku_full, url, price);
                            }
                        }
                    }


                }
            }while (clickNextButton(driver));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static boolean clickNextButton(WebDriver driver) {
        try {
            WebElement nextButton = driver.findElement(By.cssSelector(".toolbar-bottom .pages li.next a"));
            if (nextButton.isDisplayed()) {
                nextButton.click();
                return true;
            }
        } catch (NoSuchElementException e) {
            // No more pages
        }
        return false;
    }
    private static void acceptAllButton(WebDriver driver) {
        try {
            WebElement continueWithoutAcceptingButton = driver.findElement(By.xpath("//span[contains(text(),'Continue without accepting')]"));
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", continueWithoutAcceptingButton);
        } catch (NoSuchElementException e) {
            // No accept button
        }
    }
}
