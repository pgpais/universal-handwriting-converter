package lemon;

import pt.up.hs.uhc.models.Stroke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TrainingTrial {
    private String trialName;
    private Date startTime;
    private Date endTime;
    private List<Stroke> strokes;

    public TrainingTrial(List<Stroke> strokes, String trialName){
        this.strokes = strokes;
        this.trialName = trialName;
        startTime = new Date(strokes.get(0).getStartTime());
        endTime = new Date(strokes.get(strokes.size()-1).getEndTime());
    }

    public String getTrialName() {
        return trialName;
    }

    public Date getStartDate() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "TrainingTrial{" +
                "\nstartTime=" + startTime +
                "\nendTime=" + endTime +
                "\n}";
    }

    public List<String> toCSV(){
        List<String> result = new ArrayList<>(Arrays.asList(trialName, startTime.toString(), endTime.toString()));
        return new ArrayList<String>(result);
    }

    public static List<String> CSVHeaders(){
        return Arrays.asList("Trial name", "Trial Start Time", "Trial End Time");
    }
}
