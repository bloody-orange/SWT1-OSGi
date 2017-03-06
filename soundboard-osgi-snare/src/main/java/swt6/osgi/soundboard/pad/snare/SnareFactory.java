package swt6.osgi.soundboard.pad.snare;

import javafx.scene.image.Image;
import org.osgi.service.event.EventAdmin;
import swt6.osgi.soundboard.pad.Pad;
import swt6.osgi.soundboard.pad.PadFactory;

public class SnareFactory implements PadFactory {
    private Image icon;
    private EventAdmin eventAdmin;

    public SnareFactory(EventAdmin eventAdmin) {
        this.icon = new Image(this.getClass().getResourceAsStream("snareicon.png"));
        this.eventAdmin = eventAdmin;
    }

    @Override
    public Pad createPad() {
        return new Snare(this, eventAdmin);
    }

    @Override
    public String getPadType() {
        return "HiHat";
    }

    @Override
    public Image getPadIcon() {
        return icon;
    }
}
