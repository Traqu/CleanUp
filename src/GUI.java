import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {

    final private static String AUTHORS_TITLE = "     Created by Traqu";
    final private static int TEXTPANEL_WIDTH = 310;
    final private static int TEXTPANEL_HEIGHT = 26;
    final private static int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    final private static int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
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

    public GUI() throws HeadlessException {
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(AUTHORS_TITLE);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        textPanel.setSize(TEXTPANEL_WIDTH, TEXTPANEL_HEIGHT);
        textPanel.setLayout(new GridLayout());
        this.add(textPanel);

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        this.add(bottomPanel);
        bottomPanel.add(checkboxPanel);
        bottomPanel.add(buttonPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(removeButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 15));


        mainTextField.setEditable(false);
        mainTextField.setHorizontalAlignment(0);
        mainTextField.setFont(new Font("Baghdad", Font.BOLD, 12));
        mainTextField.setPreferredSize(new Dimension(0, TEXTPANEL_HEIGHT));
        mainTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createDashedBorder(new Color(0, 0, 0, 50), 1.05f, 1000, 1, false), BorderFactory.createRaisedBevelBorder()));
        textPanel.add(mainTextField);


        checkboxPanel.setSize(getWidth(), 100);
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));


        checkboxPanel.add(Box.createVerticalStrut(5));
        checkboxPanel.add(jLabel);
        checkboxPanel.add(Box.createVerticalStrut(5));
        checkboxPanel.add(DAYZ_CHECKBOX);
        checkboxPanel.add(CIV_VI_CHECKBOX);
        checkboxPanel.add(PLACEHOLDER);
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

        removeButton.addActionListener(e -> {
            getAllSelectedCheckBoxes(getContentPane());
            removeButton.setEnabled(false);
            orderReceived = true;
        });
    }

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
