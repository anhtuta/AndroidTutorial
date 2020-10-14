package com.dhbkhn.anhtu.listviewadvanced;

/**
 * Created by AnhTu on 03/03/2017.
 */

public class Student {
    String name;
    String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Student(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
