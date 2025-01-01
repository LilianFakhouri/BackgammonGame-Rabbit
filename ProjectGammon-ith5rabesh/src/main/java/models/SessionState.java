package models;

/**
 * Enum representing the state of a game session.
 */
public enum SessionState {
    SETUP,       // The session is in its configuration or setup phase
    IN_PROGRESS, // The session is currently ongoing
    FINISHED,    // The session has ended
    REPLAY       // The session is in replay mode
}
