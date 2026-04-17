package com.example.spacecolony.ui;

import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.Location;

import java.util.List;

public class SimulatorFragment extends LocationFragment {
    @Override
    protected String getTitle() {
        return "Simulator";
    }

    @Override
    protected Location sourceLocation() {
        return Location.SIMULATOR;
    }

    @Override
    protected int maxSelection() {
        return 99;
    }

    @Override
    protected String primaryText() {
        return "Train Selected";
    }

    @Override
    protected void onPrimary(List<CrewMember> selected) {
        repo.train(selected);
        toast("Training complete. XP +1.");
    }

    @Override
    protected String secondaryText() {
        return "Move to Quarters";
    }

    @Override
    protected void onSecondary(List<CrewMember> selected) {
        repo.move(selected, Location.QUARTERS);
        toast("Returned to Quarters with full energy.");
    }
}
