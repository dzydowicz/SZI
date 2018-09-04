package com.company.LogicLayer;

import com.company.LogicLayer.Enums.OrderStateEnum;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Table {

    protected Integer table_number;
    private int xPos;
    private int yPos;


    private int waiterDockXPos;
    private int waiterDockYPos;
    private int status;
    private int numberOfPeople;
    private ArrayList<BufferedImage> avatars = new ArrayList<>();
    private ArrayList<BufferedImage> choosenAvatars = new ArrayList<>();
    private List<Meal> order;
    private int orderTime;
    private OrderStateEnum orderState;

    public Table(Integer table_number, int xPos, int yPos, int numberOfPeople, int status) throws IOException {

        avatars.add(ImageIO.read(new File("resources/avatars/avatar1.png")));
        avatars.add(ImageIO.read(new File("resources/avatars/avatar2.png")));
        avatars.add(ImageIO.read(new File("resources/avatars/avatar3.png")));
        avatars.add(ImageIO.read(new File("resources/avatars/avatar4.png")));
        avatars.add(ImageIO.read(new File("resources/avatars/avatar5.png")));
        avatars.add(ImageIO.read(new File("resources/avatars/avatar6.png")));
        avatars.add(ImageIO.read(new File("resources/avatars/avatar7.png")));
        avatars.add(ImageIO.read(new File("resources/avatars/avatar8.png")));
        avatars.add(ImageIO.read(new File("resources/avatars/avatar9.png")));
        avatars.add(ImageIO.read(new File("resources/avatars/avatar10.png")));

        this.table_number = table_number;
        this.xPos = xPos;
        this.yPos = yPos;
        this.numberOfPeople = numberOfPeople;
        this.status = status;

        this.waiterDockXPos = xPos + 70;
        this.waiterDockYPos = yPos - 20;

        for (int i = 0; i < numberOfPeople; i++) {
            choosenAvatars.add(avatars.get(ThreadLocalRandom.current().nextInt(avatars.size())));
        }
    }

    public Integer getTableNumber() {
        return table_number;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double distanceTo(Table table) {
        int xDistance = Math.abs(getWaiterDockXPos() - table.getWaiterDockXPos());
        int yDistance = Math.abs(getWaiterDockYPos() - table.getWaiterDockYPos());
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

    public ArrayList<BufferedImage> getAvatars() {
        return choosenAvatars;
    }

    @Override
    public String toString() {
        return getX() + ", " + getY();
    }

    public int getStatus() {
        return status;
    }

    public void setOrder(List<Meal> meals) {
        meals.sort(Comparator.comparingInt(Meal::getTime).reversed());

        this.order = meals;
        this.orderTime = meals.iterator().next().getTime();
    }

    public List<Meal> getOrder()
    {
        return order;
    }

    public int getWaiterDockXPos() {
        return waiterDockXPos;
    }

    public int getWaiterDockYPos() {
        return waiterDockYPos;
    }

    public int getOrderTime()
    {
        return orderTime;
    }

    public OrderStateEnum getOrderState()
    {
        return orderState;
    }

    public void setOrderState(OrderStateEnum orderState)
    {
        this.orderState = orderState;
    }
}
