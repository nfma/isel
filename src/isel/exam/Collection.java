package isel.exam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nfma
 * Date: 17/07/13
 * Time: 23:05
 * To change this template use File | Settings | File Templates.
 */
public class Collection extends Album {
    private List<Album> albums; //Called it albums here instead and used a list instead of the arrays being used everywhere else, which means...
    private int count; // this is pretty much useless but there you go
    private int containerIdx; //this is even more useless... these guys are totally c++ devs...

    public Collection(String name, int count) {
        super(name);
        this.count = count;
        albums = new ArrayList<Album>(count);
    }

    public Collection(int count) {
        this("Collection " + count, count); // sorry no portuguese in code...
    }

    public void add(Album album) {
        albums.add(album);
    }

    public void show(String prefix) {
        super.show(prefix);
        for (Album album : albums) {
            album.show(INNER_PREFIX + prefix);
        }
    }

    @Override
    public String getPath(String photoName) {
        for (Album album : albums) {
            final String imagePath = album.getPath(photoName);
            if(imagePath != null)
                return super.addPath(imagePath);
        }
        return null;
    }
}