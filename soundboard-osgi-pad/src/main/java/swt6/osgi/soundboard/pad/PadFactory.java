package swt6.osgi.soundboard.pad;


import javafx.scene.image.Image;

public interface PadFactory {
    public static final String FACTORY_ADDED_EVENT =  "swt6/factory/factories/FACTORY_ADDED";
    public static final String FACTORY_STARTED_EVENT =  "swt6/factory/factories/FACTORY_STARTED/";
    public static final String FACTORY_FINISHED_EVENT = "swt6/factory/factories/FACTORY_FINISHED/";
    public Pad createPad();
    public String getPadType();
    public Image getPadIcon();
}
