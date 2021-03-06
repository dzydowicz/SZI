package com.company.ViewLayer;

import com.company.LogicLayer.AStar.AStar;
import com.company.LogicLayer.AStar.LockedArea;
import com.company.LogicLayer.AStar.Node;
import com.company.LogicLayer.Coordinates;
import com.company.LogicLayer.GeneticAlgorithm.GeneticAlgorithm;
import com.company.LogicLayer.GeneticAlgorithm.Population;
import com.company.LogicLayer.GeneticAlgorithm.Tour;
import com.company.LogicLayer.GeneticAlgorithm.TourManager;
import com.company.LogicLayer.Meal;
import com.company.LogicLayer.Table;
import com.company.TensorFlow.LabelImage;
import org.tensorflow.op.core.Imag;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class MapPanel extends JPanel {

    int screenWidth, screenHeight;
    int tableNumberTodraw;

    int randomImageNumber = ThreadLocalRandom.current().nextInt(1, 59 + 1);

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

    BufferedImage food1;
    BufferedImage food2;
    BufferedImage food3;
    BufferedImage food4;

    public boolean foodPos1 = false;
    public boolean foodPos2 = false;
    public boolean foodPos3 = false;
    public boolean foodPos4 = false;

    String pathFood1ToTensor;
    String pathFood2ToTensor;
    String pathFood3ToTensor;
    String pathFood4ToTensor;

    List<BufferedImage> foodPositions;

    List<BufferedImage> zeroNumber = new ArrayList<>();
    List<BufferedImage> oneNumber = new ArrayList<>();
    List<BufferedImage> twoNumber = new ArrayList<>();
    List<BufferedImage> threeNumber = new ArrayList<>();
    List<BufferedImage> fourNumber = new ArrayList<>();
    List<BufferedImage> fiveNumber = new ArrayList<>();
    List<BufferedImage> sixNumber = new ArrayList<>();
    List<BufferedImage> sevenNumber = new ArrayList<>();
    List<BufferedImage> eightNumber = new ArrayList<>();
    List<BufferedImage> nineNumber = new ArrayList<>();

    List<List<BufferedImage>> listOfNumbers = new ArrayList<>();

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

    BufferedImage foodTable;

    BufferedImage talkCloud;
    boolean waiterTalking = false;
    String waiterSay = "";


    //int randomMap = new Random().nextInt(3) + 1;
    public String WINE_PATH = "resources/wine1.png";


    //    int numberOfTables = ThreadLocalRandom.current().nextInt(4, 7 + 1);
    int numberOfTables = ThreadLocalRandom.current().nextInt(7, 9 + 1);
    List<Table> tables;
    private List<Table> sortedTables = null;
    List<Coordinates> chairs = new ArrayList<>();
    //ArrayList<BufferedImage> avatars = new ArrayList<>();
    List<BufferedImage> numbers = new ArrayList<>();

    private List<LockedArea> lockedAreas = new ArrayList<>();

    public MapPanel(int screenWidth, int screenHeight) throws IOException {

        chairs.add(new Coordinates(20, -15));
        chairs.add(new Coordinates(90, -15));
        chairs.add(new Coordinates(20, 30));
        chairs.add(new Coordinates(90, 30));

        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;

        waiterXpos = 100;
        waiterYpos = 200;

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        randomImageNumber = ThreadLocalRandom.current().nextInt(1, 59 + 1);

        talkCloud = ImageIO.read(new File("resources/talkCloud.png"));
        try {

            for (int x = 1; x <= 60; x++) {
                zeroNumber.add(ImageIO.read(new File("resources/handwritten_numbers/0/0 (" + x + ").jpg")));
                oneNumber.add(ImageIO.read(new File("resources/handwritten_numbers/1/1 (" + x + ").jpg")));
                twoNumber.add(ImageIO.read(new File("resources/handwritten_numbers/2/2 (" + x + ").jpg")));
                threeNumber.add(ImageIO.read(new File("resources/handwritten_numbers/3/3 (" + x + ").jpg")));
                fourNumber.add(ImageIO.read(new File("resources/handwritten_numbers/4/4 (" + x + ").jpg")));
                fiveNumber.add(ImageIO.read(new File("resources/handwritten_numbers/5/5 (" + x + ").jpg")));
                sixNumber.add(ImageIO.read(new File("resources/handwritten_numbers/6/6 (" + x + ").jpg")));
                sevenNumber.add(ImageIO.read(new File("resources/handwritten_numbers/7/7 (" + x + ").jpg")));
                eightNumber.add(ImageIO.read(new File("resources/handwritten_numbers/8/8 (" + x + ").jpg")));
                nineNumber.add(ImageIO.read(new File("resources/handwritten_numbers/9/9 (" + x + ").jpg")));
            }
            listOfNumbers.add(zeroNumber);
            listOfNumbers.add(oneNumber);
            listOfNumbers.add(twoNumber);
            listOfNumbers.add(threeNumber);
            listOfNumbers.add(fourNumber);
            listOfNumbers.add(fiveNumber);
            listOfNumbers.add(sixNumber);
            listOfNumbers.add(sevenNumber);
            listOfNumbers.add(eightNumber);
            listOfNumbers.add(nineNumber);

            bgImage = ImageIO.read(new File("resources/bg.png"));
            waiter = ImageIO.read(new File("resources/alpha.png"));
            waiterFoodImage = ImageIO.read(new File("resources/waiterFood.png"));
            waiterWineImage = ImageIO.read(new File("resources/waiterWine.png"));

            kitchen = ImageIO.read(new File("resources/kitchen.png"));
            tableImage = ImageIO.read(new File("resources/table2.png"));
            wineImage = ImageIO.read(new File(WINE_PATH));

            yellowBulb = ImageIO.read(new File("resources/bulb/yellow.png"));
            redBulb = ImageIO.read(new File("resources/bulb/red.png"));
            greenBulb = ImageIO.read(new File("resources/bulb/green.png"));

            foodTable = ImageIO.read(new File("resources/foodTable.png"));

            numbers.add(ImageIO.read(new File("resources/numbers/zero.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/one.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/two.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/three.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/four.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/five.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/six.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/seven.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/eight.png")));
            numbers.add(ImageIO.read(new File("resources/numbers/nine.png")));


        } catch (IOException e) {
            e.printStackTrace();
        }

        tables = calculateTables(numberOfTables);

        for (Table table : tables) {
            lockedAreas.add(new LockedArea(table.getX(), table.getY(), table.getX() + 140, table.getY() + 112));
        }

        lockedAreas.add(new LockedArea(580, 180, 1023, 260));

        // List<Node> nodes = AStar.findPathForWaiter(100, 100, 110, 110, lockedAreas);


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
        for (int i = 0; i < numberOfTables; i++) {
            int numberOfPeople = ThreadLocalRandom.current().nextInt(0, 4 + 1);

            if (xPos < 880) {
                Table table = new Table(i, xPos, /*area offset*/yPos, numberOfPeople, 2);
                coordinatesOfTables.add(table);
                xPos += 200;
            } else {
                xPos = 30;
                yPos += 220;
                Table table = new Table(i, xPos, /*area offset*/yPos, numberOfPeople, 2);
                coordinatesOfTables.add(table);
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
        g.drawImage(waiter, waiterXpos - 29, waiterYpos - 97, this);

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


        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 12));
        g.drawString("ODBIÓR ZAMÓWIEŃ", 860, 160);
        g.drawImage(foodTable, 950, 170, this);
        g.drawImage(foodTable, 880, 170, this);
        g.drawImage(foodTable, 810, 170, this);


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

            if (tables.get(i).getNumberOfPeople() > 0) {

                for (int j = 0; j < tables.get(i).getNumberOfPeople(); j++) {
                    g.drawImage(tables.get(i).getAvatars().get(j), tableX + chairs.get(j).getX(), tableY + chairs.get(j).getY(), this);
                }

                g.setColor(Color.GREEN);
                g.setFont(new Font("Helvetica", Font.BOLD, 12));
                g.drawString("X", tables.get(i).getWaiterDockXPos(), tables.get(i).getWaiterDockYPos());


                if (tables.get(i).getStatus() == 1) {
                    g.drawImage(yellowBulb, tableX + 55, tableY - 10, this);
                }
                if (tables.get(i).getStatus() == 2) {
                    g.drawImage(redBulb, tableX + 55, tableY - 10, this);
                }
                if (tables.get(i).getStatus() == 3) {
                    g.drawImage(greenBulb, tableX + 55, tableY - 10, this);
                }

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

            if (foodPos1) {

                g.drawImage(listOfNumbers.get(tableNumberTodraw).get(randomImageNumber), 840, 160, this);
                g.drawImage(food1, 760, 190, this);
            }

            if (foodPos2) {
                g.drawImage(food2, 830, 190, this);
            }

            if (foodPos3) {
                g.drawImage(food3, 900, 190, this);
            }

            if (foodPos4) {
                g.drawImage(food4, 970, 190, this);
            }

            if (waiterTalking) {
                g.drawImage(talkCloud, waiterXpos - 5, waiterYpos - 135, this);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.BOLD, 10));
//                g.drawString(waiterSay, waiterXpos + 5, waiterYpos - 115);

                int drawY = waiterYpos -117;
                for (String line : waiterSay.split("\n")){
                    g.drawString(line, waiterXpos-1 , drawY );
                    drawY += 10;
                }

            }

        }

    }

    public void setWaiterXpos(int xPos) {
        this.waiterXpos = xPos;
    }

    public void setWaiterYpos(int yPos) {
        this.waiterYpos = yPos;
    }

    public int getWaiterXpos() {
        return waiterXpos;
    }

    public int getWaiterYpos() {
        return waiterYpos;
    }

    public List<LockedArea> getLockedAreas() {
        return lockedAreas;
    }

    public List<Table> getTableList() {
        return tables;
    }


    public List<Table> getSortedTables() {
        return sortedTables;
    }

    public void setSortedTables(List<Table> sortedTables) {
        this.sortedTables = sortedTables;
    }

    public void paintOrder(Table table) throws IOException {
        food1 = null;
        food2 = null;
        food3 = null;
        food4 = null;

        List<Meal> meals = table.getOrder();
        System.out.println("Meals: " + meals.stream().map(Meal::getName).collect(Collectors.toList()));

        int counter = 0;

        for (Meal meal : meals) {
            int randomImg = ThreadLocalRandom.current().nextInt(1, 98 + 1);

//            System.out.println("stolik: "+table.getTableNumber());
//            System.out.println("index: "+counter);
//            System.out.println("JEDZENIE: "+ meal.getName());
//            System.out.println("wylosowana liczba: "+ randomImg);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("resources/food_images/thumb/");
            stringBuilder.append(meal.getName());
            stringBuilder.append("/");
            stringBuilder.append(meal.getName());
            stringBuilder.append(" (");
            stringBuilder.append(randomImg);
            stringBuilder.append(").jpg");

            if (counter == 0) {
                tableNumberTodraw = table.getTableNumber();
                foodPos1 = true;
                food1 = ImageIO.read(new File(stringBuilder.toString()));
                System.out.println("File drawed: " + stringBuilder.toString());
                pathFood1ToTensor = stringBuilder.toString().replace("thumb", "big");
            } else if (counter == 1) {
                foodPos2 = true;
                food2 = ImageIO.read(new File(stringBuilder.toString()));
                System.out.println("File drawed: " + stringBuilder.toString());
                pathFood2ToTensor = stringBuilder.toString().replace("thumb", "big");
            } else if (counter == 2) {
                foodPos3 = true;
                food3 = ImageIO.read(new File(stringBuilder.toString()));
                System.out.println("File drawed: " + stringBuilder.toString());
                pathFood3ToTensor = stringBuilder.toString().replace("thumb", "big");
            } else if (counter == 3) {
                foodPos4 = true;
                food4 = ImageIO.read(new File(stringBuilder.toString()));
                System.out.println("File drawed: " + stringBuilder.toString());
                pathFood4ToTensor = stringBuilder.toString().replace("thumb", "big");
            }

            counter++;
        }
    }

    public String getPathFood1ToTensor() {
        return pathFood1ToTensor;
    }

    public String getPathFood2ToTensor() {
        return pathFood2ToTensor;
    }

    public String getPathFood3ToTensor() {
        return pathFood3ToTensor;
    }

    public String getPathFood4ToTensor() {
        return pathFood4ToTensor;
    }

    public boolean isFoodPos1() {
        return foodPos1;
    }

    public boolean isFoodPos2() {
        return foodPos2;
    }

    public boolean isFoodPos3() {
        return foodPos3;
    }

    public boolean isFoodPos4() {
        return foodPos4;
    }

    public void setFoodPos1(boolean foodPos1) {
        this.foodPos1 = foodPos1;
    }

    public void setFoodPos2(boolean foodPos2) {
        this.foodPos2 = foodPos2;
    }

    public void setFoodPos3(boolean foodPos3) {
        this.foodPos3 = foodPos3;
    }

    public void setFoodPos4(boolean foodPos4) {
        this.foodPos4 = foodPos4;
    }

    public int getTableNumberTodraw() {
        return tableNumberTodraw;
    }

    public int getRandomImageNumber() {
        return randomImageNumber;
    }

    public void drawStringNearWaiter(String toSay) {

        waiterSay = "";
        waiterTalking = true;

        char[] charArray = toSay.toCharArray();
        for (int i = 0; i < toSay.length(); i++) {
            waiterSay += charArray[i];
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
            revalidate();
        }
        waiterSay = toSay;

    }

    public void addToStringNearWaiter(String added) {

    }

    public void setWaiterTalking(boolean talking) {
        waiterTalking = talking;
    }


}
