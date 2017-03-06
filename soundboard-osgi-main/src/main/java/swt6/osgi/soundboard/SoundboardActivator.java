package swt6.osgi.soundboard;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;
import swt6.osgi.soundboard.manager.SoundboardManager;
import swt6.osgi.util.JavaFxUtils;

import java.util.Dictionary;
import java.util.Hashtable;

public class SoundboardActivator implements BundleActivator {
    private SoundboardWindow window;
    private ServiceTracker<SoundboardManager, SoundboardManager> tracker;

    @Override
    public void start(BundleContext context) throws Exception {
        JavaFxUtils.initJavaFx();
        tracker = new ServiceTracker<>(context, SoundboardManager.class, new SoundboardManagerTrackerCustomizer(context, this));
        tracker.open();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        JavaFxUtils.runAndWait(() -> stopUI(context));
        tracker.close();
    }

    void startUI(BundleContext bundleContext, SoundboardManager manager) {
        window = new SoundboardWindow(manager, bundleContext);
        window.addOnCloseEventHandler(evt -> {
            try {
                bundleContext.getBundle().stop();
            } catch (BundleException e) {
                e.printStackTrace();
            }
        });

        Dictionary<String, Object> props = new Hashtable<>();
        String[] topics = new String[] {
            SoundboardManager.FACTORIES_CHANGED_EVENT
        };
        props.put(EventConstants.EVENT_TOPIC, topics);
        bundleContext.registerService(EventHandler.class.getName(), window, props);

        window.show();
    }

    void stopUI(BundleContext bundleContext) {
        if (window != null) window.close();
    }
}
