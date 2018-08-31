package com.company.LogicLayer.AStar;

public class LockedArea
{
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public LockedArea(int startX, int startY, int endX, int endY)
    {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getStartX()
    {
        return startX;
    }

    public int getStartY()
    {
        return startY;
    }

    public int getEndX()
    {
        return endX;
    }

    public int getEndY()
    {
        return endY;
    }
}
