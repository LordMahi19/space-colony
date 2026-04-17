package com.example.spacecolony.model;

public class Medic extends CrewMember {
    public Medic(String name) {
        super(name, "Medic", 4, 5, 21);
    }

    @Override
    public int getMissionBonus(MissionType type) {
        return type == MissionType.RESEARCH ? 2 : 0;
    }
}
