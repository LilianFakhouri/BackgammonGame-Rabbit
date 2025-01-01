package GUI;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Utils.CaseType;
import models.Case;

public class CaseButton extends JButton{
	private static final long serialVersionUID = 6276324191590405443L;

	public static final ImageIcon iconeNoire = new ImageIcon("images/pion_noir.png");
	public static final ImageIcon iconeBlanche = new ImageIcon("images/pion_blanc.png");
	public static final ImageIcon iconeAideBlanc = new ImageIcon("images/pion_assist_blanc.png");
	public static final ImageIcon iconeAideNoir = new ImageIcon("images/pion_assist_noir.png");
	public static final ImageIcon iconeNoireTransp = new ImageIcon("images/pion_noir_transp.png");
	public static final ImageIcon iconeBlancheTransp= new ImageIcon("images/pion_blanc_transp.png");

	private boolean isCandidate;
	private boolean isPossible;

	private Case c;
	/**
	 * 
	 * @param _case Case a associer au bouton.
	 */
	public CaseButton(Case _case){
		c = _case;
		isCandidate = false;
	}

	
	
	/**
	 * 
	 * @return Renvoie la Case associée
	 */
	public Case getCase() {
		return c;
	}

	public void setCase(Case _case) {
		c = _case;
	}
	
	
	public boolean isCandidate() {
		return isCandidate;
	}

	public void setCandidated(boolean isCandidate) {
		this.isCandidate = isCandidate;
	}



	public boolean isPossible() {
		return isPossible;
	}



	public void setPossible(boolean isPossible) {
		this.isPossible = isPossible;
	}
	
	public boolean hasSurpriseStation() {
	    for (int i = 0; i < getComponentCount(); i++) {
	        if (getComponent(i) instanceof SurpriseStationBarr) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/*
	 * public boolean hasQuestionStation() { for (int i = 0; i <
	 * getComponentCount(); i++) { if (getComponent(i) instanceof
	 * QuestionStationBarr) { return true; } } return false; }
	 */

	
	public boolean hasQuestionStation() {
	    for (Object component : getComponents()) { // Assuming `components` holds the items in the Case
	        if (component instanceof QuestionStationBarr) {
	            return true;
	        }
	    }
	    return false;
	}


	
	
	



}
