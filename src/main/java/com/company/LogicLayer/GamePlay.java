package com.company.LogicLayer;

import com.company.Main;
import com.company.ViewLayer.MainFrame;
import com.company.ViewLayer.MapPanel;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GamePlay implements Runnable {
    private static ExecutorService threads = Executors.newCachedThreadPool();

    @Override
    public void run() {


        threads.execute(new Waiter());


        System.out.println("gameplay thread");


        while (true) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Time from gameplay: " + new Date());
        }
    }
}
