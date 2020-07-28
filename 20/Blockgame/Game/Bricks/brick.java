package Game.Bricks;

import java.util.ArrayList;
import java.util.Random;

public class brick {
    /*Spalten basiert
      jedes Array stellt eine Spalte dar
      letzte array in jede zeile ist anzahl der primitiven Elementen (Squares) oder einfach groesse eines Figures
    */
    public final int[][][] figureCoord = {
        {{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}, {4}}, // I
        {{0,2,2,0}, {0,2,2,0}, {0,0,0,0}, {0,0,0,0}, {4}}, // O
        {{3,0,0,0}, {3,3,3,0}, {0,0,0,0}, {0,0,0,0}, {3}}, // J
        {{0,0,4,0}, {4,4,4,0}, {0,0,0,0}, {0,0,0,0}, {3}}, // L
        {{0,5,5,0}, {5,5,0,0}, {0,0,0,0}, {0,0,0,0}, {3}}, // S
        {{6,6,6,0}, {0,6,0,0}, {0,0,0,0}, {0,0,0,0}, {3}}, // T
        {{7,7,0,0}, {0,7,7,0}, {0,0,0,0}, {0,0,0,0}, {3}}  // Z
    };
    //speichern die Nummer des Blockes falls es square ist
    private int figureNum;
    private int brick_type;
    private int brick_size;

    //array zum speichern aktuellen Figure
    private int[][] form = new int [4][4];

    // starting coords fuer figure
    Random generator = new Random();
    int r = generator.nextInt(8);
    private int startX = r+1;
    private int startX0 = 1;
    private int startY = 0;
    public ArrayList<Square> bricks = new ArrayList<Square>();
    Random rand = new Random();
    int color;

    public int getColor() { return this.color; }

    public void setColor(int c) { this.color = c; }

    /// Copy Konstruktor
    public brick(brick b){
        this.figureNum = b.figureNum;
        this.brick_type = b.brick_type;
        this.brick_size = b.brick_size;
        this.form = b.form;
        this.r = b.r;
        this.startX = b.startX;
        this.startX0 = b.startX0;
        this.startY = b.startY;
        this.bricks = b.bricks;
        this.rand = b.rand;
        this.generator = b.generator;
        this.color = b.color;
    }

    public ArrayList<Square> getBricks () { return this.bricks; }

    public brick(){
        brick_type = rand.nextInt(figureCoord.length);
        brick_size = figureCoord[brick_type][4][0];
        figureNum = figureCoord[brick_type][0][1];
        //kopieren coordinates des Figures von "figureCoord" arr in "form" array
        for(int i = 0; i<brick_size; i++){
            System.arraycopy(figureCoord[brick_type][i], 0 , form[i], 0, figureCoord[brick_type][i].length);
        }
        makeFigure();
    }
    //geht die ganze array durch und erstellt eine Figure
    public void makeFigure(){
        for(int i = 0; i<brick_size; i++){
            for(int j = 0; j<brick_size; j++){
                //if coordinate in form = 1 -> erstllen ein Sqare in this coordinate
                if(form[i][j] >= 1){
                    color = form[i][j];
                    bricks.add(new Square(i+this.startX, j+this.startY, i+this.startX0));
                }
            }
        }
    }
    public int getSize(){
        return this.brick_size;
    }
    public int getX(){
        return this.startX;
    }
    public int getY(){
        return this.startY;
    }
    public void setStartY(){
        startY++;
    }
    public int[][] getForm(){
        return this.form;
    }

    public void setStartX(int richtung){
        if(richtung == 1){
            startX++;
        }
        else{
            startX--;
        }
    }


    public void brickRotate(){
        for(int i = 0; i<brick_size/2; i++){
            for(int j = i; j<brick_size-1-i; j++ ){
                int tmp = form[brick_size -1-j][i];
                form[brick_size-1-j][i] = form[brick_size-1-i][brick_size-1-j];
                form[brick_size-1-i][brick_size-1-j] = form[j][brick_size -1-i];
                form[j][brick_size -1-i] = form[i][j];
                form[i][j]=tmp;
            }
        }
        bricks.clear();
        makeFigure();
    }
}
