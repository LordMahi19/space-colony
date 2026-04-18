package com.example.spacecolony.model;

/**
 * Structured mission log entry that stores the text, the actor type,
 * and the specialization (for crew entries) so the UI can render icons.
 */
public class MissionLogEntry {

    public enum ActorType { CREW, THREAT, SYSTEM }

    private final String text;
    private final ActorType actorType;
    private final String specialization;

    private MissionLogEntry(String text, ActorType actorType, String specialization) {
        this.text = text;
        this.actorType = actorType;
        this.specialization = specialization;
    }

    /** Create a log entry for a crew member action. */
    public static MissionLogEntry crew(String text, String specialization) {
        return new MissionLogEntry(text, ActorType.CREW, specialization);
    }

    /** Create a log entry for a threat action. */
    public static MissionLogEntry threat(String text) {
        return new MissionLogEntry(text, ActorType.THREAT, null);
    }

    /** Create a log entry for a system message (mission start/end). */
    public static MissionLogEntry system(String text) {
        return new MissionLogEntry(text, ActorType.SYSTEM, null);
    }

    public String getText() {
        return text;
    }

    public ActorType getActorType() {
        return actorType;
    }

    public String getSpecialization() {
        return specialization;
    }
}
