import greenfoot.*;
import java.awt.Color;
import java.util.*;
/**
 * Team class
 * 
 * <p>
 * Concrete classes that extent Team must define their own concrete classes to extend
 * Piece.
 * </p>
 * 
 * <p>
 * Concrete classes must override {@link Team#generate() generate}.
 * Concrete classes must override {@link Team#doStuff() doStuff}.
 * </p>
 */
public abstract class Team  
{
    private static Color[] colors = new Color[]{Color.red, Color.blue};//, Color.green};
    private static int cidx = 0;
    /**
     * Name of team
     */
    protected String name;
    /**
     * Team color
     * 
     * Used in map graphics.
     */
    protected Color color;
    /**
     * Team flag
     * 
     * Origin point for all new Pieces. Goal for enemy Pieces.
     */
    protected Flag flag;
    /**
     * Game map
     * 
     * Instance of {@see Map Map} used for current game.
     */
    private Map map;
    
    /**
     * Number of generation points.
     */
    private int genPoints;
    
    /**
     * Team memory array
     * 
     * Used to store information for use by the team. Length is 10 and cannot be
     * changed by concrete classes that inherit from Team
     */
    protected final Integer[] memory;
    
    /**
     * Number of game frames that have passed.
     */
    private int ticks = 0;
    
    /**
     * Constructor for Team class
     * 
     * @param String name of the team
     * @param Map instance of the gameboard
     * @param int x location of the team flag
     * @param int y location of the team flag
     */
    public Team(String name, Map map, int x, int y)
    {
        genPoints = 5;
        this.name = name;
        this.color = colors[cidx++ % 2];
        this.flag = new Flag(this.color);
        this.map = map;
        map.addObject(flag, x, y);
        List<Block> blocks = flag.blocksInRange(10);
        for(Block b: blocks){
            map.removeObject(b);
        }

        memory = new Integer[100];
        for(int i = 0; i < memory.length; i++){
            memory[i] = null;
        }
        generate();
    }

    /**
     * Sets up initial Pieces and values in Team.memory array.
     * 
     * Called by constructor.
     */
    public abstract void generate();

    /**
     * Controls incrementation and usage of genPoints.
     * 
     * Overridden in concrete classes to control how the team uses genPoints.
     */
    public abstract void doStuff();

    /**
     * Generates genPoints. Calls doStuff.
     */
    public void act(){
        ticks++;
        if(ticks % 500 == 0){
            genPoints++;
        }
        doStuff();
    }

    /**
     * Gets instance of {@see #Flag Flag} for this team
     */
    public int[] getFlagLocation(){
        return new int[]{flag.getX(), flag.getY()};
    }

    /**
     * Gets team color.
     * 
     * {@link java.awt#Color java.awt.Color}
     */
    public Color getColor(){
        return color;
    }

    /**
     * Gets current value of genPoints.
     */
    public int getGenPoints(){
        return genPoints;
    }

    /**
     * Builds a new Piece at the location of Team.flag
     * 
     * Also expends genPoints.
     * @param Piece instance of Piece to be added to map.
     */
    public final void build(Piece piece){
        int cost = piece.getCost();
        if( genPoints >= cost){
            map.addObject(piece, flag.getX(), flag.getY());
            genPoints -= cost;
        } else {
            //System.out.println("Can't build");
        }
    }

    /**
     * Called by instances of Piece store new data in Team.memory array.
     * 
     * @param int index of data to replace
     * @param int data to be placed in Team.memory
     */
    public void receiveData(int index, int data){
        memory[index] = data;
    }

    /**
     * Called by instances of Piece to retrieve data from Team.memory array.
     * 
     * @param int index of data to send
     */
    public int sendData(int index){
        return memory[index];
    }

    /**
     * Called by instances of Piece that have intersected with instances of Bonus.
     * 
     * Increments genPoints by the value of the Bonus instance.
     */
    public void getBonus(Bonus b){
        int value = b.getValue();
        genPoints += value;
    }

    /**
     * Print out current contents of Team.memory to terminal.
     */
    public void displayMemory(){
        String mem = name+" Team Memory: ";
        for(Integer m: memory){
            if( m != null){
                mem += m + " ";
            }else{
                mem += "- ";
            }
        }
        System.out.println(mem);
    }
}

