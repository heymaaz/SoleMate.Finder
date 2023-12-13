package org.example.entities;

import jakarta.persistence.*;

/**
 * Represents the comparison table in the database.
 * This class is used by the DatabaseUtility class to store the comparison information.
 * The comparison table contains the following columns:
 * id, sku_full, website_url, selling_price
 * The sku_full column is a foreign key referencing the sku_full column in the shoes table.
 */
@Entity
@Table(name = "comparison")
public class Comparison {
    /** The unique identifier for the comparison. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** The shoes associated with this comparison. */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_full", referencedColumnName = "sku_full")
    private Shoes shoes;

    /** The URL of the website where the shoes are sold. */
    @Column(name = "website_url")
    private String websiteUrl;

    /** The selling price of the shoes on the website. */
    @Column(name = "selling_price")
    private String sellingPrice;

    // Constructors, getters, setters
    public Comparison() {}

    /**
     * Constructs a new Comparison instance with the specified shoes, website URL, and selling price.
     * @param shoes The Shoes entity this comparison is associated with.
     * @param websiteUrl The URL of the website where the shoes are sold.
     * @param sellingPrice The selling price of the shoes on the website.
     */
    public Comparison(Shoes shoes, String websiteUrl, String sellingPrice) {
        this.shoes = shoes;
        this.websiteUrl = websiteUrl;
        this.sellingPrice = sellingPrice;
    }

    /**
     * Sets the unique identifier for the comparison.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the unique identifier for the comparison.
     * @return The unique identifier for the comparison.
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the shoes associated with this comparison.
     * @param shoes The Shoes entity this comparison is associated with.
     */
    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }
    /**
     * Gets the shoes associated with this comparison.
     * @return The Shoes entity this comparison is associated with.
     */
    public Shoes getShoes() {
        return shoes;
    }
    /**
     * Sets the URL of the website where the shoes are sold.
     * @param websiteUrl The URL of the website where the shoes are sold.
     */
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
    /**
     * Gets the URL of the website where the shoes are sold.
     * @return The URL of the website where the shoes are sold.
     */
    public String getWebsiteUrl() {
        return websiteUrl;
    }
    /**
     * Sets the selling price of the shoes on the website.
     * @param sellingPrice The selling price of the shoes on the website.
     */
    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    /**
     * Gets the selling price of the shoes on the website.
     * @return The selling price of the shoes on the website.
     */
    public String getSellingPrice() {
        return sellingPrice;
    }
}
