package swt6.osgi.soundboard;

import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.osgi.soundboard.pad.Pad;
import swt6.osgi.soundboard.pad.PadFactory;
import swt6.osgi.util.JavaFxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class BeatLineView extends HBox implements EventHandler {
    private final Logger logger = LoggerFactory.getLogger(SoundboardWindow.class);

    private HBox checkboxes;
    private PadFactory factory;
    private int size;
    private List<Pad> pads;

    BeatLineView(PadFactory factory, int size) {
        try {
            JavaFxUtils.runAndWait(() -> {
                this.factory = factory;
                this.size = size;
                this.pads = new ArrayList<>();

                setMinHeight(40);
                setMinWidth(300);

                ImageView icon = new ImageView(factory.getPadIcon());
                icon.setFitHeight(25);
                icon.setFitWidth(25);
                getChildren().add(icon);

                addPads();
                getChildren().add(this.checkboxes);
            });
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error in BeatLineView.ctor");
            e.printStackTrace();
        }
    }

    String getPadType() {
        return factory.getPadType();
    }

    void playAt(int i) {
        if (i > size) return;
        CheckBox cb = (CheckBox) this.checkboxes.getChildren().get(i);
        if (cb.isSelected()) {
            pads.get(i).play();
        }
    }

    public void setSize(int size) {
        try {
            JavaFxUtils.runAndWait(() -> {
                pads.clear();
                checkboxes.getChildren().clear();
                addPads();
            });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addPads() {
        this.checkboxes = new HBox();
        for (int i = 0; i < size; ++i) {
            this.checkboxes.getChildren().add(new CheckBox());
            pads.add(factory.createPad());
        }
    }

    @Override
    public void handleEvent(Event event) {

    }
}
