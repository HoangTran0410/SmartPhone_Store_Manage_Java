
package giaodienchuan.model.FrontEnd.NavBar;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

public class NavBarTitle extends NavBarItem {
    
    public NavBarTitle(Rectangle rec, String text) {
        super(rec, text);
        setLayout(new GridBagLayout());
        
        setFontSize(20);
        lbLabel.setForeground(Color.WHITE);
    }
}
