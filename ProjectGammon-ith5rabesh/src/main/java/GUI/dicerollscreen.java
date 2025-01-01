package GUI;

import Views.VueTablier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import models.DeSixFaces;
import models.CouleurCase;
import models.ParametreJeu;
import models.Partie;

public class dicerollscreen extends JFrame {
    private static final long serialVersionUID = 1L;

    private DicePanel player1DicePanel, player2DicePanel;
    private JButton rollButton, startGameButton;
    private JLabel resultLabel;
    private int currentPlayer = 1; // 1 for Player 1, 2 for Player 2
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";
    private int player1Roll = -1; // Store Player 1's roll
    private int player2Roll = -1; // Store Player 2's roll

    public dicerollscreen(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;

        setTitle("Dice Roll to Decide Starter");
        setSize(800, 600); // Match VueNouvelleSession's size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setContentPane(new BackgroundPanel());
        setLayout(null); // Absolute positioning

        // Dice panels
        player1DicePanel = new DicePanel();
        player1DicePanel.setBounds(200, 200, 100, 100);
        add(player1DicePanel);

        player2DicePanel = new DicePanel();
        player2DicePanel.setBounds(500, 200, 100, 100);
        add(player2DicePanel);

        // Result label
        resultLabel = new JLabel(player1Name + ", roll the dice!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setBounds(150, 350, 500, 50);
        add(resultLabel);

        // Roll button
        rollButton = new JButton("Roll Dice");
        rollButton.setFont(new Font("Arial", Font.BOLD, 18));
        rollButton.setBackground(Color.WHITE); // Tomato color
        rollButton.setForeground(Color.BLACK);
        rollButton.setFocusPainted(false);
        rollButton.setBounds(300, 450, 200, 50);
        rollButton.addActionListener((ActionEvent e) -> takeTurn());
        add(rollButton);

        // Start Game button
        startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        startGameButton.setBackground(Color.GRAY); // Steel Blue
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setFocusPainted(false);
        startGameButton.setBounds(300, 520, 200, 50); // Positioned below Roll Dice button
        startGameButton.setEnabled(false); // Initially disabled
        startGameButton.addActionListener(e -> startGame());
        add(startGameButton);

        setVisible(true);
    }


    private void takeTurn() {
        if (currentPlayer == 1) {
            player1Roll = player1DicePanel.rollDice();
            resultLabel.setText(player2Name + ", it's your turn to roll!");
            currentPlayer = 2;
        } else if (currentPlayer == 2) {
            player2Roll = player2DicePanel.rollDice();
            determineWinner();
        }
    }

    private void determineWinner() {
        if (player1Roll > player2Roll) {
            resultLabel.setText(player1Name + " starts the game!");
        } else if (player2Roll > player1Roll) {
            resultLabel.setText(player2Name + " starts the game!");
        } else {
            resultLabel.setText("It's a tie! Roll again.");
            resetForTie();
            return;
        }
        rollButton.setEnabled(false); // Disable Roll Dice button
        startGameButton.setEnabled(true); // Enable Start Game button
    }

    private void resetForTie() {
        player1Roll = -1;
        player2Roll = -1;
        currentPlayer = 1;
        resultLabel.setText(player1Name + ", roll the dice again!");
    }

    private void startGame() {
        int starterPlayer = (player1Roll >= player2Roll) ? 1 : 2;

        ParametreJeu parametreJeu = new ParametreJeu();
        Partie partie = new Partie(parametreJeu);

        if (starterPlayer == 1) {
            partie.lancerPremierePartie(CouleurCase.BLANC);
        } else {
            partie.lancerPremierePartie(CouleurCase.NOIR);
        }

        new VueTablier(partie);
        dispose();
    }

    // Custom dice panel with a square shape and dots
    private static class DicePanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private int diceValue = 1;

        public int rollDice() {
            diceValue = new Random().nextInt(6) + 1;
            repaint();
            return diceValue;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            // Anti-aliasing for smoother graphics
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw dice background (square)
            g2.setColor(Color.WHITE);
            g2.fill(new Rectangle2D.Double(10, 10, getWidth() - 20, getHeight() - 20));

            // Draw dice border
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(new Rectangle2D.Double(10, 10, getWidth() - 20, getHeight() - 20));

            // Draw dots based on dice value
            g2.setColor(Color.BLACK);
            int[][] dotPositions = {
                    {},
                    {50, 50},
                    {30, 30, 70, 70},
                    {30, 30, 50, 50, 70, 70},
                    {30, 30, 30, 70, 70, 30, 70, 70},
                    {30, 30, 30, 70, 50, 50, 70, 30, 70, 70}
            };

            int[] dots = dotPositions[diceValue - 1];
            for (int i = 0; i < dots.length; i += 2) {
                g2.fillOval(dots[i], dots[i + 1], 10, 10);
            }

            g2.dispose();
        }
    }

    private static class BackgroundPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Paint p = new RadialGradientPaint(
                    new Point2D.Double(getWidth() / 2.0, getHeight() / 2.0),
                    getHeight(),
                    new float[]{0.0f, 0.8f},
                    new Color[]{new Color(0x333333), new Color(0x000000)},
                    RadialGradientPaint.CycleMethod.NO_CYCLE
            );
            g2.setPaint(p);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setStroke(new BasicStroke(5.0f));
            g2.setPaint(new Color(0x808080));
            g2.drawRect(2, 0, getWidth() - 5, getHeight() - 5);

            g2.dispose();
        }
    }
}