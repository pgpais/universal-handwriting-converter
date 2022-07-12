package lemon;

import pt.up.hs.uhc.models.Stroke;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainingSession {

    private Date startTime;
    private Date endTime;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.nnnnnnnnn").withZone(ZoneId.systemDefault());

    private List<Stroke> nameStrokes;
    private double nameWriteDuration;

    private List<Trial> trials;

    public TrainingSession(){
        trials = new ArrayList<>();
    }
    private List<Stroke> characterStrokes;

    public void addNameStrokes(List<Stroke> strokes){
        this.nameStrokes = strokes;

        Date nameStartDate = new Date(strokes.get(0).getStartTime());
        Date nameEndDate = new Date(strokes.get(strokes.size()-1).getEndTime());
        nameWriteDuration = (nameEndDate.getTime() - nameStartDate.getTime()) / 1000.0;
    }

    public void addCharacterStrokes(List<Stroke> characterStrokes) {
        this.characterStrokes = characterStrokes;
    }

    public void addTrial(Trial trial){
        trials.add(trial);
    }
}
