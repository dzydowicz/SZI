package com.company.LogicLayer.AStar;

import java.util.PriorityQueue;

public class AStar {
//    public static final int DIAGONAL_COST = 14;
//    public static final int V_H_COST = 10;
//
//
//
//    //Blocked Nodes are just null LogicLayer.Node values in grid
//    static Node [][] grid = new Node[5][5];
//
//    static PriorityQueue<Node> open;
//
//    static boolean closed[][];
//    static int startI, startJ;
//    static int endI, endJ;
//
//    public static void setBlocked(int i, int j){
//        grid[i][j] = null;
//    }
//
//    public static void setStartNode(int i, int j){
//        startI = i;
//        startJ = j;
//    }
//
//    public static void setEndNode(int i, int j){
//        endI = i;
//        endJ = j;
//    }
//
//    static void checkAndUpdateCost(Node current, Node t, int cost){
//        if(t == null || closed[t.i][t.j])return;
//        int t_final_cost = t.heuristicCost+cost;
//
//        boolean inOpen = open.contains(t);
//        if(!inOpen || t_final_cost<t.finalCost){
//            t.finalCost = t_final_cost;
//            t.parent = current;
//            if(!inOpen)open.add(t);
//        }
//    }
//
//    public static void AStar(){
//
//        //add the start location to open list.
//        open.add(grid[startI][startJ]);
//
//        Node current;
//
//        while(true){
//            current = open.poll();
//            if(current==null)break;
//            closed[current.i][current.j]=true;
//
//            if(current.equals(grid[endI][endJ])){
//                return;
//            }
//
//            Node t;
//            if(current.i-1>=0){
//                t = grid[current.i-1][current.j];
//                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
//
//                if(current.j-1>=0){
//                    t = grid[current.i-1][current.j-1];
//                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
//                }
//
//                if(current.j+1<grid[0].length){
//                    t = grid[current.i-1][current.j+1];
//                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
//                }
//            }
//
//            if(current.j-1>=0){
//                t = grid[current.i][current.j-1];
//                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
//            }
//
//            if(current.j+1<grid[0].length){
//                t = grid[current.i][current.j+1];
//                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
//            }
//
//            if(current.i+1<grid.length){
//                t = grid[current.i+1][current.j];
//                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
//
//                if(current.j-1>=0){
//                    t = grid[current.i+1][current.j-1];
//                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
//                }
//
//                if(current.j+1<grid[0].length){
//                    t = grid[current.i+1][current.j+1];
//                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
//                }
//            }
//        }
//    }
//
//    /*
//    Params :
//    tCase = test case No.
//    x, y = Board's dimensions
//    si, sj = start location's x and y coordinates
//    ei, ej = end location's x and y coordinates
//    int[][] blocked = array containing inaccessible LogicLayer.Node coordinates
//    */
//
//    public NodeCollection test( int x, int y, int si, int sj, int ei, int ej, int[][] blocked)
//    {
//        //Reset
//        NodeCollection nodeCollection = new NodeCollection();
//
//        grid = new Node[x][y];
//        closed = new boolean[x][y];
//        open = new PriorityQueue<>((Object o1, Object o2) ->
//        {
//            Node c1 = (Node)o1;
//            Node c2 = (Node)o2;
//            if(c1.finalCost < c2.finalCost )
//            {
//                return -1;
//            }
//            else
//            {
//                if(c1.finalCost > c2.finalCost){
//                    return 1;
//                }
//                else{
//                    return 0;
//                }
//            }
//        });
//        //Set start position
//        setStartNode(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part
//
//        //Set End Location
//        setEndNode(ei, ej);
//
//        for(int i=0;i<x;++i)
//        {
//            for(int j=0;j<y;++j)
//            {
//                grid[i][j] = new Node(i, j);
//                grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
////                  System.out.print(grid[i][j].heuristicCost+" ");
//            }
////              System.out.println();
//        }
//        grid[si][sj].finalCost = 0;
//
//           /*
//             Set blocked Nodes. Simply set the LogicLayer.Node values to null
//             for blocked Nodes.
//           */
//        for(int i=0;i<blocked.length;++i)
//        {
//            setBlocked(blocked[i][0], blocked[i][1]);
//        }
//
//        //Display initial map
//        System.out.println("Grid: ");
//        for(int i=0;i<x;++i)
//        {
//            for(int j=0;j<y;++j)
//            {
//                if(i==si&&j==sj)System.out.print("SO  "); //Source
//                else if(i==ei && j==ej)System.out.print("DE  ");  //Destination
//                else if(grid[i][j]!=null)System.out.printf("%-3d ", 0);
//                else System.out.print("BL  ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//
//        //AStar init
//        AStar();
//
//        System.out.println("\nScores for Nodes: ");
//        for(int i=0;i<x;++i){
//            for(int j=0;j<y;++j){
//                if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
//                else System.out.print("BL  ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//
//        if(closed[endI][endJ]){
//            System.out.println("Path: ");
//            Node current = grid[endI][endJ];
//            int i=0;
//            Node[] nodesArray = new Node[30];
//            while(current.parent!=null){
//                nodeCollection.addNewNode(current);
//                System.out.print("[" +current.getI() + ", "+current.getJ()+"]");
//                //tablica nodes zeby potem odwrocic kolejnosc przy wrzucaniu do nodeCollection
//                nodesArray[i] = current;
//                System.out.print(" => ");
//                current = current.parent;
//                i++;
//
//            }
//            System.out.println("End of path");
//
////
////            //odwracanie kolejnosci nodes przy dodawania do nodeCollection
////            for(i=nodesArray.length-1; i>=0;i--)
////            {
////                nodeCollection.addNewNode(nodesArray[i]);
////            }
//
//        }else System.out.println("No possible path");
//
//        return nodeCollection;
//    }
//
//    public static void main(String[] args) throws Exception{
////        test(1, 5, 5, 0, 0, 3, 2, new int[][]{{0,4},{2,2},{3,1},{3,3}});
////        test(3, 7, 7, 2, 1, 5, 4, new int[][]{{4,1},{4,3},{5,3},{2,3}});
////        test(1, 5, 5, 0, 0, 4, 4, new int[][]{{3,4},{3,3},{4,3}});
//    }
}