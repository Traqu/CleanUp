import javax.swing.*;
import java.io.File;

public class GameObject {

    private static String prePath = System.getProperty("user.home").concat(File.separator.concat("AppData").concat(File.separator).concat("Local").concat(File.separator));
    private String name;
    private String path;

    private File gameDirectory;

    private File[] files;

    private JCheckBox checkBox;

    public GameObject(String name, String individualPath) {
        this.name = name;
        this.path = prePath.concat(individualPath);
        checkBox = new JCheckBox(this.name);
        gameDirectory = new File(this.path);
        files = gameDirectory.listFiles(file -> !file.isDirectory());
    }

    public GameObject(String name, String individualPath, boolean isSelected, boolean isEnabled) {
        this.name = name;
        this.path = prePath.concat(individualPath);
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
}
