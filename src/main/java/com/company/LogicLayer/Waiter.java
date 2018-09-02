package com.company.LogicLayer;

import com.company.ViewLayer.MainFrame;
import com.company.ViewLayer.MapPanel;

import java.io.IOException;
import java.util.List;

public class Waiter implements Runnable {
    @Override
    public void run() {

        MainFrame mainFrame = null;
        try {
            mainFrame = MainFrame.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MapPanel mapPanel = mainFrame.getMapPanel();

        //List<LockedArea> lockedAreas = mapPanel.getLockedAreas();
        //List<Node> nodes = AStar.findPath(100, 100, 150, 150, lockedAreas);


        mapPanel.repaint();
        mapPanel.revalidate();

        while (true) {
            try {
                Thread.sleep(500);

                List<Table> tableList = mapPanel.getTableList();

                int counter = 0;
                int numOfActiveTables = 0;

                for(int i = 0; i < tableList.size(); i++)
                {
                    if(tableList.get(i).getNumberOfPeople() > 0){
                        numOfActiveTables +=1;
                    }
                }

                for(int i = 0; i < tableList.size(); i++)
                {
                    if(tableList.get(i).getStatus() == 3){
                        counter += 1;
                    }

                    System.out.println("Num of active tables: " + numOfActiveTables + "Num of ready to order tables:" + counter);

                    if(counter == numOfActiveTables){
                        System.out.println("BINGO, wszyscy gotowi");
                        Thread.sleep(10000);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void goToXY(MapPanel mapPanel, int desiredX, int desiredY) {
        try {
            if (mapPanel.waiterYpos <= desiredX) {
                for (int i = mapPanel.waiterXpos; i <= desiredX; i += 10) {
                    Thread.sleep(100);
                    mapPanel.setWaiterXpos(i);
                    mapPanel.repaint();
                    mapPanel.revalidate();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
