package com.company.LogicLayer;

import com.company.ViewLayer.MainFrame;
import com.company.ViewLayer.MapPanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientsLife implements Runnable {

    private static ExecutorService threads = Executors.newCachedThreadPool();

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
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        for (Table temp : tableList) {
//            if (temp.getNumberOfPeople() > 0) {
//
//                Order order = new Order();
//                order.addMealToOrder(new Meal("randomMeal", 10));
//                order.addMealToOrder(new Meal("secondRandomMeal", 20));
//
//                temp.setOrder(order);
//
//                temp.setStatus(2);
//                mapPanel.repaint();
//                mapPanel.revalidate();
//            }
//        }
//
        for(int i =0; i < tableList.size(); i++){
            if(tableList.get(i).getNumberOfPeople() > 0){
                System.out.println("Uruchamiam nowy watek dla stolika numer: "+tableList.get(i).getTableNumber() );
                threads.execute(new Order(tableList.get(i)));

//                Order order = new Order();
//                order.addMealToOrder(new Meal("randomMeal", 10));
//                order.addMealToOrder(new Meal("secondRandomMeal", 20));

//                tableList.get(i).setOrder(order);
//                tableList.get(i).setStatus(1);

                mapPanel.repaint();
                mapPanel.revalidate();
            }
        }




    }
}
