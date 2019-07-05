import java.util.*;
/**
 * Creates TicTacToe Board and can check win conditions
 * 
 * @author Jason Drews
 * @version 1.0, 7.21.18
 */
public class Board
{
    private Square[] squares;
    private ArrayList<Integer> open, plyr, comp;
    
    private int[][] winCombos = {{0,1,2},{3,4,5},{6,7,8},
                                 {0,3,6},{1,4,7},{2,5,8},
                                 {0,4,8},{2,4,6}};

    /**
     * Constructor for objects of class Board
     */
    public Board()
    {
        squares = new Square[9];
        open = new ArrayList<Integer>();
        plyr = new ArrayList<Integer>();
        comp = new ArrayList<Integer>();
        
        int index = 0;
        for(int i = 0; i < 9; i++)
        {
            squares[i] = new Square(i);
            open.add(i);
        }
    }
    
    public void makeMove(int loc, int state)
    {   
        if(state == 1)
        {
            squares[loc-1].setState(state);
            int index = open.indexOf(loc-1);
            plyr.add(open.remove(index));
        }
        else
        {
            squares[loc].setState(state);
            int index = open.indexOf(loc);
            comp.add(open.remove(index));
        }
    }
    
    public boolean hasWin()
    {
        for(int[] winCombo: winCombos)
        {
            if(squares[winCombo[0]].getState() == squares[winCombo[1]].getState() && squares[winCombo[0]].getState() == squares[winCombo[2]].getState() && 
               squares[winCombo[0]].getState() != 0)
            {
                return true;
            }
        } 
        return false;
    }

    /**
     * who = 1 if checking for player win
     * who = 2 if checking for computer win
     */
    public void checkForWin(int who)
    {
        
    }
    
    public Square getS(int loc)
    {
        return squares[loc];
    }
    
    public ArrayList<Integer> getOpen()
    {
        return open;
    }
    public ArrayList<Integer> getPlyr()
    {
        return plyr;
    }
    public ArrayList<Integer> getComp()
    {
        return comp;
    }
    
    /**
     * Formats the board to be printed out on the screen after each turn.
     */
    public String toString()
    {
        String tempBoard = squares[0].getLine(0) + "|" + squares[1].getLine(0) + "|" + squares[2].getLine(0) + "\n" +
                           squares[0].getLine(1) + "|" + squares[1].getLine(1) + "|" + squares[2].getLine(1) + "\n" +
                           squares[0].getLine(2) + "|" + squares[1].getLine(2) + "|" + squares[2].getLine(2) + "\n" +
                           "1" + squares[0].getLine(3) + "|2" + squares[1].getLine(3) + "|3" + squares[2].getLine(3) + "\n" +
                           "--------------------------" + "\n" +
                           squares[3].getLine(0) + "|" + squares[4].getLine(0) + "|" + squares[5].getLine(0) + "\n" +
                           squares[3].getLine(1) + "|" + squares[4].getLine(1) + "|" + squares[5].getLine(1) + "\n" +
                           squares[3].getLine(2) + "|" + squares[4].getLine(2) + "|" + squares[5].getLine(2) + "\n" +
                           "4" + squares[3].getLine(3) + "|5" + squares[4].getLine(3) + "|6" + squares[5].getLine(3) + "\n" +
                           "--------------------------" + "\n" +
                           squares[6].getLine(0) + "|" + squares[7].getLine(0) + "|" + squares[8].getLine(0) + "\n" +
                           squares[6].getLine(1) + "|" + squares[7].getLine(1) + "|" + squares[8].getLine(1) + "\n" +
                           squares[6].getLine(2) + "|" + squares[7].getLine(2) + "|" + squares[8].getLine(2) + "\n" +
                           "7" + squares[6].getLine(3) + "|8" + squares[7].getLine(3) + "|9" + squares[8].getLine(3) + "\n\n\n\n\n\n";
        return tempBoard;             
    }
    
    public String debug()
    {
        String tempS = "O ";
        for(int squ: open)
            tempS += squ + ", ";
        tempS += "P ";
        for(int squ: plyr)
            tempS += squ + ", ";
        tempS += "C ";
        for(int squ: comp)
            tempS += squ + ", ";
        
        return tempS;
    }
}
