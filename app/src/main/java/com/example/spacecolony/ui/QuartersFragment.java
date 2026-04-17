package com.example.spacecolony.ui;

import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.Location;

import java.util.List;

public class QuartersFragment extends LocationFragment {
    @Override
    protected String getTitle() {
        return "Quarters";
    }

    @Override
    protected Location sourceLocation() {
        return Location.QUARTERS;
    }

    @Override
    protected int maxSelection() {
        return 99;
    }

    @Override
    protected String primaryText() {
        return "Move to Simulator";
    }

    @Override
    protected void onPrimary(List<CrewMember> selected) {
        repo.move(selected, Location.SIMULATOR);
        toast("Moved to Simulator.");
    }

    @Override
    protected String secondaryText() {
        return "Move to Mission Control";
    }

    @Override
    protected void onSecondary(List<CrewMember> selected) {
        repo.move(selected, Location.MISSION_CONTROL);
        toast("Moved to Mission Control.");
    }
}
