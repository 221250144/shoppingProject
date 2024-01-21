package com.itheima.springbootinit.Goods;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

enum GoodsType {
    food,clothes,furnishing,study,electric,daily,medical,sport
}

@Table(name = "goods")
@Entity
public class Goods {
    @Id
    private String name; // 商品名
    private String description; // 描述
    private int price; // 价格
    private boolean status; // 判断有没有被加入购物车
    private String imagePath; // 图片路径
    private GoodsType type; // 商品类型

    public boolean isStatus() {
        return status;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public Goods() {
    }

    public Goods(String name, String description, int price, boolean status, String imagePath, GoodsType type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.imagePath = imagePath;
        this.type = type;
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
        return "name: " + name + " description: " + description + " price: " + price + " status: " + status + " imagePath: " + imagePath + " type: " + type;
    }
}
