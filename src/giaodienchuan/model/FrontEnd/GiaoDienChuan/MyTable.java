package giaodienchuan.model.FrontEnd.GiaoDienChuan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MyTable extends JPanel {

    ZebraJTable tb;
    DefaultTableModel tbModel;
    JScrollPane pane;

    public MyTable() {
        setLayout(new BorderLayout());

        tb = new ZebraJTable();
        tbModel = new DefaultTableModel();
        pane = new JScrollPane(tb);
        pane.getVerticalScrollBar().setUnitIncrement(8);

        tb.setFillsViewportHeight(true);
        tb.setFont(new Font("Segoe UI", 0, 16));
        tb.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tb.setRowHeight(40);

        // color
        int bgColor = 235;
        int color = 0;
        tb.getTableHeader().setBackground(new Color(bgColor, bgColor, bgColor));
        tb.getTableHeader().setForeground(new Color(color, color, color));
        tb.setBackground(new Color(bgColor, bgColor, bgColor));
        tb.setForeground(new Color(color, color, color));

        add(pane, BorderLayout.CENTER);
    }

    public void setHeaders(String[] headers) {
        tbModel.setColumnIdentifiers(headers);
        tb.setModel(tbModel);
    }

    public void setHeaders(ArrayList<String> headers) {
        tbModel.setColumnIdentifiers(headers.toArray());
        tb.setModel(tbModel);
    }

    // https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
    public void setAlignment(int column, int align) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(align);
        tb.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
    }

    // https://stackoverflow.com/questions/28823670/how-to-sort-jtable-in-shortest-way
    public void setupSort() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tb.getModel());
        tb.setRowSorter(sorter);

        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        for (int i = 0; i < tbModel.getColumnCount(); i++) {
            sortKeys.add(new RowSorter.SortKey(i, SortOrder.ASCENDING));
        }
        sorter.setSortKeys(sortKeys);
    }

    public void addRow(String[] data) {
        tbModel.addRow(data);
    }

    public JTable getTable() {
        return tb;
    }

    public DefaultTableModel getModel() {
        return tbModel;
    }

    public void clear() {
        tbModel.setRowCount(0);
    }

    // https://www.codejava.net/java-se/swing/setting-column-width-and-row-height-for-jtable
    public void setColumnsWidth(double[] percentages) {

        double total = 0;
        for (int i = 0; i < tb.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < tb.getColumnModel().getColumnCount(); i++) {
            TableColumn column = tb.getColumnModel().getColumn(i);
            column.setPreferredWidth((int) (getPreferredSize().width * (percentages[i] / total)));
        }
    }
}

/**
 * A JTable that draws a zebra striped background.
 */

//http://nadeausoftware.com/articles/2008/01/java_tip_how_add_zebra_background_stripes_jtable
class ZebraJTable extends javax.swing.JTable {

    private java.awt.Color rowColors[] = new java.awt.Color[2];
    private boolean drawStripes = false;

    public ZebraJTable() {
    }

    public ZebraJTable(int numRows, int numColumns) {
        super(numRows, numColumns);
    }

    public ZebraJTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }

    public ZebraJTable(javax.swing.table.TableModel dataModel) {
        super(dataModel);
    }

    public ZebraJTable(javax.swing.table.TableModel dataModel,
            javax.swing.table.TableColumnModel columnModel) {
        super(dataModel, columnModel);
    }

    public ZebraJTable(javax.swing.table.TableModel dataModel,
            javax.swing.table.TableColumnModel columnModel,
            javax.swing.ListSelectionModel selectionModel) {
        super(dataModel, columnModel, selectionModel);
    }

    public ZebraJTable(java.util.Vector<?> rowData,
            java.util.Vector<?> columnNames) {
        super(rowData, columnNames);
    }

    /**
     * Add stripes between cells and behind non-opaque cells.
     */
    public void paintComponent(java.awt.Graphics g) {
        if (!(drawStripes = isOpaque())) {
            super.paintComponent(g);
            return;
        }

        // Paint zebra background stripes
        updateZebraColors();
        final java.awt.Insets insets = getInsets();
        final int w = getWidth() - insets.left - insets.right;
        final int h = getHeight() - insets.top - insets.bottom;
        final int x = insets.left;
        int y = insets.top;
        int rowHeight = 16; // A default for empty tables
        final int nItems = getRowCount();
        for (int i = 0; i < nItems; i++, y += rowHeight) {
            rowHeight = getRowHeight(i);
            g.setColor(rowColors[i & 1]);
            g.fillRect(x, y, w, rowHeight);
        }
        // Use last row height for remainder of table area
        final int nRows = nItems + (insets.top + h - y) / rowHeight;
        for (int i = nItems; i < nRows; i++, y += rowHeight) {
            g.setColor(rowColors[i & 1]);
            g.fillRect(x, y, w, rowHeight);
        }
        final int remainder = insets.top + h - y;
        if (remainder > 0) {
            g.setColor(rowColors[nRows & 1]);
            g.fillRect(x, y, w, remainder);
        }

        // Paint component
        setOpaque(false);
        super.paintComponent(g);
        setOpaque(true);
    }

    /**
     * Add background stripes behind rendered cells.
     */
    public java.awt.Component prepareRenderer(
            javax.swing.table.TableCellRenderer renderer, int row, int col) {
        final java.awt.Component c = super.prepareRenderer(renderer, row, col);
        if (drawStripes && !isCellSelected(row, col)) {
            c.setBackground(rowColors[row & 1]);
        }
        return c;
    }

    /**
     * Add background stripes behind edited cells.
     */
    public java.awt.Component prepareEditor(
            javax.swing.table.TableCellEditor editor, int row, int col) {
        final java.awt.Component c = super.prepareEditor(editor, row, col);
        if (drawStripes && !isCellSelected(row, col)) {
            c.setBackground(rowColors[row & 1]);
        }
        return c;
    }

    /**
     * Force the table to fill the viewport's height.
     */
    public boolean getScrollableTracksViewportHeight() {
        final java.awt.Component p = getParent();
        if (!(p instanceof javax.swing.JViewport)) {
            return false;
        }
        return ((javax.swing.JViewport) p).getHeight() > getPreferredSize().height;
    }

    /**
     * Compute zebra background stripe colors.
     */
    private void updateZebraColors() {
        if ((rowColors[0] = getBackground()) == null) {
            rowColors[0] = rowColors[1] = java.awt.Color.white;
            return;
        }
        final java.awt.Color sel = getSelectionBackground();
        if (sel == null) {
            rowColors[1] = rowColors[0];
            return;
        }
        final float[] bgHSB = java.awt.Color.RGBtoHSB(
                rowColors[0].getRed(), rowColors[0].getGreen(),
                rowColors[0].getBlue(), null);
        final float[] selHSB = java.awt.Color.RGBtoHSB(
                sel.getRed(), sel.getGreen(), sel.getBlue(), null);
        rowColors[1] = java.awt.Color.getHSBColor(
                (selHSB[1] == 0.0 || selHSB[2] == 0.0) ? bgHSB[0] : selHSB[0],
                0.1f * selHSB[1] + 0.9f * bgHSB[1],
                bgHSB[2] + ((bgHSB[2] < 0.5f) ? 0.05f : -0.05f));
    }
}
