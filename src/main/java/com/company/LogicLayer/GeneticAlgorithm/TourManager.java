package com.company.LogicLayer.GeneticAlgorithm;

import com.company.LogicLayer.Table;
import java.util.ArrayList;

public class TourManager {

    // Holds our tables
    private static ArrayList destinationTables = new ArrayList<Table>();

    // Adds a destination table
    public static void addTable(Table table) {
        destinationTables.add(table);
    }

    // Get a table
    public static Table getTable(int index) {
        return (Table) destinationTables.get(index);
    }

    // Get the number of destination tables
    public static int numberOfTables() {
        return destinationTables.size();
    }
}