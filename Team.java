import greenfoot.*;
import java.awt.Color;
import java.util.*;

public abstract class Team  
{
    private static Color[] colors = new Color[]{Color.red, Color.blue};//, Color.green};
    private static int cidx = 0;
    protected String name;
    protected Color color;
    protected Flag flag;
    protected Map map;
    protected int genPoints;
    protected final Integer[] memory;
    protected int ticks = 0;
    public Team(String name, Map map, int x, int y)
    {
        genPoints = 0;
        this.name = name;
        this.color = colors[cidx++ % 2];
        this.flag = new Flag(this.color);
        this.map = map;
        map.addObject(flag, x, y);
        List<Block> blocks = flag.blocksInRange(10);
        for(Block b: blocks){
           // if(Math.random() > 0.75){
                map.removeObject(b);
           // }
        }
       // blocks = flag.blocksInRange(5);
      //  for(Block b: blocks){
            //if(Math.random() > 0.5){
        //        map.removeObject(b);
            //}
      //  }
        memory = new Integer[10];
        for(int i = 0; i < memory.length; i++){
            memory[i] = null;
        }
        generator();
    }
    
    public Flag getFlag(){
        return flag;
    }
    
    public Color getColor(){
        return color;
    }
    
    public int getGenPoints(){
        return genPoints;
    }
    
    public abstract void generator();
    // public void generator(){
        // int x = flag.getX();
        // int y = flag.getY();
        // //if(color == Color.red){
            // map.addObject(new Bolter(this), x, y);
            
            // map.addObject(new FlagScout(this), x, y);
        // //    map.addObject(new Guard(this, x, y, 1), x, y);
       // // }
       
    // }
    
    public void receiveData(int index, int data){
        memory[index] = data;
    }
    
    public int sendData(int index){
        return memory[index];
    }
    
    public void getBonus(Bonus b){
        int value = b.getValue();
        genPoints += value;
    }
    
    public abstract void act();
    // public void act(){
        // if(ticks++ % 10000 == 0){
            // genPoints++;
        // }
        // if( genPoints > 6){
                // if(memory[0] != null){
                    // System.out.println("Add a guard");
                    // map.addObject(new Guard(this, memory[0], memory[1], 0), flag.getX(), flag.getY());
                    // //genPoints-=3;
                // } else {
                    // map.addObject(new FlagScout(this),flag.getX(), flag.getY());
                // }
                // genPoints-=6;
            // }
        // String mem = name+" Team Memory: ";
        // for(Integer m: memory){
            // if( m != null){
                // mem += m + " ";
            // }else{
                // mem += "- ";
            // }
        // }
    // }
    
    public class Guard extends Piece{
        private int gx, gy, gr;
        public Guard(Team team, int gx, int gy, int gr){
            super(team, 0, 5, 5, 0);
            memory[0] = 0;
            memory[1] = -1;
            memory[2] = -1;
            memory[3] = 0;
            this.gx = gx;
            this.gy = gy;
            this.gr = gr;
            img.setColor(Color.white);
            img.drawOval(3,3,4,4);
        }
        
        public void movement(){
            Flag flag = team.getFlag();
            boolean hoor  = getX() > gx + gr || getX() < gx - gr;
            boolean voor = getY() > gy + gr || getY() < gy - gr;
            int oldx = memory[1];
            int oldy = memory[2];
            if(oldx == getX() && oldy == getY()){
                memory[0]++;
            }
            if(memory[0] > 2){
                memory[3] = 1;
            } else if (memory[0] == 0){
                memory[3] = 0;
            }
            memory[1] = getX();
            memory[2] = getY();
            if(memory[3] == 1){
                memory[0]--;
                int dir = Greenfoot.getRandomNumber(4);
                if(dir == 0){
                    left();
                }else if(dir == 1){
                    right();
                }else if ( dir == 2){
                    up();
                }else{
                    down();
                }
            }else{
                if( hoor && voor ){
                    if (Math.random() > 0.5){
                        if(getX() > gx)
                            left();
                        else
                            right();
                    }else{
                        if(getY() > gy) 
                            up();
                        else
                            down();
                    }
                }else if (hoor){
                    if (getX() > gx)
                        left();
                    else
                        right();
                }else if (voor){
                    if (getY() > gy)
                        up();
                    else
                        down();
                }else{
                  //  super.movement();
                }
            }
            
        }
    }
    
    public class FlagScout extends Piece{
        
        public FlagScout(Team team){
            super(team, 5, 3, 2, 0);  
            img.setColor(Color.black);
            img.fillOval(2,2,6,6);
            
            memory[6] = team.getFlag().getX() > 30 ? 1 : 0;
            memory[5] = 0;
                
        }

        public void setNewDirection(boolean rand){
            if( (getX() == memory[1] && getY() == memory[2]) || (rand && Math.random() > 0.9)){
                int newDir;
                do {    
                    newDir = Greenfoot.getRandomNumber(4);
                }while( newDir == memory[0] || newDir == ((memory[0]+2)%4));
                memory[0] = newDir;
            }
        }
        
        
        
        public void normalMove(){
            if(memory[6] == 1 && getX() > 30){
                memory[0] = Math.random() > 0.5 ? 1 : getY() > 30 ? 0 : 2;
            } else if (memory[6] == 0 && getX() < 90){
                memory[0] = Math.random() > 0.5 ? 3 : getY() > 30 ? 0 : 2;;
            }
            setNewDirection(true);
            int dir = memory[0];
            memory[1] = getX();
            memory[2] = getY();
            if(dir == 0){
                up();
            }
            if (dir == 1){
                left();
            }    
            if (dir == 2)    {
                down();        
            }    
            if (dir == 3){
                right();
            }
        }
        
        public void homeMove(){
            Flag home = team.getFlag();
            int fx = flag.getX();
            int fy = flag.getY();
            int px = getX();
            int py = getY();
            if (memory[1] == getX() && memory[2] == getY()){
                memory[0] = Greenfoot.getRandomNumber(4);   
            } else {
                if(Math.random() > 0.5){
                    if( px > fx ){
                        memory[0] = 1;
                    } else if (px < fx){
                        memory[0] = 3;
                    }
                } else {
                    if( py > fy ){
                        memory[0] = 0;
                    } else if (py < fy){
                        memory[0] = 2;
                    }
                }
            }
            
            memory[1] = getX();
            memory[2] = getY();
            if(memory[0] == 0){
                    up();
                }
            if (memory[0] == 1){
                left();
            }
            if (memory[0] == 2){
                down();
                
            }
            if (memory[0] == 3){
                right();
            }
        } 
        public void movement(){
            if (memory[5] == 1){
                homeMove();
                
                sendDataToTeam(0,memory[3]);
                sendDataToTeam(1,memory[4]);
            } else {
                normalMove();
                Flag otherFlag = searchForFlag();
                if(otherFlag != null){
                    memory[3] = otherFlag.getX();
                    memory[4] = otherFlag.getY();
                    memory[5] = 1;
                }
            }
                    }
        
    }
    
    public class Bolter extends Piece{
        public Bolter(Team team){
            super(team, 5, 3, 2, 0);
            memory[0] = Greenfoot.getRandomNumber(4);
            memory[1] = -1;
            memory[2] = -1;
            
            img.setColor(Color.white);
            img.fillOval(2,2,6,6);
        }
        
        public void act(){
            super.act();        
            
        }
        
        public void movement(){
            
            List<Bonus> bonuses = searchForBonus();
            if (getX() == memory[1] && getY() == memory[2]){
                changeDirection();
            } else if( bonuses.size() > 0){
                Bonus b = bonuses.get(0);
                int dx, dy;
                int x = getX(), y = getY();
                int bx = b.getX();
                int by = b.getY();
                if ( Math.random() > 0.5){
                    if( bx > x )
                        memory[0] = 3;
                    else if ( bx < x )
                        memory[0] = 1;
                }else{
                    if( by > y )
                        memory[0] = 2;
                    else if ( by < y )
                        memory[0] = 0;
                        
                }
            } else {
                if(Math.random() > 0.9){
                    changeDirection();
                }
            }
            normalMove();
        }
        
        public void normalMove(){
                int dir = memory[0];
                memory[1] = getX();
                memory[2] = getY();
                if(dir == 0){
                    up();
                }
                if (dir == 1){
                    left();
                }
                if (dir == 2){
                    down();
                    }
                if (dir == 3){
                    right();
                }
             
        }
        
        public void changeDirection(){
            int dir;
            do {
                dir = Greenfoot.getRandomNumber(4);
            }while(dir == memory[0] || dir == (memory[0] + 2) % 4);
            memory[0] = dir;
        }
    }
}

