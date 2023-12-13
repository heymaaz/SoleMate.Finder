package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 * Represents the shoe_model table in the database.
 * This class is used by the DatabaseUtility class to store the shoe model information.
 * The shoe_model table contains the following columns:
 * sku_base, brand, name, description
 * The sku_base column is the primary key.
 */
@Entity
@Table(name = "shoe_model")
public class ShoeModel {
    /** The base SKU of the shoe. Also, the primary key */
    @Id
    @Column(name = "sku_base")
    private String skuBase;

    /** The brand of the shoe. Default is "Nike'*/
    @Column(name = "brand")
    private String brand;

    /** The name of the shoe. */
    @Column(name = "name")
    private String name;

    /** The description of the shoe. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Constructors, getters, setters
    public ShoeModel() {}
    /**
     * Constructs a new ShoeModel instance with the specified base SKU and brand.
     * @param skuBase The base SKU of the shoe.
     * @param brand The brand of the shoe.
     */
    public ShoeModel(String skuBase, String brand) {
        this.skuBase = skuBase;
        this.brand = brand;
    }
    /**
     * Constructs a new ShoeModel instance with the specified base SKU, brand, name, and description.
     * @param skuBase The base SKU of the shoe.
     * @param brand The brand of the shoe.
     * @param name The name of the shoe.
     * @param description The description of the shoe.
     */
    public ShoeModel(String skuBase, String brand, String name, String description) {
        this.skuBase = skuBase;
        this.brand = brand;
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the base SKU of the shoe.
     * @return The base SKU of the shoe.
     */
    public String getSkuBase() {
        return skuBase;
    }
    /**
     * Sets the base SKU of the shoe.
     * @param skuBase The base SKU of the shoe.
     */
    public void setSkuBase(String skuBase) {
        this.skuBase = skuBase;
    }
    /**
     * Gets the brand of the shoe.
     * @return The brand of the shoe.
     */
    public String getBrand() {
        return brand;
    }
    /**
     * Sets the brand of the shoe.
     * @param brand The brand of the shoe.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
    /**
     * Gets the name of the shoe.
     * @return The name of the shoe.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the shoe.
     * @param name The name of the shoe.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the description of the shoe.
     * @return The description of the shoe.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets the description of the shoe.
     * @param description The description of the shoe.
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
