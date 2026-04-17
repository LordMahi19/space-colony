package com.example.spacecolony.model;

public class Pilot extends CrewMember {
    public Pilot(String name) {
        super(name, "Pilot", 5, 4, 20);
    }

    @Override
    public int getMissionBonus(MissionType type) {
        return type == MissionType.NAVIGATION ? 2 : 0;
    }
}
