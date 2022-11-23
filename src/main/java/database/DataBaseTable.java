package database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class DataBaseTable extends JPanel {
    private final Object[][] data;
    private final String[] columnName;
    private JTable DBTable;
    private DefaultTableModel model;

    public DataBaseTable(Object[][] data, String[] columnName) {
        this.data = data;
        this.columnName = columnName;

        setLayout(new BorderLayout());
        initTable();
    }

    public int[] getSelected() {
        return DBTable.getSelectedRows();
    }

    public void removeSelected(int selected) {
        model.removeRow(selected);
    }

    public Object[] getSelectedRow(int index) {
        return data[index];
    }

    public void addRow(Object[] obj) {
        model.addRow(obj);
    }

    public String getInfo(int row, int column) {
        return (String) model.getDataVector().get(row).get(column);
    }

    public Vector<Vector> getData() {
        return model.getDataVector();
    }

    public void updateRow(int index, Object[] objects) {
        model.removeRow(index);
        model.insertRow(index, objects);
    }

    public void clear() {
        model.setDataVector(new Object[][]{}, columnName);
    }

    private void initTable() {
        model = new DefaultTableModel(this.data, this.columnName);
        DBTable = new JTable(model);
        DBTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        DBTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(DBTable);
        add(scrollPane, BorderLayout.CENTER);
    }
}
