package giaodienchuan;

import giaodienchuan.model.FrontEnd.GiaoDienChuan.GiaoDienChuan;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }

        GiaoDienChuan f = new GiaoDienChuan();
        f.setVisible(true);
    }
}
