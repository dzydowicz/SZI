package com.company.LogicLayer;

import com.company.ViewLayer.MainFrame;
import com.company.ViewLayer.MapPanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Order implements Runnable {
    List<Meal> mealList = new ArrayList<>();
    Table table;

    public Order(Table table) {
        this.table = table;

    }

    public void addMealToOrder(Meal meal) {
        this.mealList.add(meal);
    }

    @Override
    public void run() {

        MainFrame mainFrame = null;
        try {
            mainFrame = MainFrame.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MapPanel mapPanel = mainFrame.getMapPanel();
        List<Table> tableList = mapPanel.getTableList();

        try {
            int randomTime = ThreadLocalRandom.current().nextInt(3000, 10000 + 1);
            Thread.sleep(randomTime);
            tableList.get(table.getTableNumber()).setOrder(new Meal("testMeal", 10));
            tableList.get(table.getTableNumber()).setStatus(3);

            mapPanel.repaint();
            mapPanel.revalidate();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
