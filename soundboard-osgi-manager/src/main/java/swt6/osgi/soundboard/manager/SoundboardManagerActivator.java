package swt6.osgi.soundboard.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;
import swt6.osgi.soundboard.pad.PadFactory;

import java.util.Dictionary;
import java.util.Hashtable;

public class SoundboardManagerActivator implements BundleActivator {
    private ServiceTracker<PadFactory, PadFactory> tracker;

    @Override
    public void start(BundleContext context) throws Exception {
        ServiceReference<?> eventAdminRef = context.getServiceReference(EventAdmin.class.getName());
        EventAdmin eventAdmin = (EventAdmin) context.getService(eventAdminRef);
        SoundboardManager manager = new SoundboardManager(eventAdmin);

        String[] topics = new String[] {
        };
        Dictionary<String, Object> props = new Hashtable<>();
        props.put(EventConstants.EVENT_TOPIC, topics);
        context.registerService(EventHandler.class.getName(), manager, props);

        tracker = new ServiceTracker<>(context, PadFactory.class, new SoundboardFactoryTrackerCustomizer(context, manager));
        tracker.open();

        context.registerService(SoundboardManager.class, manager, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        tracker.close();
    }
}
