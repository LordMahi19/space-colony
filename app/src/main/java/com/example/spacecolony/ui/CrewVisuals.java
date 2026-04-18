package com.example.spacecolony.ui;

import com.example.spacecolony.R;

/**
 * Maps crew specializations to visual resources (icons and colors).
 */
public class CrewVisuals {

    /**
     * Returns the custom drawable resource for a crew specialization.
     */
    public static int icon(String specialization) {
        switch (specialization) {
            case "Pilot":
                return R.drawable.ic_pilot;
            case "Engineer":
                return R.drawable.ic_engineer;
            case "Medic":
                return R.drawable.ic_medic;
            case "Scientist":
                return R.drawable.ic_scientist;
            default:
                return R.drawable.ic_soldier;
        }
    }

    /**
     * Returns the drawable resource for threat icons.
     */
    public static int threatIcon() {
        return R.drawable.ic_threat;
    }

    /**
     * Returns the ARGB color associated with a crew specialization.
     * Used for charts and visual emphasis.
     */
    public static int color(String specialization) {
        switch (specialization) {
            case "Pilot":
                return 0xFF00D4FF;    // nebula cyan
            case "Engineer":
                return 0xFFFFC857;    // accent gold
            case "Medic":
                return 0xFF4ADE80;    // accent success
            case "Scientist":
                return 0xFF6B4EE6;    // nebula purple
            default:
                return 0xFFFF6B6B;    // accent danger (Soldier)
        }
    }
}
