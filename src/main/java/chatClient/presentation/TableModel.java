package chatClient.presentation;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {
    List<String> rows;
    int[] columns;

    public TableModel(int[] columns, List<String> rows) {
        initColumnNames();
        this.columns=columns;
        this.rows=rows;
    }

    public int getColumnCount() {
        return columns.length;
    }

    public String getColumName(int column) {
        return columnNames[columns[column]];
    }

    public Class<?> getColumnClass(int column) {
        switch(columns[column]) {
            default: return super.getColumnClass(column);
        }
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int row, int column) {
        String id = rows.get(row);
        switch (columns[column]) {
            case ID:
                return id;
            case ONLINE:
                return false;
            default:
                return "";
        }
    }

    public static final int ID = 0;
    public static final int ONLINE = 1;

    String[] columnNames = new String[2];

    private void initColumnNames() {
        columnNames[ID] = "Id";
        columnNames[ONLINE] = "Online";
    }
}
