import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Mover here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mover extends Actor
{
    int route = 0;
    int steps = 0;
    int stepMask = 0b11000000000000000000000000000000;
    int playback = 15;
    //101010101010101010101010101010
    public void act() 
    {

        int d = -1;
        if(steps <= 15){
            String k = Greenfoot.getKey();           
            int r = -1;
            if( k != null){
                if(k.equals("w")){
                    d = 1;
                    r = 0;
                }
                if(k.equals("s")){
                    d = 0;
                    r = 1;
                }
                if(k.equals("a")){
                    d = 3;
                    r = 2;
                }
                if(k.equals("d")){
                    d = 2;
                    r = 3;
                }

                if( d >= 0){
                    route |= r << (steps * 2);
                    steps++;

                }
            }
        } else if ( playback > 0 ) {
            d = ((route & stepMask) >> (playback*2) & 0b11);
            stepMask >>= 2;
            playback--;
        } else {
            route = 0;
            steps = 0;
            playback = 15;
        }
        if(d == 1){
            setLocation(getX(), getY() - 1);
            //d = 0;
        }
        if(d == 0){
            setLocation(getX(), getY() + 1);
            //d = 1;
        }
        if(d == 3){
            setLocation(getX() - 1, getY());
            //d = 2;
        }
        if(d == 2){
            setLocation(getX() + 1, getY());
            //d = 3;
        }
        //if(route > 0)
        // System.out.println(steps + " "+ Integer.toBinaryString(route) + " " + Integer.toBinaryString(stepMask));

    }    
}

//10110001
//11000000
//10000000