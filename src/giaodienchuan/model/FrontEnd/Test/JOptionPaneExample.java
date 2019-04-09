package giaodienchuan.model.FrontEnd.Test;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import javax.imageio.ImageIO;

public class JOptionPaneExample {

    private void displayGUI() {
        JOptionPane.showConfirmDialog(null,
                        getPanel(),
                        "JOptionPane Example : ",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
    }

    private JPanel getPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Java Technology Dive Log");
        ImageIcon image = null;
        try {
            image = new ImageIcon(ImageIO.read(
                    new URL("http://i.imgur.com/6mbHZRU.png")));
        } catch(MalformedURLException mue) {
            mue.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } 

        label.setIcon(image);
        panel.add(label);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JOptionPaneExample().displayGUI();
            }
        });
    }
}