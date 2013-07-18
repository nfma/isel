package isel.exam;

import java.awt.image.ImageProducer;

/**
 * Created with IntelliJ IDEA.
 * User: nfma
 * Date: 17/07/13
 * Time: 23:05
 * To change this template use File | Settings | File Templates.
 */
public class Image {
    private String name;
    private long size;

    public Image(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    // This method is pretty fucked up to be honest.
    // At most it should be on the set, so i'm not even going to use it
    public static void showImages(String name, Image[] images) {

    }

    public String toString() {
        return null;
    }
}
