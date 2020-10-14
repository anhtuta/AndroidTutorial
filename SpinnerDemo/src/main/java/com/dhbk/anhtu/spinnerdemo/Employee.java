package com.dhbk.anhtu.spinnerdemo;

import android.text.Editable;

/**
 * Created by AnhTu on 06/03/2017.
 */

public class Employee {
    private String name;
    String dieDate;
    int sickNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDieDate() {
        return dieDate;
    }

    public void setDieDate(String dieDate) {
        this.dieDate = dieDate;
    }

    public int getSickNumber() {
        return sickNumber;
    }

    public void setSickNumber(int sickNumber) {
        this.sickNumber = sickNumber;
    }

    public Employee(String name, String dieDate, int sickNumber) {
        this.name = name;
        this.dieDate = dieDate;
        this.sickNumber = sickNumber;
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return this.name+"\nNgày chết: "+this.dieDate+"\nSố ngày ốm trước khi chết: "+this.sickNumber;
    }
}
