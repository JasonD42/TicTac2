
/**
 * Square with a location and state
 * 
 * @author Jason Drews 
 * @version 1.0, 7.12.18
 */
public class Square
{
    private int loc, state;
    private String[] lines;
    private String[] x = {"  \\  /  ",
                          "   \\/   ",
                          "   /\\   ",
                           " /  \\  "};
    private String[] o = {"   /\\   ",
                          "  /  \\  ",
                          "  \\  /  ",
                           "  \\/   "};
    private String[] e = {"        ",
                          "        ",
                          "        ",
                           "       "};

    public Square(int l)
    {
        loc = l;
        state = 0;
        lines = e;
    }
    
    public int getLoc()
    {
        return loc;
    }

    /**
     * State 0 if open
     * State 1 if player's square
     * State 2 if computer's square
     */
    public void setState(int s)
    {
        state = s;
        if(s == 1)
            lines = x;
        else
            lines = o;
    }
    public int getState()
    {
        return state;
    }
    
    public String getLine(int num)
    {
        return lines[num];
    }
}
