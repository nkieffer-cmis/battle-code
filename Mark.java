import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Write a description of class Mark here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mark extends Actor
{
    /**
     * Act - do whatever the Mark wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage img;
    public Mark(){
        img = new GreenfootImage(10,10);
        img.setColor(Color.green);
        img.fill();
        setImage(img);
    }
}
