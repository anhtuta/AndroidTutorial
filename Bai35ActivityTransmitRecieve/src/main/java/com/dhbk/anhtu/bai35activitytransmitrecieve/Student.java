package com.dhbk.anhtu.bai35activitytransmitrecieve;

import java.io.Serializable;

/**
 * Created by AnhTu on 15/03/2017.
 */

public class Student implements Serializable { //phải implement cái này mới truyền dl qua lại giữa các screen đc
    String name;
    int id;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id+" - "+this.name;
    }
}
