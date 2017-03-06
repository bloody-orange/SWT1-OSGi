package swt6.osgi.soundboard;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.osgi.soundboard.manager.SoundboardManager;
import swt6.osgi.soundboard.pad.Pad;
import swt6.osgi.soundboard.pad.PadFactory;
import swt6.osgi.util.JavaFxUtils;

import javax.swing.Timer;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class SoundboardWindow implements org.osgi.service.event.EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(SoundboardWindow.class);
    private static final int BEATS_AMOUNT = 16;
    private static final int BEATS_PER_MINUTE = 180;

    private Stage stage;
    private VBox rootPane;
    private VBox beatGrid;

    private Collection<Pad> pads = new ArrayList<>();

    private List<EventHandler<WindowEvent>> onCloseHandlers = new ArrayList<>();
    private BundleContext bundleContext;
    private SoundboardManager manager;

    private boolean isStopped = true;
    private Timer timer;
    private int beatIndex = 0;


    public SoundboardWindow(SoundboardManager manager, BundleContext bundleContext) {

        this.manager = manager;
        this.bundleContext = bundleContext;

        try {
            JavaFxUtils.runAndWait(() -> {
                initWindow();
            });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    private void initWindow() {
        Text title = new Text("Soundboard");
        beatGrid = new VBox();
        loadFactories();
        timer = new Timer(60000/BEATS_PER_MINUTE, ae -> {
            for (Node beatGridChild : beatGrid.getChildren()) {
                BeatLineView line = (BeatLineView) beatGridChild;
                line.playAt(beatIndex);
            }
            beatIndex = (beatIndex + 1) % BEATS_AMOUNT;
        });
        Button btnPlayPause = new Button("Play/Pause");

        btnPlayPause.setOnAction(e -> {
            if (isStopped) {
                timer.restart();
                isStopped = false;
            } else {
                timer.stop();
                isStopped = true;
            }
        });

        rootPane = new VBox(title, beatGrid, btnPlayPause);
    }

    private void initLines() {
        beatGrid.getChildren().clear();

    }

    private void loadFactories() {
        try {
            JavaFxUtils.runAndWait(() -> {
                beatGrid.getChildren().clear();
                for (PadFactory pf: manager.getFactories()) {
                    logger.info("adding line for " + pf.getPadType());
                    addPadFactory(pf);
                }
            });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void addPadFactory(PadFactory pf) {
        try {
            JavaFxUtils.runAndWait(() -> {
                BeatLineView line = new BeatLineView(pf, BEATS_AMOUNT);
                beatGrid.getChildren().add(line);
                logger.info("added line for " + pf.getPadType());
            });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void removePadFactory(PadFactory pf) {
        try {
            JavaFxUtils.runAndWait(() -> {
                beatGrid.getChildren().remove(getLineByName(pf.getPadType()));
            });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private PadFactory getPadFactoryByName(String name) {
        for (PadFactory sf : manager.getFactories())
            if (sf.getPadType().equals(name)) return sf;
        return null;
    }

    private BeatLineView getLineByName(String name) {
        for (Node n : beatGrid.getChildren()) {
            BeatLineView line = (BeatLineView) n;
            if (name.equals(line.getPadType())) return line;
        }
        return null;
    }


    public void close()
    {
        try {
            JavaFxUtils.runAndWait(() -> {if (stage != null) stage.close();});
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void addOnCloseEventHandler(EventHandler<WindowEvent> evt) {
        try {
            JavaFxUtils.runAndWait(() -> onCloseHandlers.add(evt));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void removeOnCloseEventHandler(EventHandler<WindowEvent> evt) {
        try {
            JavaFxUtils.runAndWait(() -> onCloseHandlers.remove(evt));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        try {
            JavaFxUtils.runAndWait(() -> {
                if (stage == null) {
                    stage = new Stage();
                    stage.setScene(new Scene(rootPane, 500, 500));
                    stage.setMinWidth(250);
                    stage.setMinHeight(250);
                    stage.setOnCloseRequest(evt -> {
                        onCloseHandlers.forEach(h -> h.handle(evt));
                    });
                    stage.setTitle("Simple Soundboard Application");
                }
                stage.show();
            });
        } catch (InterruptedException | ExecutionException e) {
        }
    }

    @Override
    public void handleEvent(org.osgi.service.event.Event event) {
        logger.info(this.getClass() + " handles event " + event.getTopic());
        if (event.getTopic().equals(SoundboardManager.FACTORIES_CHANGED_EVENT)) {
            loadFactories();
        }/* else if (evt.getTopic().equals(OptimizerManager.ALL_OPTIMIZERS_FINISHED_EVENT)) {
            btnStart.setDisable(false);
            btnStart.setText(INITIAL_BUTTON_TEXT);
            tfInput.setDisable(false);
        }*/
    }
}
