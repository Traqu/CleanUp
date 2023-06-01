import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        Gui gui = new Gui();
        final String dayzLocalPath = System.getProperty("user.home") + "\\AppData\\Local\\DayZ";
        File file = new File(dayzLocalPath);
        File[] files = file.listFiles();
        long size = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat dfTiny = new DecimalFormat("#.###");
        double tmpSIZE;

        try {
            for (File content : files) {
                if (!content.isDirectory()) {
                    size += Files.size(content.toPath());
                    if (content.delete())
                        System.out.println("Removed: " + content.getName());
                    tmpSIZE = (double) size / (1024 * 1024);
                    if (size < 10000) {
                        gui.getjTextField().setText("Removed: " + dfTiny.format(tmpSIZE) + " MB of data.");
                    } else {
                        gui.getjTextField().setText("Removed: " + df.format(tmpSIZE) + " MB of data.");
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (size == 0)
            gui.getjTextField().setText("Nothing was removed");


        try {
            Thread.sleep(2200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        gui.getjTextField().setText("Closing the application");
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.exit(0);

    }
}
