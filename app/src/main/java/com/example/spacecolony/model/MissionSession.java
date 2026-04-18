package com.example.spacecolony.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MissionSession {
    private final Storage storage;
    private final MissionType missionType;
    private final Threat threat;
    private final List<CrewMember> squad;
    private final List<MissionLogEntry> log = new ArrayList<>();
    private final Random random = new Random();
    private int actorIndex;
    private boolean ended;
    private boolean victory;

    public MissionSession(Storage storage, MissionType missionType, Threat threat, List<CrewMember> squad) {
        this.storage = storage;
        this.missionType = missionType;
        this.threat = threat;
        this.squad = squad;
        this.log.add(MissionLogEntry.system("Mission started: " + missionType.getLabel() + " vs " + threat.getName()));
    }

    public void performTurn(CrewAction action) {
        if (ended || squad.isEmpty()) {
            return;
        }
        CrewMember actor = getNextAliveActor();
        if (actor == null) {
            finishMission(false);
            return;
        }
        int outgoing;
        if (action == CrewAction.DEFEND) {
            actor.defend();
            log.add(MissionLogEntry.crew(actor.getName() + " takes a defensive stance.", actor.getSpecialization()));
            outgoing = 0;
        } else if (action == CrewAction.SPECIAL) {
            outgoing = actor.useSpecialAbility(missionType, random);
        } else {
            outgoing = actor.act(missionType, random);
        }

        if (outgoing > 0) {
            int dealt = threat.takeDamage(outgoing);
            log.add(MissionLogEntry.crew(actor.getName() + " dealt " + dealt + " damage to " + threat.getName() + ".", actor.getSpecialization()));
        }

        if (threat.isDefeated()) {
            finishMission(true);
            return;
        }

        CrewMember target = getRandomAliveCrew();
        if (target == null) {
            finishMission(false);
            return;
        }
        int threatDamage = target.takeDamage(threat.attack(random));
        log.add(MissionLogEntry.threat(threat.getName() + " retaliated for " + threatDamage + " damage against " + target.getName() + "."));
        if (target.getEnergy() <= 0) {
            log.add(MissionLogEntry.crew(target.getName() + " is down and moved to Medbay.", target.getSpecialization()));
            storage.moveCrew(target.getId(), Location.MEDBAY);
        }
        if (!hasAliveCrew()) {
            finishMission(false);
            return;
        }
    }

    private CrewMember getNextAliveActor() {
        for (int i = 0; i < squad.size(); i++) {
            CrewMember candidate = squad.get((actorIndex + i) % squad.size());
            if (candidate.getEnergy() > 0) {
                actorIndex = (actorIndex + i + 1) % squad.size();
                return candidate;
            }
        }
        return null;
    }

    private CrewMember getRandomAliveCrew() {
        List<CrewMember> alive = new ArrayList<>();
        for (CrewMember member : squad) {
            if (member.getEnergy() > 0) {
                alive.add(member);
            }
        }
        return alive.isEmpty() ? null : alive.get(random.nextInt(alive.size()));
    }

    private boolean hasAliveCrew() {
        for (CrewMember member : squad) {
            if (member.getEnergy() > 0) {
                return true;
            }
        }
        return false;
    }

    private void finishMission(boolean didWin) {
        ended = true;
        victory = didWin;
        storage.recordMission(didWin);
        for (CrewMember member : squad) {
            if (member.getEnergy() > 0) {
                member.addMissionResult(didWin);
                if (didWin) {
                    member.grantMissionXp();
                    log.add(MissionLogEntry.crew(member.getName() + " gained XP (now " + member.getExperience() + ").", member.getSpecialization()));
                    storage.moveCrew(member.getId(), Location.MISSION_CONTROL);
                } else {
                    storage.moveCrew(member.getId(), Location.QUARTERS);
                }
            } else {
                member.addMissionResult(false);
            }
        }
        log.add(MissionLogEntry.system(didWin ? "Threat neutralized!" : "Mission failed. Squad evacuated."));
    }

    public List<CrewMember> getSquad() {
        return squad;
    }

    public Threat getThreat() {
        return threat;
    }

    public MissionType getMissionType() {
        return missionType;
    }

    public List<MissionLogEntry> getLog() {
        return log;
    }

    public boolean isEnded() {
        return ended;
    }

    public boolean isVictory() {
        return victory;
    }
}
