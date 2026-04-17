package com.example.spacecolony.model;

public class CrewFactory {
    public static CrewMember create(String name, String specialization) {
        switch (specialization) {
            case "Pilot":
                return new Pilot(name);
            case "Engineer":
                return new Engineer(name);
            case "Medic":
                return new Medic(name);
            case "Scientist":
                return new Scientist(name);
            default:
                return new Soldier(name);
        }
    }
}
