package Game.Bricks;

/**
 * Square - primitiv Element fuer Bricks (Figures) 
 */
public class Square {

    private int x;
    private int x0;
    private int y;

    public Square(int x, int y, int x0){
        setX(x);
        setX0(x0);
        setY(y);
    }
   

    //setters und getters
    public void setX(int x){
        this.x = x;
    }

    public void setX0(int x0) {this.x0 = x0;}

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getX0(){
        return this.x0;
    }

    public int getY(){
        return this.y;
    }

}