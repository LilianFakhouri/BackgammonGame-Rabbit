package models;

public interface Observer {
    void update(String eventType, Object data);
}