
package giaodienchuan.model.FrontEnd.FormQuanLy;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmptyPage extends JPanel {
    
    JLabel lbInfo;
    
    public EmptyPage() {
        setLayout(new GridBagLayout());
        
        lbInfo = new JLabel("Hệ thống đang bảo trì");
        lbInfo.setFont(new Font("Arial", Font.BOLD, 40));
        lbInfo.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_computer_support_99px.png")));
        
        add(lbInfo);
    }
    
    public void setLabelText(String text) {
        lbInfo.setText(text);
    }
}
