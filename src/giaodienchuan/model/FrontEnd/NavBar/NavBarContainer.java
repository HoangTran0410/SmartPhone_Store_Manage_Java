package giaodienchuan.model.FrontEnd.NavBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JPanel;

public class NavBarContainer extends JPanel {

    private int bottomPos = 0;
    private ArrayList<NavBarItem> navItems = new ArrayList<>();

    public NavBarContainer(Rectangle rec) {
        setLayout(null);
        setBounds(rec);
        setPreferredSize(new Dimension(rec.width, rec.height));
        setBackground(new Color(35, 35, 35));
    }

    public void addItem(NavBarItem item, Boolean fullWidth) {
        if (fullWidth) {
            item.setBounds(0, bottomPos, getWidth(), item.getHeight());
            if(item instanceof NavBarButton) {
                NavBarButton btnitem = (NavBarButton)item;
                btnitem.relocate();
            }
        } else {
            item.setLocation(item.getBounds().x, item.getBounds().y);
        }

        bottomPos += item.getHeight();
        navItems.add(item); // add to arraylist
        add(item); // add to jpanel
    }

    public void addItem(NavBarItem i) {
        addItem(i, true);
    }
    
    public NavBarItem getItem(int i) {
        return navItems.get(i);
    }
    
    public int getLength() {
        return navItems.size();
    }
}
