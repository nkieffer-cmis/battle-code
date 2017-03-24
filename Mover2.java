import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Mover2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mover2 extends Actor
{
    int route = 0;
    int steps = 0;
    int stepMask = 0;
    int playback = 15;
    boolean forward = true;
    //101010101010101010101010101010
    public void act() 
    {

        int d = -1;
        if(forward){//steps <= 15){
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
                    stepMask = stepMask == 0 ? 0b11 : stepMask << 2;
                    steps++;
                }
                forward = steps <= 15;
            }
        } else{
            
            stepMask >>= 2;
            d = ((route & stepMask) >> (steps * 2) & 0b11);
            steps--;

            if ( steps < 1 ){
                stepMask = 0;
                route = 0;
                forward = true;  
            }

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
        System.out.println(steps);
        System.out.println(forward);
        System.out.println(Integer.toBinaryString(stepMask));
        System.out.println(Integer.toBinaryString(route));
        System.out.println(Integer.toBinaryString((route & stepMask) >> ((steps-1)*2)));

    }    
}

//10110001
//11000000
//10000000
