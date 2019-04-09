
package giaodienchuan.model.FrontEnd.Form;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmptyPage extends JPanel {
    public EmptyPage() {
        setLayout(new GridBagLayout());
        
        JLabel lbInfo = new JLabel("Hệ thống đang bảo trì");
        lbInfo.setFont(new Font("Arial", Font.BOLD, 40));
        lbInfo.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_computer_support_99px.png")));
        
        add(lbInfo);
    }
}
