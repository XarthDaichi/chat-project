package chatClient.presentation;

import chatProtocol.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {
    List<User> rows;
    int[] columns;

    public TableModel(int[] columns, List<User> rows) {
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
            case ONLINE: return Boolean.class;
            default: return super.getColumnClass(column);
        }
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int row, int column) {
        User user = rows.get(row);
        switch (columns[column]) {
            case NAME:
                return user.getNombre();
            case ONLINE:
                return user.isOnline();
            default:
                return "";
        }
    }

    public static final int NAME = 0;
    public static final int ONLINE = 1;

    String[] columnNames = new String[2];

    private void initColumnNames() {
        columnNames[NAME] = "Name";
        columnNames[ONLINE] = "Online";
    }
}
