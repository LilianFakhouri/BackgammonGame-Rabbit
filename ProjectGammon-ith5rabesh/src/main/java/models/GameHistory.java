package models;

import java.io.Serializable;

import Utils.Level;

public class GameHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id; // Unique game ID
    private Player winner; // Winner of the game
    private Player secondPlayer; // Second-place player
    private String duration; // Duration of the game
    private Level level; // Difficulty level of the game
//tesst
    // Constructor
    public GameHistory(int id, Player winner, Player secondPlayer, String duration, Level level) {
        this.id = id;
        this.winner = winner;
        this.secondPlayer = secondPlayer;
        this.duration = duration;
        this.level = level;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "GameHistory{" +
                "id=" + id +
                ", winner=" + winner.getPseudo() +
                ", secondPlayer=" + secondPlayer.getPseudo() +
                ", duration='" + duration + '\'' +
                ", level=" + level +
                '}';
    }
}
