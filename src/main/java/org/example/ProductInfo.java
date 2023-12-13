package org.example;
/**
 * Represents the product information, including details such as title, URL, image URL, SKU, full SKU, and price.
 * This class is used by the scraper classes to store the product information.
 */

public class ProductInfo {
    private String title;
    private String url;
    private String imageUrl;
    private String sku;
    private String sku_full;
    private String price;
    /**
     * Sets the title of the product.
     * @param title title of the product
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Gets the title of the product.
     * @return title of the product
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets the image URL of the product.
     * @param imageUrl url of the image
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    /**
     * Gets the image URL of the product.
     * @return url of the image
     */
    public String getImageUrl() {
        return imageUrl;
    }
    /**
     * Sets the full SKU of the product and extracts the base SKU to set it separately.
     * sku_full is in the format of {skuBase}-{skuColor}
     * @param sku_full The full SKU of the product.
     */
    public void setSkuFull(String sku_full) {
        this.sku_full = sku_full;
        this.sku = sku_full.substring(0,sku_full.indexOf("-"));
    }
    /**
     * Gets the full SKU of the product.
     * @return The full SKU of the product.
     */
    public String getSkuFull() {
        return sku_full;
    }
    /**
     * Gets the SKU of the product.
     * @return the sku of the product
     */
    public String getSku() {
        return sku;
    }

    /**
     * Sets the URL of the product.
     * @param url url of the product
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * Gets the URL of the product.
     * @return url of the product
     */
    public String getUrl() {
        return url;
    }
    /**
     * Sets the price of the product.
     * @param price price of the product
     */
    public void setPrice(String price) {
        this.price = price;
    }
    /**
     * Gets the price of the product.
     * @return price
     */
    public String getPrice() {
        return price;
    }
}