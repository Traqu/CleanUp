import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {

        List<GameObject> listOfGames = new ArrayList<>();

        listOfGames.add(new GameObject("DayZ", "Dayz", true, true));
        listOfGames.add(new GameObject("Civilization VI", "Firaxis Games\\Sid Meier's Civilization VI\\Logs"));
        listOfGames.add(new GameObject("Google Chrome", "Google\\Chrome\\User Data\\Default\\Cache\\Cache_Data"));

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
            boolean displayTotal;
            displayTotal = selectedGames.size() != 1;
            LogRemover.remove(userInterface, selectedGames.get(i), listOfGames, totalSizeRemoved, i == selectedGames.size() - 1, displayTotal);
            sleepFor(1000);
        }

        userInterface.getMainTextField().setText("Closing the application");
        Main.sleepFor(1500);
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
