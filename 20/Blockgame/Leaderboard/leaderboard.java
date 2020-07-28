package Leaderboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import Mainmenu.mainmenu;


public class leaderboard {

    //Maximale Zeilen und Spalten des Leaderboards
    private int scorerows = 9;
    private int scorecols = 3;

    //Filepath fuer txt Datei
    private String filePath = System.getProperty("user.dir") + "/20/Blockgame/Leaderboard/scores.txt";


    //Funktion zur visuellen Darstellung des Leaderboards
    public leaderboard(JFrame mainFrame) {

        //Tabellenkopf
        String[][] scoretable = GetFileStream();
        String[] colheads = {"Name", "Score", "Level"};
        mainFrame.getContentPane().removeAll();
        mainFrame.setLayout(null);
        mainFrame.repaint();

        //Header
        JLabel Head = new JLabel("Leaderboard");
        Head.setFont(new Font("Arial", Font.BOLD, 55));
        Head.setBounds(120, 10, 500, 80);
        Head.setVisible(true);
        mainFrame.add(Head);

        //Tabellenkopf
        JLabel ScoreHead = new JLabel("Name                   Score                Level");
        ScoreHead.setFont(new Font("Arial", Font.BOLD, 25));
        ScoreHead.setBounds(50, 80, 550, 80);
        ScoreHead.setVisible(true);
        mainFrame.add(ScoreHead);

        //Leaderboard Tabelle
        JTable table = new JTable(scoretable, colheads);
        table.setFont(new Font("Arial", Font.BOLD, 25));
        table.setBounds(50, 150, 440, 350);
        table.setVisible(true);
        table.setIntercellSpacing(new Dimension(0, 10));
        table.setShowGrid(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(Color.BLACK);
        table.setRowHeight(table.getRowHeight() + 20);
        table.getColumnModel().getColumn(0).setWidth(200);
        table.getColumnModel().getColumn(1).setWidth(200);
        table.setBackground(UIManager.getColor("Panel.background"));

        mainFrame.add(table);

        //Button um ins Main Menu zurueck zu gehen.
        JButton menu = new JButton("Main Menu");
        menu.setBounds(200, 500, 200, 60);
        menu.setVisible(true);
        menu.setBackground(Color.white);
        mainFrame.add(menu);
        // Aktion: Wechsel zum Menu
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainmenu mainmenu = new mainmenu(mainFrame);
            }
        });


    }

    //Funktion um die scores.txt datei auszulesen und als String Array zurueck zu geben.
    private String[][] GetFileStream() {

        String[][] out = new String[scorerows][scorecols];
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String zeile = null;
            int row = 0;
            while ((zeile = reader.readLine()) != null) {
                out[row] = zeile.split("-");
                row++;
            }
            return out;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}