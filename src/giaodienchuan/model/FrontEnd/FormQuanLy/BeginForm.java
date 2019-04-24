/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormQuanLy;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class BeginForm extends JPanel {

    JLabel lbInfo;

    public BeginForm(String text) {
        setLayout(new GridBagLayout());

        lbInfo = new JLabel(text);
        lbInfo.setFont(new Font("Arial", Font.BOLD, 40));
        lbInfo.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_thumb_up_99px.png")));

        add(lbInfo);
    }

    public void setLabelText(String text) {
        lbInfo.setText(text);
    }
}
