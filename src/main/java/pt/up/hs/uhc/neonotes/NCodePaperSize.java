package pt.up.hs.uhc.neonotes;

import java.util.HashMap;
import java.util.Map;

/**
 * Information about Neo notes page
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class NCodePaperSize {
    private final static Map<Integer, NCodePaperSize> SIZES = new HashMap<>();
    static {
        SIZES.put(601, new NCodePaperSize(601, "Pocket Note", 83, 148));
        SIZES.put(602, new NCodePaperSize(602, "Memo Note", 83, 148));
        SIZES.put(603, new NCodePaperSize(603, "Ring Note", 150, 210));
        SIZES.put(604, new NCodePaperSize(604, "Plain Note 1", 176, 250));
        SIZES.put(609, new NCodePaperSize(609, "Idea Pad", 210, 297));
        SIZES.put(610, new NCodePaperSize(610, "Plain Note 2", 176, 250));
        SIZES.put(611, new NCodePaperSize(611, "Plain Note 3", 176, 250));
        SIZES.put(612, new NCodePaperSize(612, "Plain Note 4", 176, 250));
        SIZES.put(613, new NCodePaperSize(613, "Plain Note 5", 176, 250));
        SIZES.put(614, new NCodePaperSize(614, "N_A4", 210, 297));
        SIZES.put(615, new NCodePaperSize(615, "Professional", 140, 205));
        SIZES.put(616, new NCodePaperSize(616, "Professional mini", 90, 140));
        SIZES.put(617, new NCodePaperSize(617, "College Note 1", 216, 280));
        SIZES.put(618, new NCodePaperSize(618, "College Note 2", 216, 280));
        SIZES.put(619, new NCodePaperSize(619, "College Note 3", 216, 280));
        SIZES.put(620, new NCodePaperSize(620, "Idea pad mini", 127, 200));
        SIZES.put(625, new NCodePaperSize(625, "N blank planner", 150, 210));
        SIZES.put(629, new NCodePaperSize(629, "BLIND NOTEBOOK", 105, 148));
    }

    private int type;
    private String name;
    private double width;
    private double height;

    private NCodePaperSize(int type, String name, double width, double height) {
        this.type = type;
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public static NCodePaperSize getPaperSizeFor(int type, double width, double height) {
        return SIZES.containsKey(type)
                ? SIZES.get(type)
                : new NCodePaperSize(type, "Unknown", width, height);
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
