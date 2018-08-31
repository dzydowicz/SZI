package com.company.ViewLayer;

import com.company.LogicLayer.AStar.LockedArea;
import com.company.LogicLayer.Coordinates;
import com.company.LogicLayer.GeneticAlgorithm.GeneticAlgorithm;
import com.company.LogicLayer.GeneticAlgorithm.Population;
import com.company.LogicLayer.GeneticAlgorithm.Tour;
import com.company.LogicLayer.GeneticAlgorithm.TourManager;
import com.company.LogicLayer.Table;
import com.company.TensorFlow.LabelImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MapPanel extends JPanel {

    int screenWidth, screenHeight;
    int szerokoscPola;
    int wysokoscPola;
    public int waiterXpos;
    public int waiterYpos; //pozycja w px
    public int wineXpos;
    public int wineYpos;
    public int foodXpos;
    public int foodYpos;
    public boolean drawWineAtKitchen = false;
    public boolean drawFoodAtKitchen = false;

    public boolean waiterFood = false;
    public boolean waiterWine = false;

    BufferedImage bgImage;
    BufferedImage waiter;
    BufferedImage waiterFoodImage;
    BufferedImage waiterWineImage;

    BufferedImage kitchen;
    BufferedImage tableImage;
    BufferedImage wineImage;
    BufferedImage foodImage;

    BufferedImage avatar1, avatar2, avatar3, avatar4, avatar5, avatar6, avatar7, avatar8, avatar9, avatar10;
    BufferedImage greenBulb, yellowBulb, redBulb;
    BufferedImage one, two, three, four, five;


    public Boolean alreadyDrawed = false;

    int[][] map = new int[30][25];
    //int randomMap = new Random().nextInt(3) + 1;
    public String WINE_PATH = "resources/wine1.png";
    public String FOOD_PATH = "resources/food1.png";

    //    int numberOfTables = ThreadLocalRandom.current().nextInt(4, 7 + 1);
    int numberOfTables = 10;
    List<Table> tables;
    List<Coordinates> chairs = new ArrayList<>();
    //ArrayList<BufferedImage> avatars = new ArrayList<>();
    List<BufferedImage> numbers = new ArrayList<>();

    private List<LockedArea> lockedAreas = new ArrayList<>();

    public MapPanel(int screenWidth, int screenHeight) throws IOException {

        chairs.add(new Coordinates(20,-15));
        chairs.add(new Coordinates(90,-15));
        chairs.add(new Coordinates(20,30));
        chairs.add(new Coordinates(90,30));

        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;

        szerokoscPola = calculateHeightForIcon();
        wysokoscPola = calculateHeightForIcon();

        waiterXpos = 400;
        waiterYpos = 160;

        wineXpos = 400;
        wineYpos = 40;

        foodXpos = 520;
        foodYpos = 40;

        setPreferredSize(new Dimension(screenWidth, screenHeight));


        try {

            bgImage = ImageIO.read(new File("resources/bg.png"));
            waiter = ImageIO.read(new File("resources/alpha.png"));
            waiterFoodImage = ImageIO.read(new File("resources/waiterFood.png"));
            waiterWineImage = ImageIO.read(new File("resources/waiterWine.png"));

            kitchen = ImageIO.read(new File("resources/kitchen.png"));
            tableImage = ImageIO.read(new File("resources/table2.png"));
            wineImage = ImageIO.read(new File(WINE_PATH));
            foodImage = ImageIO.read(new File(FOOD_PATH));

            yellowBulb = ImageIO.read(new File("resources/bulb/yellow.png"));
            redBulb = ImageIO.read(new File("resources/bulb/red.png"));
            greenBulb = ImageIO.read(new File("resources/bulb/green.png"));

            numbers.add(ImageIO.read(new File("resources/numbers/one.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/two.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/three.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/four.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/five.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/six.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/seven.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/eight.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/nine.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/ten.png")));


        } catch (IOException e) {
            e.printStackTrace();
        }

        tables = calculateTables(numberOfTables);

        for (Table table : tables)
        {
            lockedAreas.add(new LockedArea(table.getX(), table.getY(), table.getX() + 140, table.getY() + 112));
        }

        Population population = new Population(50, true);
        System.out.println("Initial distance: " + population.getFittest().getDistance());

        population = GeneticAlgorithm.evolvePopulation(population);
        for (int i = 0; i < 100; i++) {
            population = GeneticAlgorithm.evolvePopulation(population);
        }

        System.out.println("Finished");
        System.out.println("Final distance: " + population.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(population.getFittest());

        Tour tour = population.getFittest();

        Table table;

        for (int i = 0; i < tour.tourSize(); i++) {
            table = tour.getTable(i);
            System.out.print(" => " + table.getTableNumber());

        }
        System.out.println();

        LabelImage labelImage = new LabelImage();
        //    String result = labelImage.labelFood("resources/food_images/pizza/one.jpg");

        //  this.setLayout(null);

        // there should be generated map number between 1 and 3
        // we have to do 2 maps more
        //map = new MapCreator().generateMapFromFile(1);


        // this.add(gameplay, BorderLayout.CENTER);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2);
        g.drawImage(bgImage, 0, 0, null);
        drawNet(g2);
        drawTables(g2);
        drawWaiter(g2);

        //Move move = null;
//
//        g.setColor(Color.WHITE);
//        g.fillRect(waiterXpos, waiterYpos, 40, 40);

//        revalidate();
//
//        if (waiterFood) {
//            g.drawImage(waiterFoodImage, waiterXpos - 40, waiterYpos - 40, this);
//        }
//        if (waiterWine) {
//            g.drawImage(waiterWineImage, waiterXpos - 40, waiterYpos - 40, this);
//        }
//        if (!waiterFood && !waiterWine) {
//            g.drawImage(waiter, waiterXpos - 40, waiterYpos - 40, this);
//        }
//        revalidate();

    }

    //TODO poprawić generwoanie stolików
    private ArrayList<Table> calculateTables(int numberOfTables) throws IOException {

        System.out.println(numberOfTables);
        ArrayList coordinatesOfTables = new ArrayList<Table>();
        int xPos = 30;
        int yPos = 360;
        for (int i = 0; i <= numberOfTables; i++) {
            int numberOfPeople = ThreadLocalRandom.current().nextInt(0, 4 + 1);

            if (xPos < 880) {
                Table table = new Table(i, xPos, /*area offset*/yPos, numberOfPeople, 1);
                coordinatesOfTables.add(table);
                TourManager.addTable(table);
                xPos += 200;
            } else {
                xPos = 30;
                yPos += 220;
                Table table = new Table(i, xPos, /*area offset*/yPos, numberOfPeople, 1);
                coordinatesOfTables.add(table);
                TourManager.addTable(table);
                xPos += 200;
            }
        }
        return coordinatesOfTables;
    }

    public int calculateHeightForIcon() {
        return screenWidth * 5 / 8 / 21;
        //szerokosc - szerokosc ekranu
        //5/8 - taka czesc pola ma ma panel mapy
        // /21, bo mamy 21 obiektow w jednym wierszu mapy
    }




    private void drawWaiter(Graphics2D g) {
        g.drawImage(waiter, waiterXpos, waiterYpos, this);

    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }

    private void drawNet(Graphics2D g) {
        g.setColor(Color.GRAY);
        for (int z = 0; z < 1200; z = z + 20) {
            g.drawLine(z, 0, z, 1200);
        }
        for (int z = 0; z < 1200; z = z + 20) {
            g.drawLine(0, z, 1200, z);
        }
    }

    private void drawTables(Graphics2D g) {
        int areaStartHeight = 300;
        int numberOfPeople;
        int tableX;
        int tableY;
        int randomAvatar;

        for (int i = 0; i < numberOfTables; i++) {
//            int amountOfAvatars = avatars.size();

            numberOfPeople = tables.get(i).getNumberOfPeople();
            tableX = tables.get(i).getX();
            tableY = tables.get(i).getY();
            g.drawImage(tableImage, tableX, tableY, this);

            /*
            rysowanie lampki statusu stolika, klasa Table ma pole int status i oznacza to:

            1 - żółta: nikogo nie ma albo oczekują na zamówienie
            2 - czerwony: wybierają zamówienie i nie są gotowi do złożenia zamówienia
            3 - zielona: są gotowi na złożenie zamówienia

            domyślnie stoliki są tworzone ze statusem równym 1
             */

            /*
            Mamy maksymalnie 4 osoby przy stoliku, losujemy tą liczbe przy tworzenu stolika:

            1 || 2
            3 || 4

            RYSOWANIE:
            OSOBA 1: tableX + 20, tableY - 15
            OSOBA 2: tableX + 90, tableY - 15
            OSOBA 3: tableX + 20, tableY + 30
            OSOBA 4: tableX + 90, tableY +30
             */
//            if (tables.get(i).getAvatars().size() > 0) {
//
//                for(int j = 0; j < tables.get(i).getAvatars().size(); j++){
//                    System.out.println(tables.get(j).getAvatars().size());
//                    BufferedImage bufferedImage = tables.get(i).getAvatars().get(j);
//                    System.out.println("to je i: "+ j);
//                    g.drawImage(tables.get(i).getAvatars().get(j), tableX+chairs.get(j).getX(), tableY+chairs.get(j).getY(), this);
//                }
//
//            }

            if(tables.get(i).getNumberOfPeople() > 0) {

                for(int j = 0 ; j < tables.get(i).getNumberOfPeople(); j++) {

                    g.drawImage(tables.get(i).getAvatars().get(j), tableX+chairs.get(j).getX(), tableY+chairs.get(j).getY(), this);
                }
                g.drawImage(yellowBulb, tableX + 55, tableY - 10, this);

            }

            g.drawImage(numbers.get(i), tableX + 75, tableY + 55, this);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Helvetica", Font.BOLD, 12));


            g.drawString("Table ID: " + tables.get(i).getTableNumber() + " | X,Y: " + (tableX) + "," + (tableY), tableX, tableY - 40);

            //todo poprawic kolory numerkow, cos wymyslic madrego na stoliki
//            g.setColor(Color.WHITE);
//            g.setFont(new Font("Helvetica", Font.BOLD, 20));
//            //g.drawString("" + (tables.get(i).getTableNumber() + 1), tableX + 70, tableY + 75);
//
//            g.setFont(new Font("Helvetica", Font.BOLD, 12));
//            g.drawString("Number of people: " + tables.get(i).getNumberOfPeople(), tableX, tableY - 20);

        }

    }

    public void setWaiterXpos(int xPos) {
        this.waiterXpos = xPos;
    }

    public void setWaiterYpos(int yPos) {
        this.waiterYpos = yPos;
    }


}
