package com.company.LogicLayer;

import com.company.LogicLayer.Enums.OrderStateEnum;
import com.company.LogicLayer.Threads.Cook;
import com.company.ViewLayer.MainFrame;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Kitchen implements Runnable
{
    private static ExecutorService threads = Executors.newCachedThreadPool();
    private List<Table> tables;

    public Kitchen(List<Table> tables)
    {
        this.tables = tables;
    }

    @Override
    public void run()
    {
        MainFrame mainFrame = null;

        try
        {
            mainFrame = MainFrame.getInstance();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        tables.sort(Comparator.comparingInt(Table::getOrderTime));
        mainFrame.getMapPanel().setSortedTables(tables);

        for (Table table : tables)
        {
            table.setOrderState(OrderStateEnum.NOT_READY);
            int numberOfMeals = table.getOrder().size();

            threads.execute(new Cook(table));
        }
    }
}
