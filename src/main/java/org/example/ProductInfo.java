package org.example;

public class ProductInfo {
    String title;
    String url;
    String imageUrl;
    String sku;
    String sku_full;
    String price;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setSkuFull(String sku_full) {
        this.sku_full = sku_full;
        this.sku = sku_full.substring(0,sku_full.indexOf("-"));
    }

    public String getSku() {
        return sku;
    }

    public String getSkuFull() {
        return sku_full;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}