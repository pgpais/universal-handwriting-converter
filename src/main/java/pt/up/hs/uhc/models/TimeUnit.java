package pt.up.hs.uhc.models;

/**
 * Unit of time.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public enum TimeUnit {
    NANOSECOND(0.000001D),
    MICROSECOND(0.001D),
    MILLISECOND(1D),
    SECOND(1000D);

    private final double msRate;

    TimeUnit(double msRate) {
        this.msRate = msRate;
    }

    public double getMsRate() {
        return msRate;
    }

    public static TimeUnit from(String s) {
        if (s == null) {
            return null;
        }
        try {
            return valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            if (s.equalsIgnoreCase("ns")) {
                return NANOSECOND;
            } else if (s.equalsIgnoreCase("us")) {
                return MICROSECOND;
            } else if (s.equalsIgnoreCase("ms")) {
                return MILLISECOND;
            } else if (s.equalsIgnoreCase("s")) {
                return SECOND;
            }
        }
        return null;
    }
}
