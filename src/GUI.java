import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GUI extends JFrame {

    final private static String AUTHORS_TITLE = "Logs CleanUp!                      Created by Traqu";
    final private static int TEXTPANEL_WIDTH = 310;
    final private static int TEXTPANEL_HEIGHT = 26;
    final private static int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    final private static int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final Font COMMON_FONT = new Font("Baghdad", Font.BOLD, 12);
    public static final Color FADE_BLACK = new Color(0, 0, 0, 50);
    private final JTextField mainTextField = new JTextField();
    private final JButton removeButton = new JButton("Remove");

    private final List<String> selectedGamesList = new ArrayList<>();

    private boolean orderReceived = false;

    public GUI(List<GameObject> listOfGames) throws HeadlessException {

        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(AUTHORS_TITLE);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel textPanel = new JPanel();
        this.add(textPanel);
        textPanel.setSize(TEXTPANEL_WIDTH, TEXTPANEL_HEIGHT);
        textPanel.setLayout(new GridLayout());

        JPanel bottomPanel = new JPanel();
        this.add(bottomPanel);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        JPanel checkboxPanel = new JPanel();
        bottomPanel.add(checkboxPanel);
        JPanel buttonPanel = new JPanel();
        bottomPanel.add(buttonPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(removeButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 15));


        mainTextField.setEditable(false);
        mainTextField.setHorizontalAlignment(0);
        mainTextField.setPreferredSize(new Dimension(0, TEXTPANEL_HEIGHT));
        mainTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createDashedBorder(FADE_BLACK, 1.05f, 1000, 1, false), BorderFactory.createRaisedBevelBorder()));
        textPanel.add(mainTextField);


        checkboxPanel.setSize(getWidth(), 100);
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));


        checkboxPanel.add(Box.createVerticalStrut(5));
        JLabel jLabel = new JLabel("  Select the games for which you'd like to remove logs.");
        checkboxPanel.add(jLabel);
        checkboxPanel.add(Box.createVerticalStrut(5));
        for (GameObject game : listOfGames) {
            if (game.getGameDirectory().exists()) {
                checkboxPanel.add(game.getCheckBox());
            }
        }

        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icons/logo.png")));
        this.setIconImage(logo.getImage());

        this.pack();
        this.setLocation(
                SCREEN_WIDTH / 2 - this.getPreferredSize().width / 2,
                SCREEN_HEIGHT / 2 - this.getPreferredSize().height / 2
        );

        setFocusableFalseForAllComponents(getContentPane());
        setFontForAllComponents(getContentPane(), COMMON_FONT);

        removeButton.addActionListener(e -> {
            getAllSelectedCheckBoxes(getContentPane());
            removeButton.setEnabled(false);
            orderReceived = true;
        });
    }


    //------------------------------------------------------------------------------------------------------------------

    private void getAllSelectedCheckBoxes(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                if (((JCheckBox) component).isSelected()) {
                    selectedGamesList.add(((JCheckBox) component).getText());
                }
            }

            if (component instanceof Container) {
                getAllSelectedCheckBoxes((Container) component);
            }
        }
    }

    private void setFontForAllComponents(Container container, Font font) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component.getFont() != null) {
                component.setFont(font);
            }
            if (component instanceof Container) {
                setFontForAllComponents((Container) component, font);
            }
        }
    }


    private void setFocusableFalseForAllComponents(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setFocusable(false);

            if (component instanceof Container) {
                setFocusableFalseForAllComponents((Container) component);
            }
        }
    }

    public JTextField getMainTextField() {
        return mainTextField;
    }

    public List<String> getSelectedGames() {
        return selectedGamesList;
    }

    public boolean isOrderReceived() {
        return orderReceived;
    }
}
