package com.example.spacecolony.model;

public class Engineer extends CrewMember {
    public Engineer(String name) {
        super(name, "Engineer", 6, 3, 18);
    }

    @Override
    public int getMissionBonus(MissionType type) {
        return type == MissionType.REPAIR_STATION ? 2 : 0;
    }
}
