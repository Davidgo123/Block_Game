package Game;

import Game.Bricks.Square;
import Game.Bricks.brick;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class playingfield extends JPanel {

    // Grafik zum Malen/ausgeben
    Graphics2D g1;

    //ArrayList fuer neu erstelle Bricks
    ArrayList<brick> bricks = new ArrayList<brick>();

    int level = 0;
    int score = 0;

    // Counter fuer Brick Arraylist, zeigt auf aktuellen Brick
    int counter = 0;

    // Next Brick Feld
    protected int[][] next_brick =
                    {{0,0,0,0},
                     {0,0,0,0},
                     {0,0,0,0},
                     {0,0,0,0}};

    // Das Spielfeld mit Außsenmauern,
    // die ersten 3 Reihen sind nicht sichtbar im Spiel
    protected int[][] panel = {
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,9,9,9,9,9,9,9,9,9,9,9}
    };

    // Getter Playingfield
    public int[][] getPlayingField(){
        return this.panel;
    }
    // Getter Level
    public int getLevel() { return this.level; }
    // Setter Levell
    public void setLevel(int level) { this.level = level; }
    // Getter Score
    public int getScore() { return this.score; }
    // Setter Score
    public void setScore(int score) { this.score = score; }

    // Funktion um das Spielfeld GUI auszugeben
    @Override
    public void paint(Graphics g){

        //Graphen erstellung zum erstellen des Feldes/Score
        super.paint(g);
        g1 = (Graphics2D) g;

        // Linie, ab der man verliert
        g.setColor(Color.red);
        g.drawLine(60, 55, 320, 55);

        // Zaehlervariable fuer Horzontale
        int x = 0;
        // Zaehlervariable fuer die Vertikale
        int y = 30;

        //Schleife fuer die Gittererstellung des Spielfeldes
        for (int i = 3; i <= 24; i++) {
            // Springt auf Position fuer den ersten Block in der Reihe (Horizontal)
            x = 35;
            for (int j = 0; j <= 11; j++) {
                switch (panel[i][j]) {

                    // Visuelle Ausgabe des Aussenwandteils
                    case 9:
                        g1.setColor(Color.darkGray);
                        g1.drawRect(x, y, 25, 25);
                        g1.setColor(Color.lightGray);
                        g1.fillRect(x + 1, y + 1, 23, 23);
                        break;
                    // Festlegen der Farbe des Blocks
                    case 1:
                    case -1: g1.setColor(Color.yellow); break;

                    case 2:
                    case -2: g1.setColor(Color.green); break;

                    case 3:
                    case -3: g1.setColor(Color.red); break;

                    case 4:
                    case -4: g1.setColor(Color.blue); break;

                    case 5:
                    case -5: g1.setColor(Color.darkGray); break;

                    case 6:
                    case -6: g1.setColor(Color.cyan); break;

                    case 7:
                    case -7: g1.setColor(Color.magenta); break;
                }

                // Visuelle Ausgabe des Blocks
                if (panel[i][j] != 0 && panel[i][j] != 9) {
                    g1.fillRect(x + 1, y + 1, 23, 23);
                    g1.setColor(Color.black);
                    g1.drawRect(x, y, 25, 25);
                }

                // Springt einen Block nach Rechts (Horizintal)
                x += 25;
            }
            // Am Ende der Reihe beginnt es einen Block weiter unten (Vertikal)
            y += 25;
        }

        // Zaehlervariable fuer das Scoreboard (Horizontal)
        int x_1 = x;
        // Zaehlervariable fuer das Scoreboard (Vertikal)
        int y_1 = 220;

        // Formatierung fuer das next-Brick-Feld
        float[] dash1 = {10.0f};
        BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 20.0f, dash1, 0.0f);
        g1.setColor(Color.darkGray);
        g1.setStroke(dashed);
        //Ausgabe des next-Brick-Feld
        g1.drawRect(x_1+72-5,y_1-5,25*4+10,25*4+10);

        // Formatierung zuruecksetzen
        g1.setStroke(new BasicStroke(1));

        //Schleife fuer das Visuelle Ausgeben der Teilbricks vom next-Brick-Feld (5x4 Feld)
        for (int i = 0 ; i < 4; i++) {
            // Springt auf Position fuer den ersten Block in der Reihe (Horizontal)
            x_1 = x;
            for (int j = 0; j < 4; j++) {

                // Festlegen der Farbe des next-Blocks
                switch (next_brick[i][j]) {
                    case 1:
                    case -1: g1.setColor(Color.yellow); break;

                    case 2:
                    case -2: g1.setColor(Color.green); break;

                    case 3:
                    case -3: g1.setColor(Color.red); break;

                    case 4:
                    case -4: g1.setColor(Color.blue); break;

                    case 5:
                    case -5: g1.setColor(Color.darkGray); break;

                    case 6:
                    case -6: g1.setColor(Color.cyan); break;

                    case 7:
                    case -7: g1.setColor(Color.magenta); break;
                }

                // Ausgabe des next-Bricks
                if (next_brick[i][j] != 0) {
                    g1.fillRect(x_1+72+1, y_1+1, 23, 23);
                    g1.setColor(Color.black);
                    g1.drawRect(x_1+72,y_1,25,25);
                }


                // Springt einen Block nach Rechts (Horizintal)
                x_1 += 25;
            }
            // Am Ende der Reihe beginnt es einen Block weiter unten (Vertikal)
            y_1 += 25;
        }

        // Ausgabe des Scoreboard-Feldes
        g1.setColor(Color.darkGray);
        g1.setStroke(dashed);
        g1.drawRect(x + 25 ,30,200,140);
        // Ausgabe des Scoreboard-Textes
        g1.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g1.drawString("  Score: " + score, x+40, 80);
        // Ausgabe des Level-Textes
        g1.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g1.drawString("Level:  " + level, x+60, 140);

    }

    // Generiert einen neuen Random Block und gibt diesen zurueck
    public brick generate_Block() {
        return new brick();
    }

    // Gibt den aktuellen Block auf dem Feld zurueck
    public brick getbr(){
        return this.bricks.get(counter);
    }


    // Inizialisiert das Spielfeld mit den ersten beiden Steinen (aktuellen und nextBrick)
    public void ini() {
        for (int i = 0; i < 2; i++) {
            bricks.add(generate_Block());
        }
        setBrickInField(bricks.get(counter));
        setNextBrickField(bricks.get(counter+1));
    }

    // Erstellt einen neuen Block und setzt den next Block aufs Feld
    // Next Block wird neu ins Next Feld eingefuegt
    public void setNextBrick() {
        next_brick = resetNextBrickField();
        bricks.add(generate_Block());
        counter++;
        setBrickInField(bricks.get(counter));
        setNextBrickField(bricks.get(counter+1));
    }


    // Fuegt einen Block im Spielfeld hinzu
    public void setBrickInField (brick b) {
        for(Square sq : b.bricks){
            panel[sq.getY()][sq.getX()] = b.getColor();
        }
    }

    // Loescht einen Block im Spielfeld
    public void resetBrickInField (brick b) {
        for(Square sq : b.bricks){
            panel[sq.getY()][sq.getX()] = 0;
        }
    }

    // Fuegt einen Block im next-feld hinzu
    public void setNextBrickField (brick b) {
        for(Square sq : b.bricks){
            next_brick[sq.getY()][sq.getX0()] = b.getColor();
        }
    }

    // Leert das Next Brick Field
    public int[][] resetNextBrickField () {
        int[][] next = {{0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}};
        return next;
    }

    // Testfunktion um das Spielfeld auszugeben
    public void printPlayingfield(playingfield panel) {
        int[][] field = panel.getPlayingField();

        for (int i = 0; i < field.length; i++) {
            for (int y = 0; y < field[0].length; y++) {
                System.out.print("[" + field[i][y] + "]");
            }
            System.out.println("");
        }
        System.out.println("");
    }

}
