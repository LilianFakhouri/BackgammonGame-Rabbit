package models;


public class Scoreboard implements Observer {
    @Override
    public void update(String eventType, Object data) {
        if ("SESSION_LAUNCHED".equals(eventType)) {
            System.out.println("Scoreboard updated for new session: " + data);
        } else if ("GAME_STARTED".equals(eventType)) {
            System.out.println("Scoreboard updated for game start.");
        }
    }
}
