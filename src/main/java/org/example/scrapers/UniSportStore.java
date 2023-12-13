package org.example.scrapers;

import org.example.DatabaseUtility;
import org.example.ProductInfo;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.List;
/**
 * The UniSportStore class is responsible for scraping product data from the UniSportStore website.
 * It calls the runScraper method from unisportstore1 to perform the scraping of everything except the sku.
 * It saves the product data in a List of ProductInfo objects and iterates through them,
 * calling the getSKUForProduct method from unisportstore2 to get the sku for each product.
 */
public class UniSportStore {
    /**
     * Runs the scraper for the UniSportStore website.
     * This method orchestrates the process of scraping product data from UniSportStore,
     * including fetching product URLs, SKU details, and storing them in the database.
     * It calls the runScraper method from unisportstore1 to perform the scraping of everything except the sku.
     * It saves the product data in a List of ProductInfo objects and iterates through them,
     * calling the getSKUForProduct method from unisportstore2 to get the sku for each product.
     *
     * @param dbUtil The DatabaseUtility instance for interacting with the database.
     * @param driver The WebDriver instance for navigating and extracting data from the web.
     * @param js The JavascriptExecutor for executing JavaScript commands during scraping.
     */
    public static void runScraper(DatabaseUtility dbUtil, WebDriver driver, JavascriptExecutor js) {
        try {
            List<ProductInfo> products = unisportstore1.runScraper(driver,js);

            for (ProductInfo product : products) {
                String sku_full = unisportstore2.getSKUForProduct(driver, product.getUrl());
                if(!sku_full.contains("-"))
                    continue;
                product.setSkuFull(sku_full);

                if(!dbUtil.skuFullExists(sku_full)){
                    if(!dbUtil.skuExists(product.getSku()))
                        dbUtil.insertShoeModel(product.getSku(),"Nike");
                    dbUtil.insertShoes(sku_full,product.getSku(), product.getTitle(), product.getImageUrl());
                }
                if(!dbUtil.urlExists(product.getUrl())){
                    dbUtil.insertComparison(sku_full, product.getUrl(), product.getPrice());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


    }

}
