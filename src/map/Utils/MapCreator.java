package map.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MapCreator
{
    private static final String FILE_PATH = "resources/map";
    private static final int AMOUNT_OF_MAPS = 3;
    private static int TABLE = 1;
    private int tables[][] = new int[30][25];

    public int[][] generateMapFromFile(int mapNumber)
    {
        Path filePath = Paths.get(FILE_PATH + mapNumber + ".txt");

        try
        {
            Scanner in = new Scanner(filePath);

            for(int i = 0; i < 25; i++)
            {
                for(int j = 0; j < 30; j++)
                {
                    int number = in.nextInt();

                    if(number == TABLE)
                    {
                        tables[j][i] = TABLE;
                    }
                }
            }

            in.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return tables;
    }
}
