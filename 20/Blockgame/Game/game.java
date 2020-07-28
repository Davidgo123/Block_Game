package Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Game.Bricks.brick;
import Mainmenu.mainmenu;
import Game.music.*;

import java.io.*;
import java.util.ArrayList;
import Game.Logic.collision;

import Game.Bricks.Square;


// Klasse fuer die GUI
public class game {

    private SoundLoader sound1;
    private SoundLoader sound2;
    // Spielfeld/Score/brick
    playingfield panel;

    // Selbsterklärend
    boolean gameOver = false;
    boolean pause = false;
    boolean stoprun = false;

    //Fallpause in MS
    int delay=1000;

    private int scorerows = 9;
    private int scorecols = 3;
    private String filePath = System.getProperty("user.dir") + "/20/Blockgame/Leaderboard/scores.txt";

    Thread t;

    public game(JFrame mainFrame) {
        sound2 = new SoundLoader();
        sound2.loadmusic();
        sound2.playloop(SoundLoader.sound2);

        // Zuruecksetzen des Frames
        mainFrame.getContentPane().removeAll();
        mainFrame.setLayout(new BorderLayout());


        Container c = mainFrame.getContentPane();
        // Inizialisierung des Panels fuer das Spiel/Score/Brick
        panel = new playingfield();
        c.add(panel);

        // Erstellung des Play/Pause Buttons
        JButton play_pause = new JButton("Pause");
        play_pause.setBounds(360, 400, 200, 60);
        play_pause.setVisible(true);
        play_pause.setBackground(Color.white);
        panel.setLayout(null);
        panel.add(play_pause);
        // Aktion: Spiel pausieren
        play_pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!pause) {
                    pause = true;
                    sound2.stopsound(SoundLoader.sound2);
                    play_pause.setText("Play");
                } else {
                    pause = false;
                    sound2.playloop(SoundLoader.sound2);
                    play_pause.setText("Pause");
                    mainFrame.requestFocus();
                }

            }
        });

        // Erstellung des Menu Buttons
        JButton menu = new JButton("Menu");
        menu.setBounds(360, 470, 200, 60);
        menu.setVisible(true);
        menu.setBackground(Color.white);
        panel.setLayout(null);
        panel.add(menu);
        // Aktion: Wechsel zum Menu
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gameOver = true;
                sound2.stopsound(SoundLoader.sound2);
                mainmenu mainmenu = new mainmenu(mainFrame);
            }
        });


        // Groesse des Fensters
        mainFrame.setSize(600, 650);
        mainFrame.setVisible(true);
        // Programm beenden, wenn Programm geschlossen wird
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.requestFocus();
        mainFrame.repaint();

        // Inizialisierung des Spielfeldes
        panel.ini();
        // Setzt da Level und die Spielgeschwindigkeit
        checkLevel();

        t = new Thread() {
			@Override public void run() {
				while (!isInterrupted()) {
					try {
                        if(!gameOver && !pause){
						    Thread.sleep(delay);
                            checkLevel();
                            runGame();
                        } else if (gameOver && !stoprun) {

                            int posinlb = PosInLB(panel.getScore());
                            if(posinlb != -1) {
                                stoprun = true;

                                //Frame um den Namen fuer das Leaderboard einzugeben.
                                JFrame frame = new JFrame();
                                frame.getContentPane().removeAll();
                                frame.setLayout(null);
                                frame.setTitle("Enter Your Name");
                                frame.setLocationRelativeTo(null);
                                frame.setSize(300, 100);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                //Button um die Eingabe einzutragen.
                                JButton send = new JButton("Send");
                                send.setBounds(150, 20, 100, 20);
                                send.setVisible(true);
                                send.setBackground(Color.white);

                                //Textfeld zur Namenseingabe
                                JTextField textField = new JTextField();
                                textField.setBounds(10, 15, 120, 30);
                                textField.setVisible(true);
                                textField.setBackground(Color.white);

                                frame.add(textField);
                                frame.add(send);
                                frame.setVisible(true);

                                //Aktion fuer send Button, traegt in Leaderboard ein und geht ins Main Menu
                                send.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        mainmenu mainmenu = new mainmenu(mainFrame);
                                        String name = textField.getText();
                                        setLeaderboard(name, panel.getScore(), panel.getLevel(), posinlb);
                                        frame.setVisible(false);
                                    }
                                });
                            }
                        } else {
                            Thread.sleep(delay);
                        }
					} catch ( InterruptedException e ) {

                    }
				}
			}
		};

		t.start();


        /*KeyListener Interface für Empfang von Tastatur - events
        KeyAdapter - Klasse für Empfang von keyEvents
        */
        mainFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent pressed) {
                if (!gameOver || !pause) {
                    if (pressed.getKeyCode() == KeyEvent.VK_DOWN) {
                        while (collision.can_move_down(panel, panel.getbr())) {
                                panel.resetBrickInField(panel.getbr());
                                collision.brickDown(panel.getbr());
                        }
                    }
                    if (pressed.getKeyCode() == KeyEvent.VK_UP) {
                        if (collision.canMove(panel.getPlayingField(), pressed.getKeyCode(), panel.getbr()) && panel.getbr().getColor() > 0) {
                            // Quadrat muss sich nicht drehen koennen
                            if (panel.getbr().getColor() != 2) {
                                panel.resetBrickInField(panel.getbr());
                                // Prüft ob der Rotierte Block ins Spielfeld passt
                                // Wenn ja wird der Spielblock auch rotiert
                                panel.getbr().brickRotate();
                                checkRotate(panel.getbr());

                            }
                        }
                    }
                    if (pressed.getKeyCode() == KeyEvent.VK_LEFT) {
                        if (collision.canMove(panel.getPlayingField(), pressed.getKeyCode(), panel.getbr()) && panel.getbr().getColor() > 0) {
                            panel.resetBrickInField(panel.getbr());
                            collision.brickMove(pressed.getKeyCode(), panel.getbr());
                            panel.getbr().setStartX(0);
                        }
                    }

                    if (pressed.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (collision.canMove(panel.getPlayingField(), pressed.getKeyCode(), panel.getbr()) && panel.getbr().getColor() > 0) {
                            panel.resetBrickInField(panel.getbr());
                            collision.brickMove(pressed.getKeyCode(), panel.getbr());
                            panel.getbr().setStartX(1);
                        }
                    }

                    // Setzt neuen Block in Spielfeld
                    panel.setBrickInField(panel.getbr());
                    //Neuzeichnen von gui.Panel
                    panel.repaint();
                }
            }
        });
    }

    public void checkRotate (brick b) {
        for( int i = 0; i < panel.getbr().getBricks().size(); i++) {
            Square sq = panel.getbr().getBricks().get(i);
            if (panel.getPlayingField()[sq.getY()][sq.getX()] != 0 && panel.getPlayingField()[sq.getY()][sq.getX()] != panel.getbr().getColor()) {
                for (int y = 0; y < 3; y++) {
                    panel.getbr().brickRotate();
                }
            }
        }
    }

    // Setzt das Level und damit auch die Geschwindigkeit
    // abhaengig vom Score
    public void checkLevel() {
        if (panel.getScore() < 5) {
            panel.setLevel(0);
            delay = 1000;
        } else if (panel.getScore() >= 5 && panel.getScore() < 10) {
            panel.setLevel(1);
            delay = 800;
        } else if (panel.getScore() >= 10 && panel.getScore() < 15) {
            panel.setLevel(2);
            delay = 600;
        } else if (panel.getScore() >= 15 && panel.getScore() < 20) {
            panel.setLevel(3);
            delay = 400;
        } else if (panel.getScore() >= 20 && panel.getScore() < 25) {
            panel.setLevel(4);
            delay = 200;
        } else {
            panel.setLevel(5);
            delay = 150;
        }
    }

    // Fuehrt das Spiel aus, indem der aktuelle Block eine Etage tiefer plaziert wird
    // Wenn Block nicht tiefer kann, wird er an der Stelle fixiert
    // (im Array werden die Zahlen negativ in der Funktion can_move_down)
    public void runGame() {

        if (collision.can_move_down(panel, panel.getbr())) {
            panel.resetBrickInField(panel.getbr());
            collision.brickDown(panel.getbr());
            panel.getbr().setStartY();
            panel.setBrickInField(panel.getbr());
            panel.repaint();
        }else {                           
            panel.resetBrickInField(panel.getbr());
            panel.getbr().setColor(panel.getbr().getColor() * -1);
            panel.setBrickInField(panel.getbr());

            // Check if row full
            checkIfRowFull(panel.getbr(), panel);
            panel.setNextBrick();
        }

        // Pueft ob Spiel verloren wurde
        if (collision.checkGameOver(panel)) {
            gameOver = true;
            sound2.stopsound(SoundLoader.sound2);
            System.out.println("Game Over!");
        }
    }

    private void checkIfRowFull(brick currentBrick,playingfield panel) {
        sound1 = new SoundLoader();
        sound1.loadclear();
        ArrayList<Integer> rowsToCheck = new ArrayList<>();
        for (Square sq : currentBrick.getBricks()) {
            rowsToCheck.add(sq.getY());
        }

        boolean bonus = false;

        for (Integer row : rowsToCheck){
            // setzt reihe zurueck fall sie voll ist
            boolean full = true;
            for(int i = 1; i < 11; i++){
                if(panel.getPlayingField()[row][i] == 0) {
                    full = false;
                    break;
                }
            }
            if(full){
                for(int i = 1; i < 11; i++ ){
                    panel.getPlayingField()[row][i] = 0;
                }
                if (bonus) {
                    panel.setScore(panel.getScore() + 2);
                    sound1.play(SoundLoader.sound1);
                } else {
                    panel.setScore(panel.getScore() + 1);
                    bonus = true;
                    sound1.play(SoundLoader.sound1);
                }
                allElementsDown(row, panel);
            }
            panel.repaint();
        }
    }

    private void allElementsDown(Integer row, playingfield panel) {
        for (int i = row; i > 0; i--){
            for (int y = 0; y < 11; y++) {
                panel.getPlayingField()[i][y] = panel.getPlayingField()[i - 1][y];
            }
        }
    }

    //Funktion um die scores.txt datei auszulesen und als String Array zurueck zu geben.
    private String[][] GetFileStream() {

        String[][] out = new String[scorerows][scorecols];
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String zeile = null;
            int row = 0;
            while((zeile = reader.readLine()) != null) {
                out[row] = zeile.split("-");
                row++;
            }
            return out;
        } catch(Exception e) {
            return null;
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Gibt die Position des Scores im Leaderboard zurueck. -1 wenn Score zu niedrig ist.
    public int PosInLB(int goscore) {
        int i;
        String[][] leader = GetFileStream();
        for (i = 0; i < scorerows; i++) {
            if (leader[i][1] == null) {
                return i;
            } else if (goscore >= Integer.parseInt(leader[i][1])) {
                return i;
            }
        }
        return -1;
    }

    //Schreibt name score und level an die gegebene Position.
    public void setLeaderboard(String name, int goscore, int level, int pos) {
        String[][] leader = GetFileStream();
        String[][] write = new String[scorerows][scorecols];
        int count = 0;
        for (int i = 0; i < write.length; i ++) {
            if (i == pos) {
                write[i][0] = name;
                write[i][1] = goscore + "";
                write[i][2] = level + "";
                count++;
            } else {
                write[i] = leader[i - count];
            }

        }

        //Erstellt eine neue Datei um in diese zu schreiben
        File file = new File(filePath);
        newFile();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int a = 0; a < scorerows; a++) {
                if (write[a][0] != null){
                    for (int b = 0; b < scorecols; b++) {
                        writer.write(write[a][b]);
                        if (b != 2) {
                            writer.write("-");
                        }
                    }
                }
                if ((a + 1) < scorerows) {
                    if (write[a + 1][0] != null) {
                        writer.newLine();
                    }
                }
            }
            writer.close();
        }
        catch(IOException ioe) {
            System.err.println(ioe);
        }

    }

    //loescht datei und erstellt eine neue.
    public void newFile() {
        try{
            File file = new File(filePath);
            file.delete();
            file.createNewFile();
        } catch (Exception e)  {
            System.err.println(e);
        }
    }
}