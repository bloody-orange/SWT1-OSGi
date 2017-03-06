package swt6.osgi.soundboard.manager;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import swt6.osgi.soundboard.pad.PadFactory;

public class SoundboardFactoryTrackerCustomizer implements ServiceTrackerCustomizer<PadFactory, PadFactory> {

    private BundleContext context;
    private SoundboardManager manager;

    public SoundboardFactoryTrackerCustomizer(BundleContext context, SoundboardManager manager) {
        this.context = context;
        this.manager = manager;
    }

    @Override
    public PadFactory addingService(ServiceReference<PadFactory> reference) {
        PadFactory pf = context.getService(reference);
        manager.addFactory(pf);
        return pf;
    }

    @Override
    public void modifiedService(ServiceReference<PadFactory> reference, PadFactory pf) {
        PadFactory newPf = context.getService(reference);
        manager.removeFactory(pf);
        manager.addFactory(newPf);
    }

    @Override
    public void removedService(ServiceReference<PadFactory> reference, PadFactory pf) {
        manager.removeFactory(pf);
    }
}
