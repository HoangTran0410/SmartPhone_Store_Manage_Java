/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.WorkWithExcel;

import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class MyJOptionPane extends JOptionPane {
    
    JComboBox<String> cb = new JComboBox<>(new String[] {"Ghi đè", "Bỏ qua", "Ghi đè tất cả", "Bỏ qua tất cả"});
    JPanel pl = new JPanel();
    
    public MyJOptionPane(MyTable mtb, String defaultSelect) {
        cb.setBorder(BorderFactory.createTitledBorder("Hành động:"));
        cb.setSelectedItem(defaultSelect);
        mtb.resizeColumnWidth();
        
        pl.setLayout(new BorderLayout());
        pl.add(mtb, BorderLayout.CENTER);
        pl.add(cb, BorderLayout.SOUTH);
        
        this.showMessageDialog(null, pl, "Trùng mã", QUESTION_MESSAGE);
    }
    
    public String getAnswer() {
        return cb.getSelectedItem().toString();
    }
}
