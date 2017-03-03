package swt6.osgi.pad;


import javafx.scene.media.MediaPlayer;

public interface Pad {
    public double getVolume();
    public void setVolume(double volume);

    public String getPadType();

    public void play();
}
