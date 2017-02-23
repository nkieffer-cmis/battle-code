import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.*;
/**
 * Write a description of class Piece here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public abstract class Piece extends Actor
{
    /**
     * Act - do whatever the Piece wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected GreenfootImage img, visible;
    private Color color;
    private int see;
    private int step;
    private int fight;
    private int comms;
    private int ticks;
    private static int nextId = 0;
    public final int id;
    protected Team team;
    //private int newx, newy;
    private int dir;
    private int[] oldLocation;
    private double actionPoints;
    protected int[] memory;
    public Piece(Team team, int see, int step, int fight, int comms){
        this.team = team;
        this.color = team.getColor();
        img = new GreenfootImage(10, 10);
        img.setColor(color);
        img.fillOval(0,0,10,10);
        visible= new GreenfootImage(img);
        visible.setColor(Color.yellow);
        visible.fillOval(2,2,6,6);
        setImage(img);
        this.see = see;//Greenfoot.getRandomNumber(20)+1;
        this.step = step;//Greenfoot.getRandomNumber(20)+1;
        this.fight = fight;//Greenfoot.getRandomNumber(20)+1;
        this.comms = comms;
        ticks = 1;
        id = nextId++;
        dir = Greenfoot.getRandomNumber(4);
        memory = new int[10];
        actionPoints = 2;
    }

    public Color getColor(){
        return color;
    }
    
    public void act() 
    {
      //  GreenfootImage mapimg = getWorld().getBackground();
        
      //  mapimg.setColor(color.darker());
      //mapimg.drawRect(getX()*10, getY() * 10, 10,10);
        markInvisible();
        if(ticks % step == 0){
            movement();
        }
        checkForBonus();
        checkVisibility();
        checkBattles();
        ticks++;   
       
        actionPoints = 2;
    } 
    
    private void checkForBonus(){
        Bonus b = (Bonus)getOneIntersectingObject(Bonus.class);
        if(b != null){
            team.getBonus(b);
            World w = getWorld();
            w.removeObject(b);
        }
    }
    
    private boolean willCollide(int newx, int newy){
        Block b = (Block) getOneObjectAtOffset(newx, newy, Block.class);
        return b != null;
    }
    
    protected final void up(){
        if ( actionPoints > 0 ){
            int newx = 0;
            int newy = -1;
            if(!willCollide(newx, newy)){
                setLocation(getX()+newx, getY()+newy);
            }
            actionPoints-=2;
        }
    }
    
    protected final void down(){
        if ( actionPoints > 0 ){
            int newx = 0;
            int newy = 1;
            if(!willCollide(newx, newy)){
                setLocation(getX()+newx, getY()+newy);
            }
            actionPoints-=2;
        }
    }  
    
    protected final void left(){
        if ( actionPoints > 0 ){
            int newx = -1;
            int newy = 0;
            if(!willCollide(newx, newy)){
                 setLocation(getX()+newx, getY()+newy);
            }
            actionPoints-=2;
        }
    } 
    
    protected final void right(){
        if ( actionPoints > 0 ){
            int newx = 1;
            int newy = 0;
            if(!willCollide(newx, newy)){
                 setLocation(getX()+newx, getY()+newy);
            }
            actionPoints-=2;
        }
    }   
    public abstract void movement();

    
    public void checkVisibility(){
        if(actionPoints > 0){
            List<Piece> vp = visiblePieces();
            if(vp.size() > 0){
                for(Piece p: vp){
                    if(p.getColor() != color){
                        p.markVisible();
                    }
                }
            }
            actionPoints--;
        }
    }
    
    public List<Bonus> searchForBonus(){
        if(actionPoints > 0){
            List<Bonus> bonuses = getObjectsInRange(see, Bonus.class);
            actionPoints--;
            return bonuses;           
        }
        return null;
    }
    
    public Flag searchForFlag(){
       // if(actionPoints > 0){
            Map map = (Map)getWorld();
            List<Flag> flags = getObjectsInRange(see, Flag.class);
            if(flags.size() > 0 ){
                Flag flag = flags.get(0);
                if(flag.getColor() != color){
                    return flag;
                }
            }
            return null;     
     //   }
      //  return null;
    }
    
    public List<Piece> visiblePieces(){
        if(actionPoints > 0){
            List<Piece> pieces = getNeighbours(see, true, Piece.class);
            actionPoints--;
            return pieces;
        }
        return null;
    }
   
    public void checkBattles(){
        Piece p = (Piece)getOneIntersectingObject(Piece.class);
        if(p != null){
            if(p.color != color){
                battle(p);
            }   
        }

    }
    
    public void battle(Piece other){
        World w = getWorld();
        Piece[] pieces = new Piece[]{this, other};
        Piece loser = pieces[Greenfoot.getRandomNumber(2)];
        if( this.fight > other.fight){
            loser = other;
            this.fight--;
        } else if( this.fight < other.fight){
            loser = this;
            loser.fight--;
        }
        w.removeObject(loser);
            
    }
    
    public void markVisible(){
        setImage(visible);
    }
    
    public void markInvisible(){
        setImage(img);
    }
    
    public boolean sendDataToTeam(int index, Integer data){
        
        if(true){//actionPoints > 0){
            boolean success = false;
            List<Flag> flags = getObjectsInRange(comms, Flag.class);
            for(Flag flag: flags){
                if(flag.getColor() == color){
                    team.receiveData(index, data);
                    success = true;
                }
            }
           // actionPoints--;
            return success;
        }
        return false;
    }
    public Integer getDataFromTeam(int index){
        if(actionPoints > 0){
            Integer data = null;
            List<Flag> flags = getObjectsInRange(comms, Flag.class);
            for(Flag flag: flags){
                if(flag.getColor() == color){
                    data = team.sendData(index);
                }
            }
            actionPoints--;
            return data;
        }
        return null;
    }
    
    public void viewMemory(){
        String mem = "";
        for(int m: memory){
            mem += m + " ";
        }
        System.out.println(mem);

    }
    public void turnTowards(int x, int y){
        
    }
    
    
}
