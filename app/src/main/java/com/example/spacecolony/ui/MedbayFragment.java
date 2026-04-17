package com.example.spacecolony.ui;

import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.Location;

import java.util.List;

public class MedbayFragment extends LocationFragment {
    @Override
    protected String getTitle() {
        return "Medbay";
    }

    @Override
    protected Location sourceLocation() {
        return Location.MEDBAY;
    }

    @Override
    protected int maxSelection() {
        return 99;
    }

    @Override
    protected String primaryText() {
        return "Recover and Move to Quarters";
    }

    @Override
    protected void onPrimary(List<CrewMember> selected) {
        repo.recoverFromMedbay(selected);
        toast("Recovered with XP penalty and moved to Quarters.");
    }
}
