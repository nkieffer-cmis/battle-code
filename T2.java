import java.awt.Color;
public class T2 extends Team 
{
    public T2(String name, Map map, int x, int y){
        super(name, map, x, y);
    }

    public void generator(){
        int x = flag.getX();
        int y = flag.getY();

        // map.addObject(new Bolter(this), x, y);

        map.addObject(new FlagScout(this), x, y);
        //   map.addObject(new Guard(this, x, y, 1), x, y);

    }

    public void act(){
        if(ticks++ % 10000 == 0){
            genPoints++;
        }
        if( genPoints > 6){
            if(memory[0] != null){
                System.out.println("Add a guard");
                map.addObject(new Guard(this, memory[0], memory[1], 0), flag.getX(), flag.getY());
                //genPoints-=3;
            } else {
                map.addObject(new FlagScout(this),flag.getX(), flag.getY());
            }
            genPoints-=6;
        }
        String mem = name+" Team Memory: ";
        for(Integer m: memory){
            if( m != null){
                mem += m + " ";
            }else{
                mem += "- ";
            }
        }
    }
}

