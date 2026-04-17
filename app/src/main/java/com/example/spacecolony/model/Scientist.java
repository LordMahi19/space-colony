package com.example.spacecolony.model;

public class Scientist extends CrewMember {
    public Scientist(String name) {
        super(name, "Scientist", 5, 3, 19);
    }

    @Override
    public int getMissionBonus(MissionType type) {
        return type == MissionType.RESEARCH ? 2 : 0;
    }
}
