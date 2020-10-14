package com.example.shoppingmall.Home;

// 상품 정보를 담은 클래스
public class Item {
    private String image, name;
    private int price;

    public Item(){}


    // 각 변수에 대한 getter, setter
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
