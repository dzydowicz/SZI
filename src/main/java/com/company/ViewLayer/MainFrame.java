package com.company.ViewLayer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalTime;

public class MainFrame extends JFrame {

    static public MainFrame instance;

    MapPanel mapPanel;

    int screenWidth = 1024;
    int screenHeight = 768;

    JPanel leftPanel, rightPanel, northPanel, newOrdersPanel, dashboardPanel;

    public static MainFrame getInstance() throws IOException {
        if (instance == null) {
            return new MainFrame();
        } else {
            return instance;
        }
    }

    public MainFrame() throws IOException {
        //title
        super("SZI");

        instance = this;
        this.pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setVisible(true);
        setResizable(false);

        //tworzymy panel główny, mapa gry na której będzie wszystko wizualizowane
        mapPanel = new MapPanel(screenWidth, screenHeight);
        add(mapPanel, BorderLayout.CENTER);

        //tworzymy lewy panel, tutaj będą się wyswietlac nowe zamowienia
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        this.add(leftPanel, BorderLayout.WEST);

        //utworzenie panelu z zamowieniami
//        String [][] orderList = {{"", ""}};
//        newOrdersPanel = new OrdersPanel(orderList, "Zamowienia:", screenWidth, screenHeight);
//        newOrdersPanel.setPreferredSize(new Dimension(380, 768));

        try {
            dashboardPanel = new DashboardPanel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        leftPanel.add(dashboardPanel);

        pack();
    }


    public MapPanel getMapPanel(){return mapPanel;}
}