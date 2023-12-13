package org.example.scrapers;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class ProDirect2 {
    public static String getSKUForProduct(WebDriver driver, String productUrl,JavascriptExecutor js) {
        String sku_full="";
        try {
            // Navigate to the website
            driver.get(productUrl);

            // Wait for the dynamic content to load
            Thread.sleep(2000);

            // Find the "Features" button using its class and the visible text
            WebElement featuresButton = driver.findElement(By.xpath("//button[contains(@class, 'ml-tabs__tab-link') and text()='Features']"));
            js.executeScript("arguments[0].scrollIntoView(true);", featuresButton);
            // Click the "Features" button
            js.executeScript("arguments[0].click();", featuresButton);

            Thread.sleep(1000);
            WebElement manRefElement = driver.findElement(By.xpath("//span[text()='Man. Ref:']"));
            String fullText = (String) js.executeScript("return arguments[0].parentNode.textContent;", manRefElement);

            sku_full = fullText.replace("Man. Ref:", "").trim().toLowerCase();

            System.out.println("Man. Ref Value: " + sku_full);

        }catch (NoSuchElementException e) {
            System.out.println("Button not found");
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sku_full;
    }

}
