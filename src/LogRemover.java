import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.List;

abstract public class LogRemover {
    public static final DecimalFormat KILOBYTES = new DecimalFormat("#.###");
    public static final DecimalFormat MEGABYTES = new DecimalFormat("#.##");
    public static final DecimalFormat GIGABYTES = new DecimalFormat("#.##");
    static boolean anythingRemoved = false;
    static long size = 0;
    static double removedSize;

    static long tmpTotalSize = 0;
    static double totalSize = 0;

    public static void remove(GUI gui, String gameLogsPath, List<GameObject> listOfGames){
    //public static void remove(GUI gui, String gameLogsPath, File gameRootDirectory){ TODO - tymczasowo mam 2 argumenty
        System.out.println("removing logs from: " + gameLogsPath);
        gui.getMainTextField().setText("Counting total " + gameLogsPath + " files size");

        for (GameObject game : listOfGames) {
            if(game.getName().toLowerCase().equals(gameLogsPath.toLowerCase())){
                File[] files = game.getFiles();
                    try {
                        for (File file : files) {
                                tmpTotalSize += Files.size(file.toPath());
                                totalSize = (double) tmpTotalSize / (1024 * 1024);
                        }
                        for (File content : files) {
                                size += Files.size(content.toPath());
                                if (content.delete()) {
                        System.out.println("Removed: " + content.getName());
                                    anythingRemoved = true;
                                }
                                removedSize = (double) size / (1024 * 1024);
                                if (size < 10000) {
                                    gui.getMainTextField().setText("Removed: " + KILOBYTES.format(removedSize) + "/" + KILOBYTES.format(totalSize) + " MB of data.");
                                    Main.sleepFor(10);
                                } else {
                                    gui.getMainTextField().setText("Removed: " + MEGABYTES.format(removedSize) + "/" + MEGABYTES.format(totalSize) + " MB of data.");
                                    Main.sleepFor(10);
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

                    Main.sleepFor(2200);
                    gui.getMainTextField().setText("Closing the application");
                    Main.sleepFor(700);

                    System.exit(0);
                }
            }
        }
}
