package android.app.com.emilyrobot;

/**
 * Created by vinay on 4/17/17.
 */

public class Model {

    String name;
    int id_;
    int image;

    public Model(String name, int id_, int image) {
        this.name = name;
        this.id_ = id_;
        this.image=image;
    }

    public String getName() {
        return name;
    }


    public int getId() {
        return id_;
    }



    public int getImage() {
        return image;
    }


}
