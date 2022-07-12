import lemon.TrainingSession;
import lemon.Trial;
import pt.up.hs.uhc.UniversalHandwritingConverter;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.*;
import java.util.*;

public class Main {
    private static final float MIN_NAME_TO_CHARACTER_DELAY = 60 * 1000; // in milliseconds
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
        // Getting 2nd page for now
        Page page = pages.get(1);

        TrainingSession trainingSession = new TrainingSession();

        // Look for all strokes, look for a specific delay (e.g. 1 second) as that is the time between the name and the character drawings
        List<Stroke> nameStrokes = new ArrayList<>();
        List<Stroke> characterStrokes = new ArrayList<>();
        boolean foundFinalNameStroke = false;
        Date lastStrokeDate = null;
        for (Stroke stroke :
                page.getStrokes()) {
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
                    foundFinalNameStroke = true;
                    characterStrokes.add(stroke);
                    //TODO: parse strokes to find if they are part of same trial and, if not, create a new one and add to training
                    //use the constant
                    boolean isNotInSameTrial = (currentStrokeDate.getTime() - lastStrokeDate.getTime()) > MIN_DELAY_BETWEEN_TRIALS;
                    if(isNotInSameTrial){
                        Trial trial = new Trial(characterStrokes);
                        trainingSession.addTrial(trial);
                        characterStrokes = new ArrayList<>();
                    }else{
                        characterStrokes.add(stroke);
                    }
                }
                else {
                    nameStrokes.add(stroke);
                }
            }
            lastStrokeDate = new Date(stroke.getEndTime());
        }
        trainingSession.addNameStrokes(nameStrokes);
        trainingSession.addCharacterStrokes(characterStrokes);

//        Page page = pages.get(0);
//        long firstTimestamp = page.getStrokes().get(0).getStartTime();
//        Date firstDate = new Date(firstTimestamp);
//        Date lastDate = new Date(firstTimestamp);
//        for (Stroke stroke : page.getStrokes()) {
//            Date curDate = new Date(stroke.getStartTime());
//            System.out.println("Absolute Timestamp: " + stroke.getStartTime() + " | Relative Timestamp: " + (stroke.getStartTime() - firstTimestamp));
//            System.out.println("Timestamp date: " + curDate.toString());
//            System.out.println("Seconds before last stroke: " + ((curDate.getTime() - lastDate.getTime())/1000.0));
//            System.out.println();
//            lastDate = curDate;
//        }
    }
}
