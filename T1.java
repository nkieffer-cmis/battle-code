import java.awt.Color;
public class T1 extends Team 
{

    public T1(String name, Map map, int x, int y){
        super(name, map, x, y);
    }

    public void generator(){
        build(new Guard(this, 15, 15, 10));
    }

    public void doStuff(){
        if( getGenPoints() > 6){
            if(memory[0] != null){
                build(new Guard(this, memory[0], memory[1], 0));
            } else {
                build(new FlagScout(this));
            }
        }
    }
}

