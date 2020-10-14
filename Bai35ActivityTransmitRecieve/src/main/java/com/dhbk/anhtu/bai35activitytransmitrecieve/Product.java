package com.dhbk.anhtu.bai35activitytransmitrecieve;

import java.io.Serializable;

/**
 * Created by AnhTu on 15/03/2017.
 */

public class Product implements Serializable {
    int id;
    String name;
    double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.id+" - "+this.name+" - "+this.price+" dollars";
    }
}
