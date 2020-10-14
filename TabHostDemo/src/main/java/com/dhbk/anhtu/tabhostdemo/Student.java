package com.dhbk.anhtu.tabhostdemo;

/**
 * Created by AnhTu on 09/03/2017.
 */

public class Student {
    String name;
    String addr;

    public Student(String name, String addr) {
        this.name = name;
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Student() {
    }
}
