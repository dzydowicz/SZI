package com.company.LogicLayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrdersService {
    public static OrdersService instance;
    private final List<Meal> menu = new ArrayList<>(); // menu naszej restauracji
    public static final String FILE_PATH = "menu.txt";


    private OrdersService() throws IOException
    {
        //inicjalizacja listy menu
        initList();
        instance = this;
    }

    public static OrdersService getInstance() throws IOException
    {
        if (instance == null)
        {
            return new OrdersService();
        }else
        {
            return instance;
        }
    }
    //inicjalizacja odczytu menu z pliku
    private void initList() throws IOException
    {
        FileReader fileReader = new FileReader(FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        try {
            String textLine = bufferedReader.readLine();
            do {
                addMeal(textLine);
                textLine = bufferedReader.readLine();
            } while(textLine != null);
        }
        finally{
            bufferedReader.close();
        }
    }

    //dodaje jedna linie z pliku tekstowego do interfejsu uzytkownika
    private void addMeal(String line)
    {
        String text[] = null;
        text = line.split(";");
        Meal meal;
        int x = Integer.parseInt(text[1]);
        meal = new Meal(text[0],x);
         System.out.println("nazwa posilku " + meal.getName());
        menu.add(meal);
    }


    //zwraca tablice dwuwymiarowa string zawierajaca cale menu
    public String[][] getMenuToDisplay() {
        String[][] menuList = new String[menu.size()][2];


        for (int i = 0; i < menu.size(); i++) {
            menuList[i][0] = menu.get(i).getName();
            menuList[i][1] = Integer.toString(menu.get(i).getTime()) + " s";
        }

        return menuList;
    }

}

