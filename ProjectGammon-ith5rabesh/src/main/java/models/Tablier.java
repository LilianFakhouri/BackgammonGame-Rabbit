// 
//
//  @ Projet : Project Gammon
//  @ Fichier : Tablier.java
//  @ Date : 12/12/2012
//  @ Auteurs : DONG Chuan, BONNETTO Benjamin, FRANCON Adrien, POTHELUNE Jean-Michel
//
//




package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.jdom2.Attribute;
import org.jdom2.Element;


public class Tablier
{
	private ArrayList<Case> listeCase;
	private ArrayList<Case> caseVictoire;
	private ArrayList<Case> caseBarre;
	private Partie partie;
	
	public Tablier(Partie p)
	{
		partie=p;		
		listeCase= new ArrayList<Case>();
		caseVictoire = new ArrayList<Case>();
		caseBarre= new ArrayList<Case>();
		initialiserCase();
	}
	
	public Tablier()
	{
		partie= null;
		
		listeCase= new ArrayList<Case>();
		caseVictoire = new ArrayList<Case>();
		caseBarre= new ArrayList<Case>();
		initialiserCase();
	}
	
	public void initialiserCase(ArrayList<Case> listCase)
	{
		listeCase = new ArrayList<Case>();
		for (Case case1 : listCase) {
			listeCase.add(case1);
		}
		caseVictoire = new ArrayList<Case>();
		caseBarre = new ArrayList<Case>();
	}
	
	public void initialiserCaseBarre(ArrayList<Case> listCase)
	{
		caseBarre.add(listCase.get(0));
		caseBarre.add(listCase.get(1));
	}
	
	public void initialiserCaseVictoire(ArrayList<Case> listCase)
	{
		caseVictoire.add(listCase.get(0));
		caseVictoire.add(listCase.get(1));
	}
	/**
	 * Initialiser tous les cases dans le tablier
	 * 
	 * 
	 * 
	 */
	
	public void initializeStations() {
	    Random random = new Random();

	    // Place 3 question stations
	    for (int i = 0; i < 3; i++) {
	        int randomIndex;
	        do {
	            randomIndex = random.nextInt(listeCase.size());
	        } while (!listeCase.get(randomIndex).getCaseType().equals(Case.EMPTY)); // Ensure it's an empty spot

	        listeCase.get(randomIndex).setCaseType(Case.QUESTION);
	    }

	    // Place 1 surprise station
	    int randomIndex;
	    do {
	        randomIndex = random.nextInt(listeCase.size());
	    } while (!listeCase.get(randomIndex).getCaseType().equals(Case.EMPTY)); // Ensure it's an empty spot

	    listeCase.get(randomIndex).setCaseType(Case.SURPRISE);
	}

	public void initialiserCase() {
	    listeCase.clear();
	    caseVictoire.clear();
	    caseBarre.clear();

	    // Normal case initialization logic
	    caseVictoire.add(new Case(CouleurCase.BLANC, 0, 25));
	    caseVictoire.add(new Case(CouleurCase.NOIR, 0, 0));

	    caseBarre.add(new Case(CouleurCase.BLANC, 0, 0));
	    caseBarre.add(new Case(CouleurCase.NOIR, 0, 25));

	    for (int i = 1; i <= 24; i++) {
	        if (i == 1) {
	            listeCase.add(new Case(CouleurCase.BLANC, 2, i));
	        } else if (i == 6) {
	            listeCase.add(new Case(CouleurCase.NOIR, 5, i));
	        } else if (i == 8) {
	            listeCase.add(new Case(CouleurCase.NOIR, 3, i));
	        } else if (i == 12) {
	            listeCase.add(new Case(CouleurCase.BLANC, 5, i));
	        } else if (i == 13) {
	            listeCase.add(new Case(CouleurCase.NOIR, 5, i));
	        } else if (i == 17) {
	            listeCase.add(new Case(CouleurCase.BLANC, 3, i));
	        } else if (i == 19) {
	            listeCase.add(new Case(CouleurCase.BLANC, 5, i));
	        } else if (i == 24) {
	            listeCase.add(new Case(CouleurCase.NOIR, 2, i));
	        } else {
	            listeCase.add(new Case(CouleurCase.VIDE, 0, i));
	        }
	    }

	    // Add special stations
	    assignSpecialStations(3, 1); // 3 questions, 1 surprise
	}

	
	
	public void reinitialisationCase() {
	    if (caseVictoire.size() < 2 || caseBarre.size() < 2) {
	        throw new IllegalStateException("caseVictoire or caseBarre is not properly initialized.");
	    }

	    caseVictoire.get(0).setCase(CouleurCase.BLANC, 0);
	    caseVictoire.get(1).setCase(CouleurCase.NOIR, 0);

	    caseBarre.get(0).setCase(CouleurCase.BLANC, 0);
	    caseBarre.get(1).setCase(CouleurCase.NOIR, 0);

	    for (int i = 0; i < 24; i++) {
	        if (i == 0)
	            listeCase.get(i).setCase(CouleurCase.BLANC, 2);
	        else if (i == 5)
	            listeCase.get(i).setCase(CouleurCase.NOIR, 5);
	        else if (i == 7)
	            listeCase.get(i).setCase(CouleurCase.NOIR, 3);
	        else if (i == 11)
	            listeCase.get(i).setCase(CouleurCase.BLANC, 5);
	        else if (i == 12)
	            listeCase.get(i).setCase(CouleurCase.NOIR, 5);
	        else if (i == 16)
	            listeCase.get(i).setCase(CouleurCase.BLANC, 3);
	        else if (i == 18)
	            listeCase.get(i).setCase(CouleurCase.BLANC, 5);
	        else if (i == 23)
	            listeCase.get(i).setCase(CouleurCase.NOIR, 2);
	        else
            listeCase.get(i).setCase(CouleurCase.VIDE, 0);}
    }


	
	public int distanceDeuxCase(Case cDepart, Case cArrivee) {
	    if (cDepart == null || cArrivee == null) {
	        System.out.println("Invalid cases provided for distanceDeuxCase. cDepart: " 
	                           + (cDepart == null ? "null" : cDepart.getPosition()) 
	                           + ", cArrivee: " 
	                           + (cArrivee == null ? "null" : cArrivee.getPosition()));
	        return Integer.MAX_VALUE; // Or some other invalid value to indicate an error
	    }
	    return cArrivee.getPosition() - cDepart.getPosition();
	}

	
	public boolean sensDeplacementCorrect(Case cDepart, Case cArrivee)
	{
		if (distanceDeuxCase(cDepart,cArrivee)<0 && cDepart.getCouleurDame() == CouleurCase.BLANC 
				|| distanceDeuxCase(cDepart,cArrivee)>0 && cDepart.getCouleurDame() == CouleurCase.NOIR )
			return false;
		else
			return true;
	}
	/**
	 * Verifier si c'est possible de deplacer entre les deux cases
	 * @param cDepart case depart
	 * @param cArrivee case arrivee
	 * @return boolean
	 */
	public boolean isCoupPossible(Case cDepart, Case cArrivee) {
	    if (cDepart == null || cArrivee == null) {
	        System.out.println("Invalid move. cDepart: " 
	                           + (cDepart == null ? "null" : cDepart.getPosition()) 
	                           + ", cArrivee: " 
	                           + (cArrivee == null ? "null" : cArrivee.getPosition()));
	        return false;
	    }

	    if (cDepart.getNbDame() == 0) {
	        return false;
	    }

	    // Prevent movement against the rules (backward movement check)
	    int distance = distanceDeuxCase(cDepart, cArrivee);
	    if (distance < 0 && cDepart.getCouleurDame() == CouleurCase.BLANC 
	        || distance > 0 && cDepart.getCouleurDame() == CouleurCase.NOIR) {
	        return true; // Allow backward movement for negative dice
	    }

	    // Check standard rules for capture or valid arrival
	    if (cDepart.getCouleurDame() == cArrivee.getCouleurDame()) {
	        return true;
	    } else {
	        return cArrivee.getNbDame() <= 1; // Can only land on an empty or single-opponent piece case
	    }
	}


	
	public boolean siDameManger(Case cDepart, Case cArrivee)
	{
		if(cDepart.getCouleurDame() != cArrivee.getCouleurDame() 
				&& cArrivee.getCouleurDame() != CouleurCase.VIDE 
				&& cArrivee.getNbDame()==1)
			return true;
		else
			return false;
	}
	
	public Coup intToCoup(int cDepartInt, int cArriveeInt,CouleurCase couleurCaseDepart)
	{
		Coup coup = new Coup();
		
		if (cDepartInt == 0 && couleurCaseDepart == CouleurCase.BLANC)
			coup.setCaseDepart(caseBarre.get(0));
		else if (cDepartInt == 25 && couleurCaseDepart == CouleurCase.NOIR)
			coup.setCaseDepart(caseBarre.get(1));
		else if (cDepartInt != 0 && cDepartInt != 25)
			coup.setCaseDepart(listeCase.get(cDepartInt-1));
			
		if (cArriveeInt == 25 && couleurCaseDepart == CouleurCase.BLANC)
			coup.setCaseArriver(caseVictoire.get(0));
		else if (cArriveeInt == 0 && couleurCaseDepart == CouleurCase.NOIR)
			coup.setCaseArriver(caseVictoire.get(1));
		else if (cArriveeInt != 0 && cArriveeInt != 25)
			coup.setCaseArriver(listeCase.get(cArriveeInt-1));
		
		
		return coup;
	}
	
	public boolean deplacerDame(int cDepartInt, int cArriveeInt,CouleurCase couleurCaseDepart)
	{
		Coup coup = intToCoup(cDepartInt,cArriveeInt,couleurCaseDepart);
		return deplacerDame(coup.getCaseDepart(),coup.getCaseArriver());
	}
	
	public boolean deplacerDame(Case cDepart, Case cArrivee)
	{
		
		if(isCoupPossible(cDepart,cArrivee))
		{
			//enregistrement de la case de départ
			Case CaseDepartSave =  new Case(cDepart.getCouleurDame(),cDepart.getNbDame(),cDepart.getPosition());
			
			
			//suppresion du jeton si possible
			if (!cDepart.moinDame())
				return false;
			//ajout de la dame
			
			if (siDameManger(CaseDepartSave,cArrivee))
			{
				if ( cArrivee.getCouleurDame()==CouleurCase.BLANC)
					caseBarre.get(0).ajoutDame(CouleurCase.BLANC);
				else
					caseBarre.get(1).ajoutDame(CouleurCase.NOIR);
			}
				
			cArrivee.ajoutDame(CaseDepartSave.getCouleurDame());
			
			return true;
		}
		else
			return false;
	}
	
	public boolean isCaseAvant(Case caseDame)
	{
		int nbDame = 0;
		
		if(caseDame.getCouleurDame() == CouleurCase.BLANC)
		{
			for (int i=18;i<(caseDame.getPosition()-1);i++)
			{
				if (getListeCase().get(i).getCouleurDame() == CouleurCase.BLANC)
					nbDame += getListeCase().get(i).getNbDame();
			}
		}
		else
		{
			for (int i=5;i>=caseDame.getPosition();i--)
			{
				if (getListeCase().get(i).getCouleurDame() == CouleurCase.NOIR)
					nbDame += getListeCase().get(i).getNbDame();
			}
		}
		
		
		if (nbDame==0)
			return false;
		else
			return true;
	}
	
	public boolean peutMarquerDame(CouleurCase Couleur)
	{
		int nbDame = 0;
		
		if(Couleur == CouleurCase.BLANC)
		{
			nbDame += getCaseBarre().get(0).getNbDame();
			for (int i=0;i<18;i++)
			{
				if (getListeCase().get(i).getCouleurDame() == CouleurCase.BLANC)
					nbDame += getListeCase().get(i).getNbDame();
			}
		}
		else
		{
			nbDame += getCaseBarre().get(1).getNbDame();
			for (int i=6;i<24;i++)
			{
				if (getListeCase().get(i).getCouleurDame() == CouleurCase.NOIR)
					nbDame += getListeCase().get(i).getNbDame();
			}
		}
		if (nbDame==0)
			return true;
		else
			return false;
	}
	
	public boolean isTouteDameMarquee(CouleurCase couleur)
	{
		if (couleur == CouleurCase.BLANC && getCaseVictoire().get(0).getNbDame() == 15
				|| couleur == CouleurCase.NOIR && getCaseVictoire().get(1).getNbDame() == 15 )
			return true;
		else 
			return false;
			
	}
	/**
	 * Recuperer le case arivee possible avec un case et un de donnee 
	 * @param c Case Depart
	 * @param de Un de 
	 * @return Case Arivee
	 */
	public Case getCaseADistance(Case c, DeSixFaces de) {
	    if (c == null || de == null) {
	        System.out.println("Invalid input for getCaseADistance. c: " 
	                           + (c == null ? "null" : c.getPosition()) 
	                           + ", de: " 
	                           + (de == null ? "null" : de.getValeur()));
	        return null;
	    }

	    int diceValue = de.getValeur();
	    if (diceValue < 0) {
	        diceValue = Math.abs(diceValue); // Convert to positive for backward movement
	    }

	    int targetPosition;

	    // Adjust position based on player color and dice value
	    if (c.getCouleurDame() == CouleurCase.BLANC) {
	        targetPosition = c.getPosition() + (de.getValeur() < 0 ? -diceValue : diceValue);
	    } else {
	        targetPosition = c.getPosition() - (de.getValeur() < 0 ? -diceValue : diceValue);
	    }

	    // Handle out-of-bounds cases
	    if (targetPosition < 1 || targetPosition > 24) {
	        if (c.getCouleurDame() == CouleurCase.BLANC && targetPosition > 24) {
	            return caseVictoire.get(0); // White victory
	        } else if (c.getCouleurDame() == CouleurCase.NOIR && targetPosition < 1) {
	            return caseVictoire.get(1); // Black victory
	        } else {
	            return null; // Invalid move
	        }
	    }

	    // Return the corresponding case
	    return listeCase.get(targetPosition - 1);
	}




	
    /**
     * Verifier dans CaseBarre (Noir ou Blanc) il y a des damns
     * @param couleur Couleur de CaseBarre
     * @return turn = il y a des damns.  false = il y a rien
     */
	public boolean isDameDansCaseBarre(CouleurCase couleur)
	{
		if(getCaseBarre(couleur).getNbDame() == 0)
			return false;
		else
			return true;
				
	}
	/**
	 * Recuperer tous les cases possibles
	 * @param de Un de 
	 * @param couleur Couleur de joueur en cours
	 * @return une liste de tous les cases possibles
	 */
	public List<Coup> getCoupsPossibles(DeSixFaces de, CouleurCase couleur) {
	    List<Coup> possibleMoves = new ArrayList<>();

	    for (Case currentCase : listeCase) {
	        if (currentCase.getCouleurDame() == couleur && !de.isUtilise()) {
	            int targetPosition = couleur == CouleurCase.BLANC
	                ? currentCase.getPosition() + de.getValeur()
	                : currentCase.getPosition() - de.getValeur();

	            if (targetPosition >= 1 && targetPosition <= 24) {
	                Case targetCase = listeCase.get(targetPosition - 1);
	                if (isCoupPossible(currentCase, targetCase)) {
	                    possibleMoves.add(new Coup(currentCase, targetCase));
	                }
	            }
	        }
	    }

	    return possibleMoves;
	}

	/**
	 * Recuperer tous les coups possibles selon les valeurs de des
	 * @param des Une liste de des
	 * @param couleur Couleur de joueur en cours
	 * @return une listes de coups possibles
	 */
	public List<Coup> getCoupsPossibles(List<DeSixFaces> des,CouleurCase couleur)
	{	
		int somme = 0;
		
		if(des.size()==2){
			
			List<Coup> listeUnDe = new ArrayList<Coup>();
			
			for (DeSixFaces tmpDe : des){
				listeUnDe.addAll(getCoupsPossibles(tmpDe,couleur));
				somme = somme + tmpDe.getValeur();
			}
			
			DeSixFaces sommeDe = new DeSixFaces(des.get(0).getCouleurDe(),somme);
			List<Coup> listeDeuxDes = getCoupsPossibles(sommeDe,couleur);
			
			listeDeuxDes.addAll(listeUnDe);
			
			return listeDeuxDes;
		}
		
		else{//Si 4 des
			
			DeSixFaces sommeQuatreDe = new DeSixFaces(des.get(0).getCouleurDe(),4*des.get(0).getValeur());
			DeSixFaces sommeTroisDe = new DeSixFaces(des.get(0).getCouleurDe(),3*des.get(0).getValeur());
			DeSixFaces sommedeuxDe = new DeSixFaces(des.get(0).getCouleurDe(),2*des.get(0).getValeur());
			
			List<Coup> listeQuatre = getCoupsPossibles(sommeQuatreDe,couleur);
			
			listeQuatre.addAll(getCoupsPossibles(sommeTroisDe,couleur));
			listeQuatre.addAll(getCoupsPossibles(sommedeuxDe,couleur));
			listeQuatre.addAll(getCoupsPossibles(des.get(0),couleur));
			
			return listeQuatre;
		}
	}
	/**
	 * Sauvegarder tous les infos sur tablier sous Racine partie
	 * @param partie Racine de Tablier dans fichiers XML
	 */
	public void sauvegarder(Element partie)
	{
		Element tablierXML = new Element("tablier");
	    partie.addContent(tablierXML);
	    
	    Element casesXML = new Element("Cases");
	    tablierXML.addContent(casesXML);
	    
	    for(int i = 0;i<listeCase.size();i++){
	    	
	    	Element CaseXML = new Element("Case");
	    	casesXML.addContent(CaseXML);
		    
		    Attribute idCase = new Attribute("id",String.valueOf(listeCase.get(i).getPosition()));
		    CaseXML.setAttribute(idCase);
		    
			    Element couleurDameXML = new Element("couleurDame");
			    couleurDameXML.setText(String.valueOf(listeCase.get(i).getCouleurDame()));
			    CaseXML.addContent(couleurDameXML);
			    
			    Element nbrDameXML = new Element("nbrDame");
			    nbrDameXML.setText(String.valueOf(listeCase.get(i).getNbDame()));
			    CaseXML.addContent(nbrDameXML);
	    }
	    
	    Element CaseVictoiresXML = new Element("CaseVictoires");
	    tablierXML.addContent(CaseVictoiresXML);
	    
	    for(int i = 0;i<caseVictoire.size();i++){
	    	
	    	Element CaseVictoireXML = new Element("CaseVictoire");
	    	CaseVictoiresXML.addContent(CaseVictoireXML);
	    	
	    	Attribute idCaseVictoire = new Attribute("id",String.valueOf(caseVictoire.get(i).getPosition()));
	    	CaseVictoireXML.setAttribute(idCaseVictoire);
		    
			    Element couleurDameVXML = new Element("couleurDame");
			    couleurDameVXML.setText(String.valueOf(caseVictoire.get(i).getCouleurDame()));
			    CaseVictoireXML.addContent(couleurDameVXML);
			    
			    Element nbrDameVXML = new Element("nbrDame");
			    nbrDameVXML.setText(String.valueOf(caseVictoire.get(i).getNbDame()));
			    CaseVictoireXML.addContent(nbrDameVXML);
	    }
	    
	    Element CaseBarsXML = new Element("CaseBars");
	    tablierXML.addContent(CaseBarsXML);
	    
	    for(int i = 0;i<caseBarre.size();i++){
	    	
	    	Element CaseBarXML = new Element("CaseBar");
	    	CaseBarsXML.addContent(CaseBarXML);
	    	
	    	Attribute idCaseBar = new Attribute("id",String.valueOf(caseBarre.get(i).getPosition()));
	    	CaseBarXML.setAttribute(idCaseBar);
		    
			    Element couleurDameBXML = new Element("couleurDame");
			    if(caseBarre.get(i).getPosition() == 0)
			    	couleurDameBXML.setText(String.valueOf(CouleurCase.BLANC));
			    else
			    	couleurDameBXML.setText(String.valueOf(CouleurCase.NOIR));
			    CaseBarXML.addContent(couleurDameBXML);
			    
			    Element nbrDameBXML = new Element("nbrDame");
			    nbrDameBXML.setText(String.valueOf(caseBarre.get(i).getNbDame()));
			    CaseBarXML.addContent(nbrDameBXML);
	    }
	}
	/**
	 * charger tous les infos sur tablier sous Racine partie
	 * @param partie Racine de Tablier dans fichiers XML
	 */

	
	public void charger(Element Partie) {
	    listeCase = new ArrayList<>();
	    Element tablierElement = Partie.getChild("tablier");
	    if (tablierElement != null) {
	        Element casesElement = tablierElement.getChild("Cases");
	        if (casesElement != null) {
	            List<Element> listlisteCase = casesElement.getChildren("Case");
	            for (Element caseElement : listlisteCase) {
	                Case tmpCase = new Case();
	                tmpCase.charger(caseElement);
	                listeCase.add(tmpCase);
	            }
	        }
	        
	        caseVictoire = new ArrayList<>();
	        Element caseVictoiresElement = tablierElement.getChild("CaseVictoires");
	        if (caseVictoiresElement != null) {
	            List<Element> listcaseVictoire = caseVictoiresElement.getChildren("CaseVictoire");
	            for (Element caseElement : listcaseVictoire) {
	                Case tmpCase = new Case();
	                tmpCase.charger(caseElement);
	                caseVictoire.add(tmpCase);
	            }
	        }
	        
	        caseBarre = new ArrayList<>();
	        Element caseBarsElement = tablierElement.getChild("CaseBars");
	        if (caseBarsElement != null) {
	            List<Element> listcaseBarre = caseBarsElement.getChildren("CaseBar");
	            for (Element caseElement : listcaseBarre) {
	                Case tmpCase = new Case();
	                tmpCase.charger(caseElement);
	                caseBarre.add(tmpCase);
	            }
	        }
	    }
	}

	/**
	 * 
	 * GETTERS
	 * 
	 */
	public ArrayList<Case> getListeCase() {
		return listeCase;
	}

	public ArrayList<Case> getCaseVictoire() {
		return caseVictoire;
	}
	
	public Case getCaseVictoire(CouleurCase couleur) {
		if(couleur == CouleurCase.BLANC)
			return caseVictoire.get(0);
		else
			return caseVictoire.get(1);
	}

	public ArrayList<Case> getCaseBarre() {
		return caseBarre;
	}
	
	public Case getCase(int position, CouleurCase couleur) {
	    if (position >= 1 && position <= 24) {
	        return listeCase.get(position - 1);
	    } else if (position == 25 && couleur.equals(CouleurCase.BLANC)) {
	        return caseVictoire.get(0);
	    } else if (position == 0 && couleur.equals(CouleurCase.NOIR)) {
	        return caseVictoire.get(1);
	    } else if (position == 0 && couleur.equals(CouleurCase.BLANC)) {
	        return caseBarre.get(0);
	    } else if (position == 25 && couleur.equals(CouleurCase.NOIR)) {
	        return caseBarre.get(1);
	    } else {
	        // Return null for invalid positions instead of throwing exceptions
	        System.out.println("Invalid position requested: " + position);
	        return null;
	    }
	}


	
	public Case getCaseBarre(CouleurCase couleur) {
		if(couleur == CouleurCase.BLANC)
			return caseBarre.get(0);
		else
			return caseBarre.get(1);
	}
	/**
	 * Mettre les cases normals,casesVictoire,casesBars ensemble	
	 * @return une list contenant tous les cases,casesVictoire,casesBars
	 */
	public ArrayList<Case> getAllCase() {
		ArrayList<Case> listAllCase = new ArrayList<Case>();
		for (Case case1 : listeCase) {
			listAllCase.add(case1);
		}
		for (Case case1 : caseVictoire) {
			listAllCase.add(case1);
		}
		for (Case case1 : caseBarre) {
			listAllCase.add(case1);
		}
		return listAllCase;
	}

	public Partie getPartie() {
		return partie;
	}
	
	public void placeSpecialStation(String type) {
	    Random rand = new Random();
	    int randomIndex;

	    // Find an empty case to place the station
	    do {
	        randomIndex = rand.nextInt(listeCase.size());
	    } while (!listeCase.get(randomIndex).getCaseType().equals(Case.EMPTY));

	    listeCase.get(randomIndex).setCaseType(type);
	}
	
	
	
	public void initializeSpecialStations(int questionCount, int surpriseCount) {
	    Random random = new Random();

	    // Add Question Stations
	    for (int i = 0; i < questionCount; i++) {
	        int randomIndex;
	        do {
	            randomIndex = random.nextInt(listeCase.size());
	        } while (!listeCase.get(randomIndex).getCaseType().equals(Case.EMPTY));
	        listeCase.get(randomIndex).setCaseType(Case.QUESTION);
	    }

	    // Add Surprise Station
	    for (int i = 0; i < surpriseCount; i++) {
	        int randomIndex;
	        do {
	            randomIndex = random.nextInt(listeCase.size());
	        } while (!listeCase.get(randomIndex).getCaseType().equals(Case.EMPTY));
	        listeCase.get(randomIndex).setCaseType(Case.SURPRISE);
	    }
	}


	
	public void assignSpecialStations(int numQuestions, int numSurprises) {
	    Random random = new Random();

	    // Assign question stations
	    for (int i = 0; i < numQuestions; i++) {
	        int randomIndex;
	        do {
	            randomIndex = random.nextInt(listeCase.size());
	        } while (!listeCase.get(randomIndex).getCaseType().equals(Case.EMPTY));
	        listeCase.get(randomIndex).setCaseType(Case.QUESTION);
	    }

	    // Assign surprise stations
	    for (int i = 0; i < numSurprises; i++) {
	        int randomIndex;
	        do {
	            randomIndex = random.nextInt(listeCase.size());
	        } while (!listeCase.get(randomIndex).getCaseType().equals(Case.EMPTY));
	        listeCase.get(randomIndex).setCaseType(Case.SURPRISE);
	    }
	}


	private int[] enhancedDiceValues = new int[2]; // Array to store the two dice values

	public void setEnhancedDiceValues(int value1, int value2) {
	    this.enhancedDiceValues[0] = value1;
	    this.enhancedDiceValues[1] = value2;
	}

	public int[] getEnhancedDiceValues() {
	    return enhancedDiceValues;
	}

	

	
}
