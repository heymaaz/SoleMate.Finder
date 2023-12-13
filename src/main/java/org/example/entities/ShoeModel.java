package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shoe_model")
public class ShoeModel {

    @Id
    @Column(name = "sku_base")
    private String skuBase;

    @Column(name = "brand")
    private String brand;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Constructors, getters, setters
    public ShoeModel() {}
    public ShoeModel(String skuBase, String brand) {
        this.skuBase = skuBase;
        this.brand = brand;
    }
    public ShoeModel(String skuBase, String brand, String name, String description) {
        this.skuBase = skuBase;
        this.brand = brand;
        this.name = name;
        this.description = description;
    }

    // Getters and setters

    public String getSkuBase() {
        return skuBase;
    }

    public void setSkuBase(String skuBase) {
        this.skuBase = skuBase;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
