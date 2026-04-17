package com.example.spacecolony.model;

import java.io.Serializable;
import java.util.Random;

public class Threat implements Serializable {
    private final String name;
    private final int skill;
    private final int resilience;
    private final int maxEnergy;
    private int energy;

    public Threat(String name, int skill, int resilience, int maxEnergy) {
        this.name = name;
        this.skill = skill;
        this.resilience = resilience;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
    }

    public int takeDamage(int incoming) {
        int damage = Math.max(0, incoming - resilience);
        energy = Math.max(0, energy - damage);
        return damage;
    }

    public int attack(Random random) {
        return skill + random.nextInt(3);
    }

    public boolean isDefeated() {
        return energy <= 0;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }
}
