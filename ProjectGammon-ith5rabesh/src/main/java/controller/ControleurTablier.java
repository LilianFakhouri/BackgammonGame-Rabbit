package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;

import exceptions.TourNonJouableException;
import GUI.CaseButton;
import GUI.QuestionAnswerScreen;
import GUI.QuestionStationBarr;
import GUI.TriangleCaseButton;
import Utils.SoundPlayer;
import models.Case;
import models.DeSixFaces;
import models.Horloge;
import models.HorlogeEvent;
import controller.HorlogeEventListener;
import models.NiveauAssistant;
import models.Partie;
import models.Tablier;
import Views.VuePartie;
import Views.VueTablier;

public class ControleurTablier implements Controller{
	private Tablier tablier;
	private Partie partie;
	private VueTablier vueTablier;
	private VuePartie vuePartie;
	private Horloge horloge;
	
	private ControleurPartie controleurPartie;
	private JFrame frame;
	private String selectedLevel;



	public ControleurTablier(Partie partie,VuePartie vuePartie,ControleurPartie controleurPartie,String selectedLevel)
	{
		this.partie = partie;
		this.tablier = partie.getTablier();
		this.vuePartie = vuePartie;
		this.vueTablier = vuePartie.getVueTablier();
		this.controleurPartie = controleurPartie;
		this.selectedLevel=selectedLevel;
	
		build();
		vueTablier.updateDes();
		vuePartie.afficherTransition(partie.getParametreJeu().getJoueur(partie.getJoueurEnCour()).getPseudo(), "Joueur" + partie.getJoueurEnCour().toString());	
	}


	private void build() {
			
		buildTimer();
		ListenerCaseButton();
		
	}
	
	private void ListenerCaseButton() {
	    Collection<CaseButton> lCase = vueTablier.getCasesButtons();
	    for (CaseButton caseButton : lCase) {
	        caseButton.addMouseListener(new MouseListener() {

	            @Override
	            public void mouseClicked(MouseEvent e) {}

	            @Override
	            public void mouseEntered(MouseEvent e) {}

	            @Override
	            public void mouseExited(MouseEvent e) {}

	            @Override
	            public void mousePressed(MouseEvent e) {}

	            @Override
	            public void mouseReleased(MouseEvent e) {
	                CaseButton caseButton = (CaseButton) e.getSource();
	                if (!partie.isTourFini() && !partie.isPartieFini()) {
	                    if (vueTablier.getCandidat() == null 
	                            && partie.getJoueurEnCour() == caseButton.getCase().getCouleurDame()) {
	                        if (caseButton.getCase().getNbDame() != 0 
	                                && (!tablier.isDameDansCaseBarre(partie.getJoueurEnCour()) 
	                                    || caseButton.getCase().isCaseBarre())
	                                && caseButton.getCase().getCouleurDame() == partie.getJoueurEnCour()) {
	                            vueTablier.setCandidat(caseButton);
	                            if (partie.getParametreJeu().getJoueur(partie.getJoueurEnCour()).getNiveauAssistant() == NiveauAssistant.SIMPLE
	                                    || partie.getParametreJeu().getJoueur(partie.getJoueurEnCour()).getNiveauAssistant() == NiveauAssistant.COMPLET) {
	                                vueTablier.setPossibles(partie.getCoupsPossibles(caseButton.getCase()));
	                            }
	                        } else if (tablier.isDameDansCaseBarre(partie.getJoueurEnCour())) {
	                            vuePartie.afficherFenetreDemande("Attention!", "Take out the beaten pieces before playing.");
	                        }
	                    } else if (vueTablier.getCandidat() != null) {
	                        if (partie.jouerCoup(vueTablier.getCandidat().getCase(), caseButton.getCase())) {
	                            vueTablier.uncandidateAll();
	                            vueTablier.setPossibles((new ArrayList<>()));

	                            // Check if the player has landed on a Question Station
	                            if (caseButton.hasQuestionStation()) {
	                                System.out.println("Player has landed on a Question Station!");

	                                // Retrieve the question from the question station
	                                QuestionStationBarr questionStation = (QuestionStationBarr) caseButton.getComponent(0);
	                                String question = questionStation.getQuestion();

	                                // Open the QuestionAnswerScreen (you can replace this part with your actual logic)
	                                QuestionAnswerScreen questionAnswerScreen = new QuestionAnswerScreen();
	                                questionAnswerScreen.setVisible(true); // Make it visible

	                                // Optionally, you can use a pop-up to show the question too:
	                                vuePartie.afficherFenetreDemande("Question Station", "Welcome to the Question Station!\nQuestion: " + question);
	                            } else {
	                                System.out.println("No Question Station here.");
	                            }

	                            // Check if the player landed on a surprise station
	                            if (caseButton.hasSurpriseStation()) {
	                                vuePartie.afficherFenetreDemande("Surprise!", "You landed on a surprise station! Launch the dice again");
	                                SoundPlayer.playSound("/Sounds/surprise-sound.wav");
	                                vueTablier.clearSurpriseStation(caseButton);
	                                partie.lancerDes(selectedLevel);
	                                return; // Exit early for surprise station handling
	                            }

	                            // If no special station, proceed with the game logic
	                            if (partie.isPartieFini()) {
	                                controleurPartie.finPartie();
	                            }
	                            if (partie.siDesUtilises()) {
	                                changerTour();
	                            } else if (!partie.hasCoupPossible()) {
	                                changerTour();
	                                partie.lancerDes(selectedLevel);
	                                if (!partie.hasCoupPossible()) {
	                                    vuePartie.afficherFenetreDemande("No possible coup", "");
	                                    changerTour();
	                                }
	                            }
	                        } else {
	                            vueTablier.uncandidateAll();
	                            vueTablier.setPossibles((new ArrayList<>()));
	                        }
	                    }
	                }

	                vueTablier.updateUI();
	                vueTablier.updateDes();
	            }
	        });
	    }
	}
	
	/*public void nouvellePartie(Partie partie)
	{
		this.partie = partie;
		vueTablier.setPartie(partie);
		vueTablier.updateUI();
		vueTablier.updateDes();
	}*/
	
	public void changerTour() 
	{
		if (horloge!= null)
		{
			horloge.stop();
			horloge.setValue(0);
		}
		partie.changerTour();
		vuePartie.afficherTransition(partie.getParametreJeu().getJoueur(partie.getJoueurEnCour()).getPseudo(),"Joueur" + partie.getJoueurEnCour().toString());
	}
	
	private void buildTimer(){
		
		if (partie.getParametreJeu().getSecondesParTour() != 0)
		{
			horloge = new Horloge(partie.getParametreJeu().getSecondesParTour());
			horloge.addListener(new HorlogeEventListener() {
				@Override
				public void updateHorloge(HorlogeEvent horloge) {}
				
				@Override
				public void finHorloge(HorlogeEvent evt) {
					 try {
						  //Deplacement aleatoire du nombre de de restant
						  int nbDeNonUtiliser=0;
						  for (DeSixFaces de : partie.getDeSixFaces()) {
							if(!de.isUtilise())
								nbDeNonUtiliser++;
						  }
						  for(int i=0;i<nbDeNonUtiliser;i++)
						  {
							  partie.deplacementAleatoire();
						  }

					} catch (TourNonJouableException e) {
						changerTour();
					}
					  vueTablier.uncandidateAll();
					  vueTablier.setPossibles((new ArrayList<Case>()));
					  
					  if (partie.siDesUtilises())
						{	
							changerTour();			
						}
						else if(!partie.hasCoupPossible())
						{
							vuePartie.afficherFenetreDemande("No Movement possible","");
							changerTour();
						}	
					  
					  vueTablier.updateUI();
					  vueTablier.updateDes();
					  
				  }
			});
		}
		else
		{
			horloge =null;	
		}
		vuePartie.getHorlogeBarr().setHorloge(horloge);
	}

	/**
	 *  permet de récuperer l'horloge
	 * @return
	 */
	public Horloge getHorloge() {
		return horloge;
	}

	@Override
	public Controller getControleur() {
		return this;
	}

	@Override
	public JFrame getFrame() {
		return frame;
	}

	@Override
	public void retour() {}
	
	
	
	
}
