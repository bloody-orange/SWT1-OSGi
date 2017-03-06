package swt6.osgi.soundboard.pad.hihat;

import javafx.scene.image.Image;
import org.osgi.service.event.EventAdmin;
import swt6.osgi.soundboard.pad.Pad;
import swt6.osgi.soundboard.pad.PadFactory;


public class HiHatFactory implements PadFactory {
    private Image icon;
    private EventAdmin eventAdmin;

    public HiHatFactory(EventAdmin eventAdmin) {
        this.icon = new Image(this.getClass().getResourceAsStream("hihaticon.png"));
        this.eventAdmin = eventAdmin;
    }

    @Override
    public Pad createPad() {
        return new HiHat(this, eventAdmin);
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
