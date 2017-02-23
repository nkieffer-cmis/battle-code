import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.*;
/**
 * Write a description of class Flag here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Flag extends Actor
{
    /**
     * Act - do whatever the Flag wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private GreenfootImage img;
    private Color color;
    private int rounds;
    public Flag(Color color){
        this.color = color;
        img = new GreenfootImage(10,10);
        img.setColor(color);
        img.fillRect(0,0,10,10);
        setImage(img);
        rounds = 0;
    }
    
    public List<Block> blocksInRange(int range){
        return getNeighbours(range, true, Block.class);
    }
        
    
    public void act() 
    {
        rounds++;
        
        Map w = (Map)getWorld();
        Piece p = (Piece)getOneIntersectingObject(Piece.class);
        if(p != null && p.getColor() != color){
            w.score(p.getColor());
            w.init();
        } 
        
        //List<Piece> collidors = getIntersectingObjects(Piece.class);
        //for(Piece c : collidors){
            
        //}
    }  
    
    public Color getColor(){
        return color;
    }
}
