package map;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    int screenHeight, screenWidth;
    JPanel leftPanel, rightPanel;

    public MainFrame()
    {
        super("SZI");
        calculateScreenSize();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.white);

        setVisible(true);
        setResizable(false);

        MapPanel mapPanel = new MapPanel(screenWidth, screenHeight);
        add(mapPanel, BorderLayout.CENTER);

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        this.add(leftPanel, BorderLayout.WEST);
        leftPanel.setPreferredSize(new Dimension((screenWidth-1200)/2, 1000));

        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        this.add(rightPanel, BorderLayout.EAST);
        rightPanel.setPreferredSize(new Dimension((screenWidth-1200)/2,1000));

        pack();
        validate();
    }

    private void calculateScreenSize()
    {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = screen.height;
        screenWidth = screen.width;

        if (screenHeight / 9 == screenWidth / 16)
        {
            System.out.println("Proporcje sie zgadzaja");
            setSize(screenWidth, screenHeight);
        }
        else
        {
            int p = screenWidth / 16;

            if ( p*9 <= screenHeight )
            {
                System.out.println("Proporcje sie nie zgadzaja, ale jest ok");
                screenHeight = p * 9;
            }
            else
            {
                System.out.println("Rozdzielczosc nieprawidlowa!");
            }
        }
    }
}
