import greenfoot.*;
import java.awt.Color;
import java.util.*;
public class T2 extends Team 
{
    public T2(String name, Map map, int x, int y){
        super(name, map, x, y);
    }

    public void generate(){
        build(new Rumbler(this));
    }

    public void doStuff(){

        String mem = name+" Team Memory: ";
        for(Integer m: memory){
            if( m != null){
                mem += m + " ";
            }else{
                mem += "- ";
            }
        }
    }
    //Team team, int see, int step, int fight, int comms
    public class Rumbler extends Piece{
        public Rumbler(Team team){
            super(team, 3, 3, 3, 0);
        }
        
        public void movement(){
            int dir = Greenfoot.getRandomNumber(4);
            switch(dir){
                case 0: up(); break;
                case 1: down(); break;
                case 2: ;//left(); break;
                case 3: right(); break;
            }
        }
        
        
    }
}

