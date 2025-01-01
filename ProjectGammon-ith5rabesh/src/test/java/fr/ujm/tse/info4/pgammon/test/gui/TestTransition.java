package fr.ujm.tse.info4.pgammon.test.gui;

import java.awt.Container;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.JFrame;

import controller.ControleurPartie;
import models.Player;
import models.NiveauAssistant;
import models.ParametreJeu;
import models.Partie;
import Views.VuePartie;

public class TestTransition {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		JFrame frame = new JFrame("TestTransition");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(812,645);
		
		Container panel = frame.getContentPane();
		panel.setLayout(null);

		// creation des parametre et joueur
		Player jBlanc = new Player(1, "ben", "beauGoss",
				NiveauAssistant.NON_UTILISE);
		Player jNoir = new Player(2, "JM", "null", NiveauAssistant.COMPLET);

		ParametreJeu param = new ParametreJeu(0, 3, true, jBlanc, jNoir);
		Partie p = new Partie(param);
//		p.lancerPremierePartie();
		ControleurPartie controleurPartie = new ControleurPartie(p);
		VuePartie vue = controleurPartie.getVuePartie();
		vue.setBounds(0,0,830,630);

		panel.add(vue);
		SortedSet<String> hs = new ConcurrentSkipListSet<>();
		hs.add("Non");
		hs.add("Oui");

		hs.add("Peut-etre");

		vue.afficherFenetreDemande("Ca va?", hs);
		
		frame.setVisible(true);
	}
}
