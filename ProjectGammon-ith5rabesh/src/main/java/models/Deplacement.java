// 
//
//  @ Projet : Project Gammon
//  @ Fichier : Deplacement.java
//  @ Date : 12/12/2012
//  @ Auteurs : DONG Chuan, BONNETTO Benjamin, FRANCON Adrien, POTHELUNE Jean-Michel
//
//




package models;

import javax.swing.JOptionPane;

import org.jdom2.Element;
import GUI.TriangleCaseButton;


public class Deplacement
{
	private Case caseDepart;
	private Case caseArriver;
	private boolean siCaseBattue;
	private double idDeplacement;

	public Deplacement(Case caseDepart, Case caseArriver, boolean siCaseBattue)
	{
		this.caseDepart = caseDepart;
		this.caseArriver = caseArriver;
		this.siCaseBattue = siCaseBattue;
		this.idDeplacement = Math.random()*Double.MAX_VALUE;

		
	}
	
	public Deplacement()
	{
		this.idDeplacement = Math.random()*Double.MAX_VALUE;
	}
	
	public Deplacement(Case caseDepart, int diceValue, CouleurCase joueurCouleur, Partie partie) {
	    this.caseDepart = caseDepart;
	    this.siCaseBattue = false;
	    this.idDeplacement = Math.random() * Double.MAX_VALUE;

	    int positionDepart = caseDepart.getPosition();
	    int positionArriver;

	    // Calculate target position based on dice value
	    if (diceValue < 0) {
	        // Backward movement
	        positionArriver = joueurCouleur == CouleurCase.NOIR
	            ? positionDepart + Math.abs(diceValue)
	            : positionDepart - Math.abs(diceValue);
	    } else {
	        // Forward movement
	        positionArriver = joueurCouleur == CouleurCase.NOIR
	            ? positionDepart - diceValue
	            : positionDepart + diceValue;
	    }

	    // Validate target position
	    if (positionArriver < 0 || positionArriver > 25) {
	        throw new IllegalArgumentException("Invalid move: Target position " + positionArriver + " is out of bounds.");
	    }

	    // Assign target case
	    this.caseArriver = partie.getTablier().getCase(positionArriver, joueurCouleur);
	}


	
	

	
	public void sauvegarder(Element deplacementsXML)
	{
		Element deplacementXML = new Element("deplacement");
		deplacementsXML.addContent(deplacementXML);
		
			Element caseDepartXML = new Element("caseDepart");
			caseDepartXML.setText(String.valueOf(caseDepart.getPosition()));
			deplacementXML.addContent(caseDepartXML);
			
			Element caseArriverXML = new Element("caseArriver");
			caseArriverXML.setText(String.valueOf(caseArriver.getPosition()));
			deplacementXML.addContent(caseArriverXML);
			
			Element siCaseBattueXML = new Element("siCaseBattue");
			if(siCaseBattue){
				siCaseBattueXML.setText("true");
			}else{
				siCaseBattueXML.setText("false");
			}
			deplacementXML.addContent(siCaseBattueXML);
	}
	
	public void charger(Element deplacement, Partie partie) {
	    int positionDepart = Integer.valueOf(deplacement.getChildText("caseDepart"));
	    int positionArriver = Integer.valueOf(deplacement.getChildText("caseArriver"));
	    boolean siCaseBattue = Boolean.valueOf(deplacement.getChildText("siCaseBattue"));

	    CouleurCase couleur = (positionDepart < positionArriver) ? CouleurCase.BLANC : CouleurCase.NOIR;

	    this.caseDepart = partie.getTablier().getCase(positionDepart, couleur);
	    this.caseArriver = partie.getTablier().getCase(positionArriver, couleur);
	    this.siCaseBattue = siCaseBattue;
	}



	
	public Case getCaseDepart() {
		return caseDepart;
	}

	public Case getCaseArriver() {
		return caseArriver;
	}

	public boolean isSiCaseBattue() {
		return siCaseBattue;
	}


	public boolean isCaseRentree() {
		return caseArriver.isCaseVictoire();
	}


	public double getIdDeplacement() {
		return idDeplacement;
	}

	

	
	

	
	
}
