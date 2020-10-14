package vn.edu.hust.tung.myradio.Objects;

import java.io.Serializable;


/**
 * Created by tung on 3/14/17.
 */

public class RadioChannel implements Serializable {
    private int id = 0;
    private String url = "";
    private String name = "";
    int id_image = 0;
    public RadioChannel(int id, String name, String url, int id_image){
        this.id = id;
        this.url = url;
        this.name = name;
        this.id_image = id_image;
    }
    public String getURL(){
        return url;
    }
    public String getName(){
        return name;
    }
    public int getIdImage() {
        System.out.println("id_image = "+id_image);
        return id_image;
    }
    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
