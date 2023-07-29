import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static final DecimalFormat MEGABYTES = new DecimalFormat("#.##");
    public static final DecimalFormat KILOBYTES = new DecimalFormat("#.###");
    public static final String COMMON_PATH_PART = File.separator.concat("AppData").concat(File.separator).concat("Local").concat(File.separator);
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String COMMON_PATH = USER_HOME + COMMON_PATH_PART;
    public static final String CIV_VI_LOCAL_PATH = COMMON_PATH + "Firaxis Games\\Sid Meier's Civilization VI\\Logs";
    public static final String DAYZ_LOCAL_PATH = COMMON_PATH + "DayZ";
    public static final String PLACEHOLDER_PATH = COMMON_PATH + "";

    public static void main(String[] args) {

        Map<String, String> gameToPathMap = new HashMap<>();
        List<String> logPaths = new ArrayList<>(List.of(
                DAYZ_LOCAL_PATH,
                CIV_VI_LOCAL_PATH,
                "placeholder"
        ));

        GUI userInterface = new GUI();
        userInterface.setVisible(true);

        List<String> checkBoxesList = userInterface.getCheckBoxesList();


        for (int i = 0; i < checkBoxesList.size(); i++) {
            gameToPathMap.put(checkBoxesList.get(i), logPaths.get(i));
        }

        File fileDayz = new File(DAYZ_LOCAL_PATH);
        File fileCivilizationVI = new File(CIV_VI_LOCAL_PATH);
        File[] filesDayz = fileDayz.listFiles();
        File[] filesCivVI = fileCivilizationVI.listFiles();

        boolean anythingRemoved = false;
        long size = 0;
        double removedSize;

        long tmpTotalSize = 0;
        double totalSize = 0;


        userInterface.getMainTextField().setText("Press \"Remove button\" to perform cleaning.");

        while (!userInterface.isOrderReceived()) {
            sleepFor(10);
        }

        List<String> selectedGames = userInterface.getSelectedGames();
        for (String selectedGame : selectedGames) {
            LogsRemoval.remove(userInterface, selectedGame);
            //TODO
            //TODO
            //TODO
            //TODO
        }

        userInterface.getMainTextField().setText("Counting total Dayz files size");


        try {
            //Civilization VI
            for (File fileSize : filesCivVI) {
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
                        userInterface.getMainTextField().setText("Removed: " + KILOBYTES.format(removedSize) + "/" + KILOBYTES.format(totalSize) + " MB of data.");
                        sleepFor(10);
                    } else {
                        userInterface.getMainTextField().setText("Removed: " + MEGABYTES.format(removedSize) + "/" + MEGABYTES.format(totalSize) + " MB of data.");
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
                userInterface.getMainTextField().setText("Only empty filesDayz were removed");
            } else {
                userInterface.getMainTextField().setText("Nothing was removed");
            }
        }

        sleepFor(2200);
        userInterface.getMainTextField().setText("Closing the application");
        sleepFor(700);

        System.exit(0);
    }

    private static void sleepFor(int timeMs) {
        try {
            Thread.sleep(timeMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
