package swt6.osgi.soundboard.pad;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;

public abstract class AbstractPad implements Pad {
    private final Logger logger = LoggerFactory.getLogger(AbstractPad.class);

    protected EventAdmin eventAdmin;
    private Class clazz;
    private String resourceName;
    private Media media;
    private double volume = 1.0;
    private PadFactory padFactory;

    public AbstractPad(PadFactory pf, EventAdmin ea, Class clazz, String resourceName) {
        this.padFactory = pf;
        this.eventAdmin = ea;
        this.clazz = clazz;
        this.resourceName = resourceName;
        this.media = getMedia();
    }

    private Media getMedia() {
        File temp = null;

        try (InputStream input = clazz.getResourceAsStream(resourceName)) {
            // JavaFx cannot use bundled resource files!
            // need to convert it to a real file first
            temp = File.createTempFile(clazz.getName(), ".wav");
            temp.deleteOnExit();
            try (OutputStream output = new FileOutputStream(temp)) {
                byte[] buffer = new byte[4096];
                int n;
                while(-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                }
            }
            System.out.println(temp.toPath().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Media(temp.toURI().toString());
    }

    @Override
    public double getVolume() {
        return this.volume;
    }

    @Override
    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getPadType() {
        return padFactory.getPadType();
    }

    @Override
    public final void play() {
        logger.info("play " + getPadType());
        MediaPlayer player;
        try {
            player = new MediaPlayer(media);
        } catch (MediaException e) {
            media = getMedia();
            player = new MediaPlayer(media);
        }
        player.setVolume(this.getVolume());
        player.play();
        String topic = PAD_STARTED_EVENT + getPadType();
        Map<String, Object> props = new Hashtable<>();
        Event event = new Event(topic, props);
        eventAdmin.sendEvent(event);
    }
}
