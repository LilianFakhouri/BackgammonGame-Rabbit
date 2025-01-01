package models;

import org.jdom2.Element;

public class DeSixFaces {
    /* VARIABLES */
    private int valeur;
    private boolean isUtilise;
    private CouleurCase couleurDe;

    /* CONSTRUCTORS */
    public DeSixFaces() {}

    public DeSixFaces(CouleurCase couleurCase) {
        isUtilise = false;
        valeur = (int)(Math.random() * 6 + 1); // Initial roll
        couleurDe = couleurCase;
    }

    public DeSixFaces(CouleurCase couleurCase, int valeur) {
        isUtilise = false;
        this.valeur = valeur;
        this.couleurDe = couleurCase;
    }

    /* METHODS */
    public void roll() {
        this.valeur = (int)(Math.random() * 6) + 1; // Generate a value between 1 and 6
        this.isUtilise = false; // Reset usage status
    }

    public void utiliser() {
        isUtilise = true;
    }

    public void notUtiliser() {
        isUtilise = false;
    }

    public void sauvegarder(Element deSixFaces) {
        Element deSixFaceXML = new Element("deSixFace");
        deSixFaces.addContent(deSixFaceXML);

        Element valeurXML = new Element("valeur");
        valeurXML.setText(String.valueOf(valeur));
        deSixFaceXML.addContent(valeurXML);

        Element isUtiliseXML = new Element("isUtilise");
        isUtiliseXML.setText(isUtilise ? "yes" : "no");
        deSixFaceXML.addContent(isUtiliseXML);

        Element couleurDeXML = new Element("couleurDe");
        couleurDeXML.setText(String.valueOf(couleurDe));
        deSixFaceXML.addContent(couleurDeXML);
    }

    public void charger(Element deSixFace) {
        valeur = Integer.valueOf(deSixFace.getChildText("valeur"));

        switch (deSixFace.getChildText("isUtilise")) {
            case "yes": isUtilise = true; break;
            case "no": isUtilise = false;
        }

        switch (deSixFace.getChildText("couleurDe")) {
            case "BLANC": couleurDe = CouleurCase.BLANC; break;
            case "NOIR": couleurDe = CouleurCase.NOIR; break;
            case "VIDE": couleurDe = CouleurCase.VIDE;
        }
    }

    /* GETTERS AND SETTERS */
    public int getValeur() {
        return valeur;
    }

    public boolean isUtilise() {
        return isUtilise;
    }

    public CouleurCase getCouleurDe() {
        return couleurDe;
    }

	public void setValeur(int valeur) {
        if (valeur >= 1 && valeur <= 6) {
            this.valeur = valeur;
        } else {
            throw new IllegalArgumentException("Dice value must be between 1 and 6.");
        }

	}
    
}
