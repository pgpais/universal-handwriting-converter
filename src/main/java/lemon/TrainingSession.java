package lemon;

import pt.up.hs.uhc.models.Stroke;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrainingSession {
    private static final int TRIAL_PER_ROUND = 12;

    private String trainingName;
    private Date startDate;
    private Date endDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.nnnnnnnnn").withZone(ZoneId.systemDefault());

    private List<Stroke> nameStrokes;
    private double nameWriteDuration;

    private List<TrainingRound> rounds;
    private List<TrainingTrial> trials;


    public TrainingSession(String trainingName){
        this.trainingName = trainingName;
        rounds = new ArrayList<>();
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

    public void addTrial(TrainingTrial trial){
        if(trials.size() % TRIAL_PER_ROUND == 0){
            TrainingRound round = new TrainingRound("Round" + rounds.size());
            rounds.add(round);
        }
        rounds.get(rounds.size()-1).addTrial(trial);
        trials.add(trial);

        if(startDate == null || trial.getStartDate().before(startDate)){
            startDate = trial.getStartDate();
        }

        if(endDate == null || trial.getEndTime().after(endDate)){
            endDate = trial.getEndTime();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();


        StringBuilder outter = new StringBuilder();

        Pattern pattern = Pattern.compile("(?m)(^)"); //Line begin: multiline mode
        Matcher matcher = pattern.matcher(rounds.toString());

        sb.append("TrainingSession[\n");

        for (TrainingRound round :
                rounds) {
            sb.append(round.toString()).append("\n");
        }

        sb.append("]\n");


        return sb.toString();
    }

    public List<List<String>> toCSV(){
        List<List<String>> result = new ArrayList<>();
        for (TrainingRound round :
                rounds) {
            List<String> row = new ArrayList<>(Arrays.asList(trainingName, startDate.toString(), endDate.toString()));
            for (List<String> roundRow :
                    round.toCSV()) {
                List<String> resultRow = new ArrayList<>(row);
                resultRow.addAll(roundRow);
                result.add(resultRow);
            }
        }
        return result;
    }

    public static List<String> CSVHeaders(){
        List<String> headers =  new ArrayList<>(Arrays.asList("Training Name", "Training Start Date", "Training End Date"));
        headers.addAll(TrainingRound.CSVHeaders());
        return headers;
    }
}
