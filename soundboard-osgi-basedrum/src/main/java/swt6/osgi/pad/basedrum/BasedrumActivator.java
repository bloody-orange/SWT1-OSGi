package swt6.osgi.pad.basedrum;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class BasedrumActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(ShapeFactory.class, new RectangleFactory(), null);

    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
