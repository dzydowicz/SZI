import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Way implements ImageObserver
{
    boolean createBoard = false;

    public Way() {}

    public void draw(Graphics2D g)
    {
        g.setColor(Color.BLACK);

        for (int z = 0; z < 1200; z = z + 20)
        {
            g.drawLine(z, 0, z, 1000);
        }

        for (int z = 0; z < 1000; z = z + 20)
        {
            g.drawLine(0, z, 1200, z);
        }

        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(60));
    }


    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}
