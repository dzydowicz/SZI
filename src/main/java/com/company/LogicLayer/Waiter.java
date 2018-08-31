package com.company.LogicLayer;

import com.company.ViewLayer.MainFrame;
import com.company.ViewLayer.MapPanel;

import java.io.IOException;
import java.util.Date;

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
        mapPanel.alreadyDrawed = true;

        mapPanel.repaint();
        mapPanel.revalidate();

        goToXY(mapPanel, 500, 500);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Time from waiter: " + new Date());
        }


    }

    public void goToXY(MapPanel mapPanel, int desiredX, int desiredY) {

        try {
            if (mapPanel.waiterYpos <= desiredX) {
                for (int i = mapPanel.waiterXpos; i <= desiredX; i+=10) {
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
