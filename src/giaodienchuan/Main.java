package giaodienchuan;

import giaodienchuan.model.FrontEnd.GiaoDienChuan.LoginForm;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {

        }
        
        new LoginForm().setVisible(true);
    }
}
