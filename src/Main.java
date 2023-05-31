import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        final String dayzLocalPath = System.getProperty("user.home") + "\\AppData\\Local\\DayZ";
        File file = new File(dayzLocalPath);
        File[] files = file.listFiles();
        long size = 0;

        try {
            for (File content : files) {
                if (!content.isDirectory()) {
                    size += Files.size(content.toPath());
                    if (content.delete())
                        System.out.println("Removed: " + content.getName());
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double totalSizeInMB = (double) size / (1024 * 1024);
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedTotalSize = df.format(totalSizeInMB);

        if (size == 0)
            System.out.println("Nothing was removed");
        else
            System.out.println("\nRemoved: " + formattedTotalSize + " MB of data.");

    }
}
