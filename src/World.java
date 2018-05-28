import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;

public class World extends JFrame
{
    //tablica przechowujace informacje o danej kratke
    int[][] world = new int[60][40];
    ArrayList<Integer> lockedX = new ArrayList<>();
    ArrayList<Integer> lockedY = new ArrayList<>();

    ArrayList<Integer> przebyteX = new ArrayList<>();
    ArrayList<Integer> przebyteY = new ArrayList<>();

    List<Integer> xSorted;
    List<Integer> ySorted;

    // .png variables
    private BufferedImage kelner;
    private BufferedImage kitchen;
    private BufferedImage table;

    int screenHeight;
    int screenWidth;

    JPanel leftPanel, northPanel;

    static private int ILOSC_RZECZY = 15;

    Positions poz = new Positions();
    Node initialNode;
    Node finalNode;
    int rows = 60;
    int cols = 40;
    List<Node> path;
    Node node;

    AStar aStar;


    public World() throws InterruptedException
    {
        super("SZI");
        
        try {
            kelner = ImageIO.read(new File("resources/waiter.png"));
            kitchen = ImageIO.read(new File("resources/kitchen.png"));
            table = ImageIO.read(new File("resources/table.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentPane(new Init());
        calculateScreenSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        //setSize(1200, 1000);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setVisible(true);
        setResizable(false);

        generateWorld();

        Thread.sleep(1500);
        go(0, 39);
    }

    public void go(int x, int y) throws InterruptedException {


        ArrayList<Integer> list = new ArrayList<>(zwrocNajblizszaPrzeszkode(x,y));
        initialNode = new Node(x, y);

            finalNode = new Node(list.get(0), list.get(1));
            aStar = new AStar(rows, cols, initialNode, finalNode);

            path = aStar.findPath();

            for (int i=0; i<path.size(); i++)
            {
                node = path.get(i);
                Thread.sleep(100);
                repaint();

                System.out.println("X na: " + node.getRow() + " Y na: " + node.getCol());
            }

            lockedX.remove(new Integer(node.getRow()));
            lockedY.remove(new Integer(node.getCol()));

            poz.setZablokowaneX(lockedX);
            poz.setZablokowaneY(lockedY);

            go(node.getRow(), node.getCol());

    }


    public void generateWorld()
    {
        for (int i = 0; i < 60; i++) 
        {
            for (int j = 0; j < 40; j++) 
            {
                world[i][j] = 0;
            }
        }

        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();

        world[10][5] = 1;
        world[11][15] = 1;
        world[12][25] = 1;
        world[13][35] = 1;
        world[30][6] = 1;
        world[31][16] = 1;
        world[32][26] = 1;

        lockedX.add(10);
        lockedX.add(11);
        lockedX.add(12);
        lockedX.add(13);
        lockedX.add(30);
        lockedX.add(31);
        lockedX.add(32);
        lockedY.add(5);
        lockedY.add(15);
        lockedY.add(25);
        lockedY.add(35);
        lockedY.add(6);
        lockedY.add(16);
        lockedY.add(26);

        x.add(10);
        x.add(11);
        x.add(12);
        x.add(13);
        x.add(30);
        x.add(31);
        x.add(32);
        y.add(5);
        y.add(15);
        y.add(25);
        y.add(35);
        y.add(6);
        y.add(16);
        y.add(26);

        /*for (int i = 0; i < ILOSC_RZECZY; i++)
        {
            int randomX = ThreadLocalRandom.current().nextInt(2, 54 + 1);
            int randomY = ThreadLocalRandom.current().nextInt(2, 34 + 1);
       
            if (!x.contains(randomX) && !y.contains(randomY))
            {
                x.add(randomX);
                y.add(randomY);

                int random = ThreadLocalRandom.current().nextInt(0, 1 + 1);

                world[randomX][randomY] = random;
                System.out.println("X: " + randomX + " Y: " + randomY + " -" + " RAN: " + world[randomX][randomY]);

                if (world[randomX][randomY] != 0)
                {
                        lockedX.add(randomX);
                        lockedY.add(randomY);
                }

            } else {
                continue;
            }
        }*/

        poz.setZablokowaneX(lockedX);
        poz.setZablokowaneY(lockedY);

        for (int i = 0; i < 60; i++)
        {
            for (int j = 0; j < 40; j++)
            {
                System.out.println(i + " " + " " + j + " " + world[i][j]);
            }
        }
    }

     class Init extends JPanel
     {
        Way way = new Way();


        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect( 0,0,1200, 1000);



            g.setFont(new Font("Courier New", Font.BOLD, 22));
            g.drawString("KUCHNIA", 15, 50);
            g.drawImage(kitchen, 15, 50, this);
            g.setColor(Color.ORANGE);

            for (int i=0; i<przebyteX.size(); i++)
            {
                g.fillRect(przebyteX.get(i)*20,przebyteY.get(i)*20,20,20);
            }

            for (int i=0; i<60; i++)
            {
                for (int j=0; j<40; j++)
                {
                    //drawing kitchen fields
                    if(i < 20 && j < 5)
                    {
                        g.setColor(Color.RED);
                        g.fillRect(i*20,j*20,20,20);
                    }

                    //drawing bar fields
                    if(i >= 20 && i < 40 && j < 5)
                    {
                        g.setColor(Color.CYAN);
                        g.fillRect(i*20,j*20,20,20);
                    }

                    if (world[i][j] == 1 )
                    {
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(i*20,j*20,20,20);
                        g.drawImage(table, i*20, j*20, this);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Courier New", Font.BOLD, 10));
                        g.drawString((i+1) + "," + (j+1), i*20  ,j*20);
                        g.setFont(new Font("Courier New", Font.BOLD, 22));

                    }
                }
            }

            way.draw((Graphics2D) g);
            g.setColor(Color.BLACK);

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(node.getRow()*20,node.getCol()*20,20,20);
            g.setColor(Color.BLACK);
            g.drawImage(kelner, node.getRow()*20, node.getCol()*20, this);
            g.setFont(new Font("Courier New", Font.BOLD, 10));
            g.drawString((node.getRow()+1) + "," + (node.getCol()+1), node.getRow()*20  ,node.getCol()*20);

            przebyteX.add(node.getRow());
            przebyteY.add(node.getCol());

            g.setColor(Color.GREEN);
            g.fillRect(0*20,39*20,20,20);
            g.fillRect(5*20,3*20,20,20);
        }
    }

    public ArrayList<Integer> zwrocNajblizszaPrzeszkode(int waiterX, int waiterY)
    {
        ArrayList<Integer> zwracanaLista = new ArrayList<>();
        zwracanaLista.clear();

        xSorted = new ArrayList<>(poz.getZablokowaneX());
        ySorted = new ArrayList<>(poz.getZablokowaneY());

        ArrayList<Integer> odleglosciElementow = new ArrayList<>();
        for (int i=0; i<lockedX.size(); i++)
        {

                float odleglosc = (float) Math.sqrt(
                        Math.pow(waiterX - lockedX.get(i), 2) +
                                Math.pow(waiterY - lockedY.get(i), 2));
                odleglosciElementow.add((int) odleglosc);

        }

        if(!odleglosciElementow.isEmpty())
        {
            int minIndex = odleglosciElementow.indexOf(Collections.min(odleglosciElementow));

            int najblizszyOdleglosc = odleglosciElementow.get(minIndex);
            int pozycjaXNajblizszy = xSorted.get(minIndex);
            int pozycjaYNajblizszy = ySorted.get(minIndex);

            System.out.println("X: " + pozycjaXNajblizszy + " | Y: " + pozycjaYNajblizszy + " | E: " + najblizszyOdleglosc);

            zwracanaLista.add(0, pozycjaXNajblizszy);
            zwracanaLista.add(1, pozycjaYNajblizszy);
            zwracanaLista.add(2, najblizszyOdleglosc);
        }

        return zwracanaLista;
    }

    public final class Positions
    {
        public ArrayList<Integer> listX = new ArrayList<>();
        public ArrayList<Integer> listY = new ArrayList<>();

        public ArrayList<Integer> getZablokowaneX() {
            return listX;
        }

        public void setZablokowaneX(ArrayList<Integer> list) {
            this.listX = list;
        }

        public ArrayList<Integer> getZablokowaneY() {
            return listY;
        }

        public void setZablokowaneY(ArrayList<Integer> list) {
            this.listY = list;
        }
    }

    public void calculateScreenSize() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = screen.height;
        screenWidth = screen.width;

        if (screenHeight / 9 == screenWidth / 16) {
            System.out.println("Proporcje sie zgadzaja");
            setSize(screenWidth, screenHeight);
        }
        else {
            int p = screenWidth / 16;
            if ( p*9 <= screenHeight )
            {
                System.out.println("Proporcje sie nie zgadzaja, ale jest ok");
                screenHeight = p * 9;
            }
            else {
                System.out.println("Rozdzielczosc nieprawidlowa!");
            }

        }
    }
}