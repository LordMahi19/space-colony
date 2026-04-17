package com.example.spacecolony.model;

import java.util.List;
import java.util.Random;

public class MissionControl {
    private static final String[] THREATS = {
            "Asteroid Storm",
            "Alien Boarding Party",
            "Solar Flare Cascade",
            "Critical Reactor Leak"
    };

    private final Storage storage;
    private final Random random = new Random();

    public MissionControl(Storage storage) {
        this.storage = storage;
    }

    public MissionSession launchMission(List<CrewMember> squad) {
        int scaling = 4 + storage.getTotalMissions();
        MissionType type = MissionType.random(random);
        String threatName = THREATS[random.nextInt(THREATS.length)];
        Threat threat = new Threat(threatName, scaling + random.nextInt(3), 2 + random.nextInt(3), 22 + scaling);
        return new MissionSession(storage, type, threat, squad);
    }
}
