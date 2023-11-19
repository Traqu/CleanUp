import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

abstract public class LogRemover {
    public static final DecimalFormat KILOBYTES = new DecimalFormat("#.###");
    public static final DecimalFormat MEGABYTES = new DecimalFormat("#.##");
    public static final DecimalFormat GIGABYTES = new DecimalFormat("#.##");
    public static final String MAP_MARKERS_CACHE = new String("MapMarkersCache.json"); //this value is third party and is subject to change
    static boolean anythingRemoved = false;
    static long size = 0;
    static double removedSize;
    static long tmpTotalSize = 0;
    static double totalSize = 0;
    static int timeBreak = 7;
    private static final int RESTORE_TIME = timeBreak;
    private static final int SHORT_BREAK = 1;

    public static void remove(GUI gui, String selectedGame, List<GameObject> listOfGames, AtomicReference<Double> totalSizeRemoved, boolean displayTotalRemovedSize, boolean displayTotal) {
        gui.getMainTextField().setText("Counting total " + selectedGame.toUpperCase() + " files size");
        Main.sleepFor(1275);

        for (GameObject game : listOfGames) {
            if (selectedGame.equals("Google Chrome")) {
                timeBreak = SHORT_BREAK;
            }
            if (game.getName().equalsIgnoreCase(selectedGame)) {
//                game.getCheckBox().setSelected(false);
                File[] files = game.getFiles();
                try {
                    for (File file : files) {
                        if (!file.getName().equals(MAP_MARKERS_CACHE))
                            tmpTotalSize += Files.size(file.toPath());
                    }
                    totalSize = (double) tmpTotalSize / (1024 * 1024);

                    for (File content : files) {
                        if (!content.getName().equals(MAP_MARKERS_CACHE)) {
                            size += Files.size(content.toPath());
                            if (content.delete()) {
                                anythingRemoved = true;
                            }
                            removedSize = (double) size / (1024 * 1024);

                            if (totalSize < 1) {
                                gui.getMainTextField().setText("Removed: " + KILOBYTES.format(removedSize) + "/" + KILOBYTES.format(totalSize) + " MB of data.");
                            } else if (totalSize < 1000) {
                                gui.getMainTextField().setText("Removed: " + MEGABYTES.format(removedSize) + "/" + MEGABYTES.format(totalSize) + " MB of data.");
                            } else {
                                gui.getMainTextField().setText("Removed: " + GIGABYTES.format(removedSize / 1000) + "/" + GIGABYTES.format(totalSize / 1000) + " GB of data.");
                            }

                            Main.sleepFor(timeBreak);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                game.getCheckBox().setSelected(false);

                if (size == 0) {
                    if (anythingRemoved) {
                        gui.getMainTextField().setText("Only empty files were removed");
                    } else {
                        gui.getMainTextField().setText("Nothing was removed");
                    }
                }

                if (game.hasMultiplePaths()) {
                    List<File> directoryList = game.getDirectoryList();
                    deleteDirectory(directoryList.get(0));
                }
            }

            totalSizeRemoved.set(totalSizeRemoved.get() + removedSize);
            anythingRemoved = false;
            size = 0;
            totalSize = 0;
            removedSize = 0;
            tmpTotalSize = 0;
        }

        timeBreak = RESTORE_TIME;

        if (displayTotalRemovedSize && totalSizeRemoved.get() != 0 && displayTotal) {
            totalSizeRemoved.set(totalSizeRemoved.get());
            Main.sleepFor(1000);
            if (totalSizeRemoved.get() < 1) {
                gui.getMainTextField().setText("Removed total: " + KILOBYTES.format(totalSizeRemoved.get()) + " MB of data.");
            } else if (totalSizeRemoved.get() < 1000) {
                gui.getMainTextField().setText("Removed total: " + MEGABYTES.format(totalSizeRemoved.get()) + " MB of data.");
            } else {
                gui.getMainTextField().setText("Removed total: " + GIGABYTES.format(totalSizeRemoved.get() / 1000) + " GB of data.");
            }
            Main.sleepFor(1375);
        }
    }
    private static void deleteDirectory(File directory) {
        System.out.println("deleting from " + directory.getName());
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }

}
