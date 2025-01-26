package models;


public class TurnIndicator implements Observer {
    @Override
    public void update(String eventType, Object data) {
        if ("GAME_STARTED".equals(eventType)) {
            System.out.println("Turn indicator updated for new game.");
        }
    }
}
