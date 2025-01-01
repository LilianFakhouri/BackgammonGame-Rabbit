package fr.ujm.tse.info4.pgammon.test.vues;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import GUI.Avatar;
import models.NiveauAssistant;
import models.Profils;
import Views.VueListeJoueur;

public class TestVueListeJoueur {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test vue Liste Joueur");
		
		Profils tmp = Profils.getProfils();
		
		tmp.ajouter("DONG Chuan",Avatar.FLEUR.getPath(),NiveauAssistant.NON_UTILISE);
		tmp.ajouter("BONNETTO Benjamin",Avatar.CHAT_JAUNE.getPath(),NiveauAssistant.COMPLET);
		tmp.ajouter("FRANCON Adrien",Avatar.LION.getPath(),NiveauAssistant.COMPLET);
		tmp.ajouter("POTHELUNE Jean-Michel",Avatar.COCHON_D_INDE.getPath(),NiveauAssistant.SIMPLE);
		tmp.ajouter("DONG Chuan",Avatar.ESCARGOTS.getPath(),NiveauAssistant.NON_UTILISE);

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(816,638);
		Container panel = frame.getContentPane();
		panel.setLayout(new FlowLayout());
		
		VueListeJoueur vueListeJoueur = new VueListeJoueur();
		frame.setContentPane(vueListeJoueur);
		
		
			
		
		frame.setVisible(true);
	}
}
