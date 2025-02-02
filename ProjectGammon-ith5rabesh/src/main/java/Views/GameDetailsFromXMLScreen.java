package Views;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class GameDetailsFromXMLScreen extends JPanel {

    private JTable detailsTable;
    private DefaultTableModel tableModel;

    public GameDetailsFromXMLScreen() {
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900, 600));

        JLabel titleLabel = new JLabel("Game Details from XML", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Table Columns
        String[] columnNames = {
            "Session ID", "State", "Max Games", " Player 2", "Winner Name", 
            "Black Score", "White Score","Level"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        detailsTable = new JTable(tableModel);
        detailsTable.setFillsViewportHeight(true);
        detailsTable.setRowHeight(25);
        detailsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(detailsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load game details from XML
        loadGameDetailsFromXML();

        // Back button
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

    private Map<String, String> loadPlayerNames() {
        Map<String, String> playerNames = new HashMap<>();

        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            File profilsFile = new File("sauvegarde/profils.xml"); 
            Document document = saxBuilder.build(profilsFile);
            Element rootElement = document.getRootElement();

            List<Element> players = rootElement.getChildren("joueurs");
            for (Element player : players) {
                String id = player.getAttributeValue("id");
                String pseudo = player.getChildText("pseudo");
                playerNames.put(id, pseudo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return playerNames;
    }

    private void loadGameDetailsFromXML() {
        tableModel.setRowCount(0); // Clear existing rows
        Map<String, String> playerNames = loadPlayerNames(); // Load player names

        File folder = new File("sauvegardeSessions"); // Path to XML files folder
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".xml")); // Filter XML files

        if (files != null) {
            for (File file : files) {
                try {
                    SAXBuilder saxBuilder = new SAXBuilder();
                    Document document = saxBuilder.build(file);
                    Element rootElement = document.getRootElement();
                    Element sessionElement = rootElement.getChild("session");

                    // Extract session details
                    String sessionId = sessionElement.getAttributeValue("id");
                    String state = sessionElement.getChildText("etatSession");
                    String maxGames = sessionElement.getChildText("idMaxPartie");

                    // Extract or determine the level
                    String level = "Medium"; // Default value
                    Element parametres = sessionElement.getChild("parametres");
                    if (parametres != null) {
                        level = parametres.getChildText("level");
                        if (level == null || level.isEmpty()) {
                            level = "Easy"; // Default value if not specified
                        }
                    }

                    // Players and scores
                    Element joueursElement = sessionElement.getChild("joueurs");
                    Element joueurNoir = joueursElement.getChild("joueurNoir");
                    String blackId = joueurNoir != null ? joueurNoir.getAttributeValue("id") : "N/A";
                    String blackName = playerNames.getOrDefault(blackId, "Unknown Player");
                    int blackScore = joueurNoir != null ? Integer.parseInt(joueurNoir.getChildText("score")) : 0;

                    Element joueurBlanc = joueursElement.getChild("joueurBlanc");
                    String whiteId = joueurBlanc != null ? joueurBlanc.getAttributeValue("id") : "N/A";
                    String whiteName = playerNames.getOrDefault(whiteId, "Unknown Player");
                    int whiteScore = joueurBlanc != null ? Integer.parseInt(joueurBlanc.getChildText("score")) : 0;

                    // Determine the winner
                    String winnerName;
                    if (blackScore > whiteScore) {
                        winnerName = blackName;
                    } else if (whiteScore > blackScore) {
                        winnerName = whiteName;
                    } else {
                        winnerName = "Nobody"; // If scores are equal
                    }
                    String player2Name = "Unknown";
                    if (joueursElement != null) {
                        Element joueurBlanc1 = joueursElement.getChild("joueurBlanc");
                        if (joueurBlanc1 != null) {
                            String player2Id = joueurBlanc1.getAttributeValue("id");
                            player2Name = playerNames.getOrDefault(player2Id, "Unknown Player");
                        }
                    }

                    // Current player
                    String currentPlayer = sessionElement.getChildText("joueurEnCour");

                    // Add data to the table
                    tableModel.addRow(new Object[]{
                        sessionId, state, maxGames, player2Name, winnerName, blackScore, whiteScore, level
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


   
    private void switchToMenu() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.dispose();
        }
    }
}
