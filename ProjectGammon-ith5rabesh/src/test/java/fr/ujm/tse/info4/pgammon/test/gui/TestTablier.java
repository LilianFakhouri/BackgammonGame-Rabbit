package fr.ujm.tse.info4.pgammon.test.gui;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import models.Player;
import models.NiveauAssistant;
import models.ParametreJeu;
import models.Partie;
import Views.VuePartie;

public class TestTablier {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test Design");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(816,638);
		
		Container panel = frame.getContentPane();
		panel.setLayout(new FlowLayout());

		
		// comment récuperer ce que l'utilisateur saisie.
		System.out.println("Lancement du test de la Partie");

		// creation des parametre et joueur
		Player jBlanc = new Player(1, "ben", "beauGoss",
				NiveauAssistant.NON_UTILISE);
		Player jNoir = new Player(2, "JM", "null", NiveauAssistant.COMPLET);

		ParametreJeu param = new ParametreJeu(0, 3, true, jBlanc, jNoir);
		Partie p = new Partie(param);
//		p.lancerPremierePartie();
		// partie lancée
		
		VuePartie vp = new VuePartie(p);
		panel.add(vp);
		frame.setVisible(true);
	}
}
