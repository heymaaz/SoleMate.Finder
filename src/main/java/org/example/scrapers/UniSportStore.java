package org.example.scrapers;

import org.example.DatabaseUtility;
import org.example.ProductInfo;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class UniSportStore {
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
