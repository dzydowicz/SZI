package com.company.LogicLayer;

import com.company.LogicLayer.AStar.AStar;
import com.company.LogicLayer.AStar.Node;
import com.company.LogicLayer.Enums.OrderStateEnum;
import com.company.LogicLayer.GeneticAlgorithm.GeneticAlgorithm;
import com.company.LogicLayer.GeneticAlgorithm.Population;
import com.company.LogicLayer.GeneticAlgorithm.Tour;
import com.company.LogicLayer.GeneticAlgorithm.TourManager;
import com.company.TensorFlow.LabelImage;
import com.company.ViewLayer.MainFrame;
import com.company.ViewLayer.MapPanel;
import com.company.ViewLayer.OrdersPanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Waiter implements Runnable {

    private static ExecutorService threads = Executors.newCachedThreadPool();
    private final Logger log = Logger.getLogger(this.getClass().getName());

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
        //List<Node> nodes = AStar.findPathForWaiter(100, 100, 150, 150, lockedAreas);


        mapPanel.repaint();
        mapPanel.revalidate();

        List<Table> activeTableList = new ArrayList<>();

        while (notAllReady) {
            try {
                sleep(500);

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

        /**
         * Tu jest moment w którym kelner ma już trase do stolików aby zebrać od nich zamówienia,
         * potrzebujemy astarem wyznaczyć droge pokolei do kazdego stolu z poprzedniego
         */

        for (Table tempTable : optimizedRouteForWaiter)
        {
            List<Node> routeToTable = AStar.findPathForWaiter(mapPanel.getWaiterXpos(), mapPanel.getWaiterYpos(),
                    tempTable.getWaiterDockXPos(), tempTable.getWaiterDockYPos(), mapPanel.getLockedAreas());
            goToXYWithAStar(mapPanel, routeToTable);

            for (Meal meal : tempTable.getOrder())
            {
                try
                {
                    OrdersPanel.getInstance().addNewInPreparationOrder(meal.getName(), String.valueOf(tempTable.getTableNumber()));
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }

            try
            {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tempTable.setStatus(2);

            mapPanel.repaint();
            mapPanel.revalidate();
        }

        goToXYWithAStar(mapPanel, AStar.findPathForWaiter(mapPanel.getWaiterXpos(), mapPanel.getWaiterYpos(), 580, 150, mapPanel.getLockedAreas()));

        threads.execute(new Kitchen(optimizedRouteForWaiter));

        deliverMeals(mapPanel);
    }

    private void deliverMeals(MapPanel mapPanel)
    {
        List<Table> sortedTables = null;

        do
        {
            try
            {
                Thread.sleep(400);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            sortedTables = mapPanel.getSortedTables();
        } while (sortedTables == null);

        do {
            try
            {
                Thread.sleep(400);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }while(!sortedTables.get(0).getOrderState().equals(OrderStateEnum.ON_TABLE));

        goToXYWithAStar(mapPanel, AStar.findPathForWaiter(mapPanel.getWaiterXpos(), mapPanel.getWaiterYpos(), 890, 160, mapPanel.getLockedAreas()));

        for (Table sortedTable : sortedTables)
        {
            if (sortedTable.getOrderState().equals(OrderStateEnum.ON_TABLE))
            {
                String tensorDigitLabel = LabelImage.labelDigit("resources/handwritten_numbers/"
                        + mapPanel.getTableNumberTodraw() + "/" + mapPanel.getTableNumberTodraw() + " (" + mapPanel.getRandomImageNumber() + ").jpg");

                if(!tensorDigitLabel.equalsIgnoreCase(sortedTable.getTableNumber().toString()))
                {
                    log.severe("Tensor recognized digit image as " + tensorDigitLabel + ", but should be " + sortedTable.getTableNumber());
                }
                else
                {
                    System.out.println("Recognized digit image correctly. Value: " + tensorDigitLabel);
                }

                if (mapPanel.isFoodPos1())
                {
                    String tensorLabel = LabelImage.labelFood(mapPanel.getPathFood1ToTensor());

                    if (!tensorLabel.equalsIgnoreCase(sortedTable.getOrder().get(0).getName()))
                    {
                        log.severe("Tensor recognized image as " + tensorLabel + ", but should be " + sortedTable.getOrder().get(0).getName());
                    }
                    else
                    {
                        System.out.println("Recognized " + tensorLabel + " correctly.");
                    }
                }

                if (mapPanel.isFoodPos2())
                {
                    String tensorLabel = LabelImage.labelFood(mapPanel.getPathFood2ToTensor());

                    if (!tensorLabel.equalsIgnoreCase(sortedTable.getOrder().get(1).getName()))
                    {
                        log.severe("Tensor recognized image as " + tensorLabel + ", but should be " + sortedTable.getOrder().get(0).getName());
                    }
                    else
                    {
                        System.out.println("Recognized " + tensorLabel + " correctly.");
                    }
                }

                if (mapPanel.isFoodPos3())
                {
                    String tensorLabel = LabelImage.labelFood(mapPanel.getPathFood3ToTensor());

                    if (!tensorLabel.equalsIgnoreCase(sortedTable.getOrder().get(2).getName()))
                    {
                        log.severe("Tensor recognized image as " + tensorLabel + ", but should be " + sortedTable.getOrder().get(0).getName());
                    }
                    else
                    {
                        System.out.println("Recognized " + tensorLabel + " correctly.");
                    }
                }

                if (mapPanel.isFoodPos4())
                {
                    String tensorLabel = LabelImage.labelFood(mapPanel.getPathFood4ToTensor());

                    if (!tensorLabel.equalsIgnoreCase(sortedTable.getOrder().get(3).getName()))
                    {
                        log.severe("Tensor recognized image as " + tensorLabel + ", but should be " + sortedTable.getOrder().get(0).getName());
                    }
                    else
                    {
                        System.out.println("Recognized " + tensorLabel + " correctly.");
                    }
                }

                mapPanel.setFoodPos1(false);
                mapPanel.setFoodPos2(false);
                mapPanel.setFoodPos3(false);
                mapPanel.setFoodPos4(false);

                mapPanel.repaint();
                mapPanel.revalidate();

                sortedTable.setOrderState(OrderStateEnum.TAKEN);

                goToXYWithAStar(mapPanel, AStar.findPathForWaiter(mapPanel.getWaiterXpos(), mapPanel.getWaiterYpos(),
                        sortedTable.getWaiterDockXPos(), sortedTable.getWaiterDockYPos(), mapPanel.getLockedAreas()));

                try
                {
                    Thread.sleep(500);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                sortedTable.setStatus(1);

                mapPanel.repaint();
                mapPanel.revalidate();

                goToXYWithAStar(mapPanel, AStar.findPathForWaiter(mapPanel.getWaiterXpos(), mapPanel.getWaiterYpos(), 890, 160, mapPanel.getLockedAreas()));
            }
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
                    sleep(100);
                    mapPanel.setWaiterXpos(i);
                    mapPanel.repaint();
                    mapPanel.revalidate();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goToXYWithAStar(MapPanel mapPanel, List<Node> path)
    {
        try
        {
            for(Node node : path)
            {
                sleep(20);
                mapPanel.setWaiterXpos(node.getXPos());
                mapPanel.setWaiterYpos(node.getYPos());
                mapPanel.repaint();
                mapPanel.revalidate();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
