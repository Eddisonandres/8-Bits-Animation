package src;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/** Template to create bitmap objects */
public class Bitmap extends JFrame
{
    //Variables
    private String[][] bitmap; //2D array of string
    private int sizeX; //Horizontal size 
    private int sizeY; //Vertical size

    static public StyledDocument styledDocument;
    static public Map<Integer, Color> colorMap;

    // Public methods
    /**
     * Constructor
     * @param sizeX Horizontal size
     * @param sizeY Vertical size
     * @return a constructed Bitmap object
     */
    Bitmap(int sizeX, int sizeY)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        //Initialise the array with the provided size
        this.bitmap = new String[sizeX][sizeY];
    }
    /**
     * ==========================================================================
     * Method to enter the values to the arrays in the animation
     * @param image name of the secuence of images
     * @throws IOException
     * @throws FileNotFoundException
     * ==========================================================================
     */
    void inputAnimation(String image, String DELIMITER, String FOLDERFILES) throws FileNotFoundException, IOException
    {
        // Constants
        
        // Variables
        int count = 0;
        // Read the csv file and store in a BuffereadReader variable
        try (BufferedReader br = new BufferedReader(new FileReader(FOLDERFILES + "/" + image + ".csv")))
        {
            String line;
            // Loop to run into the file line by line
            while ((line = br.readLine()) != null) 
            {
                // Store the line in an array using the delimiter
                String[] values = line.split(DELIMITER);
                // Loop to run into each number of the line into the array
                for(int countX = 0; countX < values.length; countX ++)
                {
                    // Increasing X will store one code at a time
                    bitmap[countX][count] = values[countX];
                }
                count ++;
            }
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }

    }
    /**
     * ==========================================================================
     * Print each image for the animation
     * @param arrayTextPane 
     * ==========================================================================
     */
    void printAnimation(JTextPane arrayTextPane)
    {
        int digite;

        // Initialize the StyledDocument
        styledDocument = arrayTextPane.getStyledDocument();
        
        // Initialize the color mapping
        colorMap = new HashMap<>();
        colorMap.put(0, Color.WHITE);
        colorMap.put(1, Color.RED);
        colorMap.put(2, Color.BLUE);
        colorMap.put(3, Color.ORANGE);
        colorMap.put(4, Color.BLACK);
        colorMap.put(5, Color.YELLOW);
        colorMap.put(6, Color.GRAY);
        colorMap.put(7, Color.GREEN);
        colorMap.put(8, Color.PINK);
        colorMap.put(9, Color.CYAN);
        
        arrayTextPane.setText("");
        arrayTextPane.setFont(new Font("Consolas", Font.PLAIN, 10));
        arrayTextPane.setAlignmentX(CENTER_ALIGNMENT);
        arrayTextPane.setAlignmentY(CENTER_ALIGNMENT);
        
        //For each line
        for (int countY = 0; countY < sizeY; countY ++)
        {
            addNumberToTextPane(0,true);
            //Go thorough each line
            for(int countX = 0; countX < sizeX; countX ++)
            {
                digite = Integer.parseInt(bitmap[countX][countY]); // Extract the number from the array
                // Print the colour acording to the number
                addNumberToTextPane(digite,false);

            }
        }
    }
    /**
     * ==========================================================================
     * Add text to the nubers printed
     * @param number to print
     * @param newLine define if the line ends
     * ==========================================================================
     */
    static public void addNumberToTextPane(int number, boolean newLine) {
        // Get the color for the given number
        Color color = colorMap.getOrDefault(number, Color.BLACK);

        // Create a SimpleAttributeSet to represent the style (e.g., background color)
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setBackground(style, color);
        StyleConstants.setForeground(style, color);

        // Append the number to the StyledDocument with the specified style
        try {
            if(newLine == false)
            {
                styledDocument.insertString(styledDocument.getLength(), String.valueOf(number)+" " , style);
            }
            else
            {
                styledDocument.insertString(styledDocument.getLength(), "\n" , style);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
