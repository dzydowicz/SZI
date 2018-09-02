package com.company.ViewLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JScrollPane scrollPane;
    private JTable orderListTable;
    String[] columnNames = {"Danie", "Czas oczekiwania"};
    DefaultTableModel model;


    private JLabel title;

    public MenuPanel(String[][] list, int screenWidth, int screenHeigth) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(screenWidth / 8 + screenWidth / 18, screenHeigth / 2));
        this.setBackground(Color.white);

        title = new JLabel("MENU:");
        this.add(title);

        model = new DefaultTableModel(list, columnNames);
        orderListTable = new JTable(model);

        scrollPane = new JScrollPane(orderListTable);
        orderListTable.setFillsViewportHeight(true);

        this.add(scrollPane);
    }

    public void setOrdersList(String[][] list) {
        model.setDataVector(list, columnNames);
        model.fireTableDataChanged();

    }
}

