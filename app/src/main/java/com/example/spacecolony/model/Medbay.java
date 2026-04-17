package com.example.spacecolony.model;

import java.util.List;

public class Medbay {
    private final Storage storage;

    public Medbay(Storage storage) {
        this.storage = storage;
    }

    public void processRecovery(List<CrewMember> selected) {
        for (CrewMember member : selected) {
            member.applyMedbayPenalty();
            storage.moveCrew(member.getId(), Location.QUARTERS);
        }
    }
}
