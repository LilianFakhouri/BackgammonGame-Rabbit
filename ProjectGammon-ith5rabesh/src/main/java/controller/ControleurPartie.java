// 
//
//  @ Projet : Project Gammon
//  @ Fichier : ControleurPartie.java
//  @ Date : 12/12/2012
//  @ Auteurs : DONG Chuan, BONNETTO Benjamin, FRANCON Adrien, POTHELUNE Jean-Michel
//
//

package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Random;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.jdom2.JDOMException;

import Utils.SoundPlayer;
import models.Case;
import models.CouleurCase;
import models.Coup;
import models.Deplacement;
import models.SessionState;
import models.Tablier;
import models.GestionDeSession;
import models.NiveauAssistant;
import models.Partie;
import models.Profils;
import models.QuestionDice;
import models.Session;
import Views.VuePartie;

public class ControleurPartie implements Controller {
	private Session session;
	private VuePartie vuePartie;
	private ControleurTablier controleurTablier;
	private ControleurPartie controleurPartie;
	private JFrame frame;
	private Controller controleur;
	private int positionRevuePartie;
	private boolean isSensAvancer;
	private Timer timerRevuePartie;
	private CouleurCase couleurVideau;
	
	private ControleurPartie controller;

	public void setController(ControleurPartie controller) {
		this.controller = controller;
	}

	private void setupEasyLevel() {
		Tablier tablier = session.getPartieEnCours().getTablier();
		tablier.reinitialisationCase();

		// Place 3 question stations
		for (int i = 0; i < 3; i++) {
			tablier.placeSpecialStation(Case.QUESTION);
		}

		// Place 1 surprise station
		tablier.placeSpecialStation(Case.SURPRISE);

		vuePartie.updateUI(); // Refresh the UI
	}

	public void setLevel(String level) {
		if ("Easy".equals(level)) {
			setupEasyLevel();
		}
		// Add other levels as needed
	}

	@Deprecated
	public ControleurPartie(Partie partie) {
	    controleurPartie = this;

	    // Initialize VuePartie with the Partie
	    vuePartie = new VuePartie(partie);

	    // Retrieve the selected level from VuePartie
	    String selectedLevel = vuePartie.getSelectedLevel(); // Ensure this method exists in VuePartie

	    // Build the game components
	    build();

	    // Initialize ControleurTablier with the selected level
	    controleurTablier = new ControleurTablier(partie, vuePartie, this, selectedLevel);
	}


	public ControleurPartie(Session session, Controller controleur) {
	    couleurVideau = CouleurCase.VIDE;
	    this.controleur = controleur;
	    controleurPartie = this;
	    this.session = session;

	    // Initialize VuePartie
	    vuePartie = new VuePartie(session.getPartieEnCours());
	    timerRevuePartie = null;
	    build();

	    // Get the selected level from VuePartie
	    String selectedLevel = vuePartie.getSelectedLevel();

	    // Pass the selectedLevel to ControleurTablier
	    controleurTablier = new ControleurTablier(session.getPartieEnCours(), vuePartie, this, selectedLevel);

	    // Update scores in VuePartie
	    vuePartie.getPanelEnCoursVueBas().updateScore(
	        session.getScores().get(session.getParametreSession().getJoueurBlanc()),
	        session.getScores().get(session.getParametreSession().getJoueurNoir())
	    );
	}


	private void build() {
		listenerBack();
		listenerLancerDe();
		listenerGetCoupPossibleJoueur1();
		listenerGetCoupPossibleJoueur2();
		listenerButtonVideau();
		listenerPartieSuivante();
		listenerInterrompreSession();
		listenerRevoirPartie();
		listenerBoutonRevuePartie();
		listenerTimer();
		listenerInterromprePartie();
		listenerBoutonAide();
	}

	public void listenerTimer() {
		timerRevuePartie = new Timer(1000, null);
		timerRevuePartie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (positionRevuePartie < session.getPartieEnCours().nbDeplacementHistorise()) {
					if (!isSensAvancer) {
						isSensAvancer = true;
					} else {
						positionRevuePartie++;
					}

					Deplacement dep = session.getPartieEnCours().ProchainDeplacement(positionRevuePartie);
					if (dep != null)
						vuePartie.getPanelTermineVueBas().getReplayBarr().goTo(dep, isSensAvancer);
				} else {
					timerRevuePartie.stop();
				}
				vuePartie.updateUI();
				vuePartie.getVueTablier().updateUI();
				vuePartie.getVueTablier().updateDes();
			}

		});
	}
	
	

	private void listenerBoutonAide() {

		vuePartie.getPanelEnCoursVueBas().getHelp().addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				URI uri = URI.create(
						"http://www.google.fr/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&ved=0CEoQFjAA&url=http%3A%2F%2Ffr.wikipedia.org%2Fwiki%2FBackgammon&ei=R0XUUM6YL4yHhQelkYHYBQ&usg=AFQjCNEOHnc7riItGN_di3jAPILrXp9twA&sig2=uesTfMvnLMwYI8reGb-vWw&bvm=bv.1355534169,d.ZG4&cad=rja");
				try {
					Desktop.getDesktop().browse(uri);
				} catch (IOException e1) {

					e1.printStackTrace();
				}

			}
		});
	}

	public void listenerBoutonRevuePartie() {
		vuePartie.getPanelTermineVueBas().getReplayBarr().getEndBtn().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (timerRevuePartie != null)
					timerRevuePartie.stop();
				isSensAvancer = true;

				for (int i = positionRevuePartie; i < session.getPartieEnCours().nbDeplacementHistorise(); i++) {
					positionRevuePartie++;
					Deplacement dep = session.getPartieEnCours().ProchainDeplacement(positionRevuePartie);
					if (dep != null)
						vuePartie.getPanelTermineVueBas().getReplayBarr().goTo(dep, isSensAvancer);
				}

				vuePartie.getPanelTermineVueBas().getReplayBarr().goEnd();
				vuePartie.updateUI();
				vuePartie.getVueTablier().updateUI();
				vuePartie.getVueTablier().updateDes();

			}

		});

		vuePartie.getPanelTermineVueBas().getReplayBarr().getBeginBtn().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (timerRevuePartie != null)
					timerRevuePartie.stop();
				positionRevuePartie = 0;
				isSensAvancer = true;

				vuePartie.getPanelTermineVueBas().getReplayBarr().goBegin();
				session.getPartieEnCours().getTablier().reinitialisationCase();
				vuePartie.updateUI();
				vuePartie.getVueTablier().updateUI();
				vuePartie.getVueTablier().updateDes();

			}

		});

		vuePartie.getPanelTermineVueBas().getReplayBarr().getNextBtn().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (timerRevuePartie != null)
					timerRevuePartie.stop();
				if (positionRevuePartie < session.getPartieEnCours().nbDeplacementHistorise()) {
					if (!isSensAvancer) {
						isSensAvancer = true;
					} else {
						positionRevuePartie++;
					}

					Deplacement dep = session.getPartieEnCours().ProchainDeplacement(positionRevuePartie);
					if (dep != null)
						vuePartie.getPanelTermineVueBas().getReplayBarr().goTo(dep, isSensAvancer);
				}
				vuePartie.updateUI();
				vuePartie.getVueTablier().updateUI();
				vuePartie.getVueTablier().updateDes();
			}

		});

		vuePartie.getPanelTermineVueBas().getReplayBarr().getPrevBtn().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (timerRevuePartie != null)
					timerRevuePartie.stop();
				if (positionRevuePartie > 0) {
					if (isSensAvancer) {
						isSensAvancer = false;
					} else {
						positionRevuePartie--;
					}
					Deplacement dep = session.getPartieEnCours().PrecedentDeplacement(positionRevuePartie);

					if (dep != null)
						vuePartie.getPanelTermineVueBas().getReplayBarr().goTo(dep, isSensAvancer);
				}
				vuePartie.updateUI();
				vuePartie.getVueTablier().updateUI();
				vuePartie.getVueTablier().updateDes();

			}

		});

	}

	public void listenerRevoirPartie() {

		vuePartie.getPaneldroitrevoir().getUndo().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (vuePartie.getEtat() != SessionState.REPLAY) {
					positionRevuePartie = 0;
					isSensAvancer = true;
					vuePartie.setEtat(SessionState.REPLAY);
					vuePartie.getPanelTermineVueBas().getReplayBarr()
							.setTours(session.getPartieEnCours().getHistoriqueToursJoueur());
					session.getPartieEnCours().getTablier().reinitialisationCase();
					vuePartie.updateUI();
					vuePartie.getVueTablier().updateUI();
					vuePartie.getVueTablier().updateDes();
				} else {

					if (timerRevuePartie != null)
						timerRevuePartie.start();

				}
			}
		});

	}

	public void listenerBack() {
		vuePartie.getPaneldroitencours().getBack().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				session.getPartieEnCours().annulerDernierCoup();
				vuePartie.updateUI();
				vuePartie.getVueTablier().updateDes();
			}

		});
	}

	public void listenerLancerDe() {
	    vuePartie.getPaneldroitencours().getDices().addMouseListener(new MouseListener() {
	        @Override
	        public void mouseClicked(MouseEvent arg0) {
	        }

	        @Override
	        public void mouseEntered(MouseEvent arg0) {
	        }

	        @Override
	        public void mouseExited(MouseEvent arg0) {
	        }

	        @Override
	        public void mousePressed(MouseEvent arg0) {
	        }

	        @Override
	        public void mouseReleased(MouseEvent arg0) {
	            if (!session.getPartieEnCours().isPartieFini()) {
	                String selectedLevel = vuePartie.getSelectedLevel();
	                SoundPlayer.playSound("/Sounds/dice_roll.wav.wav");

	                // Roll standard dice based on the level
	                session.getPartieEnCours().lancerDes(selectedLevel);

	                // Add question dice for Medium and Hard levels
	                if ("Medium".equals(selectedLevel) || "Hard".equals(selectedLevel)) {
	                    int questionDiceValue = new Random().nextInt(3) + 1; // Random value between 1 and 3
	                    vuePartie.getQuestionDiceGui().setValue(questionDiceValue);
	                    vuePartie.getQuestionDiceGui().setVisible(true);
	                    vuePartie.getQuestionDiceGui().roll();
	                }

	                // Update UI
	                if (controleurTablier.getHorloge() != null) {
	                    controleurTablier.getHorloge().restart();
	                }

	                vuePartie.updateUI();
	                vuePartie.getVueTablier().updateUI();
	                vuePartie.getVueTablier().updateDes();

	                // Check for possible moves
	                if (!session.getPartieEnCours().hasCoupPossible()) {
	                    vuePartie.afficherFenetreDemande("No possible move", "");
	                    controleurTablier.changerTour();
	                }
	            }
	        }
	    });
	}


	
	
	public void listenerGetCoupPossibleJoueur1() {
		vuePartie.getPaneljoueur1().getCouppossible().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (session.getPartieEnCours().getParametreJeu().getJoueurBlanc()
						.getNiveauAssistant() == NiveauAssistant.NON_UTILISE) {
					session.getPartieEnCours().getParametreJeu().getJoueurBlanc()
							.setNiveauAssistant(NiveauAssistant.SIMPLE);
				} else {
					session.getPartieEnCours().getParametreJeu().getJoueurBlanc()
							.setNiveauAssistant(NiveauAssistant.NON_UTILISE);
					vuePartie.getVueTablier().setPossibles(null);
					vuePartie.getVueTablier().updateUI();
				}
				vuePartie.getPaneljoueur1().updateData();

			}
		});
	}

	public void listenerGetCoupPossibleJoueur2() {
		vuePartie.getPaneljoueur2().getCouppossible().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (session.getPartieEnCours().getParametreJeu().getJoueurNoir()
						.getNiveauAssistant() == NiveauAssistant.NON_UTILISE) {
					session.getPartieEnCours().getParametreJeu().getJoueurNoir()
							.setNiveauAssistant(NiveauAssistant.SIMPLE);
				} else {
					session.getPartieEnCours().getParametreJeu().getJoueurNoir()
							.setNiveauAssistant(NiveauAssistant.NON_UTILISE);
					vuePartie.getVueTablier().setPossibles((new ArrayList<Case>()));
					vuePartie.getVueTablier().updateUI();
				}
				vuePartie.getPaneljoueur2().updateData();

			}
		});
	}

	public void listenerButtonVideau() {

		if (!session.getParametreSession().isUtiliseVideau()) {
			vuePartie.getPaneldroitencours().getVideau().setEnabled(false);
		} else {
			vuePartie.getPaneldroitencours().getVideau().addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					if ((couleurVideau == CouleurCase.VIDE
							|| session.getPartieEnCours().getJoueurEnCour() != couleurVideau)
							&& (session.getPartieEnCours().isTourFini()
									&& !session.getPartieEnCours().isPartieFini())) {
						SortedSet<String> hs = new ConcurrentSkipListSet<>();
						hs.add("No");
						hs.add("Yes");
						vuePartie.afficherFenetreDemande("Do you accept the doubling cube ?", hs)
								.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {

										String action = e.getActionCommand();
										if (action == "Yes") {
											couleurVideau = session.getPartieEnCours().getJoueurEnCour();
											session.getPartieEnCours().doublerVideau();

										} else if (action == "No") {
											finPartie();
											// if (!session.isSessionFini())
											// controleurPartie.nouvellePartie();

										}
										vuePartie.getPaneldroitencours().updateVideau();
									}

								});
					}
				}
			});

		}
	}

	public void listenerInterromprePartie() {
		vuePartie.getPaneldroitrevoir().getX_white().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				SortedSet<String> hs = new ConcurrentSkipListSet<>();
				hs.add("Finish");
				hs.add("Cancel");
				hs.add("Save");
				vuePartie.afficherFenetreDemande("What do you want to do ?", hs)
						.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								String action = e.getActionCommand();
								if (action == "Finish") {
									if (session.meilleurJoueur() != null) {
										session.finSession(session.meilleurJoueur());
									}
									((ControleurPrincipal) controleur).finSession();
									controleur.retour();
								} else if (action == "Cancel") {

								} else if (action == "Save") {
									
									try {
										GestionDeSession gestion = GestionDeSession.getGestionDeSession();
										gestion.sauvegarder();

									} catch (IOException e1) {

										e1.printStackTrace();
									} catch (JDOMException e1) {

										e1.printStackTrace();
									}

									Profils profil = Profils.getProfils();
									profil.sauvegarder();

									controleur.retour();

								}
							}
						});
			}
		});

	}

	public void listenerInterrompreSession() {
		vuePartie.getPanelEnCoursVueBas().getX_black().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				SortedSet<String> hs = new ConcurrentSkipListSet<>();
				hs.add("Finish");
				hs.add("Cancel");
				hs.add("Save");
				vuePartie.afficherFenetreDemande("What do you want to do ?", hs)
						.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								String action = e.getActionCommand();
								if (action == "Finish") {
									if (session.meilleurJoueur() != null) {
										session.finSession(session.meilleurJoueur());

									}
									((ControleurPrincipal) controleur).finSession();
									controleur.retour();
									
							
								} else if (action == "Cancel") {

								} else if (action == "Save") {
									try {
										GestionDeSession gestion = GestionDeSession.getGestionDeSession();
										gestion.sauvegarder();


									} catch (IOException e1) {

										e1.printStackTrace();
									} catch (JDOMException e1) {

										e1.printStackTrace();
									}

									Profils profil = Profils.getProfils();
									profil.sauvegarder();

									controleur.retour();

								}
							}
						});
			}
		});

	}

	public void listenerPartieSuivante() {
		vuePartie.getPaneldroitrevoir().getNext().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (session.isSessionFini()) {
					controleur.retour();
					((ControleurPrincipal) controleur).finSession();
					Profils profil = Profils.getProfils();
					profil.sauvegarder();
				} else
					controleurPartie.nouvellePartie();

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
	}

	public void nouvellePartie() {
	    // Start a new game session
	    session.nouvellePartie();
	    session.LancerPartie();

	    // Update the current game in VuePartie
	    vuePartie.setPartie(session.getPartieEnCours());
	    vuePartie.setEtat(SessionState.IN_PROGRESS);

	    // Retrieve the selected level from VuePartie
	    String selectedLevel = vuePartie.getSelectedLevel(); // Ensure this method exists in VuePartie

	    // Re-initialize the ControleurTablier with the selected level
	    controleurTablier = new ControleurTablier(session.getPartieEnCours(), vuePartie, this, selectedLevel);

	    // Launch the new game
	    session.LancerPartie();
	    vuePartie.updateUI();
	}


	public void finPartie() {
	    if (controleurTablier.getHorloge() != null) {
	        controleurTablier.getHorloge().stop();
	        controleurTablier.getHorloge().setValue(0);
	    }

	    // Stop the game timer
	    vuePartie.stopGameTimer();

	    // Save the game history (add this part)
//	    saveGameHistory();

	    session.finPartie();

	    vuePartie.getPanelEnCoursVueBas().updateScore(
	            session.getScores().get(session.getParametreSession().getJoueurBlanc()),
	            session.getScores().get(session.getParametreSession().getJoueurNoir()));

	    if (session.verifFinSession()) {
	        session.finSession();
	        vuePartie.getPaneldroitrevoir().getLabnext().setText("<html>Finish<br>Session");
	        vuePartie.afficherFenetreDemande(session.getPartieEnCours().getParametreJeu()
	                .getJoueur(session.getPartieEnCours().getJoueurEnCour()).getPseudo(), " remporte la session!");
	    } else {
	        vuePartie.afficherFenetreDemande(session.getPartieEnCours().getParametreJeu()
	                .getJoueur(session.getPartieEnCours().getJoueurEnCour()).getPseudo(), " remporte la partie!");
	    }
	    vuePartie.setEtat(SessionState.FINISHED);
	}

//	// Method to save the game history
//	private void saveGameHistory() {
//	    try {
//	        // You can define the history format (e.g., JSON, XML, or a custom format)
//	        // Here we're assuming you have a method to get the game history as a string or object
//	        String gameHistory = getGameHistoryAsString();
//
//	        // Save to file (you can use a more sophisticated method if needed)
//	        File historyFile = new File("game_history.json"); // Use a unique file for each session if needed
//	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile, true))) {
//	            writer.write(gameHistory);
//	            writer.newLine(); // Optionally add a line break between games
//	        }
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}

//	// Example method to get the game history as a string (customize based on your data structure)
//	private String getGameHistoryAsString() {
//	    // Collect game data like players, scores, winner, etc.
//	    StringBuilder history = new StringBuilder();
//	    history.append("Game Date: ").append(LocalDateTime.now().toString()).append("\n");
//	    history.append("Player 1: ").append(session.getParametreSession().getJoueurBlanc().getPseudo()).append("\n");
//	    history.append("Player 2: ").append(session.getParametreSession().getJoueurNoir().getPseudo()).append("\n");
//	    history.append("Winner: ").append(session.getPartieEnCours().getParametreJeu()
//	            .getJoueur(session.getPartieEnCours().getJoueurEnCour()).getPseudo()).append("\n");
//	    history.append("Scores: ");
//	    history.append(session.getScores().get(session.getParametreSession().getJoueurBlanc())).append(" - ");
//	    history.append(session.getScores().get(session.getParametreSession().getJoueurNoir())).append("\n");
//	    // Add any additional details you need
//	    return history.toString();
//	}



	public Partie getPartie() {
		return session.getPartieEnCours();
	}

	public VuePartie getVuePartie() {
		return vuePartie;
	}

	public ControleurTablier getControleurTablier() {
		return controleurTablier;
	}

	@SuppressWarnings("unused")
	private void testInitialisation() {
		ArrayList<Case> lCase = new ArrayList<Case>();

		lCase.add(new Case(CouleurCase.NOIR, 1, 1));
		lCase.add(new Case(CouleurCase.VIDE, 0, 2));
		lCase.add(new Case(CouleurCase.VIDE, 0, 3));
		lCase.add(new Case(CouleurCase.VIDE, 0, 4));
		lCase.add(new Case(CouleurCase.VIDE, 0, 5));
		lCase.add(new Case(CouleurCase.VIDE, 0, 6));
		lCase.add(new Case(CouleurCase.VIDE, 0, 7));
		lCase.add(new Case(CouleurCase.VIDE, 0, 8));
		lCase.add(new Case(CouleurCase.VIDE, 0, 9));
		lCase.add(new Case(CouleurCase.VIDE, 0, 10));
		lCase.add(new Case(CouleurCase.VIDE, 0, 11));
		lCase.add(new Case(CouleurCase.VIDE, 0, 12));
		lCase.add(new Case(CouleurCase.VIDE, 0, 13));
		lCase.add(new Case(CouleurCase.VIDE, 0, 14));
		lCase.add(new Case(CouleurCase.VIDE, 0, 15));
		lCase.add(new Case(CouleurCase.VIDE, 0, 16));
		lCase.add(new Case(CouleurCase.VIDE, 0, 17));
		lCase.add(new Case(CouleurCase.VIDE, 0, 18));
		lCase.add(new Case(CouleurCase.VIDE, 0, 19));
		lCase.add(new Case(CouleurCase.VIDE, 0, 20));
		lCase.add(new Case(CouleurCase.VIDE, 0, 21));
		lCase.add(new Case(CouleurCase.VIDE, 0, 22));
		lCase.add(new Case(CouleurCase.VIDE, 0, 23));
		lCase.add(new Case(CouleurCase.BLANC, 1, 24));

		session.getPartieEnCours().getTablier().initialiserCase(lCase);

		lCase = new ArrayList<Case>();
		lCase.add(new Case(CouleurCase.BLANC, 14, 25));
		lCase.add(new Case(CouleurCase.NOIR, 14, 0));
		session.getPartieEnCours().getTablier().initialiserCaseVictoire(lCase);

		lCase = new ArrayList<Case>();
		lCase.add(new Case(CouleurCase.BLANC, 0, 0));
		lCase.add(new Case(CouleurCase.NOIR, 0, 25));
		session.getPartieEnCours().getTablier().initialiserCaseBarre(lCase);
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

	}


}
