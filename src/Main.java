import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        GUI gui = new GUI();
        gui.setVisible(true);

        final String COMMON_PATH_PART = File.separator.concat("AppData").concat(File.separator).concat("Local").concat(File.separator);
        final String USER_HOME = System.getProperty("user.home");
        final String COMMON_PATH = USER_HOME + COMMON_PATH_PART;
        final String DAYZ_LOCAL_PATH = COMMON_PATH + "DayZ";
        final String CIV_VI_LOCAL_PATH = COMMON_PATH + "Firaxis Games\\Sid Meier's Civilization VI\\Logs";

        File fileDayz = new File(DAYZ_LOCAL_PATH);
        File fileCivilizationVI = new File(CIV_VI_LOCAL_PATH);
        File[] filesDayz = fileDayz.listFiles();
        File[] filesCivVI = fileCivilizationVI.listFiles();

        boolean anythingRemoved = false;
        long size = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat dfTiny = new DecimalFormat("#.###");
        double removedSize;

        long tmpTotalSize = 0;
        double totalSize = 0;


        gui.getMainTextField().setText("Press \"Remove button\" to perform cleaning.");
        while(!gui.isOrderReceived()){
            sleepFor(10);
        }

        List<String> selectedGames = gui.getSelectedGames();
        System.out.println("Selected games are:");
        for (String selectedGame : selectedGames) {
            System.out.println(selectedGame);
        }

        gui.getMainTextField().setText("Counting total Dayz files size");


        try {
            //Civilization VI
            for(File fileSize : filesCivVI){
                System.out.println(fileSize);
                tmpTotalSize += Files.size(fileSize.toPath());
                fileSize.delete();
            }
            //DayZ
            for (File fileSize : filesDayz) {
                if (!fileSize.isDirectory()) {
                    tmpTotalSize += Files.size(fileSize.toPath());
                    totalSize = (double) tmpTotalSize / (1024 * 1024);
                }
            }
            for (File content : filesDayz) {
                if (!content.isDirectory()) {
                    size += Files.size(content.toPath());
                    if (content.delete()) {
//                        System.out.println("Removed: " + content.getName());
                        anythingRemoved = true;
                    }
                    removedSize = (double) size / (1024 * 1024);
                    if (size < 10000) {
                        gui.getMainTextField().setText("Removed: " + dfTiny.format(removedSize) + "/" + dfTiny.format(totalSize) + " MB of data.");
                        sleepFor(10);
                    } else {
                        gui.getMainTextField().setText("Removed: " + df.format(removedSize) + "/" + df.format(totalSize) + " MB of data.");
                        sleepFor(10);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (size == 0) {
            if (anythingRemoved) {
                gui.getMainTextField().setText("Only empty filesDayz were removed");
            } else {
                gui.getMainTextField().setText("Nothing was removed");
            }
        }

        sleepFor(2200);
        gui.getMainTextField().setText("Closing the application");
        sleepFor(700);

        //System.exit(0);
    }

    private static void sleepFor(int timeMs) {
        try {
            Thread.sleep(timeMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
