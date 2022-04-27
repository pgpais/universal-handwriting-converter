import pt.up.hs.uhc.UniversalHandwritingConverter;
import pt.up.hs.uhc.models.Format;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.*;
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
        for (Stroke stroke : page.getStrokes()) {
            System.out.println(stroke.getStartTime() - firstTimestamp);
        }
    }
}
