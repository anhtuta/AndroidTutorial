package bkhn.anhtu.contentproviderdemo;

import java.io.Serializable;

/**
 * Created by anhtu on 28/03/2017.
 */

public class DanhBa implements Serializable {
    String name;
    String phone;

    public DanhBa(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public DanhBa() {

    }

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

    @Override
    public String toString() {
        return this.name+"\n"+this.phone;
    }
}
