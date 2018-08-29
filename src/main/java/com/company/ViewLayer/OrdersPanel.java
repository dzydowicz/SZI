package com.company.ViewLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class OrdersPanel extends JPanel {

    private JScrollPane scrollPane;
    private JTable orderListTable;
    String [] columnNames = {"Danie", "Nr stolika", "Godzina zamówienia"};
    DefaultTableModel model;

    private JLabel title;

    public OrdersPanel(String[][] list, String panelTitle, int screenWidth, int screenHeight){

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.white);

        title = new JLabel(panelTitle);
        this.add(title);

        model = new DefaultTableModel(list, columnNames);
        orderListTable = new JTable(model);

        TableColumnModel columnModel = orderListTable.getColumnModel();
        columnModel.getColumn(0).setWidth(200/*3*screenWidth/8/4*/);
        columnModel.getColumn(1).setWidth(25/*screenWidth/8/4*/);
        columnModel.getColumn(2).setWidth(25/*screenWidth/8/4*/);

        scrollPane = new JScrollPane(orderListTable);
        orderListTable.setFillsViewportHeight(true);

        this.add(scrollPane);
    }
    public void setOrdersList(String[][] list){
        model.setDataVector(list, columnNames);
        model.fireTableDataChanged();

    }
    public void addNewInPreparationOrder(String[] list)
    {
        model.addRow(list);
        model.fireTableDataChanged();
    }
}
