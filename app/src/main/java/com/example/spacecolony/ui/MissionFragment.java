package com.example.spacecolony.ui;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import com.example.spacecolony.GameRepository;
import com.example.spacecolony.R;
import com.example.spacecolony.model.CrewAction;
import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.Location;
import com.example.spacecolony.model.MissionSession;

import java.util.List;

public class MissionFragment extends Fragment {
    private CrewAdapter adapter;
    private MissionSession session;
    private TextView title;
    private TextView log;
    private ProgressBar threatBar;
    private View threatCard;
    private MaterialButton launch;
    private MaterialButton attack;
    private MaterialButton defend;
    private MaterialButton special;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mission, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new CrewAdapter(3);
        RecyclerView recycler = view.findViewById(R.id.missionCrewRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(360);
        recycler.setItemAnimator(animator);
        recycler.setAdapter(adapter);
        threatCard = view.findViewById(R.id.missionThreatCard);
        title = view.findViewById(R.id.missionTitle);
        log = view.findViewById(R.id.missionLog);
        threatBar = view.findViewById(R.id.threatEnergyBar);
        launch = view.findViewById(R.id.launchMissionButton);
        attack = view.findViewById(R.id.missionAttackButton);
        defend = view.findViewById(R.id.missionDefendButton);
        special = view.findViewById(R.id.missionSpecialButton);

        launch.setOnClickListener(v -> launchMission());
        attack.setOnClickListener(v -> perform(CrewAction.ATTACK));
        defend.setOnClickListener(v -> perform(CrewAction.DEFEND));
        special.setOnClickListener(v -> perform(CrewAction.SPECIAL));
        refreshSelection();
        updateButtons();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshSelection();
    }

    private void refreshSelection() {
        adapter.setCrew(GameRepository.get().at(Location.MISSION_CONTROL));
    }

    private void launchMission() {
        List<CrewMember> selected = adapter.getSelected();
        if (selected.size() < 2) {
            Toast.makeText(requireContext(), "Select 2-3 crew members.", Toast.LENGTH_SHORT).show();
            return;
        }
        session = GameRepository.get().getMissionControl().launchMission(selected);
        log.setText("");
        appendLog();
        title.setText(session.getMissionType().getLabel() + " vs " + session.getThreat().getName());
        threatBar.setMax(session.getThreat().getMaxEnergy());
        threatBar.setProgress(0);
        animateThreatProgress(session.getThreat().getEnergy());
        if (threatCard != null) {
            UiEffects.pulse(threatCard);
        } else {
            UiEffects.pulse(threatBar);
        }
        updateButtons();
    }

    private void perform(CrewAction action) {
        if (session == null || session.isEnded()) {
            return;
        }
        session.performTurn(action);
        animateThreatProgress(session.getThreat().getEnergy());
        UiEffects.pulse(threatBar);
        adapter.notifyDataSetChanged();
        appendLog();
        if (session.isEnded()) {
            Toast.makeText(requireContext(), session.isVictory() ? "Mission successful!" : "Mission failed.", Toast.LENGTH_SHORT).show();
            refreshSelection();
        }
        updateButtons();
    }

    private void appendLog() {
        if (session == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String line : session.getLog()) {
            sb.append(line).append('\n');
        }
        log.setText(sb.toString());
    }

    private void updateButtons() {
        boolean active = session != null && !session.isEnded();
        attack.setEnabled(active);
        defend.setEnabled(active);
        special.setEnabled(active);
        launch.setEnabled(!active);
    }

    private void animateThreatProgress(int target) {
        int start = threatBar.getProgress();
        ObjectAnimator anim = ObjectAnimator.ofInt(threatBar, "progress", start, target);
        anim.setDuration(220);
        anim.start();
    }
}
