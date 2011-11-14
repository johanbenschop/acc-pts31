/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.gui;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author johan
 */
class airportTableModel extends AbstractTableModel {

    private String[] columnNames = {"Airport ID",
        "Name",
        "City",
        "Country",
        "IATA/FAA",
        "ICAO"};
    private Object[][] data = { 
        {"Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false)},
    };

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}