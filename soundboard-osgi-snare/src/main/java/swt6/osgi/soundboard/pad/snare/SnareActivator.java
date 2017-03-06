package swt6.osgi.soundboard.pad.snare;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;
import swt6.osgi.soundboard.pad.PadFactory;

public class SnareActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        ServiceReference<?> eventAdminReference = bundleContext.getServiceReference(EventAdmin.class.getName());
        EventAdmin eventAdmin = (EventAdmin) bundleContext.getService(eventAdminReference);

        bundleContext.registerService(PadFactory.class, new SnareFactory(eventAdmin), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
    }
}
