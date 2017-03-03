package swt6.osgi.pad;

public abstract class AbstractPad implements Pad {
    private double volume = 1.0;
    private PadFactory padFactory;

    public AbstractPad(PadFactory pf) {
        this.padFactory = pf;
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
}
