package swt6.osgi.soundboard.pad.basedrum;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.osgi.service.event.EventAdmin;
import swt6.osgi.soundboard.pad.AbstractPad;
import swt6.osgi.soundboard.pad.PadFactory;

import java.io.*;

public class Basedrum extends AbstractPad {

    public Basedrum(PadFactory pf, EventAdmin ea) {
        super(pf, ea, Basedrum.class, "basedrum.wav");
    }
}
