import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GUI extends JFrame {

    private final static String VERSION;

    static {
        String version = "has not been loaded properly"; // Domyślna wartość

        try (InputStream inputStream = GUI.class.getResourceAsStream("version");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            version = bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        VERSION = version;
    }




    final private static String APPLICATION_TITLE = " CleanUp!";
    final private static int TEXTPANEL_WIDTH = 310;
    final private static int TEXTPANEL_HEIGHT = 26;
    final private static int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    final private static int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final Font COMMON_FONT = new Font("Baghdad", Font.BOLD, 12);
    public static final Color FADE_BLACK = new Color(0, 0, 0, 50);
    public static final Dimension DIMENSION = new Dimension(125, 25);
    private final JTextField mainTextField = new JTextField();
    private final JButton removeButton = new JButton("☠ Remove ☠");
    private final JButton clickAllButton = new JButton("Select all");
    private final List<String> selectedGamesList = new ArrayList<>();
    private final JButton removeAllButton = new JButton("Remove all");
    private boolean orderReceived = false;
    private boolean allSelected = false;

    public GUI(List<GameObject> listOfGames) throws HeadlessException {

        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(APPLICATION_TITLE);
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
        bottomPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(clickAllButton);

        clickAllButton.setPreferredSize(DIMENSION);
        clickAllButton.setMaximumSize(DIMENSION);
        clickAllButton.setMinimumSize(DIMENSION);

        removeButton.setPreferredSize(DIMENSION);
        removeButton.setMaximumSize(DIMENSION);
        removeButton.setMinimumSize(DIMENSION);

        removeAllButton.setPreferredSize(DIMENSION);
        removeAllButton.setMaximumSize(DIMENSION);
        removeAllButton.setMinimumSize(DIMENSION);

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(removeAllButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(removeButton);
        buttonPanel.add(Box.createVerticalGlue());
        JLabel authorsLabel = new JLabel("      created by Traqu");
        JLabel versionLabel = new JLabel("          version " + VERSION);
        buttonPanel.add(authorsLabel);
        buttonPanel.add(versionLabel);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

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
        checkboxPanel.add(Box.createVerticalStrut(5));


        /**This loop adds checkboxes to the gui*/
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
        authorsLabel.setFont(new Font("Baghdad", Font.ITALIC, 11));
        authorsLabel.setForeground(Color.GRAY);
        versionLabel.setFont(new Font("Baghdad", Font.ITALIC, 11));
        versionLabel.setForeground(Color.GRAY);
        removeButton.setForeground(new Color(17, 14, 14));

        removeButton.addActionListener(e -> {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            disableAllCheckboxes(checkboxPanel);
            getAllSelectedCheckBoxes(getContentPane());
            removeButton.setEnabled(false);
            removeAllButton.setEnabled(false);
            clickAllButton.setEnabled(false);
            orderReceived = true;
        });

        removeAllButton.addActionListener(e -> {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            removeButton.setEnabled(false);
            removeAllButton.setEnabled(false);
            clickAllButton.setEnabled(false);
            clickAllButton.setText("Deselect all");
            for (GameObject game : listOfGames) {
                game.getCheckBox().setSelected(true);
            }
            setAllSelected(true);
            orderReceived = true;
            for (GameObject game : listOfGames) {
                if (!game.getCheckBox().isEnabled()) {
                    game.getCheckBox().setSelected(false);
                }
            }
            disableAllCheckboxes(checkboxPanel);
            getAllSelectedCheckBoxes(getContentPane());
        });

        clickAllButton.addActionListener(e -> {
            if (allSelected) {
                clickAllButton.setText("Select all");
                for (GameObject game : listOfGames) {
                    game.getCheckBox().setSelected(false);
                }
                setAllSelected(false);
            } else {
                clickAllButton.setText("Deselect all");
                for (GameObject game : listOfGames) {
                    game.getCheckBox().setSelected(true);
                }
                setAllSelected(true);
            }
            for (GameObject game : listOfGames) {
                if (!game.getCheckBox().isEnabled()) {
                    game.getCheckBox().setSelected(false);
                }
            }
        });
    }

    private static void disableAllCheckboxes(JPanel checkboxPanel) {
        Component[] components = checkboxPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                component.setEnabled(false);
            }
        }
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

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }
}
