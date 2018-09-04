package com.company.ViewLayer;

import com.company.LogicLayer.Meal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrdersPanel extends JPanel {

    private JScrollPane scrollPane;
    private JTable orderListTable;
    String [] columnNames = {"Danie", "Nr stolika", "Godzina zamówienia"};
    DefaultTableModel model;
    private static OrdersPanel instance;

    private JLabel title;

    public OrdersPanel(String[][] list, String panelTitle, int screenWidth, int screenHeight){

        instance = this;

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

    public static OrdersPanel getInstance() throws IOException
    {
        return instance;
    }

    public void setOrdersList(String[][] list){
        model.setDataVector(list, columnNames);
        model.fireTableDataChanged();

    }

    public void addNewInPreparationOrder(String tableNumber, String mealName)
    {
        String firstRowValue = (String) model.getValueAt(0, 0);

        if(firstRowValue.equals(""))
        {
            String[][] temp = {{tableNumber, mealName, LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))}};
            setOrdersList(temp);
        }
        else
        {
            String[] temp = {tableNumber, mealName, LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))};
            model.addRow(temp);
            model.fireTableDataChanged();
        }
    }
}
