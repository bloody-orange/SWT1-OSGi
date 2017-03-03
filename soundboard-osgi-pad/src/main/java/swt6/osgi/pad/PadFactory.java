package swt6.osgi.pad;


import javafx.scene.image.Image;

public interface PadFactory {
    public Pad createPad();
    public String getPadType();
    public Image getPadIcon();
}
