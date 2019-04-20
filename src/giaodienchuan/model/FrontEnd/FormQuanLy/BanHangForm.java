/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormQuanLy;

import java.awt.BorderLayout;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class BanHangForm extends JPanel {

    JTextField txTest = new JTextField(15);

    public BanHangForm() {
        setLayout(new BorderLayout());
        
        JPanel plTime = new JPanel();
        plTime.add(txTest);
        this.add(plTime, BorderLayout.CENTER);
        
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                txTest.setText(LocalTime.now().toString());
            }
        }, 0, 1000);
    }
}

