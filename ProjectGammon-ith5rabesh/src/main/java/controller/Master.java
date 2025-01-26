package controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import GUI.dicerollscreen;
import Views.VuePartie;
import models.GestionDeSession;
import models.ParametreJeu;
import models.Partie;
import models.Scoreboard;
import models.Session;
import models.SysData;
import models.TurnIndicator;
import models.Observer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Master {
    private static Master master;
    private int idSession;
    private ArrayList<Session> listSession;
    private ControleurPrincipal controleurPrincipal;
    private Partie partie; // Reference to the game (Partie)
    private List<Observer> observers = new CopyOnWriteArrayList<>();

    // Register an observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Unregister an observer
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Notify all observers of an update
    private void notifyObservers(String eventType, Object data) {
        for (Observer observer : observers) {
            observer.update(eventType, data);
        }
    }
    //TEST
    //tetsttt
    
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
        master = new Master();

        InputStream inputStream = SysData.class.getClassLoader().getResourceAsStream("questions.json");
        if (inputStream == null) {
            System.err.println("Error: questions.json not found.");
            return;
        }

        SysData.getInstance().loadQuestionsFromInputStream(inputStream);

        // Register observers
        master.addObserver(new Scoreboard());
        master.addObserver(new TurnIndicator());

        master.startGame();
    }

    public void startGame() {
        ParametreJeu parametreJeu = new ParametreJeu();
        partie = new Partie(parametreJeu);
        notifyObservers("GAME_STARTED", partie); // Notify observers
        System.out.println("Game setup is complete!");
    }


    public void launchSession(ParametreJeu parametreJeu) {
        if (isSessionLaunchable()) {
            Session session = new Session(idSession, parametreJeu);
            listSession.add(session);
            notifyObservers("SESSION_LAUNCHED", session); // Notify observers
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
