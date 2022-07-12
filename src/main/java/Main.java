import lemon.TrainingSession;
import lemon.TrainingTrial;
import pt.up.hs.uhc.UniversalHandwritingConverter;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final float MIN_NAME_TO_CHARACTER_DELAY = 15 * 1000; // in milliseconds
    private static final float MIN_DELAY_BETWEEN_TRAININGS = 600 * 1000; // in milliseconds
    private static final float MIN_DELAY_BETWEEN_TRIALS = 1 * 1000; // in milliseconds

    public static void main(String[] args) {
        String filePath = "log.neonotes";

        UniversalHandwritingConverter converter = new UniversalHandwritingConverter();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            switch (arg){
                case "-p":
                    i++;
                    filePath = args[i];
                    break;
                default:
                    break;
            }
        }

        File file = new File(filePath);
        System.out.println(file.getAbsolutePath());
        converter.file(file);
        List<Page> pages = converter.getPages();
        // TODO: figure out why pages are in a weird order. (it might be necessary to order them by first stroke date)
        Page page = pages.get(1);

        TrainingSession trainingSession = new TrainingSession("Training1");

        // Look for all strokes, look for a specific delay (e.g. 1 second) as that is the time between the name and the character drawings
        List<Stroke> nameStrokes = new ArrayList<>();
        List<Stroke> characterStrokes = new ArrayList<>();
        boolean foundFinalNameStroke = false;
        Date lastStrokeDate = null;
        int trialNumber = 0;
        for (Stroke stroke :
                page.getStrokes()) {
            trialNumber++;
            Date currentStrokeDate = new Date(stroke.getStartTime());
            System.out.println(currentStrokeDate.toString());

            if (lastStrokeDate == null) {
                nameStrokes.add(stroke);

            } else {
                boolean isAnotherTraining = (currentStrokeDate.getTime() - lastStrokeDate.getTime()) > MIN_DELAY_BETWEEN_TRAININGS;
                boolean isNotNameStroke = (currentStrokeDate.getTime() - lastStrokeDate.getTime()) > MIN_NAME_TO_CHARACTER_DELAY;
                if(isAnotherTraining){
                    break;
                }
                else if (isNotNameStroke || foundFinalNameStroke) {
                    if(!foundFinalNameStroke){
                        foundFinalNameStroke = true;
                        characterStrokes.add(stroke);
                        continue;
                    }

                    //TODO: parse strokes to find if they are part of same trial and, if not, create a new one and add to training
                    //use the constant
                    boolean isNotInSameTrial = (currentStrokeDate.getTime() - lastStrokeDate.getTime()) > MIN_DELAY_BETWEEN_TRIALS;
                    if(isNotInSameTrial){
                        TrainingTrial trial = new TrainingTrial(characterStrokes, "Trial" + trialNumber);
                        trainingSession.addTrial(trial);
                        characterStrokes = new ArrayList<>();
                    }
                    characterStrokes.add(stroke);
                }
                else {
                    nameStrokes.add(stroke);
                }
            }
            lastStrokeDate = new Date(stroke.getEndTime());
        }
        TrainingTrial trial = new TrainingTrial(characterStrokes, "Trial"+trialNumber);
        trainingSession.addTrial(trial);
        trainingSession.addNameStrokes(nameStrokes);
        trainingSession.addCharacterStrokes(characterStrokes);

        System.out.println(trainingSession);
        try {
            List<String> headers = TrainingSession.CSVHeaders();
            List<List<String>> data = trainingSession.toCSV();
            List<List<String>> dataWithHeaders = new ArrayList<>();
            dataWithHeaders.add(headers);
            dataWithHeaders.addAll(data);
            writeToCSV(dataWithHeaders, "training.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static String convertToCSV(List<String> data) {
        String[] dataArray = data.toArray(new String[0]);
        return Stream.of(dataArray)
                .collect(Collectors.joining(","));
    }

    public static void writeToCSV(List<List<String>> dataLines, String fileName) throws FileNotFoundException {
        File csvOutputFile = new File(fileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            for (List<String> dataLine : dataLines) {
                String s = convertToCSV(dataLine);
                pw.println(s);
            }
        }

        if(csvOutputFile.exists()){
            System.out.println(fileName + " written successfully");
        }
    }
}
