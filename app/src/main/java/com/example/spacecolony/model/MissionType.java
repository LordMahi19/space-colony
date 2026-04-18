package com.example.spacecolony.model;

import java.util.Random;

public enum MissionType {
    COMBAT("Alien Skirmish"),
    REPAIR_STATION("Repair Station"),
    RESEARCH("Research Anomaly"),
    NAVIGATION("Asteroid Navigation"),
    RESCUE("Rescue Operation");

    private final String label;

    MissionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static MissionType random(Random random) {
        MissionType[] values = values();
        return values[random.nextInt(values.length)];
    }
}
