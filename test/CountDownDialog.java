package giaodienchuan.model.FrontEnd.Test;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CountDownDialog {

    private JFrame parentFrame;
    private JDialog dialog;

    protected void init() {
        this.dialog = new JDialog(this.parentFrame, "Copie", true);
        this.dialog.setResizable(false);
        this.dialog.getContentPane().add(createPane());
        this.dialog.pack();
        this.dialog.setSize(450, 250);
        Dimension Size = Toolkit.getDefaultToolkit().getScreenSize();
        this.dialog.setLocation(new Double((Size.getWidth() / 2) - (dialog.getWidth() / 2)).intValue(), new Double((Size.getHeight() / 2) - (dialog.getHeight() / 2)).intValue());
    }

    protected Container createPane() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0, 51, 153));
        JButton b1 = new JButton("Hello");
        mainPanel.add(b1);
        return mainPanel;

    }

    public void show() {
        if (this.dialog == null) {
            init();
        }
        this.dialog.setVisible(true);
    }

    protected void close() {
        this.dialog.dispose();
        this.dialog.setVisible(false);
    }

    public void setFrame(JFrame frame) {
        parentFrame = (JFrame) frame;
    }

    public static void main(String args[]) {
        CountDownDialog obj = new CountDownDialog();
        obj.init();
        obj.show();
    }
}
