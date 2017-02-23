import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.Color;
/**
 * Write a description of class Map here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Map extends World
{

    /**
     * Constructor for objects of class Map.
     * 
     */
    private Flag redFlag;
    private Flag blueFlag;
    private int redScore = 0;
    private int blueScore = 0;
    private final int flagZone = 10;
    Team t1, t2, t3;
    private int roundN = 0;
    public Map()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(120, 60, 10); 

        init();
        //   t1 = new Team("Beans", this, 1,1);

    }

    public void init(){
        Random r = new Random(roundN++);
        List<Actor> actors = getObjects(null);
        for(Actor a: actors){
            removeObject(a);
        }
        int x1 = (int)(30 * r.nextDouble());//Greenfoot.getRandomNumber(30);
        int y1 = (int)(60 * r.nextDouble());//Greenfoot.getRandomNumber(60);
        int x2 = 90 + (int)(30 * r.nextDouble());//Greenfoot.getRandomNumber(30);
        int y2 = (int)(60 * r.nextDouble());//Greenfoot.getRandomNumber(60);

        for( int x = 0; x < 120; x++){
            for ( int y = 0; y < 30; y++){
                //!(x > flagZone || y > flagZone) ||
                if(  r.nextDouble() > 0.97 ){
                    addObject(new Block(), x, y);
                    addObject(new Block(), 120 - x, 60 - y);
                }
            }
        }

        List<Block> blocks = getObjects(Block.class);
        int[][] coords = new int[][]{{-1,-1},{0, -1},{1, -1},
                {-1,  0},        {1,  0},
                {-1,  1},{0,  1},{1,  1}};
        int newx, newy;
        for(Block b: blocks){
            for( int[] coord: coords){
                if(Math.random() > 0){
                    newx = b.getX() + coord[0];
                    newy = b.getY() + coord[1];
                    addObject(new Block(), newx, newy);
                }
            }
        }
        if (Math.random() > 0.5){
            t1 = new T1("Rice", this, x1, y1);
            t2 = new T2("Sauce", this, x2, y2);
        }else{
            t1 = new T1("Rice", this, x2, y2);
            t2 = new T2("Sauce", this, x1, y1);
        }
        
        addObject(new Mark(), 30, 30);

    }

    public void score(Color color){
        if (color == Color.red){
            redScore++;
        }else{
            blueScore++;
        }
    }

    public void act(){
        t1.act();
        t2.act();
        if(Math.random() > 0.999){
            int x = 30 + Greenfoot.getRandomNumber(60);
            int y = Greenfoot.getRandomNumber(60);
            if(getObjectsAt(x,y, null).size() == 0)
                addObject(new Bonus(),x ,y);
        }
        showText(String.format("Red: %s %d Blue: %s %d", redScore, t1.getGenPoints(),
                blueScore, t2.getGenPoints()),30, 39); 

    }
}
