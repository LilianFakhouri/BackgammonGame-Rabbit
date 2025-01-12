package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import models.GameHistory;
import models.SysData;

public class GameHistoryScreen extends JPanel {

    private JTable historyTable;
    private DefaultTableModel tableModel;

    public GameHistoryScreen() {
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        // Title Label
        JLabel titleLabel = new JLabel("Game History", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        add(titleLabel, BorderLayout.NORTH);

        // Table Model and Table
        String[] columnNames = {"ID", "Winner", "Second Player", "Duration", "Level"};
        tableModel = new DefaultTableModel(columnNames, 0);
        historyTable = new JTable(tableModel);
        historyTable.setFillsViewportHeight(true);
        historyTable.setRowHeight(25);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load game history data
        loadGameHistory();

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> switchToMenu());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadGameHistory() {
        List<GameHistory> gameHistories = SysData.getInstance().getGameHistories();
        for (GameHistory game : gameHistories) {
            tableModel.addRow(new Object[]{
                game.getId(),
                game.getWinner(),
                game.getSecondPlayer(),
                game.getDuration(),
                game.getLevel()
            });
        }
    }

    private void switchToMenu() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.getContentPane().removeAll();
            parentFrame.setContentPane(new VueMenu()); // Replace with your actual menu class
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game History");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new GameHistoryScreen());
        frame.setVisible(true);
    }
}
