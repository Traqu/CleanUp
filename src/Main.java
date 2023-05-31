import java.io.File;

public class Main {
    public static void main(String[] args) {
        final String dayzLocalPath = System.getProperty("user.home") + "\\AppData\\Local\\DayZ";
        File file = new File(dayzLocalPath);
        File[] files = file.listFiles();

        try {
            for (File content : files) {
                if (!content.isDirectory()) {
                    if (content.delete())
                        System.out.println("Removed: " + content.getName());
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
