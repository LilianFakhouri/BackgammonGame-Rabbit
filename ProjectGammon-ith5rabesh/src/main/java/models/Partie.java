// 
//
//  @ Projet : Project Gammon
//  @ Fichier : Partie.java
//  @ Date : 12/12/2012
//  @ Auteurs : DONG Chuan, BONNETTO Benjamin, FRANCON Adrien, POTHELUNE Jean-Michel
//
//

package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;

import exceptions.TourNonJouableException;

public class Partie {
	private ParametreJeu parametreJeu;
	private Videau videau;
	private ArrayList<DeSixFaces> deSixFaces;
	private Tablier tablier;
	private CouleurCase premierJoueur;
	private CouleurCase joueurEnCour;
	private ArrayList<Tour> historiqueToursJoueur;
	private int idPartie;
	private boolean partieFini;

	
	private int deUtiliser;
	
	private boolean tourFini;
	private boolean diceRolled;
	private int enhancedDiceValue1;
	private int enhancedDiceValue2;


	
	public void resetTurn() {
	    this.tourFini = false; // Ensure the turn is marked as not finished
	    this.diceRolled = false; // Allow the player to roll the dice again
	}


	public void setTourFini(boolean tourFini) {
	    this.tourFini = tourFini;
	}

	public boolean isDiceRolled() {
	    return diceRolled;
	}

	public void setDiceRolled(boolean diceRolled) {
	    this.diceRolled = diceRolled;
	}


	/**
	 * 
	 * @param p
	 */
	public Partie(ParametreJeu p) {
		parametreJeu = p;
		tablier = new Tablier();
		videau = new Videau();
		
		historiqueToursJoueur = new ArrayList<Tour>();
		
		deSixFaces = new ArrayList<DeSixFaces>();
		
		//ces variables permet de connaitre l'état de la partie
		tourFini=true;
		partieFini = false;
	}
	/**
	 * 
	 * @param idPartie
	 * @param p
	 */
	public Partie(int idPartie ,ParametreJeu p) {
		parametreJeu = p;
		this.idPartie = idPartie;
		
		tablier = new Tablier();
		videau = new Videau();
		
		historiqueToursJoueur = new ArrayList<Tour>();
		deSixFaces = new ArrayList<DeSixFaces>();
		
		//ces variables permet de connaitre l'état de la partie
		tourFini=true;
		partieFini = false;
		
	}
	/**
	 * 
	 */
	public void lancerPremierePartie(CouleurCase starterPlayer) {
	    partieFini = false; // The game is not finished

	    // Initialize the starting player
	    if (starterPlayer == CouleurCase.BLANC || starterPlayer == CouleurCase.NOIR) {
	        premierJoueur = starterPlayer;
	        joueurEnCour = premierJoueur; // Set the current player
	        System.out.println("The game starts with " + joueurEnCour + " as the starting player.");
	    } else {
	        throw new IllegalArgumentException("Invalid player color. Must be BLANC or NOIR.");
	    }
	}



	/**
	 * 
	 * @param joueur
	 */
	public void lancerNouvellePartie(CouleurCase joueur) {
		partieFini = false;
		
		if(joueur == CouleurCase.BLANC)
			joueurEnCour = CouleurCase.NOIR;
		else
			joueurEnCour = CouleurCase.BLANC;
		
		premierJoueur = joueurEnCour;
	}
	/**
	 * 
	 */
	
	
	


	public void debutTour()
	{
		historiqueToursJoueur.add(new Tour(joueurEnCour, deSixFaces));
	}
	/**
	 * 
	 */
	public void changerTour() {
	    if (tablier.isTouteDameMarquee(joueurEnCour)) {
	        finPartie(); // End the game if all checkers are marked
	    } else {
	        joueurEnCour = (joueurEnCour == CouleurCase.BLANC) ? CouleurCase.NOIR : CouleurCase.BLANC;
	    }

	    deSixFaces = new ArrayList<>(); // Reset dice
	    tourFini = true; // Mark the turn as finished
	}


	/**
	 * 
	 */
	public void finPartie() {
		//System.out.println("le joueur "+joueurEnCour + " a gagnée");
			partieFini=true;
		
	}
	
	/**
	 * 
	 */
	public void choixPremierJoueurLancementPartie() {
		ArrayList<DeSixFaces> deChoix = new ArrayList<DeSixFaces>();
		deChoix.add(new DeSixFaces(joueurEnCour));
		deChoix.add(new DeSixFaces(joueurEnCour));
		
		if(deChoix.get(0).getValeur() == deChoix.get(1).getValeur())
		{
			choixPremierJoueurLancementPartie();
		}
		else if(deChoix.get(0).getValeur() > deChoix.get(1).getValeur())
			joueurEnCour = CouleurCase.BLANC;
		else
			joueurEnCour = CouleurCase.NOIR;	
		
		premierJoueur = joueurEnCour;
	}

	/**
	 * 
	 * @param caseDepartInt
	 * @param caseArriveeInt
	 * @return
	 */
	public boolean jouerCoup(int caseDepartInt, int caseArriveeInt) {
		Coup coup = tablier.intToCoup(caseDepartInt, caseArriveeInt, joueurEnCour);
			return jouerCoup(coup.getCaseDepart(), coup.getCaseArriver());
	}
	
	/**
	 * 
	 * @param caseDepart
	 * @param caseArrivee
	 * @return
	 */
	public boolean jouerCoup(Case caseDepart, Case caseArrivee) {
		
		
		if(isCoupPossible(caseDepart,caseArrivee))
		{
			CouleurCase joueurEnemie;
			
			if (joueurEnCour == CouleurCase.BLANC)
				joueurEnemie = CouleurCase.NOIR;
			else
				joueurEnemie = CouleurCase.BLANC;
			
			int nbDameBarre = tablier.getCaseBarre(joueurEnemie).getNbDame();
			
			if (tablier.deplacerDame(caseDepart, caseArrivee))
			{
				deSixFaces.get(deUtiliser).utiliser();
				
				getDernierTour().addDeplacement(new Deplacement(caseDepart, caseArrivee,(nbDameBarre < tablier.getCaseBarre(joueurEnemie).getNbDame())));
				
				if (tablier.isTouteDameMarquee(joueurEnCour))
					finPartie();	
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}

	public boolean jouerCoup(Coup coup) {
		return jouerCoup(coup.getCaseDepart(),coup.getCaseArriver());
	}
	
	/**
	 * 
	 * @param caseDame
	 * @param de
	 * @return
	 */
	public boolean peutMarquerCetteDame(Case caseDame,DeSixFaces de)
	{
		Case caseVictoire;
		
		caseVictoire = tablier.getCaseVictoire(joueurEnCour);
		
			if (((tablier.distanceDeuxCase(caseDame, caseVictoire) == de.getValeur() 
					&& joueurEnCour == CouleurCase.BLANC)
					|| (tablier.distanceDeuxCase(caseDame, caseVictoire) == -de.getValeur() 
							&& joueurEnCour == CouleurCase.NOIR))
					&& !de.isUtilise())
				{return true;
				}
			else if (((tablier.distanceDeuxCase(caseDame, caseVictoire) < de.getValeur() 
					&& joueurEnCour == CouleurCase.BLANC && !tablier.isCaseAvant(caseDame))
					|| (tablier.distanceDeuxCase(caseDame, caseVictoire) > -de.getValeur() 
							&& joueurEnCour == CouleurCase.NOIR)&& !tablier.isCaseAvant(caseDame))
					&& !de.isUtilise())
				{return true;
				}
			return false;
	}
	
	/**
	 * 
	 * @param caseDame
	 * @return
	 */
	public boolean peutMarquerCetteDame(Case caseDame)
	{
		boolean siDeExiste = false;
		@SuppressWarnings("unused")
		int deUtiliser =0;
		Case caseVictoire;
		
		caseVictoire = tablier.getCaseVictoire(joueurEnCour);
		
		
		for (int i=0;i<deSixFaces.size();i++){
			if (((tablier.distanceDeuxCase(caseDame, caseVictoire) == deSixFaces.get(i).getValeur() 
					&& joueurEnCour == CouleurCase.BLANC)
					|| (tablier.distanceDeuxCase(caseDame, caseVictoire) == -deSixFaces.get(i).getValeur() 
							&& joueurEnCour == CouleurCase.NOIR))
					&& !deSixFaces.get(i).isUtilise())
				{siDeExiste = true;
				deUtiliser = i;}
			else if (((tablier.distanceDeuxCase(caseDame, caseVictoire) > deSixFaces.get(i).getValeur() 
					&& joueurEnCour == CouleurCase.BLANC && !tablier.isCaseAvant(caseDame))
					|| (tablier.distanceDeuxCase(caseDame, caseVictoire) > -deSixFaces.get(i).getValeur() 
							&& joueurEnCour == CouleurCase.NOIR)&& !tablier.isCaseAvant(caseDame))
					&& !deSixFaces.get(i).isUtilise())
				{siDeExiste = true;
				deUtiliser = i;}
			{
				
			}
		}
		if (!siDeExiste)
			return false;
			
		return true;
	}
	
	/**
	 * 
	 */
	public void annulerDernierCoup() {
		Deplacement dernierDeplacement;
		
		Tour dernierTour = getDernierTour();
		if (dernierTour != null)
			dernierDeplacement = getDernierTour().getDernierDeplacement();
		else
			dernierDeplacement =null;
		
		if (dernierDeplacement!=null){
			for (DeSixFaces de : dernierTour.getDeSixFaces()) {
				if (de.isUtilise() && de.getValeur() == Math.abs(tablier.distanceDeuxCase(dernierDeplacement.getCaseArriver(), dernierDeplacement.getCaseDepart())))
				{
					//recuperation de la couleur de la dame manger
					CouleurCase CaseArriverSaveCouleur;
					if (dernierDeplacement.getCaseArriver().getCouleurDame() == CouleurCase.BLANC)
						CaseArriverSaveCouleur = CouleurCase.NOIR;
					else
						CaseArriverSaveCouleur = CouleurCase.BLANC;
					
					tablier.deplacerDame(dernierDeplacement.getCaseArriver(),dernierDeplacement.getCaseDepart());
					de.notUtiliser();
					if(getDernierTour().getDernierDeplacement().isSiCaseBattue())
					{
						tablier.deplacerDame(tablier.getCaseBarre(CaseArriverSaveCouleur),dernierDeplacement.getCaseArriver());
					}
					if (tourFini)
					{
						tourFini = false;
						deSixFaces = dernierTour.getDeSixFaces();
						joueurEnCour = dernierTour.getCouleurJoueur();
					}
					dernierTour.supprimerDernierDeplacement();
					return;
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean siDesUtilises()
	{
		for (int i=0;i<deSixFaces.size();i++){
			if (!deSixFaces.get(i).isUtilise())
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 */
	public void lancerDes() {
		deSixFaces = new ArrayList<DeSixFaces>();
		deSixFaces.add(new DeSixFaces(joueurEnCour));
		deSixFaces.add(new DeSixFaces(joueurEnCour));
		if (deSixFaces.get(0).getValeur() == deSixFaces.get(1).getValeur()) {
			deSixFaces.add(new DeSixFaces(joueurEnCour, deSixFaces.get(0)
					.getValeur()));
			deSixFaces.add(new DeSixFaces(joueurEnCour, deSixFaces.get(0)
					.getValeur()));
		}	
		tourFini=false;
		debutTour();
	}

	public void doublerVideau() {
		videau.doubler();
	}
/**
 * 
 * @throws TourNonJouableException
 */
	public void deplacementAleatoire() throws TourNonJouableException {
		 List<Coup> casesPossible = getCoupsPossibles();
		 if (casesPossible.size() != 0)
			 jouerCoup(casesPossible.get((int)(Math.random()*casesPossible.size())));
		 else
			 throw new TourNonJouableException("Pas de possibilité de faire un déplacement");
	}


	/**
	 * 
	 * @param c
	 * @return
	 */
	public List<Case> getCoupsPossibles(Case c) {
		ArrayList<Case> caseReturn = new ArrayList<Case>() ;
		Case caseArriver;
			for (DeSixFaces de : deSixFaces) {
				if (!de.isUtilise())
					{
						caseArriver = tablier.getCaseADistance(c, de);
						if(isCoupPossible(c,caseArriver))
						{
							caseReturn.add(caseArriver);
						}
					}
			}
		
		return caseReturn;
		
		
	}
/**
 * 
 * @return
 */
	public List<Coup> getCoupsPossibles() {

		List<Coup> listeUnDe = new ArrayList<Coup>();
		
		for (Case caseDame : tablier.getAllCase()) {
			if(caseDame.getCouleurDame() == joueurEnCour)
			{
				for (@SuppressWarnings("unused") DeSixFaces tmpDe : deSixFaces){
					for (Case caseDametmp : getCoupsPossibles(caseDame)) {
						listeUnDe.add(new Coup(caseDame, caseDametmp));
					}
					
				}
			}
		}	
		return listeUnDe;
	}
	
/**
 * 
 * @param caseDepart
 * @param caseArrivee
 * @return
 */
	public boolean isCoupPossible(Case caseDepart, Case caseArrivee) {
		// verification de l'existance du de;
				boolean siDeExiste = false;
				deUtiliser =0;
				
				for (int i=0;i<deSixFaces.size();i++){
					
					if(peutMarquerCetteDame(caseDepart,deSixFaces.get(i)) && caseArrivee.isCaseVictoire() && caseArrivee.getCouleurDame() == caseDepart.getCouleurDame())
					{
						siDeExiste = true;
						deUtiliser = i;
					}
					else if (((tablier.distanceDeuxCase(caseDepart, caseArrivee) == deSixFaces.get(i).getValeur() 
							&& joueurEnCour == CouleurCase.BLANC)
							|| (tablier.distanceDeuxCase(caseDepart, caseArrivee) == -deSixFaces.get(i).getValeur() 
									&& joueurEnCour == CouleurCase.NOIR))
								&& !deSixFaces.get(i).isUtilise())
					{
						siDeExiste = true;
						deUtiliser = i;
					}

				}
				if (!siDeExiste)
					return false;
				
				if (tablier.sensDeplacementCorrect(caseDepart, caseArrivee))
				{
					return tablier.isCoupPossible(caseDepart, caseArrivee);
				}
				else
					return false;
			}

	/**
	 * 
	 * @param caseDepart
	 * @return
	 */
	public boolean isCoupPossible(Case caseDepart) {
		boolean possible=false;
		for (DeSixFaces de : deSixFaces) {
			if(isCoupPossible(caseDepart,tablier.getCaseADistance(caseDepart, de)) 
					&& !de.isUtilise())
				possible=true;
		}
		return possible;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasCoupPossible() {
		for (Case caseDame : tablier.getAllCase()) {
			if((!tablier.isDameDansCaseBarre(joueurEnCour) && caseDame.getCouleurDame() == joueurEnCour)
					|| caseDame.isCaseBarre())
				for (DeSixFaces de : deSixFaces) {
					if(!de.isUtilise())
						if(isCoupPossible(caseDame,tablier.getCaseADistance(caseDame, de)))
							return true;
				}
		}
		return false;	
	}
/**
 * 
 * @return
 */
	public Tour getDernierTour()
	{
		if (historiqueToursJoueur.size() != 0)
			return historiqueToursJoueur.get(historiqueToursJoueur.size()-1);
		else
			return null;
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	public Deplacement ProchainDeplacement(int i) {

		Deplacement deplacementProchain = null;
		Tour touractuelle = null;
		int j =0;
		for (Tour tour : historiqueToursJoueur) {
			touractuelle = tour;
			for (Deplacement deplacement : tour.getListDeplacement()) {
				j++;
				if(j == (i))
				{
					deplacementProchain = deplacement;
					break;
				}
				
			}
		}
	
		if (deplacementProchain!=null && touractuelle !=null ){
			tablier.deplacerDame(deplacementProchain.getCaseDepart(),deplacementProchain.getCaseArriver());
		}
		
		return deplacementProchain;
	}
	
	
	
/**
 * 
 * @param i
 * @return
 */
	public Deplacement PrecedentDeplacement(int i) {
		
		Deplacement deplacementPrecedent = null;
		Tour touractuelle = null;
		int j =0;
		for (Tour tour : historiqueToursJoueur) {
			touractuelle = tour;
			for (Deplacement deplacement : tour.getListDeplacement()) {
				j++;
				if(j == (i))
				{
					deplacementPrecedent = deplacement;
					break;
				}
			}
		}
	
		if (deplacementPrecedent!=null && touractuelle !=null ){
		
					//recuperation de la couleur de la dame manger
					CouleurCase CaseArriverSaveCouleur;
					if (deplacementPrecedent.getCaseArriver().getCouleurDame() == CouleurCase.BLANC)
						CaseArriverSaveCouleur = CouleurCase.NOIR;
					else
						CaseArriverSaveCouleur = CouleurCase.BLANC;
					
					
					tablier.deplacerDame(deplacementPrecedent.getCaseArriver(),deplacementPrecedent.getCaseDepart());
					
					if(deplacementPrecedent.isSiCaseBattue())
					{
						tablier.deplacerDame(tablier.getCaseBarre(CaseArriverSaveCouleur),deplacementPrecedent.getCaseArriver());
					}					
					
				}

			return deplacementPrecedent;
	}


	public int nbDeplacementHistorise()
	{	
		int i = 0;
		for (Tour tour : historiqueToursJoueur) {
			for (@SuppressWarnings("unused") Deplacement deplacement : tour.getListDeplacement()) {
				i++;
			}
		}
		return i;
	}
	
	/* SERIALISATION */

	/**
	 * 
	 * @param session
	 */
	public void sauvegarder(Element session) {
		
		Element partie = new Element("partie");
		session.addContent(partie);
		
		Element videauXML = new Element("videau");
		videauXML.setText(String.valueOf(videau.getvideau()));
		partie.addContent(videauXML);
	    
	    Element joueurEnCourXML = new Element("joueurEnCour");
	    joueurEnCourXML.setText(String.valueOf(joueurEnCour));
	    partie.addContent(joueurEnCourXML);
	    
	    Element idPartieXML = new Element("idPartie");
	    idPartieXML.setText(String.valueOf(idPartie));
	    partie.addContent(idPartieXML);

	    Element deUtiliserXML = new Element("deUtiliser");
	    deUtiliserXML.setText(String.valueOf(deUtiliser));
	    partie.addContent(deUtiliserXML);
	    
	    Element premierJoueurXML = new Element("premierJoueur");
	    premierJoueurXML.setText(String.valueOf(premierJoueur));
	    partie.addContent(premierJoueurXML);
	    
	    Element deSixFacesXML = new Element("deSixFaces");
	    partie.addContent(deSixFacesXML);
	    
		for(int i=0;i<deSixFaces.size();i++){
			deSixFaces.get(i).sauvegarder(deSixFacesXML);
		}
		
		tablier.sauvegarder(partie);
		
		Element historiqueToursJoueurXML = new Element("historiqueToursJoueur");
		partie.addContent(historiqueToursJoueurXML);
		    
		for(int i=0;i<historiqueToursJoueur.size();i++){
			historiqueToursJoueur.get(i).sauvegarder(historiqueToursJoueurXML);
		}
	
	}
/**
 * 
 * @param partie
 */
	public void charger(Element partie) {
		
		videau = new Videau(Integer.valueOf(partie.getChildText("videau")));
		
		switch(partie.getChildText("joueurEnCour")){
			case "NOIR":joueurEnCour = CouleurCase.NOIR;break;
			case "BLANC":joueurEnCour = CouleurCase.BLANC;break;
			case "VIDE":joueurEnCour = CouleurCase.VIDE;
		}
		
		idPartie = Integer.valueOf(partie.getChildText("idPartie"));
		deUtiliser = Integer.valueOf(partie.getChildText("deUtiliser"));
		
		switch(partie.getChildText("premierJoueur")){
			case "NOIR":premierJoueur = CouleurCase.NOIR;break;
			case "BLANC":premierJoueur = CouleurCase.BLANC;break;
			case "VIDE":premierJoueur = CouleurCase.VIDE;
		}
		
		 List<Element> listDeSixFaces = partie.getChild("deSixFaces").getChildren("deSixFace");
		 Iterator<Element> it = listDeSixFaces.iterator();
		 
		 while(it.hasNext()){
			 DeSixFaces tmpDe = new DeSixFaces();
			 tmpDe.charger(it.next());
			 deSixFaces.add(tmpDe);
		 }
		 
			
		tablier = new Tablier(this);
		
		tablier.charger(partie);
		
		List<Element> listhistoriqueToursJoueur = partie.getChild("historiqueToursJoueur").getChildren("tour");
		Iterator<Element> i = listhistoriqueToursJoueur.iterator();
		 
		while(i.hasNext()){
			Tour tmpTour = new Tour();
			tmpTour.charger(i.next(),this);
			historiqueToursJoueur.add(tmpTour);
		}
	}

	/* GETTERS ET SETTERS */

	public ParametreJeu getParametreJeu() {
		return parametreJeu;
	}

	public void setParametreJeu(ParametreJeu parametreJeu) {
		this.parametreJeu = parametreJeu;
	}

	public Videau getVideau() {
		return videau;
	}

	public void setVideau(Videau videau) {
		this.videau = videau;
	}

	public ArrayList<DeSixFaces> getDeSixFaces() {
		return deSixFaces;
	}

	public void setDeSixFaces(ArrayList<DeSixFaces> deSixFaces) {
		this.deSixFaces = deSixFaces;
	}

	public Tablier getTablier() {
		return tablier;
	}

	public void setTablier(Tablier tablier) {
		this.tablier = tablier;
	}

	public CouleurCase getJoueurEnCour() {
		return joueurEnCour;
	}

	public void setJoueurEnCour(CouleurCase joueurEnCour) {
		this.joueurEnCour = joueurEnCour;
	}

	public List<Tour> getHistoriqueToursJoueur() {
		return historiqueToursJoueur;
	}

	public void setHistoriqueToursJoueur(ArrayList<Tour> historiqueToursJoueur) {
		this.historiqueToursJoueur = historiqueToursJoueur;
	}

	public int getIdPartie() {
		return idPartie;
	}

	public void setIdPartie(int idPartie) {
		this.idPartie = idPartie;
	}


	public boolean isPartieFini() {
		return partieFini;
	}


	public boolean isTourFini() {
		return tourFini;
	}
	
	public CouleurCase getPremierJoueur() {
		return premierJoueur;
	}
	
	public void grantExtraTurn() {
	    System.out.println("Extra turn granted to the current player!");
	    // Logic to ensure the current player gets another turn
	    // Example: Do not increment the turn counter or reassign player turn
	}


	public void setEnhancedDiceValues(int value1, int value2) {
	    this.enhancedDiceValue1 = value1;
	    this.enhancedDiceValue2 = value2;
	}

	
	public int[] getEnhancedDiceValues() {
	    return new int[]{enhancedDiceValue1, enhancedDiceValue2};
	}
	

	

}
