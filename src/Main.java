import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
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
    //
    public static final String DAYZ_LOCAL_PATH = COMMON_PATH + "DayZ";
    public static final File FILE_DAYZ = new File(DAYZ_LOCAL_PATH);
    public static final File[] FILES_DAYZ = FILE_DAYZ.listFiles();
    //
    public static final String CIV_VI_LOCAL_PATH = COMMON_PATH + "Firaxis Games\\Sid Meier's Civilization VI\\Logs";
    public static final File FILE_CIVILIZATION_VI = new File(CIV_VI_LOCAL_PATH);
    public static final File[] FILES_CIV_VI = FILE_CIVILIZATION_VI.listFiles();
    //

    public static void main(String[] args) {

        List<GameObject> listOfGames = new ArrayList<>();

        listOfGames.add(new GameObject("DayZ", "Dayz", true, true));
        listOfGames.add(new GameObject("Civilization VI", "Firaxis Games\\Sid Meier's Civilization VI\\Logs"));
        //TODO → add more games here...

        listOfGames.add(new GameObject("Game request? Contact `traqu` on Discord.", "", false, false));



        Map<String, String> gameToPathMap = new HashMap<>();
        List<String> logPaths = new ArrayList<>(List.of(
                DAYZ_LOCAL_PATH,
                CIV_VI_LOCAL_PATH,
                "placeholder"
        ));

        GUI userInterface = new GUI(listOfGames);
        userInterface.setVisible(true);

        List<String> checkBoxesList = userInterface.getCheckBoxesList();


        for (int i = 0; i < checkBoxesList.size(); i++) {
            gameToPathMap.put(checkBoxesList.get(i), logPaths.get(i));
        }

        boolean anythingRemoved = false;
        long size = 0;
        double removedSize;

        long tmpTotalSize = 0;
        double totalSize = 0;


        userInterface.getMainTextField().setText("Press \"Remove button\" to perform cleaning.");

        while (!userInterface.isOrderReceived()) {
            sleepFor(10);
        }

        Map<String, File[]> gameNameToFilePathMapping = new HashMap<>();


        List<File[]> theList = new ArrayList<>();
        addPathsToList(theList);
        for (File[] files : theList) {
            for (File file : files) {
                //System.out.println(file.getName());
            }
        }





        List<String> selectedGames = userInterface.getSelectedGames();







        for (String selectedGame : selectedGames) {
            System.out.println(selectedGame);
            LogRemover.remove(userInterface, selectedGame, listOfGames);
//            gameNameToFilePathMapping.put(selectedGame, gameToPathMap.get(selectedGame))
//            LogsRemoval.remove(userInterface, selectedGame, //TODO element z NOWEJ mapy który V bedzie miał jako File[]     );
//            //TODO
        }









//        try {
//            //Civilization VI
//            for (File fileSize : FILES_CIV_VI) {
//                System.out.println(fileSize);
//                tmpTotalSize += Files.size(fileSize.toPath());
//                fileSize.delete();
//            }
//            //DayZ
//            for (File fileSize : FILES_DAYZ) {
//                if (!fileSize.isDirectory()) {
//                    tmpTotalSize += Files.size(fileSize.toPath());
//                    totalSize = (double) tmpTotalSize / (1024 * 1024);
//                }
//            }
//            for (File content : FILES_DAYZ) {
//                if (!content.isDirectory()) {
//                    size += Files.size(content.toPath());
//                    if (content.delete()) {
////                        System.out.println("Removed: " + content.getName());
//                        anythingRemoved = true;
//                    }
//                    removedSize = (double) size / (1024 * 1024);
//                    if (size < 10000) {
//                        userInterface.getMainTextField().setText("Removed: " + KILOBYTES.format(removedSize) + "/" + KILOBYTES.format(totalSize) + " MB of data.");
//                        sleepFor(10);
//                    } else {
//                        userInterface.getMainTextField().setText("Removed: " + MEGABYTES.format(removedSize) + "/" + MEGABYTES.format(totalSize) + " MB of data.");
//                        sleepFor(10);
//                    }
//                }
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (size == 0) {
//            if (anythingRemoved) {
//                userInterface.getMainTextField().setText("Only empty filesDayz were removed");
//            } else {
//                userInterface.getMainTextField().setText("Nothing was removed");
//            }
//        }
//
//        sleepFor(2200);
//        userInterface.getMainTextField().setText("Closing the application");
//        sleepFor(700);
//
//        System.exit(0);
    }

    public static void sleepFor(int timeMs) {
        try {
            Thread.sleep(timeMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static void addPathsToList(List<File[]> list) {
        Class<Main> mainClass = Main.class;
        Field[] declaredFields = mainClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.getType().isArray() && declaredField.getType().getComponentType() == File.class) {
                try {
                    declaredField.setAccessible(true);
                    File[] files = (File[]) declaredField.get(null); // null, ponieważ pola są statyczne
                    list.add(files);
          //          System.out.println("Dodano pole typu File[]: " + declaredField.getName());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
