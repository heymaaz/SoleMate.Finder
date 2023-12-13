package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "comparison")
public class Comparison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_full", referencedColumnName = "sku_full")
    private Shoes shoes;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "selling_price")
    private String sellingPrice;

    // Constructors, getters, setters
    public Comparison() {}

    public Comparison(Shoes shoes, String websiteUrl, String sellingPrice) {
        this.shoes = shoes;
        this.websiteUrl = websiteUrl;
        this.sellingPrice = sellingPrice;
    }

    // Getters and setters
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }
    public Shoes getShoes() {
        return shoes;
    }
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
    public String getWebsiteUrl() {
        return websiteUrl;
    }
    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    public String getSellingPrice() {
        return sellingPrice;
    }


}
