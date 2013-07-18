package isel.exam;

/**
 * Created with IntelliJ IDEA.
 * User: nfma
 * Date: 17/07/13
 * Time: 23:04
 * To change this template use File | Settings | File Templates.
 */
public class Container {
//  This is pretty tricky, the exercise states that a container has a collection but the constructor takes an array of albums.
//  A collection is on itself an Album but it also contains a list of albums. So, I'm going to assume that we need to add all the
//  albums into a collection
    private Collection collection;

    public Container(Album[] albums) {
        collection = new Collection("Albums", albums.length); //only way to be able to print Albums in point four of the exercise
        for (Album album : albums) {
            collection.add(album);
        }
    }

    public void show() {
        collection.show(""); // So, I assume no prefix at the top, since I can't access the INNER_PREFIX here.
    }

    public String getPath(String photoName) {
        return collection.getPath(photoName);
    }
}
