
package picrosssolver;
import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Isaac
 * An attempt at a basic algorithm to interpret and solve Picross/Hanjie puzzles
 * (fw, its probably gonna suck, sorry if you have to read through this)
 * 
 * its on 2017 me, we didn't know any better at the time.
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
    
    */
    
    /*
    CURRENT TO-DO:
    -build function to convert the 2d string arraylist into 3d int (which will be a bitch to manipulate i think)
    
    -work out solutions to visualise the grid in an interactable format. perhaps a webform is a starting point.
        -idea of using awt/swing to make a custom ui?
        
    
    */
    public static void main(String[] args) {
        // TODO code application logic here
       int colums = 0;
       int rows = 0;
       String puzzle = "";

       Scanner read = new Scanner(System.in);
       ArrayList<ArrayList<String>> puzVals = new ArrayList<>();
       ArrayList<Integer> testPuz = new ArrayList<>();

       while(true){
       System.out.println("Select a Puzzle by filename, list with 'list', or exit with 'exit'");
       puzzle = read.nextLine();
       if(puzzle.equals("list")){
            printPuzzles();
            puzzle = "";
       } else if (puzzle.equals("exit")){
            System.out.println("Exiting Application");
            System.exit(0);
       } else {
            puzzle = "ExPuzzles\\" + puzzle + ".txt";
            puzVals = readFile(puzzle);
            printArr(puzVals);
            //drawGrid(puzVals);
       }
       }//end while loop
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
            String trimmed = toSplit.replaceAll("(\\],)", ""); //removes all cases of ],
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
       ArrayList<Integer> nothing = new ArrayList<Integer>();
       nothing.add(0);
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
    
    public static void drawGrid(ArrayList<ArrayList<String>> arrIn){
        //for now: print the arrays in a text grid aligned like the puzzles
        //in future, display this as some GUI with clickable grids, look into awt/swing i guess
        int width = arrIn.get(0).size();
        int height = arrIn.get(1).size();
        
        int[][][] gridArr = new int[width][height][1];
        /*
            Array structure:
        [rows][columns][variable]
        variable indicates the stauts of the cell in the grid
        according values legend:
            0 - Undetermined
            1 - Filled (black space)
            2 - Flagged (crossed space)
        
        */
        
        //initiate array, set all variables to undetermined/0
        for(int x=0;x<width;x++){
            for(int y=0; y<height; y++){
                gridArr[x][y][0] = 0;
            }
        }
        
        
        
        //draw a grid... somewhere.
        
        //the exiting clause
        
        return;
    }
}
