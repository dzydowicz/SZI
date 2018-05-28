package map;

import map.Utils.MapCreator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapPanel extends JPanel
{
    int screenWidth, screenHeight;
    BufferedImage waiter, kitchen, table, bar;
    int[][] map = new int[30][25];

    MapPanel(int screenWidth, int screenHeight)
    {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;

        setPreferredSize(new Dimension(1200, 1000));

        try
        {
            waiter = ImageIO.read(new File("resources/waiter.png"));
            kitchen = ImageIO.read(new File("resources/kitchen.png"));
            table = ImageIO.read(new File("resources/table.png"));
            bar = ImageIO.read(new File("resources/bar.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        this.setLayout(null);

        // there should be generated map number between 1 and 3
        // we have to do 2 maps more
        map = new MapCreator().generateMapFromFile(1);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawBackground((Graphics2D) g);
        draw(g);
    }

    private void drawBackground(Graphics2D g)
    {
        g.setColor(Color.BLACK);

        for (int z = 0; z < 1200; z = z + 40)
        {
            g.drawLine(z, 0, z, 1000);
        }

        for (int z = 0; z < 1000; z = z + 40)
        {
            g.drawLine(0, z, 1200, z);
        }

        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(60));
    }

    private void draw(Graphics g)
    {
        for (int i=0; i<30; i++)
        {
            for (int j=0; j<25; j++)
            {
                //drawing kitchen fields
                if(i < 15 && j < 5)
                {
                    g.setColor(Color.RED);
                    g.fillRect(i*40,j*40,40,40);
                }

                //drawing bar fields
                if(i >= 15 && i < 30 && j < 5)
                {
                    g.setColor(Color.cyan);
                    g.fillRect(i*40,j*40,40,40);
                }

                if (map[i][j] == 1 )
                {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(i*40,j*40,40,40);
                    g.drawImage(table, i*40, j*40, this);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Courier New", Font.BOLD, 10));
                    g.drawString((i+1) + "," + (j+1), i*40  ,j*40);
                    g.setFont(new Font("Courier New", Font.BOLD, 22));
                }
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 30));
        g.drawString("KUCHNIA", 15, 20);
        g.drawImage(kitchen, 15, 20, this);
        g.drawString("BAR", 660, 20);
        g.drawImage(bar, 16*40, 20, this);

        g.setColor(Color.GREEN);
        g.fillRect(10*40, 4*40, 40, 40);
        g.fillRect(19*40, 4*40, 40, 40);
    }
}
