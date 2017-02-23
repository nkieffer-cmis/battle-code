import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

public class Bonus extends Actor
{
    private GreenfootImage img;
    private int value;
    public Bonus(){
        img = new GreenfootImage(10,10);
        value = Greenfoot.getRandomNumber(10) + 1;
        img.setColor(Color.cyan);
        img.fillOval(0,0,10,10);
        img.setColor(Color.black);
        img.drawString(""+value, 0,0);
        setImage(img);
    }
    
    public void setLocation(int x, int y){
        
    }
    
    public int getValue(){
        return value;
    }
    
    public void act() 
    {
        // Add your action code here.
    }    
}
