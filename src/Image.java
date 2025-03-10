package src;
public class Image {

    private String name;
    private Bitmap bitmap;

    
    public Image(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }


    public String getName() {
        return name;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }
    
}
