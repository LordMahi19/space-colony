package com.example.spacecolony.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spacecolony.GameRepository;
import com.example.spacecolony.R;
import com.example.spacecolony.model.Location;
import com.example.spacecolony.model.Storage;

public class HomeFragment extends Fragment {
    private View homeRoot;
    private boolean didPlayIntro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        homeRoot = view.findViewById(R.id.homeRoot);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        if (view == null) {
            return;
        }
        Storage storage = GameRepository.get().getStorage();
        ((TextView) view.findViewById(R.id.homeCountQuarters)).setText(String.valueOf(storage.getCrewAt(Location.QUARTERS).size()));
        ((TextView) view.findViewById(R.id.homeCountSimulator)).setText(String.valueOf(storage.getCrewAt(Location.SIMULATOR).size()));
        ((TextView) view.findViewById(R.id.homeCountMission)).setText(String.valueOf(storage.getCrewAt(Location.MISSION_CONTROL).size()));
        ((TextView) view.findViewById(R.id.homeCountMedbay)).setText(String.valueOf(storage.getCrewAt(Location.MEDBAY).size()));
        ((TextView) view.findViewById(R.id.homeTotals)).setText(
                "Total recruited: " + storage.getTotalCrewRecruited()
                        + "\nTotal missions: " + storage.getTotalMissions()
                        + "\nMission wins: " + storage.getTotalMissionWins()
                        + "\nTraining sessions: " + storage.getTotalTrainingSessions()
        );
        if (homeRoot != null && !didPlayIntro) {
            didPlayIntro = true;
            UiEffects.staggerChildren(homeRoot, 40);
        }
    }
}
