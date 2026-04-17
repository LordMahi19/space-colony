package com.example.spacecolony.ui;

public class CrewVisuals {
    public static int icon(String specialization) {
        switch (specialization) {
            case "Pilot":
                return android.R.drawable.ic_menu_compass;
            case "Engineer":
                return android.R.drawable.ic_menu_manage;
            case "Medic":
                return android.R.drawable.ic_menu_info_details;
            case "Scientist":
                return android.R.drawable.ic_menu_search;
            default:
                return android.R.drawable.ic_menu_directions;
        }
    }
}
