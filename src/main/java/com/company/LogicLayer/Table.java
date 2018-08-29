package com.company.LogicLayer;

public class Table {

    protected Integer table_number;
    private int xPos;
    private int yPos;
    private int status;
    private int numberOfPeople;

    public Table(Integer table_number, int xPos, int yPos, int numberOfPeople, int status){
        this.table_number = table_number;
        this.xPos = xPos;
        this.yPos = yPos;
        this.numberOfPeople = numberOfPeople;
        this.status = status;
    }

    public Integer getTableNumber() { return table_number;}

    public int getX() {return xPos;}

    public int getY() { return yPos; }

    public int getNumberOfPeople() { return numberOfPeople; }

    public double distanceTo(Table table) {
        int xDistance = Math.abs(getX() - table.getX());
        int yDistance = Math.abs(getY() - table.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );

        return distance;
    }

    @Override
    public String toString(){
        return getX()+", "+getY();
    }
}
