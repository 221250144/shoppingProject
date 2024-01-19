package com.itheima.springbootinit.Goods;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Table(name = "goods")
@Entity
public class Goods {
    @Id
    private String name;
    private String description;
    private int price;
    private boolean status;
    private String imagePath;

    public Goods() {
    }

    public Goods(String name, String description, int price, boolean status, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public boolean getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
