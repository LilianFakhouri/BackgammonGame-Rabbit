package fr.ujm.tse.info4.pgammon.test.vues;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import controller.ControleurPartie;
import GUI.Avatar;
import models.Player;
import models.NiveauAssistant;
import models.ParametreJeu;
import models.Partie;

public class TestVuePartie {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test vue partie");

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(816,638);
		Container panel = frame.getContentPane();
		panel.setLayout(new FlowLayout());
		
		Player jBlanc = new Player(1, "ben", Avatar.CHAT_JAUNE.getPath(),NiveauAssistant.NON_UTILISE);
		Player jNoir = new Player(2, "JM", Avatar.CHEVAL.getPath(), NiveauAssistant.COMPLET);
		
		ParametreJeu param = new ParametreJeu(0, 3, true, jBlanc, jNoir);
		Partie p = new Partie(param);
//		p.lancerPremierePartie();
		
		ControleurPartie controleurPartie = new ControleurPartie(p);
		
		frame.setContentPane(controleurPartie.getVuePartie());
		
		
		frame.setVisible(true);
	}

}
