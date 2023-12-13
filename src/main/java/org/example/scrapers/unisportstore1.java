package org.example.scrapers;

import org.example.ProductInfo;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;
/**
 * The UniSportStore1 class is responsible for scraping product data from the UniSportStore website.
 * It utilizes Selenium WebDriver for web navigation and data extraction.
 * It saves the product data in a List of ProductInfo objects and returns it.
 */
public class unisportstore1 {
    /**
     * Runs the scraper for the UniSportStore website.
     * This method orchestrates the process of scraping product data from UniSportStore,
     * and stores them in a List of ProductInfo objects.
     * It calls the acceptCookiesIfPresent method to accept the cookies on the website.
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
            driver.get("https://www.unisportstore.com/football-boots/1409-nike-football-boots/");
            do {
                acceptCookiesIfPresent(driver);
                Thread.sleep(2000);

                // Now extract the data
                List<WebElement> productElements = driver.findElements(By.cssSelector("ul[data-testid='products'] > li"));
                for (WebElement productElement : productElements) {

                    // Extract the title
                    String title = productElement.findElement(By.cssSelector("img[data-testid='product-image']")).getAttribute("alt");

                    // Extract the URL
                    String url = productElement.findElement(By.cssSelector("a[href]")).getAttribute("href");

                    // Extract the price
                    String price = "";
                    try {
                        price = productElement.findElement(By.cssSelector("span[class*='text-base']")).getText();
                    } catch (NoSuchElementException e) {
                        System.out.println("Price not found for product: " + title);
                    }

                    // Extract the image URL
                    String imageUrl = productElement.findElement(By.cssSelector("img[data-testid='product-image']")).getAttribute("src");

                    // Print the details
                    System.out.println("Title: " + title);
                    System.out.println("URL: " + url);
                    System.out.println("Price: " + price);
                    System.out.println("Image URL: " + imageUrl);

                    ProductInfo product = new ProductInfo();
                    product.setTitle(title);
                    product.setImageUrl(imageUrl);
                    product.setUrl(url);
                    product.setPrice(price);
                    products.add(product);
                }


            }while(clickNextButton(driver));



        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Clicks the 'Next' button on the UniSportStore website if it is present and clickable.
     * This method finds the 'Next' button using its aria-label attribute.
     * It then checks if the button is enabled and not obscured by other elements.
     * If the button is enabled and not obscured by other elements, it clicks the button.
     * If the button is not clickable it returns false.
     * @param driver
     * @return
     */
    private static boolean clickNextButton(WebDriver driver) {
        try {
            WebElement nextButton = driver.findElement(By.cssSelector("a[aria-label='Next Page']"));

            // Check if the button is enabled and not obscured by other elements
            if (nextButton != null && nextButton.isEnabled() && nextButton.isDisplayed()) {
                nextButton.click();
                return true;
            }
        } catch (NoSuchElementException | TimeoutException e) {
            // No more pages or the next button is not clickable within the wait time
        } catch (ElementClickInterceptedException e) {
            // Element is not clickable at the moment (might be obscured by another element)
        }
        return false;
    }
    /**
     * Accepts the cookies on the UniSportStore website if the cookie consent banner is present.
     * This method finds the "Accept all" button using its class and aria-label attributes.
     * It then checks if the button is displayed and enabled.
     * If the button is displayed and enabled, it clicks the button to accept the cookies.
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

}

