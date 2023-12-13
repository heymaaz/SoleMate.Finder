package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "shoes")
public class Shoes {

    @Id
    @Column(name = "sku_full")
    private String skuFull;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_base", referencedColumnName = "sku_base")
    private ShoeModel shoeModel;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "image_url")
    private String imageUrl;

    // Constructors, getters, setters
    public Shoes() {}

    public Shoes(String skuFull, ShoeModel shoeModel, String fullName, String imageUrl) {
        this.skuFull = skuFull;
        this.shoeModel = shoeModel;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
    }

    // Getters and setters

    public String getSkuFull() {
        return skuFull;
    }

    public void setSkuFull(String skuFull) {
        this.skuFull = skuFull;
    }

    public void setShoeModel(ShoeModel shoeModel) {
        this.shoeModel = shoeModel;
    }
    public ShoeModel getShoeModel() {
        return shoeModel;
    }
    public void setShoeModel(String skuBase, String brand) {
        this.shoeModel = new ShoeModel(skuBase, brand);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
