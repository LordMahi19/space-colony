package com.example.spacecolony.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage implements Serializable {
    private final HashMap<Integer, CrewMember> crewById = new HashMap<>();
    private final HashMap<Integer, Location> locationByCrewId = new HashMap<>();
    private int totalCrewRecruited;
    private int totalMissions;
    private int totalMissionWins;
    private int totalTrainingSessions;

    public void addCrew(CrewMember crewMember) {
        crewById.put(crewMember.getId(), crewMember);
        locationByCrewId.put(crewMember.getId(), Location.QUARTERS);
        totalCrewRecruited++;
    }

    public List<CrewMember> getCrewAt(Location location) {
        List<CrewMember> members = new ArrayList<>();
        for (Map.Entry<Integer, CrewMember> entry : crewById.entrySet()) {
            Location current = locationByCrewId.get(entry.getKey());
            if (current == location) {
                members.add(entry.getValue());
            }
        }
        return members;
    }

    public CrewMember getCrewById(int id) {
        return crewById.get(id);
    }

    public void moveCrew(int id, Location destination) {
        CrewMember member = crewById.get(id);
        if (member == null) {
            return;
        }
        locationByCrewId.put(id, destination);
        if (destination == Location.QUARTERS) {
            member.restoreEnergy();
        }
    }

    public void incrementTrainingSessions(int count) {
        totalTrainingSessions += count;
    }

    public void recordMission(boolean victory) {
        totalMissions++;
        if (victory) {
            totalMissionWins++;
        }
    }

    public HashMap<Integer, CrewMember> getCrewByIdMap() {
        return crewById;
    }

    public HashMap<Integer, Location> getLocationByCrewIdMap() {
        return locationByCrewId;
    }

    public int getTotalCrewRecruited() {
        return totalCrewRecruited;
    }

    public int getTotalMissions() {
        return totalMissions;
    }

    public int getTotalMissionWins() {
        return totalMissionWins;
    }

    public int getTotalTrainingSessions() {
        return totalTrainingSessions;
    }
}
