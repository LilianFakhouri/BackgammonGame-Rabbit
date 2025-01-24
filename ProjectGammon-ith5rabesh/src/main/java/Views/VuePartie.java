package Views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.slf4j.event.Level;

import GUI.DeButton;
import GUI.GameTimerBarr;
import GUI.HorlogeBarr;
import GUI.QuestionStationBarr;
import GUI.SurpriseStationBarr;
import GUI.MonochromeVue;
import GUI.TriangleCaseButton;
import GUI.questionDiceGui;
import models.CouleurCase;
import models.SessionState;
import models.SysData;
import models.Partie;
import models.QuestionDice;
import models.DeSixFaces;
import models.EnhancedDice;
import models.Question;
import Views.VueNouvelleSession;
public class VuePartie extends MonochromeVue {
	private List<QuestionDice> desButton;

    private static final long serialVersionUID = 2417367501490643145L;

    // Background image
    public static final ImageIcon img_fond = new ImageIcon("images/fond_partie.png");

    private Partie partie;
    private VueTablier vueTablier;
    private SessionState etat;
    private QuestionDice questionDiceGui;
    private EnhancedDice enhancedDice; 
    private EnhancedDice enhancedDice2; 
    
    
    
    private questionspanel quess;
    private PanelTermineVueDroite panelDroitRevoir;
    private PanelEnCoursVueDroite panelDroitEnCours;
    private PanelEnCoursVueBas panelEnCoursVueBas;
    private PanelTermineVueBas panelTermineVueBas;
    private PanelJoueurVuePartie panelJoueur1;
    private PanelJoueurVuePartie panelJoueur2;
    private HorlogeBarr horlogeBarr;
    private GameTimerBarr gameTimerBarr;
    

    private List<TriangleCaseButton> triangles; // List of all triangle buttons
    private List<QuestionStationBarr> questionStations; // Question markers
    private SurpriseStationBarr surpriseStationBarr; // Surprise marker
  private String selectedLevel=VueNouvelleSession.getSelectedLevel();

    /**
     * Constructor for VuePartie.
     *
     * @param partie The game session
     */
    public VuePartie(Partie partie) {
        this.partie = partie;
        vueTablier = new VueTablier(partie);
        
        setOpaque(false);
        build();
    }
    
  

    /**
     * Builds the game view with all components and initializes logic.
     */
    private void build() {
        setPreferredSize(new Dimension(800, 600));
        setOpaque(false);
        setLayout(null);

        vueTablier.setBounds(173, 5, 547, 446);
        add(vueTablier);

        etat = SessionState.IN_PROGRESS;

        // Initialize the timer bar
        horlogeBarr = new HorlogeBarr(null);
        horlogeBarr.setBounds(122, 455, 598, 20);
        add(horlogeBarr);

        
     // Initialize GameTimerBarr
        gameTimerBarr = new GameTimerBarr();
        gameTimerBarr.setBounds(122, 485, 100, 20); // Adjust position and size as needed
        add(gameTimerBarr);


        // Initialize panels
        panelDroitEnCours = new PanelEnCoursVueDroite(partie);
        panelDroitEnCours.setBounds(720, 0, 80, 476);
        add(panelDroitEnCours);

        panelTermineVueBas = new PanelTermineVueBas();
        panelTermineVueBas.setBounds(0, 480, 800, 95);
        add(panelTermineVueBas);

        panelEnCoursVueBas = new PanelEnCoursVueBas();
        panelEnCoursVueBas.setBounds(0, 480, 800, 95);
        add(panelEnCoursVueBas);

        panelDroitRevoir = new PanelTermineVueDroite();
        panelDroitRevoir.setBounds(720, 0, 80, 476);
        add(panelDroitRevoir);

        panelJoueur1 = new PanelJoueurVuePartie(partie.getParametreJeu().getJoueurBlanc(), CouleurCase.BLANC);
        panelJoueur1.setBounds(10, 5, 150, 210);
        add(panelJoueur1);

        panelJoueur2 = new PanelJoueurVuePartie(partie.getParametreJeu().getJoueurNoir(), CouleurCase.NOIR);
        panelJoueur2.setBounds(10, 235, 150, 215);
        add(panelJoueur2);
       


        initializeTriangles();


        // Place random question stations and surprise stations
        placeRandomQuestionStations();
        placeRandomSurprise();
        
        if (selectedLevel.equals("Medium")) {
            // Instantiate only if it hasn't been instantiated yet
            questionDiceGui = new QuestionDice(); 
            questionDiceGui.setBounds(256, 50, 26, 26); // Set position and size
            add(questionDiceGui); // Add to this VuePartie
            vueTablier.addQuestionDice(questionDiceGui, 256, 50); // Add to vueTablier if needed
            questionDiceGui.setVisible(false); // Initially hidden
        }else if (selectedLevel.equals("Hard")) {
            System.out.println("hello hard ");
            
            // Initialize both enhanced dice
            enhancedDice = new EnhancedDice();
            enhancedDice2 = new EnhancedDice();

            // Set bounds and add the first enhanced dice
            enhancedDice.setBounds(256, 257, 28, 28); // Example position for dice 1
            add(enhancedDice);
            vueTablier.addEnhancedDice(enhancedDice, 256, 257);

            // Set bounds and add the second enhanced dice
            enhancedDice2.setBounds(256, 200, 28, 28); // Example position for dice 2
            add(enhancedDice2);
            vueTablier.addEnhancedDice(enhancedDice2, 256, 200);

            enhancedDice.setVisible(true); // Make visible
            enhancedDice2.setVisible(true); // Make visible

            // Initialize and add question dice
            questionDiceGui = new QuestionDice();
            questionDiceGui.setBounds(256, 50, 26, 26);
            add(questionDiceGui);
            vueTablier.addQuestionDice(questionDiceGui, 256, 50);
            questionDiceGui.setVisible(false); // Initially hidden
        }

        
        

        setEtat(getEtat()); // Check if this is necessary or redundant based on your game logic
    }
    /**
     * Initializes the triangle cases on the board.
     */
    /**
     * Launches a question based on the current game level.
     */
    





  
    private void initializeTriangles() {
        // Assuming vueTablier has a method to return all triangle buttons
        triangles = vueTablier.getAllTriangles();
    }
    

    /**
     * Randomly assigns question marks to three triangle buttons.
     * 
     */
    private void placeRandomQuestionStations() {
        if (triangles == null || triangles.isEmpty()) {
            return;
        }

        Random random = new Random();
        List<Integer> chosenIndices = new ArrayList<>();
        questionStations = new ArrayList<>();
        

        for (int i = 0; i < 3; i++) { // Add 3 question marks
            int randomIndex;
            do {
                randomIndex = random.nextInt(triangles.size());
            } while (chosenIndices.contains(randomIndex));
            chosenIndices.add(randomIndex);

            TriangleCaseButton triangle = triangles.get(randomIndex);

            QuestionStationBarr questionStation = new QuestionStationBarr("?");
            questionStation.setBounds(0, 0, triangle.getWidth(), triangle.getHeight());
            triangle.add(questionStation);
            questionStations.add(questionStation);
            triangle.repaint();
        }
    }

    /**
     * Randomly assigns a surprise station to one triangle button.
     */
    private void placeRandomSurprise() {
        if (triangles == null || triangles.isEmpty()) {
            return;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(triangles.size());
        TriangleCaseButton triangle = triangles.get(randomIndex);
        surpriseStationBarr = new SurpriseStationBarr("$");
        surpriseStationBarr.setBounds(0, 0, triangle.getWidth(), triangle.getHeight());
        triangle.add(surpriseStationBarr);
        triangle.repaint();
        
    }

    /**
     * Starts the game timer.
     */
    public void startGameTimer() {
        if (gameTimerBarr != null) {
            gameTimerBarr.resetTimer();
            gameTimerBarr.startTimer();
        }
    }

    /**
     * Stops the game timer.
     */
    public void stopGameTimer() {
        if (gameTimerBarr != null) {
            gameTimerBarr.stopTimer();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img_fond.getImage(), 0, 0, this);
        super.paintComponent(g);
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
        vueTablier.setVisible(false);
        vueTablier = new VueTablier(partie);
        vueTablier.setBounds(173, 30, 547, 446);
        panelDroitEnCours.setPartie(partie);
        add(vueTablier);
    }

    public SessionState getEtat() {
        return etat;
    }

    public void setEtat(SessionState etat) {
        this.etat = etat;

        if (etat.equals(SessionState.IN_PROGRESS)) {
            panelDroitEnCours.setVisible(true);
            panelDroitRevoir.setVisible(false);
        } else {
            panelDroitEnCours.setVisible(false);
            panelDroitRevoir.setVisible(true);
        }

        if (etat.equals(SessionState.REPLAY)) {
            panelTermineVueBas.setVisible(true);
            panelEnCoursVueBas.setVisible(false);
        } else {
            panelTermineVueBas.setVisible(false);
            panelEnCoursVueBas.setVisible(true);
        }
    }
//    public void updateDes(){
//
//		List<DeSixFaces> des = partie.getDeSixFaces();
//		
//		if(desButton != null){
//			for(QuestionDice de_btn : desButton){
//				remove(de_btn);
//			}
//		}
//		desButton = new ArrayList<>();
//		
//		int size = des.size();
//		int i = 0;
//		if(size>0)
//			for(DeSixFaces de : des){
//				QuestionDice btn = new QuestionDice(de);
//				int y = (int) (252 + 40*((float)i-size/2));
//				btn.setBounds(427-173, y,
//						btn.getPreferredSize().width , btn.getPreferredSize().height);
//				add(btn);
//				desButton.add(btn);
//				i++;
//			}
//    }
    public VueTablier getVueTablier() {
        return vueTablier;
    }

    public PanelEnCoursVueBas getPanelEnCoursVueBas() {
        return panelEnCoursVueBas;
    }

    public PanelEnCoursVueDroite getPaneldroitencours() {
        return panelDroitEnCours;
    }

    public PanelTermineVueDroite getPaneldroitrevoir() {
        return panelDroitRevoir;
    }

    public PanelTermineVueBas getPanelTermineVueBas() {
        return panelTermineVueBas;
    }

    public PanelJoueurVuePartie getPaneljoueur1() {
        return panelJoueur1;
    }

    public PanelJoueurVuePartie getPaneljoueur2() {
        return panelJoueur2;
    }

    public HorlogeBarr getHorlogeBarr() {
        return horlogeBarr;
    }



    public void setSelectedLevel(String level) {
        this.selectedLevel = level;
    }
    
    public String getSelectedLevel() {
        return selectedLevel;
    }

    
    public QuestionDice getQuestionDiceGui() {
        return questionDiceGui;
    }


    
    public EnhancedDice getenhancedDiceGui() {
        return enhancedDice;
    }
    



	public EnhancedDice getenhancedDiceGui2() {
		return enhancedDice2;
	}
}
