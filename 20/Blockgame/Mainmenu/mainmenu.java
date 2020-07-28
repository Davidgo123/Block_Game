package Mainmenu;

import Leaderboard.leaderboard;
import Game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainmenu {

    public mainmenu(JFrame mainFrame) {

        // Zuruecksetzen des Frames
        mainFrame.getContentPane().removeAll();
        mainFrame.setLayout(null);

        // Button fuer Spiel starten
        JButton play = new JButton("Play Game");
        play.setBounds(175,380,250,60);
        play.setFont(new Font("Arial", Font.BOLD, 30));
        play.setVisible(true);
        play.setBackground(Color.white);
        mainFrame.add(play);
        // Aktion: Wechsel zum Spiel
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game game = new game(mainFrame);
            }
        });

        // Button zum oeffnen der Stats
        JButton Stats = new JButton("Leaderboard");
        Stats.setBounds(175,450,250,60);
        Stats.setFont(new Font("Arial", Font.BOLD, 30));
        Stats.setVisible(true);
        Stats.setBackground(Color.white);
        mainFrame.add(Stats);
        Stats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leaderboard leaderboard = new leaderboard(mainFrame);
            }
        });

        // Button zum beenden des Programms
        JButton quit = new JButton("Quit");
        quit.setBounds(175,520,250,60);
        quit.setFont(new Font("Arial", Font.BOLD, 30));
        quit.setVisible(true);
        quit.setBackground(Color.white);
        mainFrame.add(quit);
        // Aktion: Programm beenden
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        // Spiellogo
        ImageIcon logo = new ImageIcon(getClass().getResource("logo.png"));
        logo.setImage(logo.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT));
        JLabel label = new JLabel(logo);
        label.setBounds(175,100, 250, 250);
        mainFrame.add(label);
        mainFrame.repaint();

        // Label "Blockgame"
        JLabel Name = new JLabel(" Blockgame");
        Name.setFont(new Font("Arial", Font.BOLD, 55));
        Name.setBounds(140, 10, 320, 80);
        Name.setVisible(true);
        mainFrame.add(Name);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
