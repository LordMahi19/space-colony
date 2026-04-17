package com.example.spacecolony.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.List;

public abstract class LocationFragment extends Fragment {
    protected CrewAdapter adapter;
    protected GameRepository repo;

    protected abstract String getTitle();

    protected abstract Location sourceLocation();

    protected abstract int maxSelection();

    protected abstract String primaryText();

    protected abstract void onPrimary(List<CrewMember> selected);

    @Nullable
    protected String secondaryText() {
        return null;
    }

    protected void onSecondary(List<CrewMember> selected) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        repo = GameRepository.get();
        adapter = new CrewAdapter(maxSelection());
        TextView title = view.findViewById(R.id.locationTitle);
        RecyclerView recyclerView = view.findViewById(R.id.locationRecycler);
        Button primary = view.findViewById(R.id.locationPrimary);
        Button secondary = view.findViewById(R.id.locationSecondary);

        title.setText(getTitle());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(380);
        animator.setChangeDuration(260);
        animator.setMoveDuration(320);
        recyclerView.setItemAnimator(animator);
        recyclerView.setAdapter(adapter);
        primary.setText(primaryText());
        primary.setOnClickListener(v -> performPrimary());

        String secondaryLabel = secondaryText();
        if (secondaryLabel == null) {
            secondary.setVisibility(View.GONE);
        } else {
            secondary.setText(secondaryLabel);
            secondary.setOnClickListener(v -> performSecondary());
        }
        refresh();
    }

    protected void refresh() {
        adapter.setCrew(repo.at(sourceLocation()));
    }

    private void performPrimary() {
        List<CrewMember> selected = adapter.getSelected();
        if (selected.isEmpty()) {
            toast("Select at least one crew member.");
            return;
        }
        onPrimary(selected);
        refresh();
    }

    private void performSecondary() {
        List<CrewMember> selected = adapter.getSelected();
        if (selected.isEmpty()) {
            toast("Select at least one crew member.");
            return;
        }
        onSecondary(selected);
        refresh();
    }

    protected void toast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
