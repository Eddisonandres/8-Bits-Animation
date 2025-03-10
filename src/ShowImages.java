package src;
// Description: Application designed to generate and display images from CSV files, utilizing numerical values to transform them into colored characters.

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.io.BufferedReader;

/**
 * ShowImages
 */
public class ShowImages {

    // Components (aka widgets, controls)
    static JFrame window;
    static JPanel panel;
    static JLabel arrayLabel;
    static JLabel nameLabel;
    static JLabel counterLabel;
    static JTextField nameTextField;
    static JTextArea arrayTextArea;
    static JTextPane arrayTextPane;
    static JComboBox<String> imageComboBox;
    static JButton addButton;
    static JButton showButton;
    static JRadioButton newRadioButton;
    static JRadioButton loadRadioButton;
    static GridBagConstraints gridBag;
    // Objects
    static Bitmap bitmap;
    static Image image;

    // constants
    static final String EXTENSION = "csv";
    static final String DELIMITER = " ";
    static final String FOLDERFILES = "Files";
    static final String ICON = "icono.png";
    static final String BACKGROUNDIMAGE = "Background.png";

    // Variables
    static public StyledDocument styledDocument;
    static public Map<Integer, Color> colorMap;

    /**
     * ==========================================================================
     *  Method to get visible textarea
     * ==========================================================================
     */
    static void selectOption()
    {
        nameTextField.setText("");
        counterLabel.setText("0");
        // when the option is new project
        if(newRadioButton.isSelected())
        {
            nameLabel.setEnabled(true);
            nameTextField.setEnabled(true);
            arrayLabel.setEnabled(true);
            arrayTextArea.setEnabled(true);
            addButton.setEnabled(true);
            showButton.setEnabled(true);
            imageComboBox.setEnabled(false);
        }
        // when the option is load a project
        else
        {
            nameLabel.setEnabled(false);
            nameTextField.setEnabled(false);
            arrayLabel.setEnabled(false);
            arrayTextArea.setEnabled(false);
            addButton.setEnabled(false);
            showButton.setEnabled(true);
            addValuesImageComboBox();
        }
    }
    /**
     * ==========================================================================
     * Method to store files with the array
     * ==========================================================================
     */
    static void addarray()
    {
        // Variables
        File file;
        FileWriter fileWriter;
        String fileName;
        String name = nameTextField.getText();
        int secuenceImage;

        // error in case the name is empy
        if(nameTextField.getText().equals("") || arrayTextArea.getText().equals(""))
        {
            JOptionPane.showMessageDialog(window,
            "Name and array cannot be left empty",
            "Error!", JOptionPane.ERROR_MESSAGE);
        }
        // Valid name
        else
        {
            // Secuence of image
            secuenceImage = Integer.parseInt(counterLabel.getText()) + 1;
            counterLabel.setText(secuenceImage + "");;

            // Format the file name
            fileName = name + "__" + secuenceImage + "." + EXTENSION;
            // Create a file    
            file = new File(fileName);
            try {
                fileWriter = new FileWriter(FOLDERFILES + "/" + file);
                // Write the player's information in the file
                fileWriter.write(arrayTextArea.getText());
                // Close the file
                fileWriter.close();
                // Tell user that we saved
                JOptionPane.showMessageDialog(window, "array saved in file: " + fileName);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(window, "Error cannot write on this file!");
            }

            // Reset the window
            arrayTextArea.setText("");
        }
    }
    /**
     * ==========================================================================
     * Method to add images to the radiobutton
     * ==========================================================================
     */
    static void addValuesImageComboBox()
    {
        // Variables
        boolean addElement;
        String project;

        String currentDirectory = System.getProperty("user.dir"); // Extract the current path
        File directory = new File(currentDirectory + "/" + FOLDERFILES);
        

        // Look for files with the extension required
        File[] files = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name)
            {
                return name.toLowerCase().endsWith(EXTENSION);
            }
        });
        // When files with the extension are found
        if (files.length != 0)
        {
            String[] foundFiles = new String[files.length];
            // Construct the array with the file's names
            for(int i=0; i < files.length; i++)
            {
                foundFiles[i] = files[i].getName();
            }

            // fill the Combobox with the projects
            for(int list = 0; list < foundFiles.length; list++)
            {
                addElement = true;
                project = foundFiles[list].split("__")[0];
                for(int element = 0; element < imageComboBox.getItemCount(); element++)
                {
                    // when the image is the same than before addElement is false
                    if(project.equals(imageComboBox.getItemAt(element)))
                    {
                        addElement = false;
                    }
                }
                // When the variable addElement is true the name is add to the combobox
                if(addElement == true)
                {
                    imageComboBox.addItem(project);
                }
                // Combobox enabled
                imageComboBox.setEnabled(true);
            }
        }
        // Error when there are not any files with the extension
        else
        {
            JOptionPane.showMessageDialog(window, 
                "There are not any files to load", 
                "Error!",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * ==========================================================================
     * Method to show images in the TextArea
     * @throws FileNotFoundException
     * @throws IOException
     * ==========================================================================
     */
    static void animation(String nameImage) throws FileNotFoundException, IOException
    {
        String[] imagesNames = validateSize(nameImage);
        
        // Error when the files are not ok
        if(imagesNames[0]== "0")
        {
            JOptionPane.showMessageDialog(window, 
            "The files for the animation does not have the same size or does not have a valid format", 
            "Error!",
            JOptionPane.ERROR_MESSAGE);
        }
        // When the validation of the files is good
        else
        {
            // New Frame to display the images
            JFrame windowImage = new JFrame("Images");
            // Create a textpane to show the image
            JTextPane textPaneImage = new JTextPane();
            StyledDocument doc = textPaneImage.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);
            // Put the textpane into a scrollpane
            JScrollPane scrollPane = new JScrollPane(textPaneImage);

            // Get the dimension of the screen
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

            // Calculate the position of the JFrame
            int x = (screen.width - windowImage.getWidth()) / 2;
            int y = (screen.height - windowImage.getHeight()) / 2;

            // Set the position of the JFrame and other features
            windowImage.setLocation(x, y);
            
            windowImage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            windowImage.setSize(400, 300);
            windowImage.setIconImage(new ImageIcon("icono.png").getImage()); // Icon
            
            windowImage.add(scrollPane);
            windowImage.setVisible(true);
            
            ArrayList<Image> image = new ArrayList<Image>(); // Creating the object to store data of the files

            // Create each image related with the name
            for(int index = 2; index < imagesNames.length; index ++)
            {
                //Create a bitmap object
                bitmap = new Bitmap(Integer.parseInt(imagesNames[0]), Integer.parseInt(imagesNames[1]));
                bitmap.inputAnimation(imagesNames[index], DELIMITER, FOLDERFILES);
                image.add(new Image(imagesNames[index], bitmap));
            }

            // Loop to print the image in the textArea
            int count = 0;
            do 
            {
                // Loop to run into the file to print each number of the image
                for (int index=0; index < image.size(); index++)
                { 
                    image.get(index).getBitmap().printAnimation(textPaneImage);
                    windowImage.paintComponents(windowImage.getGraphics());
                    try {
                        // Wait 300 miliseconds
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                count ++;
            }while (count <= 5); 
            // Close the window where the image is displayed
            windowImage.dispose();
        }

    }
    /**
     * ==========================================================================
     * Method to action the button show button
     * @throws FileNotFoundException
     * @throws IOException
     * ==========================================================================
     */
    static void clickShow() throws FileNotFoundException, IOException
    {
        // Variables
        String optionRadiobutton;
        boolean valid = true;

        // When user chooses to create a new project
        if(newRadioButton.isSelected())
        {
            optionRadiobutton = nameTextField.getText();
            if (optionRadiobutton.equals(""))
                valid = false;
        }
        // When user chooses to load a project created
        else
        {
            optionRadiobutton = imageComboBox.getSelectedItem().toString();
            if (imageComboBox.getSelectedItem().toString().equals(""))
                valid = false;
        }
        // Error when there is not a name of project to show
        if (valid == false)
        {
            JOptionPane.showMessageDialog(window, 
                "You must choose a name to show the image(s)", 
                "Error!",
                JOptionPane.WARNING_MESSAGE);
        }
        // Execute the method animation
        else
        {
            animation(optionRadiobutton);
        }
    }
    /**
     * ==========================================================================
     * Method to validate size of image
     * @param image
     * @return the size of the array and names of the files [0:sizeX, 1:sizeY, name of eache image of the project...]
     * ==========================================================================
     */
    static String[] validateSize(String image)
    {
        // Variables
        String currentDirectory = System.getProperty("user.dir"); // Extract the current path
        int countY = 0, countX = 0;
        File directory = new File(currentDirectory + "/" + FOLDERFILES);
        int lengthName;
        String nameFile;
        boolean firstFile = false;
        boolean firstX = false;
        int lenghtX = 0;
        int lenghtY = 0;
        boolean valideFile = true;
        String nameNoExtension;
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> IndexStoreName = new ArrayList<>();
        

        lengthName = image.length();

        // Look for files with the extension required
        File[] files = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name)
            {
                return name.toLowerCase().endsWith(EXTENSION);
            }
        });
        // Valide each file to look all files related to the name of the image to show the image
        for(int i=0; i < files.length; i++)
        {
            nameFile = files[i].getName().toLowerCase(); // Get the name of the file
            // Validate if the file has the name of the image
            if(nameFile.substring(0, lengthName).equals(image))
            {
                // Try to read the information of the file
                try (BufferedReader br = new BufferedReader(new FileReader(FOLDERFILES + "/" + files[i].getName())))
                {
                    String line;
                    countY = 0;
                    // Loop to run into the file line by line
                    while ((line = br.readLine()) != null) 
                    {
                        countX = 0;
                        // Store the line in an array using the delimiter
                        String[] values = line.split(DELIMITER);
                        // Loop to run into each number of the line into the array
                        for(int index = 0; index < values.length; index ++){

                            try {
                                // When the file has a number less than 0 or more than 9 (represents the colors) display an error
                                if(Integer.parseInt(values[index]) < 0 || Integer.parseInt(values[index]) > 9)
                                {
                                    JOptionPane.showMessageDialog(window, 
                                        "The file does not have valid numbers", 
                                        "Error!",
                                        JOptionPane.WARNING_MESSAGE);
                                    valideFile = false;
                                    break;
                                }
                            // When the file has values different to number display an error
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(window, 
                                    "The file does not have valid numbers", 
                                    "Error!",
                                    JOptionPane.WARNING_MESSAGE);
                                    valideFile = false;
                                    break;
                            }

                            countX ++;
                        }
                        // validation for the first row of the file
                        if(firstX == false && valideFile == true)
                        {
                            firstX = true;
                            lenghtX = countX;
                        }
                        // validate if all columns have the same lenght
                        else if(lenghtX != countX || valideFile == false)
                        {
                            valideFile = false;
                            break;
                        }
                        countY ++;
                    }
                    // Validate if the file is the firts to validate
                    if(firstFile == false && valideFile == true)
                    {
                        lenghtX = countX;
                        lenghtY = countY;
                        firstFile = true;
                    }
                    // Validate if all the file has the same dimensions
                    else if (lenghtX != countX || lenghtY != countY || valideFile == false) {
                        valideFile = false;
                        break;
                    }
                    // Store the name of the image
                    IndexStoreName.add(nameFile);

                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(window, 
                        "Error " + e, 
                        "Error!",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        // When the validation is true the program retunrs the length for rows and columns of the file
        if(valideFile == true)
        {
            result.add(countX + "");
            result.add(countY + "");
            // Store name of the images
            for(int index = 0; index < IndexStoreName.size(); index ++)
            {
                nameNoExtension = IndexStoreName.get(index).substring(0,IndexStoreName.get(index).length() - (EXTENSION.length() + 1));
                result.add(nameNoExtension);
            }
        }
        // When the validation is false the program returns 0
        else
        {
            result.add(0+"");
            result.add(0+"");

        }
        String[] resultUnion = result.toArray(new String[0]);;
        return resultUnion;
    }

    /**
     * ==========================================================================
     * Main method
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     * ==========================================================================
     */

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Change the GUI to look like the native OS
        try {
            UIManager.setLookAndFeel((UIManager.getSystemLookAndFeelClassName()));
        } catch (Exception e) {} 
        
        // Setup the window
        window = new JFrame("Images");
        window.setIconImage(new ImageIcon(ICON).getImage()); // Icon
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Close the window when click the X
        window.setResizable(false); // window is not resiable

        // Load the image for the background
        ImageIcon imagenBack = new ImageIcon(BACKGROUNDIMAGE);
        JLabel labelIcon = new JLabel(imagenBack);
        labelIcon.setBounds(0, 0, imagenBack.getIconWidth(), imagenBack.getIconHeight());

        
        // Panel
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridBagLayout());
        gridBag = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Labels
        nameLabel = new JLabel("Name of the project");
        arrayLabel = new JLabel("Insert codes");
        counterLabel = new JLabel("0");

        // TextFields
        nameTextField = new JTextField(40);
        nameTextField.setToolTipText("Enter project name."); // Add tooltip to name field

        // Combobox
        imageComboBox = new JComboBox<>();
        Dimension dimension = new Dimension(100, imageComboBox.getPreferredSize().height);
        imageComboBox.setPreferredSize(dimension);
        imageComboBox.setToolTipText("Select Project"); // Add tooltip to combobox

        // Buttons
        addButton = new JButton("Add array");
        addButton.addActionListener(event -> addarray());
        addButton.setToolTipText("Add a new Image or Project(animation) array");// Add tooltip to button
        showButton = new JButton("Show Image");
        showButton.addActionListener(event -> {
            try {
                clickShow();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        showButton.setToolTipText("Show Image or Project(animation)"); // Tooltip on the button showbutton

        // TextArea
        arrayTextArea = new JTextArea(20,40);
        arrayTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        arrayTextArea.setToolTipText("""
                <html>Input numbers separated by space. i.e '1 2 3'
                <br>0 = White
                <br>1 = Red
                <br>2 = Blue
                <br>3 = Orange
                <br>4 = Black
                <br>5 = yellow
                <br>6 = Gray
                <br>7 = Green
                <br>8 = Pink
                <br>9 = Cyan</html>
                """); // Array textArea tooltip
        JScrollPane scrollPane = new JScrollPane(arrayTextArea);
        

        // RadioButton
        newRadioButton = new JRadioButton("New Projec");
        newRadioButton.addActionListener(event -> selectOption());
        newRadioButton.setToolTipText("Create a new project"); // Tooltip on the Radiobutton to create a project 
        loadRadioButton = new JRadioButton("Load Project");
        loadRadioButton.addActionListener(event -> selectOption());
        loadRadioButton.setToolTipText("Load an Existing project"); // Tooltip on the Radiobutton to load a project
        ButtonGroup options = new ButtonGroup(); // Used to group radiobuttons
        
        options.add(newRadioButton);
        options.add(loadRadioButton);

        // Place the components
        window.add(panel);
        gridBag.anchor = GridBagConstraints.EAST;
        panel.add(labelIcon, gridBag);

        // Options
        gridBag.anchor = GridBagConstraints.WEST;
        gridBag.gridy = 0;
        gridBag.gridx = 0;
        panel.add(newRadioButton,gridBag);
        gridBag.gridy = 0;
        gridBag.gridx = 0;
        gridBag.anchor = GridBagConstraints.CENTER;
        panel.add(loadRadioButton,gridBag);

        // Name of the files
        gridBag.anchor = GridBagConstraints.WEST;
        gridBag.gridy = 1;
        panel.add(nameLabel,gridBag);
        gridBag.gridy = 2;
        panel.add(nameTextField, gridBag);
        
        // Buttons array
        gridBag.gridy = 3;
        gridBag.gridx = 0;
        gridBag.anchor = GridBagConstraints.WEST;
        gridBag.insets = new Insets(5, 5, 5, 5); // Padding around the button
        panel.add(addButton, gridBag);
        // Combobox with store images
        gridBag.gridy = 3;
        gridBag.gridx = 0;
        gridBag.anchor = GridBagConstraints.CENTER;
        gridBag.insets = new Insets(5, 5, 5, 5); // Padding around the button
        panel.add(imageComboBox, gridBag);
        gridBag.gridy = 3;
        gridBag.gridx = 0;
        gridBag.anchor = GridBagConstraints.EAST;
        gridBag.insets = new Insets(5, 5, 5, 5); // Padding around the button
        panel.add(showButton, gridBag);

        // Text the array
        gridBag.anchor = GridBagConstraints.CENTER; // Center the array
        gridBag.gridy = 3;
        panel.add(arrayLabel, gridBag);
        gridBag.gridy = 4;
        panel.add(scrollPane, gridBag);

        // Label to count names
        panel.add(counterLabel);
        
        // Resize the window
        window.pack();
        window.setVisible(true);
        window.setLocationRelativeTo(null);

        // Disable options to new project
        arrayLabel.setEnabled(false);
        arrayTextArea.setEnabled(false);
        addButton.setEnabled(false);
        showButton.setEnabled(false);
        imageComboBox.setEnabled(false);
        nameTextField.setEnabled(false);
        nameLabel.setEnabled(false);
        counterLabel.setVisible(false);
    }
}