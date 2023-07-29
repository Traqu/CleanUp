import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {

    final private static String AUTHORS_TITLE = "     Created by Traqu";
    final private static int TEXTPANEL_WIDTH = 310;
    final private static int TEXTPANEL_HEIGHT = 26;
    final private static int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    final private static int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final Font COMMON_FONT = new Font("Baghdad", Font.BOLD, 12);
    public static final Color FADE_BLACK = new Color(0, 0, 0, 50);
    private JPanel textPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JPanel checkboxPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JTextField mainTextField = new JTextField();
    private JButton removeButton = new JButton("Remove");
    private JButton fakeButton = new JButton();

    private List<String> selectedGamesList = new ArrayList<>();

    final private JCheckBox DAYZ_CHECKBOX = new JCheckBox("DayZ", true);
    final private JCheckBox CIV_VI_CHECKBOX = new JCheckBox("Civilization VI");
    final private JCheckBox PLACEHOLDER = new JCheckBox("PLACEHOLDER");

    final private JLabel jLabel = new JLabel("  Select the games for which you'd like to remove logs.");
    private boolean orderReceived = false;

    private List<String> checkBoxesList = new ArrayList<>();

    public GUI() throws HeadlessException {

             this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(AUTHORS_TITLE);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.add(textPanel);
        textPanel.setSize(TEXTPANEL_WIDTH, TEXTPANEL_HEIGHT);
        textPanel.setLayout(new GridLayout());

        this.add(bottomPanel);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(checkboxPanel);
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
        checkboxPanel.add(jLabel);
        checkboxPanel.add(Box.createVerticalStrut(5));
        addAllChechboxes(checkboxPanel);
        PLACEHOLDER.setEnabled(false);
        DAYZ_CHECKBOX.setEnabled(true);

        ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("icons/logo.png"));
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
    /**
     * EXAMPLE FONTS:
     * Yu Gothic UI
     * Dubai
     * Corbel
     * Century Schoolbook
     * Bookman Old Style
     * Bell MT
     */

    private void getAllSelectedCheckBoxes(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                if (((JCheckBox) component).isSelected())
                    selectedGamesList.add(((JCheckBox) component).getText());
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

    private void addAllChechboxes(JPanel panel) {
        Class<? extends GUI> guiClass = this.getClass();
        Field[] declaredFields = guiClass.getDeclaredFields();
        List<Boolean> accessibilities = new ArrayList<>();

        for (Field declaredField : declaredFields) {
            accessibilities.add(declaredField.isAccessible());
            declaredField.setAccessible(true);
            if (declaredField.getType().getName().contains("JCheckBox")) {
                try {
                    declaredField.setAccessible(true);
                    JCheckBox checkBox = (JCheckBox) declaredField.get(this);
                    panel.add(checkBox);
                    checkBoxesList.add(checkBox.getText());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(accessibilities.get(i));
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

    public List<String> getCheckBoxesList() {
        return checkBoxesList;
    }
}
