package com.company.LogicLayer.Threads;

import com.company.LogicLayer.Enums.OrderStateEnum;
import com.company.LogicLayer.Table;
import com.company.ViewLayer.MainFrame;
import com.company.ViewLayer.MapPanel;

import java.io.IOException;

public class Cook implements Runnable
{
    private Table table;
    private MapPanel mapPanel;

    public Cook(Table table)
    {
        this.table = table;
    }

    @Override
    public void run()
    {
        MainFrame mainFrame = null;

        try {
            mainFrame = MainFrame.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mapPanel = mainFrame.getMapPanel();

        if(table.getTableNumber().equals(mapPanel.getSortedTables().get(0).getTableNumber()))
        {
            try
            {
                Thread.sleep(table.getOrderTime() * 1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println("Skończono zamówienie stolika nr " + (table.getTableNumber()+1));
            table.setOrderState(OrderStateEnum.ON_TABLE);

            //TODO RYSOWANIE
            try {
                mapPanel.paintOrder(table);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapPanel.repaint();
            mapPanel.revalidate();

        }
        else
        {
            int tableIndexInSortedList = getTableIndexInSortedList();
            boolean previousOrderTaken = false;

            while(!previousOrderTaken)
            {
                try
                {
                    Thread.sleep(400);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                if(mapPanel.getSortedTables().get(tableIndexInSortedList-1).getOrderState().equals(OrderStateEnum.TAKEN))
                {
                    previousOrderTaken = true;
                }
            }

            //TODO
        }
    }

    private int getTableIndexInSortedList()
    {
        for (int i = 0; i < mapPanel.getSortedTables().size(); i++)
        {
            if(mapPanel.getSortedTables().get(i).getTableNumber().equals(table.getTableNumber()))
            {
                return i;
            }
        }

        return -1;
    }
}
