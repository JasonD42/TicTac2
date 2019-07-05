import java.util.ArrayList;
import java.util.Random;
/**
 * Logic-based TicTacToe CPU (~AI)
 * 
 * @author Jason Drews 
 * @version 1.0, 7.21.18
 */
public class AI
{
    private Random gen = new Random();
    private int[][] winCombos = {{0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}};
    private static int win, pWin;

    public AI()
    {

    }

    public int makeMove(Board board, int turn)
    {
        ArrayList<Integer> open = board.getOpen();
        ArrayList<Integer> plyr = board.getPlyr();
        ArrayList<Integer> comp = board.getComp();

        ArrayList<Integer> options = new ArrayList<Integer>();

        if(turn < 2)
        {
            return firstMove(open, plyr, comp, turn);
        }
        else if(compCanWin(open,plyr,comp) != -1)
        {
            return win;
        }
        else
        {
            for(int i = 0; i < open.size(); i++)
            {
                if(notBadMove(open.get(i), open, plyr, comp))
                    options.add(open.get(i));
            }
            for(int i = 0; i < options.size(); i++)
            {
                if(canForceWin(options.get(i), open, plyr, comp))
                {
                    //System.out.println("Forced Win");
                    return options.get(i);
                }
            }
            System.out.println(options);
            return options.get(gen.nextInt(options.size()));
        }
    }

    /**
     * Method which handles all possible moves for the computer's first turn
     */
    private int firstMove(ArrayList<Integer> open, ArrayList<Integer> plyr, ArrayList<Integer> comp, int turn)
    {
        int[] first = {0,2,4,4,4,4,6,8};
        int[] second = {0,2,6,8};
        if(turn == 0)
            return first[gen.nextInt(8)];
        else
        {
            int plyrMove = plyr.get(0);
            if(plyrMove == 4)
                return second[gen.nextInt(4)];
            else
                return 4;
        }
    }

    private int compCanWin(ArrayList<Integer> open, ArrayList<Integer> plyr, ArrayList<Integer> comp)
    {
        win = -1;
        for(int[] winCombo: winCombos)
        {
            if((comp.contains((Integer)winCombo[0]) && comp.contains((Integer)winCombo[1])) && open.contains((Integer)winCombo[2]))
                win = winCombo[2];
            else if((comp.contains((Integer)winCombo[0]) && comp.contains((Integer)winCombo[2])) && open.contains((Integer)winCombo[1]))
                win = winCombo[1];
            else if((comp.contains((Integer)winCombo[1]) && comp.contains((Integer)winCombo[2])) && open.contains((Integer)winCombo[0]))
                win = winCombo[0];
        }
        return win;
    }

    private int plyrCanWin(ArrayList<Integer> open, ArrayList<Integer> plyr, ArrayList<Integer> comp)
    {
        pWin = -1;
        for(int[] winCombo: winCombos)
        {
            if((plyr.contains((Integer)winCombo[0]) && plyr.contains((Integer)winCombo[1])) && open.contains((Integer)winCombo[2]))
                pWin = winCombo[2];
            else if((plyr.contains((Integer)winCombo[0]) && plyr.contains((Integer)winCombo[2])) && open.contains((Integer)winCombo[1]))
                pWin = winCombo[1];
            else if((plyr.contains((Integer)winCombo[1]) && plyr.contains((Integer)winCombo[2])) && open.contains((Integer)winCombo[0]))
                pWin = winCombo[0];
        }
        return pWin;
    }

    /**
     * Set of methods to determine all moves which lead to a forced win for the computer.
     */
    private boolean canForceWin(int squ, ArrayList<Integer> open, ArrayList<Integer> plyr, ArrayList<Integer> comp)
    {
        ArrayList<Integer> tempOpen = new ArrayList<Integer>();
        ArrayList<Integer> tempPlyr = new ArrayList<Integer>();
        ArrayList<Integer> tempComp = new ArrayList<Integer>();
        for(Integer num: open)
            tempOpen.add(num);
        for(Integer num: plyr)
            tempPlyr.add(num);
        for(Integer num: comp)
            tempComp.add(num);

        int index = tempOpen.indexOf(squ);
        tempComp.add(tempOpen.remove(index));

        if(compHasDoubleWin(tempOpen,tempPlyr,tempComp))
            return true;
        else if(compCanWin(tempOpen,tempPlyr,tempComp) != -1)
        {
            int index2 = tempOpen.indexOf(win);
            tempPlyr.add(tempOpen.remove(index2));

            ArrayList<Integer> tempOptions = new ArrayList<Integer>();
            if(plyrCanWin(tempOpen, tempPlyr, tempComp) != -1)
                tempOptions.add(pWin);
            else
                for(int move: tempOpen)
                    tempOptions.add(move);
            //System.out.println(win + ", " + tempOptions);
            for(int i = 0; i < tempOptions.size(); i++)
            {
                int index3 = tempOpen.indexOf(tempOptions.get(i));
                tempComp.add(tempOpen.remove(index3));
                if(compHasDoubleWin(tempOpen,tempPlyr,tempComp))
                {
                    //System.out.println("One move from forced win");
                    return true;
                }
                else
                    tempOpen.add(tempComp.remove(tempComp.size()-1));
            }
            return false;
        }
        else
            return false;
    }    

    private boolean compHasDoubleWin(ArrayList<Integer> tempOpen, ArrayList<Integer> tempPlyr, ArrayList<Integer> tempComp)
    {
        int numWins;
        numWins = 0;
        for(int[] winCombo: winCombos)
        {
            if(((tempComp.contains((Integer)winCombo[0]) && tempComp.contains((Integer)winCombo[1])) && tempOpen.contains((Integer)winCombo[2])) ||
            ((tempComp.contains((Integer)winCombo[0]) && tempComp.contains((Integer)winCombo[2])) && tempOpen.contains((Integer)winCombo[1])) ||
            ((tempComp.contains((Integer)winCombo[1]) && tempComp.contains((Integer)winCombo[2])) && tempOpen.contains((Integer)winCombo[0])))
            {
                //System.out.println("WC: " + winCombo[0] + ", " + winCombo[1] + ", " + winCombo[2]);
                numWins++;
            }
        }
        if(numWins >= 2)
            return true;
        else
            return false;
    }

    /**
     * Set of methods to determine all moves which lead to a loss or forced loss.
     */
    private boolean notBadMove(int squ, ArrayList<Integer> open, ArrayList<Integer> plyr, ArrayList<Integer> comp)
    {
        ArrayList<Integer> tempOpen = new ArrayList<Integer>();
        ArrayList<Integer> tempPlyr = new ArrayList<Integer>();
        ArrayList<Integer> tempComp = new ArrayList<Integer>();
        for(Integer num: open)
            tempOpen.add(num);
        for(Integer num: plyr)
            tempPlyr.add(num);
        for(Integer num: comp)
            tempComp.add(num);

        int index = tempOpen.indexOf(squ);
        tempComp.add(tempOpen.remove(index));

        if(playerCannotForceWin(tempOpen,tempPlyr,tempComp))
            return true;
        else
            return false;
    }

    private boolean playerCannotForceWin(ArrayList<Integer> tempOpen, ArrayList<Integer> tempPlyr, ArrayList<Integer> tempComp)
    {
        if(plyrOneAway(tempOpen,tempPlyr,tempComp) || plyrHasDoubleWin(tempOpen, tempPlyr, tempComp))
            return false;
        else
            return true;
    }

    private boolean plyrOneAway(ArrayList<Integer> tempOpen, ArrayList<Integer> tempPlyr, ArrayList<Integer> tempComp)
    {
        for(int[] winCombo: winCombos)
        {
            if(((tempPlyr.contains((Integer)winCombo[0]) && tempPlyr.contains((Integer)winCombo[1])) && tempOpen.contains((Integer)winCombo[2])) ||
            ((tempPlyr.contains((Integer)winCombo[0]) && tempPlyr.contains((Integer)winCombo[2])) && tempOpen.contains((Integer)winCombo[1])) ||
            ((tempPlyr.contains((Integer)winCombo[1]) && tempPlyr.contains((Integer)winCombo[2])) && tempOpen.contains((Integer)winCombo[0])))
            {
                //System.out.println("WC: " + winCombo[0] + ", " + winCombo[1] + ", " + winCombo[2]);
                return true;
            }
        }
        return false;
    }

    private boolean plyrHasDoubleWin(ArrayList<Integer> tempOpen, ArrayList<Integer> tempPlyr, ArrayList<Integer> tempComp)
    {
        if(compHasDoubleWin(tempOpen, tempPlyr, tempComp))
            return false;
        ArrayList<Integer> tempOptions = new ArrayList<Integer>();
        if(compCanWin(tempOpen, tempPlyr, tempComp) != -1)
            tempOptions.add(win);
        else
            for(int move: tempOpen)
                tempOptions.add(move);

        int numWins;
        for(int i = 0; i < tempOptions.size(); i++)
        {
            int index = tempOpen.indexOf(tempOptions.get(i));
            tempPlyr.add(tempOpen.remove(index));

            //System.out.println(tempComp + ", " + tempPlyr);
            numWins = 0;
            for(int[] winCombo: winCombos)
            {
                if(((tempPlyr.contains((Integer)winCombo[0]) && tempPlyr.contains((Integer)winCombo[1])) && tempOpen.contains((Integer)winCombo[2])) ||
                ((tempPlyr.contains((Integer)winCombo[0]) && tempPlyr.contains((Integer)winCombo[2])) && tempOpen.contains((Integer)winCombo[1])) ||
                ((tempPlyr.contains((Integer)winCombo[1]) && tempPlyr.contains((Integer)winCombo[2])) && tempOpen.contains((Integer)winCombo[0])))
                {
                    //System.out.println("WC: " + winCombo[0] + ", " + winCombo[1] + ", " + winCombo[2]);
                    numWins++;
                }
            }
            tempOpen.add(tempPlyr.remove(tempPlyr.size()-1));
            if(numWins >= 2)
                return true;
        }
        return false;
    }
}
