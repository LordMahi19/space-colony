package com.example.spacecolony.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolony.R;
import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.MissionType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {
    private final List<CrewMember> crew = new ArrayList<>();
    private final Set<Integer> selectedIds = new HashSet<>();
    private final int maxSelection;

    public CrewAdapter(int maxSelection) {
        this.maxSelection = maxSelection;
    }

    public void setCrew(List<CrewMember> data) {
        crew.clear();
        crew.addAll(data);
        selectedIds.retainAll(getCurrentIds());
        notifyDataSetChanged();
    }

    private Set<Integer> getCurrentIds() {
        Set<Integer> ids = new HashSet<>();
        for (CrewMember member : crew) {
            ids.add(member.getId());
        }
        return ids;
    }

    public List<CrewMember> getSelected() {
        List<CrewMember> selected = new ArrayList<>();
        for (CrewMember member : crew) {
            if (selectedIds.contains(member.getId())) {
                selected.add(member);
            }
        }
        return selected;
    }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crew, parent, false);
        return new CrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        CrewMember member = crew.get(position);
        boolean checked = selectedIds.contains(member.getId());
        holder.name.setText(member.getName() + " (" + member.getSpecialization() + ")");
        holder.stats.setText("Skill " + member.getEffectiveSkill(MissionType.COMBAT)
                + " | Res " + member.getResilience()
                + " | XP " + member.getExperience()
                + " | M " + member.getMissionsCompleted()
                + " W " + member.getVictories()
                + " T " + member.getTrainingSessions());
        holder.energy.setText("Energy " + member.getEnergy() + "/" + member.getMaxEnergy());
        holder.bar.setMax(member.getMaxEnergy());
        holder.bar.setProgress(member.getEnergy());
        holder.icon.setImageResource(CrewVisuals.icon(member.getSpecialization()));
        holder.checkBox.setChecked(checked);
        holder.itemView.setOnClickListener(v -> toggle(member.getId(), holder.getBindingAdapterPosition()));
        holder.checkBox.setOnClickListener(v -> toggle(member.getId(), holder.getBindingAdapterPosition()));
    }

    private void toggle(int id, int adapterPosition) {
        if (adapterPosition == RecyclerView.NO_POSITION) {
            return;
        }
        if (selectedIds.contains(id)) {
            selectedIds.remove(id);
        } else if (selectedIds.size() < maxSelection) {
            selectedIds.add(id);
        }
        notifyItemChanged(adapterPosition);
    }

    @Override
    public int getItemCount() {
        return crew.size();
    }

    static class CrewViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView stats;
        final TextView energy;
        final CheckBox checkBox;
        final ImageView icon;
        final ProgressBar bar;

        CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.crewName);
            stats = itemView.findViewById(R.id.crewStats);
            energy = itemView.findViewById(R.id.crewEnergy);
            checkBox = itemView.findViewById(R.id.crewCheck);
            icon = itemView.findViewById(R.id.crewIcon);
            bar = itemView.findViewById(R.id.crewEnergyBar);
        }
    }
}
