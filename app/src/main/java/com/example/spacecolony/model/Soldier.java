package com.example.spacecolony.model;

public class Soldier extends CrewMember {
    public Soldier(String name) {
        super(name, "Soldier", 8, 1, 16);
    }

    @Override
    public int getMissionBonus(MissionType type) {
        return type == MissionType.COMBAT ? 2 : 0;
    }
}
