public class Utility  
{
    public static void p(){
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
    }
    public static int[] decodeCoords(int coordInt){
        int x = (0b11111110000000 & coordInt) >> 7;
        int y = 0b00000001111111 & coordInt;
        return new int[]{x, y};
    }
    
    public static int encodeCoords(int x, int y){
        return (x << 7) | y;
    }
    
    public static int[] oog(int i){ return null;}
}
//1111111111111111111111111111111
//1111111222222233333334444444000
//1111111000000000000000000000000
//0000000111111100000000000000000
//0000000000000011111110000000000
//0000000000000000000001111111000
//0000000000000000000000000000111