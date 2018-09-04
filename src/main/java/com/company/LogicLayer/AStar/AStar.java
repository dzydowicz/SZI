package com.company.LogicLayer.AStar;

import com.company.LogicLayer.Coordinates;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    private static int DEFAULT_HV_COST = 10; // Horizontal - Vertical Cost
    private static int DEFAULT_DIAGONAL_COST = 14;
    private static final int ROWS = 1024;
    private static final int COLS = 768;

    private int hvCost;
    private int diagonalCost;
    private Node[][] searchArea = new Node[ROWS][COLS];
    private PriorityQueue<Node> openList;
    private List<Node> closedList;
    private Node initialNode;
    private Node finalNode;

    public AStar(Node initialNode, Node finalNode, int hvCost, int diagonalCost, List<LockedArea> lockedAreas) {
        this.hvCost = hvCost;
        this.diagonalCost = diagonalCost;
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.openList = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                return node0.getF() < node1.getF() ? -1 : node0.getF() > node1.getF() ? 1 : 0;
            }
        });
        setNodes();
        setLockedAreas(lockedAreas);
        this.closedList = new ArrayList<Node>();
    }

    public AStar(Node initialNode, Node finalNode, List<LockedArea> lockedAreas) {
        this(initialNode, finalNode, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST, lockedAreas);
    }

    public static List<Node> findPathForWaiter(Coordinates start, Coordinates end, List<LockedArea> lockedAreas)
    {
        return findPathForWaiter(start.getX(), start.getY(), end.getX(), end.getY(), lockedAreas);
    }

    public static List<Node> findPathForWaiter(int startX, int startY, int endX, int endY, List<LockedArea> lockedAreas)
    {
        Node initialNode = new Node(startX, startY);
        Node finalNode = new Node(endX, endY);

        AStar aStar = new AStar(initialNode, finalNode, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST, lockedAreas);

        return aStar.findPath();
    }

    private void setLockedAreas(List<LockedArea> lockedAreas)
    {
        for (LockedArea lockedArea : lockedAreas)
        {
            for(int i = lockedArea.getStartX(); i <= lockedArea.getEndX(); i++)
            {
                for(int j = lockedArea.getStartY(); j <= lockedArea.getEndY(); j++)
                {
                    try
                    {
                        setBlock(i, j);
                    }
                    catch(NullPointerException | ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println("x=" + i + ", y=" + j);
                    }
                }
            }
        }
    }
    
    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(i, j);
                node.calculateHeuristic(getFinalNode());
                this.searchArea[i][j] = node;
            }
        }
    }

//    public void setBlocks(int[][] blocksArray) {
//        for (int i = 0; i < blocksArray.length; i++) {
//            int row = blocksArray[i][0];
//            int col = blocksArray[i][1];
//            setBlock(row, col);
//        }
//    }

    public List<Node> findPath() {
        openList.add(initialNode);
        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            closedList.add(currentNode);
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<Node>();
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<Node>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addAdjacentNodes(Node currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Node currentNode) {
        int row = currentNode.getXPos();
        int col = currentNode.getYPos();
        int lowerRow = row + 10;
        if (lowerRow < getSearchArea().length) {
            if (col - 10 >= 0) {
                checkNode(currentNode, col - 10, lowerRow, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            }
            if (col + 10 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 10, lowerRow, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            }
            checkNode(currentNode, col, lowerRow, getHvCost());
        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int row = currentNode.getXPos();
        int col = currentNode.getYPos();
        int middleRow = row;
        if (col - 10 >= 0) {
            checkNode(currentNode, col - 10, middleRow, getHvCost());
        }
        if (col + 10 < getSearchArea()[0].length) {
            checkNode(currentNode, col + 10, middleRow, getHvCost());
        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int row = currentNode.getXPos();
        int col = currentNode.getYPos();
        int upperRow = row - 10;
        if (upperRow >= 0) {
            if (col - 10 >= 0) {
                checkNode(currentNode, col - 10, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            }
            if (col + 10 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 10, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            }
            checkNode(currentNode, col, upperRow, getHvCost());
        }
    }

    private void checkNode(Node currentNode, int col, int row, int cost) {
        Node adjacentNode = getSearchArea()[row][col];
        if (!adjacentNode.isBlock() && !getClosedList().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    private void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }

    public Node[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public List<Node> getClosedList() {
        return closedList;
    }

    public void setClosedList(List<Node> closedList) {
        this.closedList = closedList;
    }

    public int getHvCost() {
        return hvCost;
    }

    public void setHvCost(int hvCost) {
        this.hvCost = hvCost;
    }

    private int getDiagonalCost() {
        return diagonalCost;
    }

    private void setDiagonalCost(int diagonalCost) {
        this.diagonalCost = diagonalCost;
    }
}
