package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.Point2D;
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
        setLayout(new BorderLayout()); // Use BorderLayout for the main panel
        setPreferredSize(new Dimension(800, 600));

        // Title Label
        JLabel titleLabel = new JLabel("Game History", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0xCCCCCC));
        add(titleLabel, BorderLayout.NORTH); // Add to the top (north)

        // Table Model and Table
        String[] columnNames = {"ID", "Winner", "Second Player", "Duration", "Level"};
        tableModel = new DefaultTableModel(columnNames, 0);
        historyTable = new JTable(tableModel);
        historyTable.setFillsViewportHeight(true);
        historyTable.setRowHeight(25);

        // Styling the table
        historyTable.setFont(new Font("Arial", Font.PLAIN, 16));
        historyTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        historyTable.getTableHeader().setBackground(new Color(0x444444));
        historyTable.getTableHeader().setForeground(Color.WHITE);
        historyTable.setBackground(new Color(0x222222));
        historyTable.setForeground(Color.WHITE);
        historyTable.setGridColor(new Color(0x555555));
        historyTable.setShowGrid(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.getViewport().setBackground(new Color(0x222222)); // Set background color
        add(scrollPane, BorderLayout.CENTER); // Add to the center

        // Load game history data
        loadGameHistory();

        // Back Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center the button
        buttonPanel.setBackground(new Color(0x333333)); // Match background
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(0x444444));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> switchToMenu());
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH); // Add to the bottom
    }

    private void loadGameHistory() {
        List<GameHistory> gameHistories = SysData.getInstance().getGameHistories();
        for (GameHistory game : gameHistories) {
            tableModel.addRow(new Object[]{
                game.getId(),
                game.getWinner().getPseudo(),  // Ensure Player has getPseudo() method
                game.getSecondPlayer().getPseudo(),  // Ensure Player has getPseudo() method
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

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Paint p;
        int h = getHeight();
        int w = getWidth();

        // Background
        p = new RadialGradientPaint(
                new Point2D.Double(getWidth() / 2.0, getHeight() / 2.0),
                getHeight(),
                new float[]{0.0f, 0.8f},
                new Color[]{new Color(0x333333), new Color(0x000000)},
                RadialGradientPaint.CycleMethod.NO_CYCLE
        );

        g2.setPaint(p);
        g2.fillRect(0, 0, w, h);

        // Border
        p = new Color(0x808080);
        g2.setStroke(new BasicStroke(5.0f));
        g2.setPaint(p);
        g2.drawRect(2, 0, w - 5, h - 5);

        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game History");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new GameHistoryScreen());
        frame.setVisible(true);
    }
}
