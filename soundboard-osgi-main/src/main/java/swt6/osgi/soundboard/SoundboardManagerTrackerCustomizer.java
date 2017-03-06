package swt6.osgi.soundboard;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import swt6.osgi.soundboard.manager.SoundboardManager;
import swt6.osgi.soundboard.pad.PadFactory;
import swt6.osgi.util.JavaFxUtils;

import java.util.concurrent.ExecutionException;

public class SoundboardManagerTrackerCustomizer implements ServiceTrackerCustomizer<SoundboardManager, SoundboardManager> {

    private BundleContext context;
    private SoundboardActivator activator;

    public SoundboardManagerTrackerCustomizer(BundleContext context, SoundboardActivator activator) {
        this.context = context;
        this.activator = activator;
    }

    @Override
    public SoundboardManager addingService(ServiceReference<SoundboardManager> reference) {
        SoundboardManager manager = context.getService(reference);

        try {
            JavaFxUtils.runAndWait(() -> activator.startUI(context, manager));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return manager;
    }

    @Override
    public void modifiedService(ServiceReference<SoundboardManager> reference, SoundboardManager manager) {
        try {
            SoundboardManager newManager = context.getService(reference);
            JavaFxUtils.runAndWait(() -> activator.stopUI(context));
            JavaFxUtils.runAndWait(() -> activator.startUI(context, newManager));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removedService(ServiceReference<SoundboardManager> reference, SoundboardManager manager) {
        try {
            JavaFxUtils.runAndWait(() -> activator.stopUI(context));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
