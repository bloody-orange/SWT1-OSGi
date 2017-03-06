package swt6.osgi.soundboard.pad.basedrum;

import javafx.scene.image.Image;
import org.osgi.service.event.EventAdmin;
import swt6.osgi.soundboard.pad.Pad;
import swt6.osgi.soundboard.pad.PadFactory;

public class BasedrumFactory implements PadFactory {
    private Image icon;
    private EventAdmin eventAdmin;

    public BasedrumFactory(EventAdmin eventAdmin) {
        this.icon = new Image(this.getClass().getResourceAsStream("basedrumicon.png"));
        this.eventAdmin = eventAdmin;
    }

    @Override
    public Pad createPad() {
        return new Basedrum(this, eventAdmin);
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
