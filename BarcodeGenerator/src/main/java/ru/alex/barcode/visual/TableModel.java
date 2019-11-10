package ru.alex.barcode.visual;

import java.util.Collections;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;

public class TableModel extends DefaultTableModel {
    private List<String> lines = Collections.emptyList();
    private String delimiter;

    public void setLines(List<String> lines, String delimiter) {
        this.lines = lines;
        this.delimiter = delimiter;
        fireTableDataChanged();
    }

    public TableModel setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        fireTableDataChanged();
        return this;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return lines != null ? lines.size() : 0;
    }

    @Override
    public Object getValueAt(int row, int column) {
        String line = lines.get(row);
        switch (column) {
            case 0:
                return StringUtils.substringBefore(line, delimiter);
            case 1:
                return StringUtils.substringAfter(line, delimiter);
        }
        return super.getValueAt(row, column);
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Товар";
            case 1:
                return "Акцизная марка";
        }
        return super.getColumnName(column);
    }

}
