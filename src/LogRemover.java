import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.List;

abstract public class LogRemover {
    public static final DecimalFormat KILOBYTES = new DecimalFormat("#.###");
    public static final DecimalFormat MEGABYTES = new DecimalFormat("#.##");
    static boolean anythingRemoved = false;
    static long size = 0;
    static double removedSize;

    static long tmpTotalSize = 0;
    static double totalSize = 0;

    //static double finalTotalSize;

    public static void remove(GUI gui, String selectedGame, List<GameObject> listOfGames) {
        gui.getMainTextField().setText("Counting total " + selectedGame.toUpperCase() + " files size");
        Main.sleepFor(1000);

        for (GameObject game : listOfGames) {
            if (game.getName().equalsIgnoreCase(selectedGame)) {
                File[] files = game.getFiles();
                try {
                    for (File file : files) {
                        tmpTotalSize += Files.size(file.toPath());
                        totalSize = (double) tmpTotalSize / (1024 * 1024);
                    }
                    for (File content : files) {
                        size += Files.size(content.toPath());
                        if (content.delete()) {
                            anythingRemoved = true;
                        }
                        removedSize = (double) size / (1024 * 1024);
                        if (size < 10000) {
                            gui.getMainTextField().setText("Removed: " + KILOBYTES.format(removedSize) + "/" + KILOBYTES.format(totalSize) + " MB of data.");
                            Main.sleepFor(10);
                        } else if (size > 100000) {
                            gui.getMainTextField().setText("Removed: " + MEGABYTES.format(removedSize / 1000) + "/" + MEGABYTES.format(totalSize / 1000) + " GB of data.");
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
                        gui.getMainTextField().setText("Only empty files were removed");
                    } else {
                        gui.getMainTextField().setText("Nothing was removed");
                    }
                }
            }
            size = 0;
            totalSize = 0;
            removedSize = 0;
            tmpTotalSize = 0;
        }
    }
}
