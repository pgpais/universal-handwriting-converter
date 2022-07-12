package lemon;

import pt.up.hs.uhc.models.Stroke;

import java.util.Date;
import java.util.List;

public class Trial {
    private Date startTime;
    private Date endTime;
    private List<Stroke> strokes;

    public Trial(List<Stroke> strokes){
        this.strokes = strokes;
        startTime = new Date(strokes.get(0).getStartTime());
        endTime = new Date(strokes.get(strokes.size()-1).getEndTime());
    }
}
