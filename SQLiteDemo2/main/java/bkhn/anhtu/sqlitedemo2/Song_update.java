package bkhn.anhtu.sqlitedemo2;

/**
 * Created by anhtu on 29/03/2017.
 */

public class Song_update {
    String name;
    String singer;
    boolean isFavorite;

    public Song_update(String name, String singer, boolean isFavorite) {
        this.name = name;
        this.singer = singer;
        this.isFavorite = isFavorite;
    }
}
