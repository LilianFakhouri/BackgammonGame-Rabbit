package controller;

import java.util.ArrayList;
import java.util.Calendar;

import GUI.dicerollscreen;
import Views.VuePartie;
import models.GestionDeSession;
import models.ParametreJeu;
import models.Partie;
import models.Session;
import models.SysData;

public class Master {
    private static Master master;
    private int idSession;
    private ArrayList<Session> listSession;
    private ControleurPrincipal controleurPrincipal;
    private Partie partie; // Reference to the game (Partie)

    public Master() {
        Calendar date = Calendar.getInstance();
        idSession = 10000 * date.get(Calendar.MONTH)
                + 1000 * date.get(Calendar.DATE)
                + 100 * date.get(Calendar.HOUR)
                + 10 * date.get(Calendar.MINUTE)
                + date.get(Calendar.SECOND);
        controleurPrincipal = new ControleurPrincipal(this);
        listSession = new ArrayList<Session>();
    }

    public static void main(String[] args) {
        master = new Master(); // Create the Master instance
        SysData.getInstance().loadQuestions("questions.json");
        master.startGame();    // Start the game
    }

    public void startGame() {
        // Initialize game parameters
        ParametreJeu parametreJeu = new ParametreJeu();

        // Create and start the game
        partie = new Partie(parametreJeu);
//        partie.lancerPremierePartie(); // Determine who starts the game

        // Now open the dice roll screen
//        new dicerollscreen();  // This will show the dice roll screen after the start button is clicked.

        System.out.println("Game setup is complete!");
    }


    public void launchSession(ParametreJeu parametreJeu) {
        if (isSessionLaunchable()) {
            listSession.add(new Session(idSession, parametreJeu));
        }
        try {
            GestionDeSession gestion = GestionDeSession.getGestionDeSession();
            gestion.setListSession(listSession);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSession(Session session) {
        if (isSessionLaunchable()) {
            listSession.add(session);
        }
    }

    public void stopSession(int id) {
        if (listSession.size() != 0) {
            for (Session session : listSession) {
                if (session.getIdSession() == id) {
                    listSession.remove(session);
                    break;
                }
            }
        }
    }

    public boolean isSessionLaunchable() {
        return listSession.size() != 1;
    }

    public Session getSession() {
        // Modify for multi-threading if necessary
        return listSession.get(listSession.size() - 1);
    }

    public ControleurPrincipal getControleurPrincipal() {
        return controleurPrincipal;
    }

    public void setControleurPrincipal(ControleurPrincipal controleurPrincipal) {
        this.controleurPrincipal = controleurPrincipal;
    }
}