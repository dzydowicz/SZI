package com.company.LogicLayer.GeneticAlgorithm;

import com.company.LogicLayer.Table;

import java.util.ArrayList;
import java.util.Collections;

public class Tour{

    // Holds our tour of tables
    private ArrayList tour = new ArrayList<Table>();
    // Cache
    private double fitness = 0;
    private int distance = 0;

    // Constructs a blank tour
    public Tour(){
        for (int i = 0; i < TourManager.numberOfTables(); i++) {
            tour.add(null);
        }
    }

    public Tour(ArrayList tour){
        this.tour = tour;
    }

    // Creates a random individual
    public void generateIndividual() {
        // Loop through all our destination tables and add them to our tour
        for (int tableIndex = 0; tableIndex < TourManager.numberOfTables(); tableIndex++) {
            setTable(tableIndex, TourManager.getTable(tableIndex));
        }
        // Randomly reorder the tour
        Collections.shuffle(tour);
    }

    // Gets a table from the tour
    public Table getTable(int tourPosition) {
        return (Table)tour.get(tourPosition);
    }

    // Sets a table in a certain position within a tour
    public void setTable(int tourPosition, Table table) {
        tour.set(tourPosition, table);
        // If the tours been altered we need to reset the fitness and distance
        fitness = 0;
        distance = 0;
    }

    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/(double)getDistance();
        }
        return fitness;
    }

    // Gets the total distance of the tour
    public int getDistance(){
        if (distance == 0) {
            int tourDistance = 0;
            // Loop through our tour's tables
            for (int tableIndex=0; tableIndex < tourSize(); tableIndex++) {
                // Get table we're travelling from
                Table fromTable = getTable(tableIndex);
                // Table we're travelling to
                Table destinationTable;
                // Check we're not on our tour's last table, if we are set our 
                // tour's final destination table to our starting table
                if(tableIndex+1 < tourSize()){
                    destinationTable = getTable(tableIndex+1);
                }
                else{
                    destinationTable = getTable(0);
                }
                // Get the distance between the two tables
                tourDistance += fromTable.distanceTo(destinationTable);
            }
            distance = tourDistance;
        }
        return distance;
    }

    // Get number of tables on our tour
    public int tourSize() {
        return tour.size();
    }

    // Check if the tour contains a table
    public boolean containsTable(Table table){
        return tour.contains(table);
    }

    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getTable(i)+"|";
        }
        return geneString;
    }
}