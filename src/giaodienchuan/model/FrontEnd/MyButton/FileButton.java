package giaodienchuan.model.FrontEnd.MyButton;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class FileButton extends JButton {

    public FileButton() {
        this.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_opened_folder_20px.png")));
    }
}
