package giaodienchuan.model.FrontEnd.MyButton;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MoreButton extends JButton {

    public MoreButton() {
        this.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_more_20px.png")));
    }
}
