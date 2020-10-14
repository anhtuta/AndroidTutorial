package bkhn.anhtu.sqlitedemo2;

/**
 * Created by anhtu on 29/03/2017.
 */

public class Song {
    int id;
    String name;
    String singer;
    boolean isFavorite;

    public Song(int id, String name, String singer, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        String favorite = isFavorite ? "favorite" : "dislike";
        return new String(id+" - "+name+" - "+singer+" - "+favorite);
    }
}
