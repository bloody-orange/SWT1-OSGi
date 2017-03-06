package swt6.osgi.soundboard.pad.hihat;


import org.osgi.service.event.EventAdmin;
import swt6.osgi.soundboard.pad.AbstractPad;
import swt6.osgi.soundboard.pad.PadFactory;

public class HiHat extends AbstractPad {
    public HiHat(PadFactory pf, EventAdmin ea) {
        super(pf, ea, HiHat.class, "hihat.wav");
    }
}