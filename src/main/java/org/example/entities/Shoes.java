package org.example.entities;

import jakarta.persistence.*;
/**
 * Represents the shoes table in the database.
 * This class is used by the DatabaseUtility class to store the shoes information.
 * The shoes table contains the following columns:
 * sku_full, sku_base, full_name, image_url
 * The sku_full column is the primary key.
 * The sku_base column is a foreign key referencing the sku_base column in the shoe_model table.
 */
@Entity
@Table(name = "shoes")
public class Shoes {
    /** The full SKU of the shoe. Also, the primary key */
    @Id
    @Column(name = "sku_full")
    private String skuFull;
    /** The shoe model associated with this shoe. Foreign Key referencing the shoe_model table*/
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_base", referencedColumnName = "sku_base")
    private ShoeModel shoeModel;
    /** The full name of the shoe. */
    @Column(name = "full_name")
    private String fullName;
    /** The image URL of the shoe. */
    @Column(name = "image_url")
    private String imageUrl;

    public Shoes() {}
    /**
     * Constructs a new Shoes instance with the specified full SKU, shoe model, full name, and image URL.
     * @param skuFull The full SKU of the shoe.
     * @param shoeModel The shoe model associated with this shoe.
     * @param fullName The full name of the shoe.
     * @param imageUrl The image URL of the shoe.
     */
    public Shoes(String skuFull, ShoeModel shoeModel, String fullName, String imageUrl) {
        this.skuFull = skuFull;
        this.shoeModel = shoeModel;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the full SKU of the shoe.
     * @return The full SKU of the shoe.
     */
    public String getSkuFull() {
        return skuFull;
    }
    /**
     * Sets the full SKU of the shoe.
     * @param skuFull The full SKU of the shoe.
     */
    public void setSkuFull(String skuFull) {
        this.skuFull = skuFull;
    }

    /**
     * Sets the shoe model associated with this shoe.
     * @param shoeModel The shoe model associated with this shoe.
     */
    public void setShoeModel(ShoeModel shoeModel) {
        this.shoeModel = shoeModel;
    }
    /**
     * Gets the shoe model associated with this shoe.
     * @return The shoe model associated with this shoe.
     */
    public ShoeModel getShoeModel() {
        return shoeModel;
    }
    /**
     * Sets the shoe model associated with this shoe.
     * @param skuBase The base SKU of the shoe.
     * @param brand The brand of the shoe.
     */
    public void setShoeModel(String skuBase, String brand) {
        this.shoeModel = new ShoeModel(skuBase, brand);
    }

    /**
     * Gets the full name of the shoe.
     * @return The full name of the shoe.
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * Sets the full name of the shoe.
     * @param fullName The full name of the shoe.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * Gets the image URL of the shoe.
     * @return The image URL of the shoe.
     */
    public String getImageUrl() {
        return imageUrl;
    }
    /**
     * Sets the image URL of the shoe.
     * @param imageUrl The image URL of the shoe.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
