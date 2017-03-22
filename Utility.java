public class Utility  
{
    public static int[] decodeCoords(int coordInt){
        int x = (0b11111110000000 & coordInt) >> 7;
        int y = 0b00000001111111 & coordInt;
        return new int[]{x, y};
    }
    
    public static int encodeCoords(int x, int y){
        return (x << 7) | y;
    }
}
