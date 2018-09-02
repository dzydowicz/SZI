package com.company.LogicLayer;

import com.company.LogicLayer.GeneticAlgorithm.GeneticAlgorithm;
import com.company.LogicLayer.GeneticAlgorithm.Population;
import com.company.LogicLayer.GeneticAlgorithm.Tour;
import com.company.LogicLayer.GeneticAlgorithm.TourManager;
import com.company.ViewLayer.MainFrame;
import com.company.ViewLayer.MapPanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Waiter implements Runnable {
    @Override
    public void run() {

        boolean notAllReady = true;

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

        List<Table> activeTableList = new ArrayList<>();

        while (notAllReady) {
            try {
                Thread.sleep(500);

                List<Table> tableList = mapPanel.getTableList();

                int counter = 0;
                int numOfActiveTables = 0;

                for (int i = 0; i < tableList.size(); i++) {
                    if (tableList.get(i).getNumberOfPeople() > 0) {
                        numOfActiveTables += 1;
                    }
                }

                for (int i = 0; i < tableList.size(); i++) {
                    if (tableList.get(i).getStatus() == 3) {
                        counter += 1;
                    }

                    // System.out.println("Num of active tables: " + numOfActiveTables + "Num of ready to order tables:" + counter);

                    if (counter == numOfActiveTables) {
                        notAllReady = false;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<Table> tableList = mapPanel.getTableList();

        for (int j = 0; j < tableList.size(); j++) {
            if (tableList.get(j).getNumberOfPeople() > 0) {
                activeTableList.add(tableList.get(j));
            }
        }
        System.out.println("BINGO, wszyscy gotowi");


        for (Table temp : activeTableList) {
            TourManager.addTable(temp);
            System.out.println("dodano stol do tour managera");
        }


        /**
         * ALGORYTM GENETYCZNY
         * SLUZY DO WYZNACZENIA NAJBARDZIEJ OPTYMALNEJ SCIEZKI POMIEDZY STOLIKAMI
         */
        Population population = new Population(50, true);
        System.out.println("Initial distance: " + population.getFittest().getDistance());

        population = GeneticAlgorithm.evolvePopulation(population);
        for (int i = 0; i < 10; i++) {
            population = GeneticAlgorithm.evolvePopulation(population);
        }

        System.out.println("Finished");
        System.out.println("Final distance: " + population.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(population.getFittest());

        Tour tour = population.getFittest();
        Table table;
        List<Table> fromGeneticAlgorithm = new ArrayList<>();
        System.out.println("Najlepsza trasa dla stolow bez uwzglednienia pozycji kelnera:");
        for (int i = 0; i < tour.tourSize(); i++) {
            table = tour.getTable(i);
            fromGeneticAlgorithm.add(tour.getTable(i));
            System.out.print(" => " + table.getTableNumber());
        }
        System.out.println();
        int waiterXPos = mapPanel.getWaiterXpos();
        int waiterYPos = mapPanel.getWaiterYpos();

        System.out.println("Najlepsza trasa dla stolow wraz z uwzglednieniem pozycji kelnera:");
        List<Table> optimizedRouteForWaiter = getStartingPoint(fromGeneticAlgorithm, waiterXPos, waiterYPos);
        for(Table table1 : optimizedRouteForWaiter){
            System.out.print(" => " + table1.getTableNumber());
        }


    }

    public List<Table> getStartingPoint(List<Table> fromGeneticAlgorithm, int waiterXPos, int waiterYPos) {
        Table closest = null;
        List<Table> bestRoute = new ArrayList<>();
        double min = Integer.MAX_VALUE;

        //szukamy najblizszego stolika z listy stolow ktora zwrocil algorytm genetyczny
        //najblizszy bedzie punktem poczatkowym kelnera
        for (Table temp : fromGeneticAlgorithm) {
            int xDistance = Math.abs(waiterXPos - temp.getWaiterDockXPos());
            int yDistance = Math.abs(waiterYPos - temp.getWaiterDockYPos());
            double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
            if (distance < min) {
                min = distance;
                closest = temp;
            }
        }
        System.out.println("Stolik bedacy najblizej kelnera: " + closest.getTableNumber());
        for (int i = fromGeneticAlgorithm.indexOf(closest); i < fromGeneticAlgorithm.size(); i++) {
            bestRoute.add(fromGeneticAlgorithm.get(i));
        }
        for (int j = 0; j < fromGeneticAlgorithm.indexOf(closest); j++) {
            bestRoute.add(fromGeneticAlgorithm.get(j));
        }

        return bestRoute;
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
