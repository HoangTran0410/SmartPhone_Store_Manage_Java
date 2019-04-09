package giaodienchuan.model.FrontEnd.Test;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

//https://stackoverflow.com/questions/4941372/how-to-insert-image-into-jtable-cell
public class TableIcon extends JPanel {

    public TableIcon() {
        Icon aboutIcon = new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png"));
        Icon addIcon = new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_bar_chart_30px.png"));
        Icon copyIcon = new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png"));

        String[] columnNames = {"Picture", "Description"};
        Object[][] data = {
                    {aboutIcon, "About"},
                    {addIcon, "Add"},
                    {copyIcon, "Copy"}};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            @Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Table Icon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TableIcon());
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
