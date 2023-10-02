import javax.swing.*;
import java.io.File;

public class GameObject {

    private static final String PRE_PATH = System.getProperty("user.home").concat(File.separator.concat("AppData").concat(File.separator).concat("Local").concat(File.separator));
    private final String name;
    private String path;
    private final File gameDirectory;
    private final File[] files;

    private final JCheckBox checkBox;

    public GameObject(String name, String individualPath) {
        this.name = name;
        this.path = PRE_PATH.concat(individualPath);
        checkBox = new JCheckBox(this.name);
        gameDirectory = new File(this.path);
        files = gameDirectory.listFiles(file -> !file.isDirectory());
    }public GameObject(String name, String individualPath, boolean notAppdataInPath) {
        this.name = name;
        this.path = PRE_PATH.concat(individualPath);
        checkBox = new JCheckBox(this.name);
        if(notAppdataInPath){
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
}