import java.awt.Color;
public class T2 extends Team 
{
    public T2(String name, Map map, int x, int y){
        super(name, map, x, y);
    }

    public void generator(){
        build(new Guard(this, 15, 15, 15));
        build(new Guard(this, 15, 45, 15));
    }

    public void doStuff(){
        if( getGenPoints() > 6){
            if(memory[0] != null){
                build(new Guard(this, memory[0], memory[1], 0));
            } else {
                build(new FlagScout(this));
            }
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

