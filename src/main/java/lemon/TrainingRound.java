package lemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrainingRound {
    private List<TrainingTrial> trials;
    private String roundName;
    private Date startDate;
    private Date endDate;


    public TrainingRound(String roundName){
        this.roundName = roundName;
        trials = new ArrayList<>(12);
    }

    public void addTrial(TrainingTrial trial){
        trials.add(trial);

        if(startDate == null || trial.getStartDate().before(startDate)){
            startDate = trial.getStartDate();
        }

        if(endDate == null || trial.getEndTime().after(endDate)){
            endDate = trial.getEndTime();
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Pattern pattern = Pattern.compile("(?m)(^)"); //Line begin: multiline mode
        Matcher matcher = pattern.matcher(trials.toString());

        sb.append(
                "Round[\n" +
                "roundStartDate: " + startDate + "\n" +
                "roundEndDate: " + endDate + "\n");

        for (TrainingTrial trial :
                trials) {
            sb.append(trial.toString()).append("\n");
        }

        sb.append("]\n");


        return sb.toString();
    }

    public List<List<String>> toCSV(){
        List<List<String>> result = new ArrayList<>();
        for (TrainingTrial trial :
                trials) {
            List<String> row = new ArrayList<>(Arrays.asList(roundName, startDate.toString(), endDate.toString()));
            List<String> trialRow = trial.toCSV();
            for (String trialData :
                    trialRow) {
                row.add(trialData);
            }
            result.add(row);
        }
        return result;
    }

    public static List<String> CSVHeaders(){
        List<String> headers =  new ArrayList<>(Arrays.asList("Round Name", "Round Start Date", "Round End Date"));
        headers.addAll(TrainingTrial.CSVHeaders());
        return headers;
    }
}
