package swt6.osgi.soundboard.pad;


import javafx.scene.media.MediaPlayer;

public interface Pad {
    public static final String PAD_STARTED_EVENT =  "swt6/pad/pads/PAD_STARTED/";
    public static final String PAD_FINISHED_EVENT = "swt6/pad/pads/PAD_FINISHED/";

    public double getVolume();
    public void setVolume(double volume);

    public String getPadType();

    public void play();
}
