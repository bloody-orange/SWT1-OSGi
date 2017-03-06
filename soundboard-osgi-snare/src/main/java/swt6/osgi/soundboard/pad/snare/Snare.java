package swt6.osgi.soundboard.pad.snare;

import org.osgi.service.event.EventAdmin;
import swt6.osgi.soundboard.pad.AbstractPad;
import swt6.osgi.soundboard.pad.PadFactory;

public class Snare extends AbstractPad {
    public Snare(PadFactory pf, EventAdmin ea) {
        super(pf, ea, Snare.class, "snare.wav");
    }
}
