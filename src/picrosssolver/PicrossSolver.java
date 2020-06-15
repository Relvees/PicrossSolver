
package picrosssolver;
import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Isaac
 * An attempt at a basic algorithm to interpret and solve Picross/Hanjie puzzles
 * (fw, its probably gonna suck, sorry if you have to read through this)
 * 
 * its ok 2017 me, we didn't know any better at the time.
 */

public class PicrossSolver {
    /*
    So how does this all work?
    Well, picross puzzles are formed in a N*M grid, typically in multiples of 5
    Each column and row has a set ordered list of values 
    These values indicate the hidden "boxes" to be filled in the corresponding row/column
    
    Interpret the grid in a triple arraylist composed by the following
    all integers
    <row/col<#<values>>>
    row/col is a 0||1 value
    # indicates the position of the row or column values
    values of the col/row
        Problems to check:
        If total of values + spaces exceeds the sizes
            e.g. size 15, values 10,5,1
    [[1],[2,2],[1,1],[2,2],[1,1]]
    [[3],[2,2],[0],[1,1],[4]]
    
    CURRENT TO-DO:
    
    -work out solutions to visualise the grid in an interactable format. perhaps a webform is a starting point.
        -idea of using awt/swing to make a custom ui?
    */
    
    public static void main(String[] args) {
        // TODO code application logic here
        //readFile("ExPuzzles\\P1.txt");
        //testUI(); //test function for generating a gui
        testGridUI(); //a mockup of the structure
        //testfunction(); // console-based array displayer
        //now lets combine these mand make something hideous!
        //loadPuzzle();
    }
    public static void testfunction(){
       String selection = "";

       Scanner read = new Scanner(System.in);
       ArrayList<ArrayList<String>> puzVals = new ArrayList<>();
       ArrayList<Integer> testPuz = new ArrayList<>();
        
       while(true){
        System.out.println("Select a Puzzle by filename, list with 'list', or exit with 'exit'");
        selection = read.nextLine();
        if(selection.equals("list")){
             printPuzzles();
             selection = "";
        } else if (selection.equals("exit")){
             System.out.println("Exiting Application");
             System.exit(0);
        } else {
             selection = "ExPuzzles\\" + selection + ".txt";
             puzVals = readFile(selection);
             printArr(puzVals);
             //drawGrid(puzVals);
        }
       }//end while loop
        
    }
    public static void testUI(){
        /*
        drawing canvas elements to represent individual cells on the display

        how do we activate/deactivate the icons? 
        degrees of on-click detection based on cursor position 
        relative to the closest node?
        Is there boundary detection
        Suggestion of using JLabels due to inbuilt event handlers 
        https://stackoverflow.com/questions/8023468/java-grid-of-clickable-elements
        perhaps arrange this using a gridlayout?
        https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
        class{
            method{
                jframe frameName = new jframe("windowTitle");
                classcomponent objectName = new classcomponent();
                container.add(name) ;

        a frame is main window with a layout
        a frame contains a number of panes
        a panel is a sub-part of a frame with a layout
        a panel contains components
        components can vary and are added FIFO
        components are arranged into a pane or frame's layout
        they also have event handelrs to execute code shenanegans.

        a frame is packed with panes and then set visible
        
        */
        Scanner read = new Scanner(System.in);
        int testAmount = 0;
        int y = 0;
        System.out.println("how many (1-11)");
        testAmount = Integer.parseInt(read.nextLine());
        
        JFrame testWindow = new JFrame("moderate understanding going on");
        JPanel gridTest = new JPanel();
        gridTest.setLayout(new GridLayout(4,3)); //(y,x)
        gridTest.add(new JButton("Button initial"));
        
        for (int x=0; x<testAmount; x++){
            y = x+1;
            gridTest.add(new JButton("Button " + y));
        }
        
        testWindow.add(gridTest, BorderLayout.NORTH);
        testWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testWindow.pack();
        testWindow.setVisible(true);
    }
    public static void testGridUI(){
        //variables used
        int y = 0; //number for buttons
        ArrayList<ArrayList<String>> puzVals = new ArrayList<>();
        puzVals = readFile("ExPuzzles\\P2.txt"); //an example puzzle that looks like an 'up' arrow
        int pCols = puzVals.get(0).size();
        int pRows = puzVals.get(1).size();
        int cellCount = pCols * pRows;        
        
        //define structure
        JFrame gridFrame = new JFrame("moderate understanding going on");
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel lUpPanel = new JPanel();
        JPanel lLowPanel = new JPanel();
        JPanel rUpPanel = new JPanel();
        JPanel rLowPanel = new JPanel();
        gridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //define layouts
        gridFrame.setLayout(new GridLayout(1,2)); //(y,x) (2-wide)
        leftPanel.setLayout(new GridLayout(2,1)); //(y,x) (2-tall)
        rightPanel.setLayout(new GridLayout(2,1)); //(y,x)
        lUpPanel.setLayout(new GridLayout(1,1));
        lLowPanel.setLayout(new GridLayout(pRows,1));
        rUpPanel.setLayout(new GridLayout(1,pCols));
        //rLowPanel.setLayout(new GridLayout(5,5)); //25 items, 5*5
        rLowPanel.setLayout(new GridLayout(pRows,pCols)); 
        //dynamic to loaded puzzle, should be 5*5
        
        
        //create test elements 
        
        /*
        This should just be a dump of the array values as single labels for now.
        0 = rows
        1 = columns
        */
        //example column index
        for (int x=0; x<pRows; x++){
            lLowPanel.add(new JLabel(puzVals.get(0).get(x),4));
            /*
            public JLabel(String text,int horizontalAlignment)
            horizontalAlignment - One of the following constants defined 
            in SwingConstants: LEFT, CENTER, RIGHT, LEADING or TRAILING.
            but this is an Integer, not string.
            0 = centre
            1 = error
            2 = left
            3 = error
            4 = right??
            oh java you silly.
            */
        }
        
        
        //puzzle grid
        for (int x=0; x<cellCount; x++){
            y = x+1;
            rLowPanel.add(new JButton(""));
        }
        
        //final merge-up
        leftPanel.add(lUpPanel);
        leftPanel.add(lLowPanel);
        rightPanel.add(rUpPanel);
        rightPanel.add(rLowPanel);
        gridFrame.add(leftPanel);
        gridFrame.add(rightPanel);
        
        //render and display
        gridFrame.pack();
        gridFrame.setVisible(true);
    }
    public static ArrayList<ArrayList<String>> readFile(String InArrURL) {
       /*
        returns a double arraylist of values corresponding to the row/colums of the puzzle
        example input
        [[1],[2,2],[1,1],[2,2],[1,1]]
        [[3],[2,2],[0],[1,1],[4]]
        
        This causing issues in programming, switching it up
        new input type:
        [1],[2,2],[1,1],[2,2],[1,1]
        [3],[2,2],[0],[1,1],[4]
        
        */
       //return inputArr TODO;
       ArrayList<String> input = new ArrayList();
       ArrayList<ArrayList<String>> output = new ArrayList();
       //based on Files.readAllLines(new File("input.txt").toPath(), Charset.defaultCharset() );
       try{
        input.addAll(Files.readAllLines(Paths.get(InArrURL), Charset.defaultCharset()));    //this literally just dumps it as a generic string, look into manipulating this array
        for(int x=0; x<input.size(); x++){
            //get line of string
            String toSplit = input.get(x);
            String trimmed = toSplit.replaceAll("(\\]\\,)", ""); //removes all cases of ],
            trimmed = trimmed.replaceAll("(\\]{2})", ""); //removes case of ]]
            trimmed = trimmed.replaceAll("(\\[{2})", ""); //removes case of [[
            String[] split = trimmed.split("(\\[)"); //presumably splits on [
            ArrayList<String> splitArr = new ArrayList(Arrays.asList(split));
            output.add(splitArr);
        }
        
        
        /* tom suggests using .split(regex)
        I think it needs prepping for that
        
        replaceall(x, y) replaces match x with regex y
            objective
                    - remove [[ and ]] from start and end
                    - remove all ],
            regex notes - () - grouped expression
                    - \\ - escapes the escaper
                    - \\[ - escapes the squarebrace
                    - {2} specifies the amount of the preceeding regex to match
                    - , - literally just the comma
            in summary:
                    (//],)   - ],
                    (//[{2}) - [[
                    (//]{2}) - ]]
        
        split(regex)  - split into arrays on regex
            objective - split on value '['
        
        Arrays.aslist(array) convert split array to arraylist
        */
        
       }catch(IOException e){
           System.out.println("File's not there bro");
           System.exit(0);
       }
       return output;
    }
    public static void printPuzzles(){
        //TODO - limit displayed files to specific file format, explode on . and take right half, print if explode = wanted filetype.
        //printing all files within the "ExPuzzles" folder
        final File dirName = new File("ExPuzzles\\");
        File[] filesList = dirName.listFiles();
        if(filesList != null){
            //files exist, are stored in a 1d array as File objects
            System.out.println();
            for(int x=0; x<filesList.length; x++){
                System.out.print(filesList[x].getName() + " ");
            }
            System.out.println();
        } else {
            System.out.println("No Puzzles found! Does the ExPuzzles directory exit?");
            return;
        }
        return;
    }
    public static void printArr(ArrayList<ArrayList<String>> arrIn){
        for(int x=0; x<arrIn.size(); x++){
            switch (x){
            case 0:
                System.out.println("\nColumns");
                break;
            case 1:
                System.out.println("\nRows");
                break;
            }
            for(int y=0; y<arrIn.get(x).size(); y++){
                System.out.println(arrIn.get(x).get(y));
            }
        }
        //TODO
    }
    public static void loadPuzzle(){
       String selection = "";

       Scanner read = new Scanner(System.in);
       ArrayList<ArrayList<String>> puzVals = new ArrayList<>();
        
       while(true){
        System.out.println("Select a Puzzle by filename, list with 'list', or exit with 'exit'");
        selection = read.nextLine();
        if(selection.equals("list")){
             printPuzzles();
             selection = "";
        } else if (selection.equals("exit")){
             System.out.println("Exiting Application");
             System.exit(0);
        } else {
             selection = "ExPuzzles\\" + selection + ".txt";
             drawGrid(readFile(selection));
        }
       }//end while loop
        
    }
    public static void drawGrid(ArrayList<ArrayList<String>> arrIn){
        //to-do: build the gui to read in these values
        //figure a way to represent them without utterly butchering the UI
        
        int largestCol = 1;
        int largestRow = 1;
        int largestColID = 0;
        int largestRowID = 0;
        int currentSize = 1;
        int compareLength = 1;
        //represent the dimensions of each grid
        int columns = arrIn.get(0).size();
        int rows = arrIn.get(1).size();
        
        for (int x=0; x<columns; x++){
            compareLength = arrIn.get(0).get(x).length();
            if (compareLength>currentSize){
                currentSize = compareLength;
                largestColID = x;
            }
        }
        largestCol = currentSize;
        compareLength = 1;
        for (int y=0; y<rows; y++){
            compareLength = arrIn.get(1).get(y).length();
            if (compareLength>currentSize){
                currentSize = compareLength;
                largestRowID = y;
            }
        }
        largestRow = currentSize;
        
        //Need to be able to split on the commas whilst knowing the quantity. whiiiich is tricky.
        /*
        testPrint("ceiling 2.5 = " + Math.ceil(2.5));
        testPrint("5/2 = " + 5/2);
        testPrint("5.0/2 = " + 5.0/2);
        testPrint("ceiling(5/2) = " + Math.ceil(5/2));
        testPrint("5/2.0 = " + 5/2.0);
        1           1,0,1
        1,1         2,1,3
        1,1,1       3,2,5
        1,1,1,1     4,3,7
        1,1,1,1,1   5,4,9
        floor(n/2)+1
        
        
        so we effectively need to create a frame/panel arrangement 
        that follows a similar pattern to below
        ________________________________________
        | stats     |                          |
        |           |            column        |
        |   left    |  right        index      |
        |   upper   |    upper                 |
        |-----------|--------------------------|
        |           |                          |
        |  row      |       puzzle             |
        |   index   |           grid           |
        |           |                          |
        |  left     |             right        |
        |    lower  |               lower      |
        |           |                          |
        ----------------------------------------
        initial layer is a left-right split of two panes
        left contains the stats box, and the rows index
        right contains the columns index, and the main puzzle grid
        assuming you can multi-nest panes for whatever dumb reason
        then this should work
        
        ________________________________________
        | stats     |3 | | |                   |
        | here      |2 |2| |    gridlayout     |
        |  X*Y      |1 |1|1|    align-right    |
        ----------------------------------------
        |     |3|2|1|                          |
        |         |1|                          |
        |       |2|1|   gridlayout             |
        |           |   bunch o buttons        |
        |gridlayout |      go here             |
        | labels    |                          |
        |           | (imagine its all boxes)  |
        |           |                          |
        ----------------------------------------
        */
        return;
    }
    public static void testPrint(String valueIn){
        System.out.println(valueIn);
        return;
    }
}
