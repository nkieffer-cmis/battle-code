import greenfoot.*;
import java.awt.Color;
import java.util.*;
/**
 * memory[0] => moveLeft
 * memory[1] => last x position
 * memory[2] => movement state (0 = stuck, 1 = progressing, >=2 = seeking a path
 */
public class T1 extends Team 
{
    public T1(String name, Map map, int x, int y){
        super(name, map, x, y);
    }

    public void generate(){
        //        Team team, int see, int step, int fight, int comms
        int[] fl = getFlagLocation();
        Generic g = null;
        for(int i = 0; i < 10; i++){
            g = new Generic(this, 0, 1, 0, 0);
            g.memory[0] = fl[0] > 60 ? 1 : 0;
            g.memory[2] = 20 * i;
            build(g);
        }
    }

    public void doStuff(){

        
    }

    public class Generic extends Piece{
        public Generic(Team team, int see, int step, int fight, int comms){
            super(team, see, step, fight, comms);
        }

        public void movement(){
            System.out.println(String.format("%d %d %d %d", getX(), memory[0], memory[1], memory[2]));
            boolean moveRight= memory[0] == 0;
            if( moveRight ){
                if( memory[2] == 0 ){
                    left();
                    memory[2] = Greenfoot.getRandomNumber(5);
                }else if ( memory[2] >= 2){
                    if ( Math.random() > 0.5)
                        up();
                    else
                        down();
                    memory[2]--;
                }else{
                    right();
                    memory[2] = memory[1] == getX() ? 0 : 1;
                }

            }else{
                if( memory[2] == 0 ){
                    right();
                    memory[2] = Greenfoot.getRandomNumber(5);
                }else if (memory[2] >= 2){
                    if ( Math.random() > 0.5)
                        up();
                    else
                        down();
                    memory[2]--;
                }else{
                    left();
                    memory[2] = memory[1] == getX() ? 0 : 1;
                }
            } 
            memory[1] = getX();
            if ( !moveRight && getX() <= 0 ){
                System.out.println("Move Right");
                memory[0] = 0;
                memory[1] = -1;
                memory[2] = 1;
            }
            if (moveRight && getX() >= 119){
                System.out.println("Move Left");
                memory[0] = 1;
                memory[1] = -1;
                memory[2] = 1;
            }
        }
    }
}

