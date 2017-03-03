package swt6.osgi.pad.basedrum;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import swt6.osgi.pad.AbstractPad;
import swt6.osgi.pad.PadFactory;

import java.io.File;

public class Basedrum extends AbstractPad {
    private static final Media SOUND = new Media(Basedrum.class.getResource("basedrum.wav").toString());

    public Basedrum(PadFactory pf) {
        super(pf);
    }

    @Override
    public void play() {
        MediaPlayer player = new MediaPlayer(SOUND);
        player.setVolume(this.getVolume());
        player.play();
    }
}
