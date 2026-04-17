package com.example.spacecolony.ui;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolony.GameRepository;
import com.example.spacecolony.R;
import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.Location;
import com.example.spacecolony.model.Storage;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {
    private CrewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new CrewAdapter(0);
        RecyclerView recycler = view.findViewById(R.id.statsRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(340);
        recycler.setItemAnimator(animator);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        if (view == null) {
            return;
        }
        Storage storage = GameRepository.get().getStorage();
        int missions = Math.max(1, storage.getTotalMissions());
        int winRate = (int) ((storage.getTotalMissionWins() * 100f) / missions);
        TextView label = view.findViewById(R.id.statsSummary);
        ProgressBar winRateBar = view.findViewById(R.id.statsWinRateBar);
        label.setText("Win rate: " + winRate + "%");
        int start = winRateBar.getProgress();
        ObjectAnimator anim = ObjectAnimator.ofInt(winRateBar, "progress", start, winRate);
        anim.setDuration(650);
        anim.start();

        List<CrewMember> all = new ArrayList<>();
        all.addAll(storage.getCrewAt(Location.QUARTERS));
        all.addAll(storage.getCrewAt(Location.SIMULATOR));
        all.addAll(storage.getCrewAt(Location.MISSION_CONTROL));
        all.addAll(storage.getCrewAt(Location.MEDBAY));
        adapter.setCrew(all);
    }
}
