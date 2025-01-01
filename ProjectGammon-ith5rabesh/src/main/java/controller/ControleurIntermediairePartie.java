package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jdom2.JDOMException;

import GUI.GameTimerBarr;
import GUI.dicerollscreen;
import models.CouleurCase;
import models.GestionDeSession;
import models.Player;
import models.Session;
import models.ParametreJeu;
import Views.VueIntermediairePartie;
import Views.VuePartie;

public class ControleurIntermediairePartie implements Controller{

	private ControleurPrincipal controleurPrincipal;
	private ControleurIntermediairePartie controleurIntermediairePartie;
	private boolean isNouvellePartie;
	private VueIntermediairePartie vueCreationPartie;
	private JFrame frame;
	private CouleurCase joueurEnCourDeModification;
	protected ControleurListeJoueur controleurListeJoueur;
	
	
	
	public ControleurIntermediairePartie(boolean isNouvellePartie,ControleurPrincipal controleurPrincipal)
	{
		this.controleurPrincipal = controleurPrincipal;
		this.isNouvellePartie = isNouvellePartie;
		controleurIntermediairePartie = this;
		vueCreationPartie = new VueIntermediairePartie(this.isNouvellePartie);
		controleurPrincipal.getFrame().setContentPane(vueCreationPartie);
		frame = controleurPrincipal.getFrame();
		build();
	}
	
	public void build()
	{
		listenerRetour();
		listenerStartGame();
		listenerCommencer();
		listenerCommencerCharger();
		listenerSuppresionSession();
		listenerChargerJoueur1();
		listenerChargerJoueur2();
	}
	
	
	public void listenerStartGame() {
	    vueCreationPartie.getVueNouvelleSession().getBoutonCommencer().addMouseListener(new MouseListener() {

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
	            int temp = vueCreationPartie.getVueNouvelleSession().getPanelParamètre().getNbTemps() * 1000;
	            int nbPartie = vueCreationPartie.getVueNouvelleSession().getPanelParamètre().getNbParties();
	            boolean videau = vueCreationPartie.getVueNouvelleSession().getPanelParamètre().getVideau().isSelected();

	            Player jBlanc = vueCreationPartie.getVueNouvelleSession().getPanelJoueur1().getJoueur();
	            Player jNoir = vueCreationPartie.getVueNouvelleSession().getPanelJoueur2().getJoueur();

	            if (jBlanc == null || jNoir == null) {
	                vueCreationPartie.afficherFenetreDemande("Oups!", "Please choose players!");
	                return;
	            }

	            if (jBlanc == jNoir) {
	                vueCreationPartie.afficherFenetreDemande("Oups!", "Players chosen are the same!");
	                return;
	            }
	            
	            

	            // Creation des paramètres de jeu
	            ParametreJeu param = new ParametreJeu(temp, nbPartie, videau, jBlanc, jNoir);

	            // Hide the current creation view
	            vueCreationPartie.setVisible(false);

	            // Start a new session and initialize the timer
	            VuePartie vuePartie = controleurPrincipal.nouvelleSession2(param);

	            // Start the game timer
	            vuePartie.startGameTimer();

	            // Refresh the UI
	            vuePartie.revalidate();
	            vuePartie.repaint();
	        }
	    });
	}

	




	public void listenerSuppresionSession()
	{
		vueCreationPartie.getVueChargerPartie().getPanelParametresVueCharger().getBoutonSupprimer().addMouseListener(new MouseListener() {

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

				try {
					GestionDeSession gestion = GestionDeSession.getGestionDeSession();
					gestion.supprimerSession(vueCreationPartie.getVueChargerPartie().getSession().getIdSession());
					gestion.sauvegarder();
					vueCreationPartie.getVueChargerPartie().updateData();
					vueCreationPartie.getVueChargerPartie().updateUI();

				} catch (IOException | JDOMException e1) {
					e1.printStackTrace();
					vueCreationPartie.afficherFenetreDemande("P'tit soucis", "Les sessions n'ont pas été chargées");

				}

			}

		});
		
		
	}
	
	public void listenerRetour()
	{
		vueCreationPartie.getBoutonRetour().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {
				vueCreationPartie.setVisible(false);
				controleurPrincipal.retour();
				}
		});
	}
	
	public void listenerCommencer() {
	    vueCreationPartie.getVueNouvelleSession().getBoutonCommencer().addMouseListener(new MouseListener() {
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
	            int temp = vueCreationPartie.getVueNouvelleSession().getPanelParamètre().getNbTemps() * 1000;
	            int nbPartie = vueCreationPartie.getVueNouvelleSession().getPanelParamètre().getNbParties();
	            boolean videau = vueCreationPartie.getVueNouvelleSession().getPanelParamètre().getVideau().isSelected();

	            Player jBlanc = vueCreationPartie.getVueNouvelleSession().getPanelJoueur1().getJoueur();
	            Player jNoir = vueCreationPartie.getVueNouvelleSession().getPanelJoueur2().getJoueur();

	            if (jBlanc == null || jNoir == null) {
	                vueCreationPartie.afficherFenetreDemande("Oups!", "Please choose players!");
	                return;
	            }

	            if (jBlanc == jNoir) {
	                vueCreationPartie.afficherFenetreDemande("Oups!", "Players chosen are the same!");
	                return;
	            }

	            // Hide the current window
	            vueCreationPartie.setVisible(false);

	            // Open the dice roll screen with player names
	            SwingUtilities.invokeLater(() -> {
	                dicerollscreen diceRoll = new dicerollscreen(jBlanc.getPseudo(), jNoir.getPseudo());
	                diceRoll.setVisible(true);
	            });
	        }
	    });
	}


	
	public void listenerCommencerCharger()
	{
		vueCreationPartie.getVueChargerPartie().getBoutonCommencer().addMouseListener(new MouseListener() {

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
				if(vueCreationPartie.getVueChargerPartie().getSession() == null){
					vueCreationPartie.afficherFenetreDemande("Oups!", "No session selected");
				}
				else{
					vueCreationPartie.setVisible(false);
					controleurPrincipal.chargerSession(vueCreationPartie.getVueChargerPartie().getSession());
				}
			}
		});
			
	}
	


	public void  listenerChargerJoueur1(){
		vueCreationPartie.getVueNouvelleSession().getBoutonChangerJoueurBlanc().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {
				vueCreationPartie.setVisible(false);
				controleurListeJoueur = new ControleurListeJoueur(true,controleurIntermediairePartie);
				joueurEnCourDeModification = CouleurCase.BLANC;}
		
		});
	}
	
	public void  listenerChargerJoueur2(){
		vueCreationPartie.getVueNouvelleSession().getBoutonChangerJoueurNoir().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {
				vueCreationPartie.setVisible(false);
				controleurListeJoueur = new ControleurListeJoueur(true,controleurIntermediairePartie);
				joueurEnCourDeModification = CouleurCase.NOIR;
				}
		
		});
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
	public void retour() {
		getFrame().setContentPane(vueCreationPartie);
		vueCreationPartie.setVisible(true);

		//build();
	}
	
	public void retour(Player j) {
		getFrame().setContentPane(vueCreationPartie);
		vueCreationPartie.setVisible(true);
		if (joueurEnCourDeModification == CouleurCase.BLANC)
			vueCreationPartie.getVueNouvelleSession().setJoueur1(j);
		else
			vueCreationPartie.getVueNouvelleSession().setJoueur2(j);
		//TODO  mettre a jour la fenétre
		//build();
	}
	
	
}
