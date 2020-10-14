package vn.edu.hust.tung.myradio.Objects;


/**
 * Created by tung on 3/15/17.
 */

public class FavoriteChannel {
    int _id;
    int id_radio;
    public FavoriteChannel(int _id, int id_radio){
        this._id= _id;
        this.id_radio = id_radio;
    }

    public int get_id() {
        return _id;
    }

    public int getId_radio() {
        return id_radio;
    }
}
