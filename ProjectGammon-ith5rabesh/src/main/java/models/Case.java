//
//  @ Projet : Project Gammon
//  @ Fichier : Case.java
//  @ Date : 12/12/2012
//  @ Auteurs : DONG Chuan, BONNETTO Benjamin, FRANCON Adrien, POTHELUNE Jean-Michel
//
//

package models;

import org.jdom2.Element;

public class Case {
    private CouleurCase couleurDame;
    private int nbDame;
    private int position;

    public static final String EMPTY = "EMPTY";
    public static final String QUESTION = "QUESTION";
    public static final String SURPRISE = "SURPRISE";
    public static final String QUESTION_STATION = "QUESTION_STATION"; // New constant

    private String caseType;

    // Constructor for initializing with all attributes
    public Case(CouleurCase couleur, int nbDame, int position, String caseType) {
        this.couleurDame = couleur;
        this.nbDame = nbDame;
        this.position = position;
        this.caseType = caseType;
    }

    // Default constructor
    public Case() {
        this.couleurDame = CouleurCase.VIDE;
        this.nbDame = 0;
        this.position = -1; // Default value, can be changed as needed
        this.caseType = EMPTY; // Default type
    }

    // Existing constructor (kept for backward compatibility)
    public Case(CouleurCase couleur, int nbDame, int position) {
        this.couleurDame = couleur;
        this.nbDame = nbDame;
        this.position = position;
        this.caseType = EMPTY;
    }

    // Setters and getters
    public CouleurCase getCouleurDame() {
        return couleurDame;
    }

    public void setCouleurDame(CouleurCase couleurDame) {
        this.couleurDame = couleurDame;
    }

    public int getNbDame() {
        return nbDame;
    }

    public void setNbDame(int nbDame) {
        this.nbDame = nbDame;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCase(CouleurCase couleur, int nbDame) {
        this.couleurDame = couleur;
        this.nbDame = nbDame;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    // Method to mark a case as a question station
    public void setAsQuestionStation() {
        this.caseType = QUESTION_STATION;
    }

    // Method to return a marker based on the case type
    public String getMarker() {
        switch (this.caseType) {
            case QUESTION:
                return "?"; // Question marker
            case SURPRISE:
                return "🎁"; // Surprise marker
            case QUESTION_STATION:
                return "❓"; // Question station marker
            default:
                return ""; // No marker
        }
    }

    // Method to load case details from an XML element
    public void charger(Element caseElement) {
        switch (caseElement.getChildText("couleurDame")) {
            case "BLANC":
                couleurDame = CouleurCase.BLANC;
                break;
            case "NOIR":
                couleurDame = CouleurCase.NOIR;
                break;
            case "VIDE":
                couleurDame = CouleurCase.VIDE;
                break;
        }

        nbDame = Integer.valueOf(caseElement.getChildText("nbrDame"));
        position = Integer.valueOf(caseElement.getAttributeValue("id"));
        caseType = caseElement.getChildText("caseType"); // Load case type
    }

    // Check if the case is a victory case
    public boolean isCaseVictoire() {
        return (position == 25 && couleurDame == CouleurCase.BLANC)
                || (position == 0 && couleurDame == CouleurCase.NOIR);
    }

    // Check if the case is a barre case
    public boolean isCaseBarre() {
        return (position == 0 && couleurDame == CouleurCase.BLANC)
                || (position == 25 && couleurDame == CouleurCase.NOIR);
    }

    // Add a dame to the case
    public boolean ajoutDame(CouleurCase couleur) {
        if (this.couleurDame == couleur) {
            nbDame += 1;
            return true;
        } else if (this.couleurDame == CouleurCase.VIDE) {
            nbDame += 1;
            this.couleurDame = couleur;
            return true;
        } else if (nbDame <= 1) {
            this.couleurDame = couleur;
            return true;
        } else {
            return false;
        }
    }

    // Remove a dame from the case
    public boolean moinDame() {
        if (nbDame >= 1) {
            nbDame -= 1;
            if (nbDame == 0) {
                couleurDame = CouleurCase.VIDE;
            }
            return true;
        } else {
            return false;
        }
    }

    // Override toString to display case details
    @Override
    public String toString() {
        String marker = getMarker();
        return "Case " + position + " - Type: " + caseType + " - Dames: " + nbDame + 
               " - Color: " + couleurDame + " " + (marker.isEmpty() ? "" : "Marker: " + marker);
    }
}
