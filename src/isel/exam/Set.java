package isel.exam;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created with IntelliJ IDEA.
 * User: nfma
 * Date: 17/07/13
 * Time: 23:05
 * To change this template use File | Settings | File Templates.
 */
public class Set extends Album {
    private List<Image> images; // Lists again..

    public Set(String name, Image[] images) {
        super(name);
        this.images = asList(images); // I'm not in favour of arrays...
    }

    public void show(String prefix) {
        super.show(prefix);
        for (Image image : images) {
            System.out.println(INNER_PREFIX + prefix + image.getName());
        }
    }

    @Override
    public String getPath(String photoName) {
        for (Image image : images) {
            if(areEquals(photoName, image.getName()))
                return addPath(photoName);
        }
        return null;
    }

    private boolean areEquals(String photoName, String imageName) {
        return (imageName != null && imageName.equals(photoName)) || (imageName == null && photoName == null);
    }
}
