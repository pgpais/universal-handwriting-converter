package pt.up.hs.uhc.models;

import pt.up.hs.uhc.exceptions.UnknownUnitException;

/**
 * Unit of length.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public enum LengthUnit {
    M(1000),
    CM(10),
    MM(1D),
    IN(25.4D),
    PT(1D / 72D * 25.4D),
    PC(1D / 22D * (1D / 72D * 25.4D)),
    EM(0.2371063D),
    EX(0.66084D),
    ANOTO(0.0378788D);

    private final double mmRate;

    LengthUnit(double mmRate) {
        this.mmRate = mmRate;
    }

    public double getMmRate() {
        return mmRate;
    }

    public static LengthUnit from(String s) {
        try {
            return valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownUnitException(s);
        }
    }
}
