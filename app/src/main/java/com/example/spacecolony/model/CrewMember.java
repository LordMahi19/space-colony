package com.example.spacecolony.model;

import java.io.Serializable;
import java.util.Random;

public abstract class CrewMember implements Serializable {
    private static int nextId = 1;

    private final int id;
    private final String name;
    private final String specialization;
    private final int baseSkill;
    private final int baseResilience;
    private final int baseMaxEnergy;
    private int experience;
    private int energy;
    private int missionsCompleted;
    private int victories;
    private int lostMissions;
    private int trainingSessions;
    private int defendedTurns;

    protected CrewMember(String name, String specialization, int baseSkill, int baseResilience, int baseMaxEnergy) {
        this.id = nextId++;
        this.name = name;
        this.specialization = specialization;
        this.baseSkill = baseSkill;
        this.baseResilience = baseResilience;
        this.baseMaxEnergy = baseMaxEnergy;
        this.energy = baseMaxEnergy;
    }

    public static void setNextId(int next) {
        nextId = Math.max(1, next);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getExperience() {
        return experience;
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaxEnergy() {
        return baseMaxEnergy;
    }

    public int getMissionsCompleted() {
        return missionsCompleted;
    }

    public int getVictories() {
        return victories;
    }

    public int getLostMissions() {
        return lostMissions;
    }

    public int getTrainingSessions() {
        return trainingSessions;
    }

    public int getResilience() {
        return baseResilience;
    }

    public int getEffectiveSkill(MissionType type) {
        return baseSkill + experience + getMissionBonus(type);
    }

    public int act(MissionType type, Random random) {
        return getEffectiveSkill(type) + random.nextInt(3);
    }

    public int useSpecialAbility(MissionType type, Random random) {
        return getEffectiveSkill(type) + 1 + random.nextInt(4);
    }

    public void defend() {
        defendedTurns++;
    }

    public int takeDamage(int incoming) {
        int defenseBoost = defendedTurns > 0 ? 2 : 0;
        int damage = Math.max(0, incoming - (baseResilience + defenseBoost));
        energy = Math.max(0, energy - damage);
        if (defendedTurns > 0) {
            defendedTurns--;
        }
        return damage;
    }

    public void train() {
        experience++;
        trainingSessions++;
    }

    public void restoreEnergy() {
        energy = baseMaxEnergy;
        defendedTurns = 0;
    }

    public void applyMedbayPenalty() {
        experience = Math.max(0, experience - 1);
        restoreEnergy();
    }

    public void addMissionResult(boolean victory) {
        missionsCompleted++;
        if (victory) {
            victories++;
        } else {
            lostMissions++;
        }
    }

    public void grantMissionXp() {
        experience++;
    }

    public abstract int getMissionBonus(MissionType type);
}
