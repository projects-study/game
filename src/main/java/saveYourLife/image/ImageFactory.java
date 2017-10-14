package saveYourLife.image;

public class ImageFactory {

    private static ImageFactory instance;

    private ImageFactory(){

    }

    public static synchronized ImageFactory getInstance(){
        return instance == null ? instance = new ImageFactory() : instance;
    }

}
