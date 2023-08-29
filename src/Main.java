import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<GameObject> listOfGames = new ArrayList<>();

        listOfGames.add(new GameObject("DayZ", "Dayz", true, true));
        listOfGames.add(new GameObject("Civilization VI", "Firaxis Games\\Sid Meier's Civilization VI\\Logs"));
        //TODO → add more games here...

        listOfGames.add(new GameObject("Game request? Contact `traqu` on Discord.", "", false, false));

        GUI userInterface = new GUI(listOfGames);
        userInterface.setVisible(true);

        userInterface.getMainTextField().setText("Press \"Remove button\" to perform cleaning.");

        while (!userInterface.isOrderReceived()) {
            sleepFor(10);
        }

        List<String> selectedGames = userInterface.getSelectedGames();

        for (String selectedGame : selectedGames) {
            LogRemover.remove(userInterface, selectedGame, listOfGames);
            sleepFor(800);
        }
        Main.sleepFor(0);
        userInterface.getMainTextField().setText("Closing the application");
        Main.sleepFor(1000);
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
