package org.example.scrapers;

import org.openqa.selenium.*;

import java.util.List;
/**
 * The unisportstore2 class is responsible for scraping sku from the product page.
 * It utilizes Selenium WebDriver for web navigation and data extraction.
 * It is called by the UniSportStore class.
 * It saves the sku in a String and returns it.
 */
public class unisportstore2 {
    /**
     * Runs the scraper for the UniSportStore website.
     * This method orchestrates the process of scraping sku from UniSportStore,
     * and stores them in a String.
     * It calls the acceptCookiesIfPresent method to accept the cookies on the website.
     * It then extracts the sku.
     * It saves the sku in a String and returns it.
     *
     * @param driver The WebDriver instance for navigating and extracting data from the web.
     * @param productUrl The URL of the product page.
     * @return sku_full The sku of the product.
     */
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
    /**
     * Accepts the cookies on the UniSportStore website.
     * This method finds the "Accept all" button on the cookie consent banner and clicks it.
     * It prints a message to the console to indicate whether the button was found and clicked.
     *
     * @param driver The WebDriver instance for navigating and extracting data from the web.
     */
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
    /**
     * Clicks the Specifications tab on the UniSportStore website.
     * This method finds Specifications the tab using its title and clicks it.
     * It prints a message to the console to indicate whether the tab was found and clicked.
     *
     * @param driver The WebDriver instance for navigating and extracting data from the web.
     * @param tabTitle The title of the tab to click. This is case-sensitive. Must match the title exactly. "Specifications"
     */
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
