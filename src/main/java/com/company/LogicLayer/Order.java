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
        List<Meal> menu = OrdersService.getMenu();
        List<Meal> orderedMeals = new ArrayList<>();

        try {
            int randomTime = ThreadLocalRandom.current().nextInt(100, 1000 + 1);
            Thread.sleep(randomTime);

            for (int i = 0; i < table.getNumberOfPeople(); i++)
            {
                int randomMeal = ThreadLocalRandom.current().nextInt(1, 6 + 1);
                orderedMeals.add(menu.get(randomMeal - 1));
            }

            table.setOrder(orderedMeals);
            table.setStatus(3);

            mapPanel.repaint();
            mapPanel.revalidate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
