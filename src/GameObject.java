import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private static final String PRE_PATH = System.getProperty("user.home").concat(File.separator.concat("AppData").concat(File.separator).concat("Local").concat(File.separator));
    private final String name;
    private String path;
    private final File gameDirectory;
    private final File[] files;
    private boolean hasMultiplePaths = false;
    private final JCheckBox checkBox;
    private List<File> directoryList = new ArrayList<>();

    public GameObject(String name, String individualPath) {
        this.name = name;
        this.path = PRE_PATH.concat(individualPath);
        checkBox = new JCheckBox(this.name);
        gameDirectory = new File(this.path);
        files = gameDirectory.listFiles(file -> !file.isDirectory());
    }

    public GameObject(String name, String individualPath, String anotherIndividualPath) {
        hasMultiplePaths = true;
        this.name = name;
        this.path = PRE_PATH + individualPath;
        checkBox = new JCheckBox(this.name);

        List<File> allFiles = new ArrayList<>();

        processDirectory(new File(this.path), allFiles);

        this.path = PRE_PATH + anotherIndividualPath;
        processDirectory(new File(this.path), allFiles, directoryList);

        files = allFiles.toArray(new File[0]);

        gameDirectory = new File(this.path);
    }

    public GameObject(String name, String individualPath, boolean notAppdataInPath) {
        this.name = name;
        this.path = PRE_PATH.concat(individualPath);
        checkBox = new JCheckBox(this.name);
        if (notAppdataInPath) {
            this.path = System.getProperty("user.home") + File.separator + individualPath;
        }
        gameDirectory = new File(this.path);
        files = gameDirectory.listFiles(file -> !file.isDirectory());
    }


    public GameObject(String name, String individualPath, boolean isSelected, boolean isEnabled) {
        this.name = name;
        this.path = PRE_PATH.concat(individualPath);
        checkBox = new JCheckBox(this.name);
        checkBox.setEnabled(isEnabled);
        checkBox.setSelected(isSelected);
        gameDirectory = new File(this.path);
        files = gameDirectory.listFiles(file -> !file.isDirectory());
    }

    private void processDirectory(File directory, List<File> fileList) {
        File[] directoryFiles = directory.listFiles(file -> !file.isDirectory());

        if (directoryFiles != null) {
            fileList.addAll(List.of(directoryFiles));
        }

        File[] subdirectories = directory.listFiles(File::isDirectory);

        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                processDirectory(subdirectory, fileList);
            }
        }
    }

    private void processDirectory(File directory, List<File> fileList, List<File> directoryList) {
        File[] directoryFiles = directory.listFiles(file -> !file.isDirectory());

        if (directoryFiles != null) {
            fileList.addAll(List.of(directoryFiles));
        }

        directoryList.add(directory);

        File[] subdirectories = directory.listFiles(File::isDirectory);

        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                processDirectory(subdirectory, fileList, directoryList);
            }
        }
    }


    public String getName() {
        return name;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    public File[] getFiles() {
        return files;
    }

    public File getGameDirectory() {
        return gameDirectory;
    }
    public String getPath() {
        return path;
    }

    public boolean hasMultiplePaths() {
        return hasMultiplePaths;
    }
    public List<File> getDirectoryList() {
        return directoryList;
    }
}