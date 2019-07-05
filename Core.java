import java.util.*;
/**
 * Driver for TicTacToe Game
 * 
 * @author Jason Drews
 * @version 1.0, 7.21.18
 */
public class Core
{
    private static Board board;
    private static AI cpu;
    private static Scanner input;
    private static int whoFirst, turnNum;

    public static void main(String[] args)
    {
        board = new Board();
        cpu = new AI();
        input = new Scanner(System.in);

        System.out.println("Welcome to Jason's Tic-Tac-Toe!\nUnfortunately, the computer will never lose.\nGood luck!"); 
        welcome();
    }

    public static void welcome()
    {
        board = new Board();
        cpu = new AI();
        turnNum = 0;

        whoFirst = 0;
        int count = 0;
        while(whoFirst != 1 && whoFirst != 2)
        {
            if(count > 0)
                System.out.println("Error: Please enter 1 or 2.");
            System.out.print("\nEnter 1 if you want to go first or 2 for the computer to go first: "); 
            whoFirst = input.nextInt();
            count++;
        }

        System.out.println();
        System.out.println("Make moves by inputing the number corresponding to the square you choose.");

        play(whoFirst);
    }

    public static void play(int first)
    {        
        System.out.print(board);
        //System.out.print(board.debug());
        if(board.hasWin())
            endgame(first);
        else if(turnNum == 9)
            endgame(0);
        else
        {
            if(first == 1)
            {
                boolean validMove = false;
                int error = 0;
                int move = 0;
                while(!validMove)
                {
                    if(error == 1)
                        System.out.println("Error: That space is already chosen.");
                    else if (error == 2)
                        System.out.println("Error: Please input only numbers between 1 and 9.");
                    System.out.print("Enter your move: ");
                    move = input.nextInt(); //!! Add valid number checker
                    if(move > 9 || move < 1)
                        error = 2;
                    else
                    if(isInOpen(move-1))
                        validMove = true;
                    else
                        error = 1;
                }
                board.makeMove(move, 1);
                System.out.println("\n\n");
                turnNum++;
                play(2);
            }
            else if (first == 2)
            {
                System.out.print("Thinking...");
                try    
                {
                    Thread.sleep(800);
                }
                catch(InterruptedException ex) 
                {
                    Thread.currentThread().interrupt();
                }
                board.makeMove(cpu.makeMove(board, turnNum++), 2);
                System.out.println("\n\n");
                play(1);
            }
        }
    }

    public static void endgame(int whoLost)
    {
        if(whoLost == 2)
            System.out.println("Wait, what?? You beat the computer!");
        else if (whoLost == 1)
            System.out.println("The computer won, but don't feel bad. It's only a game.");
        else
            System.out.println("Hmm, call it a draw? It is a tie.");

        System.out.println();
        String playAgain = "";
        boolean exit = false;
        int count = 0;
        while(!exit)
        {
            if(count > 0) //Has to clear nextLine once before taking input
                System.out.print("Want to play again? (yes/no): ");
            playAgain = input.nextLine();

            if(playAgain.equalsIgnoreCase("yes") || playAgain.equalsIgnoreCase("y"))
                welcome();
            else if(playAgain.equalsIgnoreCase("no") || playAgain.equalsIgnoreCase("n"))
            {
                System.out.println("Giving up are you? Have a nice day.");
                System.exit(0);
            }
            else if (count > 0) //Error message only prints after nextLine is set
                System.out.println("Error: Please enter yes or no.");
            count++;
        }
    }

    private static boolean isInOpen(int move)
    {
        ArrayList<Integer> openSqu = board.getOpen();
        if(openSqu.contains((Integer)move))
            return true;
        else
            return false;
    }
}
