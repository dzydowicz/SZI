package com.company.ViewLayer;

import com.company.LogicLayer.GamePlay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardPanel extends JPanel {

    BufferedImage waiter;
    public int waiterXpos;
    public int waiterYpos; //pozycja w px

    private static ExecutorService threads = Executors.newCachedThreadPool();

    public DashboardPanel() throws IOException {

        waiter = ImageIO.read(new File("resources/alpha.png"));

        JButton startButton = new JButton("Start");
        this.add(startButton);

        startButton.addActionListener(e -> {
            // threads.execute(new LabelImage());
            threads.execute(new GamePlay()); //sluzy do losowania zamowien

            //takie sobie przyjmuje zalozenie ze mam 1 dzialajacych kucharzy
            // threads.execute(new CurrentCreatingMeal());
            //---------------------------------------------------------------------
            //threads.execute(new IfWaiterGoThread());

        });

    }


}
