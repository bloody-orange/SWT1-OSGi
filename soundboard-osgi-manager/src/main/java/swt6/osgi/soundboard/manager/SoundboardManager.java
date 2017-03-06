package swt6.osgi.soundboard.manager;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.osgi.soundboard.pad.PadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SoundboardManager implements EventHandler {
    private final Logger logger = LoggerFactory.getLogger(SoundboardManager.class);

    public static final String FACTORIES_CHANGED_EVENT = "swt/factories/FACTORIES_CHANGED";

    private EventAdmin eventAdmin;
    private List<PadFactory> factories = new ArrayList<>();

    public SoundboardManager(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    public List<PadFactory> getFactories() {
        return factories;
    }

    public void addFactory(PadFactory f) {
        logger.info("adding factory for " + f.getPadType());
        factories.add(f);
        fireChangedEvent();
    }

    public void removeFactory(PadFactory f) {
        logger.info("removing factory for " + f.getPadType());
        factories.remove(f);
        fireChangedEvent();
    }

    private void fireChangedEvent() {
        eventAdmin.sendEvent(new Event(FACTORIES_CHANGED_EVENT, (Map<String, ?>) null));
    }

    @Override
    public void handleEvent(Event event) {
        logger.info("event: " + event.getTopic());
    }
}
