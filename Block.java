import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Write a description of class Block here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Block extends Actor
{
    private GreenfootImage img;
    public Block(){
        img = new GreenfootImage(10,10);
        img.setColor(Color.black);
        img.fillRect(0,0,10,10);
        setImage(img);
    }
    public void act() 
    {
        // Add your action code here.
    }   
    
    public void destroy(){
        World w = getWorld();
        w.removeObject(this);
    }
}
