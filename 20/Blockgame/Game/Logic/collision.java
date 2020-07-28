package Game.Logic;

import Game.Bricks.brick;
import Game.Bricks.Square;
import Game.*;

public class collision extends playingfield{

    // Checkt ob in den ersten 4 Reihen ein Block fixiert wurde
    // Wenn das der Fall ist, wird true zurueckgegeben (also Spiel verloren)
    public static boolean checkGameOver(playingfield panel) {
        for (int i = 0; i < 4; i++) {
            for (int y = 0; y < panel.getPlayingField()[0].length; y++) {
                if (panel.getPlayingField()[i][y] < 0) {
                    return true;
                }
            }
        }
        return false;
    }
    


    public static boolean canMove(int[][] panel, int richtung, brick b) {
        for (Square sq : b.getBricks()){
            if(richtung == 39){
                if(panel[sq.getY()][sq.getX()+1] == 9 || panel[sq.getY()][sq.getX()+1] < 0 ){
                    return false;
                }
            }
            if(richtung == 37){
                if(panel[sq.getY()][sq.getX()-1] == 9 || panel[sq.getY()][sq.getX()-1] < 0 ){
                    return false;
                }
            }
        }

        return true;
    }

    public static void brickMove(int richtung, brick b){
        //39 ist keycode von keyEvent.VK_RIGHT 
        if(richtung == 39){
            for(Square sq : b.getBricks()){
                sq.setX(sq.getX()+1);
                //startX+=1;
            }
        }
        //37 ist keycode von keyEvent.VK_LEFT  
        else if(richtung == 37){
            for(Square sq : b.getBricks()){
                sq.setX(sq.getX()-1);
                // startX-=1;
            }
        }
    }

    public static boolean can_move_down(playingfield panel, brick b) {
        for (Square sq : b.getBricks()) {
            if(panel.getPlayingField()[sq.getY()+1][sq.getX()] == 9 || panel.getPlayingField()[sq.getY()+1][sq.getX()] < 0 ){
                return false;
            }
        }
        return true;
    }

    public static void brickDown(brick b){
        for(Square sq : b.getBricks()){
            sq.setY(sq.getY()+1);
        }
    }

}
