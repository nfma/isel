package isel.exam;

/**
 * Created with IntelliJ IDEA.
 * User: nfma
 * Date: 17/07/13
 * Time: 23:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class Album {
    protected static final String INNER_PREFIX = "    ";
    private String name;

//    public Container(String name) {
//        this.name = name;
//    }
//    this must be a typo cause it's not possible to use constructors of other classes here.
//    I used the constructor below to represent what should be correct

    public Album(String name) {
        this.name = name;
    }

    protected String addPath(String path) {
        return name + "/" + path;
    }

    public void show(String prefix) {
        System.out.println(prefix + name);
    }

    public abstract String getPath(String photoName);
}
