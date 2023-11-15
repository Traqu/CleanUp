import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static final GameObject DAYZ = new GameObject("DayZ", "Dayz", true, true);
    public static final GameObject CIVILIZATION_VI = new GameObject("Civilization VI", "Firaxis Games\\Sid Meier's Civilization VI\\Logs");
    public static final GameObject GOOGLE_CHROME = new GameObject("Google Chrome", "Google\\Chrome\\User Data\\Default\\Cache\\Cache_Data");
    public static final GameObject DOWNLOADS = new GameObject("Downloads", "Downloads", true);

    public static void main(String[] args) {

        List<GameObject> listOfGames = new ArrayList<>();

        listOfGames.add(DAYZ);
        listOfGames.add(CIVILIZATION_VI);
        listOfGames.add(GOOGLE_CHROME);
        listOfGames.add(DOWNLOADS);

        //TODO → add more games here ↑...

        listOfGames.add(new GameObject("Game request? Contact `traqu` on Discord.", "", false, false));

        GUI userInterface = new GUI(listOfGames);
        userInterface.setVisible(true);

        userInterface.getMainTextField().setText("Press \"Remove button\" to perform cleaning.");

        while (!userInterface.isOrderReceived()) {
            sleepFor(10);
        }

        List<String> selectedGames = userInterface.getSelectedGames();

        AtomicReference<Double> totalSizeRemoved = new AtomicReference<>(0.0);

        for (int i = 0; i < selectedGames.size(); i++) {
            LogRemover.remove(userInterface, selectedGames.get(i), listOfGames, totalSizeRemoved, i == selectedGames.size() - 1, selectedGames.size() != 1);
            sleepFor(1000);
        }

        userInterface.getMainTextField().setText("Closing the application");
        Main.sleepFor(1250);
        System.exit(0);
    }

    public static void sleepFor(int timeMs) {
        try {
            Thread.sleep(timeMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

//TODO add C:\Users\piotr\AppData\Local\Google\Chrome\User Data\Default\Service Worker\CacheStorage to GoogleChrome