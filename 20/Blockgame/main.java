import javax.swing.*;
import Mainmenu.mainmenu;

public class main {

    public static void main(String[] args){

        JFrame mainFrame = new JFrame("Block Game");
        mainFrame.setSize(600,650);
        mainFrame.setVisible(true);

        mainmenu main = new mainmenu(mainFrame);
    }
}
