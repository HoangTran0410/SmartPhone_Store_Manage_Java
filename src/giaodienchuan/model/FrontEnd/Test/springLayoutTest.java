
package giaodienchuan.model.FrontEnd.Test;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class springLayoutTest {

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setLayout(null);
        f.setTitle("Giao diện chuẩn");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String[] labels = {"Name: ", "Fax: ", "Email: ", "Address: "};
        int numPairs = labels.length;

        //Create and populate the panel.
        JPanel p = new JPanel(new SpringLayout());
        p.setSize(200, 150);
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            p.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            p.add(textField);
        }

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                numPairs, 2, //rows, cols
                6, 6, //initX, initY
                6, 6);       //xPad, yPad

        f.add(p, BorderLayout.SOUTH);
        f.setVisible(true);
    }
}
