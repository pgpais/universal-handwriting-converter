import lemon.TrainingSession;
import pt.up.hs.uhc.UniversalHandwritingConverter;
import pt.up.hs.uhc.models.Format;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.*;
import java.util.Date;
import java.util.List;

public class Main {

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
        Page page = pages.get(0);
        long firstTimestamp = page.getStrokes().get(0).getStartTime();
        Date firstDate = new Date(firstTimestamp);
        Date lastDate = new Date(firstTimestamp);
        for (Stroke stroke : page.getStrokes()) {
            Date curDate = new Date(stroke.getStartTime());
            System.out.println("Absolute Timestamp: " + stroke.getStartTime() + " | Relative Timestamp: " + (stroke.getStartTime() - firstTimestamp));
            System.out.println("Timestamp date: " + curDate.toString());
            System.out.println("Seconds before last stroke: " + ((curDate.getTime() - lastDate.getTime())/1000.0));
            System.out.println();
            lastDate = curDate;
        }
    }
}
