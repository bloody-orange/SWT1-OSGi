package swt6.osgi.pad.basedrum;

import javafx.scene.image.Image;
import swt6.osgi.pad.Pad;
import swt6.osgi.pad.PadFactory;

public class BasedrumFactory implements PadFactory {
    private Image icon;

    public BasedrumFactory() {
        this.icon = new Image(this.getClass().getResourceAsStream("basedrumicon.png"));
    }

    @Override
    public Pad createPad() {
        return new Basedrum(this);
    }

    @Override
    public String getPadType() {
        return "Basedrum";
    }

    @Override
    public Image getPadIcon() {
        return icon;
    }
}
