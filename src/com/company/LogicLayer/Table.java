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
    public void setTable_number(Integer table_number) { this.table_number = table_number; }

    public int getTableXpos() {return xPos;}

    public int getTableYpos() { return yPos; }

    public int getNumberOfPeople() { return numberOfPeople; }
}
