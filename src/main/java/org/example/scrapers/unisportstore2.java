package org.example.scrapers;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class unisportstore2 {
    public static String getSKUForProduct(WebDriver driver, String productUrl) {
        String sku_full="";
        try {
            // Navigate to the website
            driver.get(productUrl);

            // Wait for the dynamic content to load
            Thread.sleep(1000);
            acceptCookiesIfPresent(driver);

            Thread.sleep(1000);
            clickTab(driver, "Specifications");
            Thread.sleep(1000);


            // Find all elements with the class 'specText text'
            List<WebElement> specElements = driver.findElements(By.cssSelector("div.specText.text"));

            String styleCode = "";
            for (WebElement element : specElements) {
                String text = element.getText();
                // Check if the text contains a hyphen, indicating it might be a style code
                if (text.contains("-")) {
                    styleCode = text;
                    break;
                }
            }

            if (!styleCode.isEmpty()) {
                System.out.println("Style Code: " + styleCode);
                sku_full=styleCode.toLowerCase();
            } else {
                System.out.println("Style code not found.");
            }
        }catch (NoSuchElementException e) {
            System.out.println("Style code element not found.");
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sku_full;
    }

    private static void acceptCookiesIfPresent(WebDriver driver) {
        try {
            // Find the "Accept all" button using its class and aria-label attributes
            WebElement acceptButton = driver.findElement(By.cssSelector("button.coi-banner__accept[aria-label='Accept all']"));

            // Check if the button is displayed and enabled
            if (acceptButton != null && acceptButton.isDisplayed() && acceptButton.isEnabled()) {
                acceptButton.click();
                System.out.println("Accepted cookies.");
            }
        } catch (NoSuchElementException e) {
            // If the element is not found, it means the cookie consent banner is not present
            System.out.println("Cookie consent banner not present.");
        }
    }
    private static void clickTab(WebDriver driver, String tabTitle) {
        try {
            // Find the tab with the specified title
            WebElement tab = driver.findElement(By.xpath("//div[@class='tabHeader' and text()='" + tabTitle + "']/ancestor::div[@role='button']"));

            // Click the tab if it is not null and is visible
            if (tab != null && tab.isDisplayed()) {
                tab.click();
                System.out.println("Clicked tab: " + tabTitle);
            } else {
                System.out.println("Tab not found or not visible: " + tabTitle);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Tab not found: " + tabTitle);
        }
    }

}
