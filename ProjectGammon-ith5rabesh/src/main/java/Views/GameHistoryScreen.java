package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import models.GameHistory;
import models.SysData;

public class GameHistoryScreen extends JPanel {

    private VueMenu vueMenu; // Reference to VueMenu
    private JTable historyTable;
    private DefaultTableModel tableModel;

    public GameHistoryScreen(VueMenu vueMenu) {
        this.vueMenu = vueMenu; // Store reference to VueMenu
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        JLabel titleLabel = new JLabel("Game History", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0xCCCCCC));
        add(titleLabel, BorderLayout.NORTH);

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

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.getViewport().setBackground(new Color(0x222222));
        add(scrollPane, BorderLayout.CENTER);

        loadGameHistory();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0x333333));
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(0x444444));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> switchToMenu());
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadGameHistory() {
        List<GameHistory> gameHistories = SysData.getInstance().getGameHistories();
        for (GameHistory game : gameHistories) {
            tableModel.addRow(new Object[] {
                game.getId(),
                game.getWinner().getPseudo(),
                game.getSecondPlayer().getPseudo(),
                game.getDuration(),
                game.getLevel()
            });
        }
    }

    private void switchToMenu() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.dispose(); // Simply close the Game History frame
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Paint p;
        int h = getHeight();
        int w = getWidth();

        p = new RadialGradientPaint(
                new Point2D.Double(getWidth() / 2.0, getHeight() / 2.0),
                getHeight(),
                new float[]{0.0f, 0.8f},
                new Color[]{new Color(0x333333), new Color(0x000000)},
                RadialGradientPaint.CycleMethod.NO_CYCLE
        );

        g2.setPaint(p);
        g2.fillRect(0, 0, w, h);

        p = new Color(0x808080);
        g2.setStroke(new BasicStroke(5.0f));
        g2.setPaint(p);
        g2.drawRect(2, 0, w - 5, h - 5);

        g2.dispose();
    }
}
