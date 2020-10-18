package com.example.shoppingmall;

import java.io.Serializable;

// 상품 정보를 담은 클래스
public class Item implements Serializable {
    private String image, name, price;
    private boolean check;

    public Item(){}

    public Item(String image, String name, String price, boolean check){
        this.image = image;
        this.name = name;
        this.price = price;
        this.check = check;
    }


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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
