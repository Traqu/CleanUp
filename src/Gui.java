import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private static int width = 310;
    private static int heigth = 65;
    private static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int screenHeigth = Toolkit.getDefaultToolkit().getScreenSize().height;
    private Font font = new Font("Baghdad", Font.BOLD, 12);
    private JTextField jTextField = new JTextField("TEXT");
    public Gui() throws HeadlessException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(width, heigth);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocation(
                screenWidth / 2 - this.getPreferredSize().width / 2 - width / 2,
                screenHeigth / 2 - this.getPreferredSize().height / 2
        );
        this.add(jTextField);
        jTextField.setEditable(false);
        jTextField.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        jTextField.setFont(font);

//        String[] fontFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//        for (String fontFamily : fontFamilies) {
//            System.out.println(fontFamily);
//        }
    }

    public JTextField getjTextField() {
        return jTextField;
    }

    public void setjTextField(JTextField jTextField) {
        this.jTextField = jTextField;
    }
}