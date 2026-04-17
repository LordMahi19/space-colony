package com.example.spacecolony.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spacecolony.GameRepository;
import com.example.spacecolony.R;
import com.example.spacecolony.model.CrewFactory;

public class RecruitFragment extends Fragment {
    private static final String[] SPECIALIZATIONS = {"Pilot", "Engineer", "Medic", "Scientist", "Soldier"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recruit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText nameInput = view.findViewById(R.id.recruitNameInput);
        Spinner specSpinner = view.findViewById(R.id.recruitSpecSpinner);
        ImageView preview = view.findViewById(R.id.recruitImagePreview);
        Button create = view.findViewById(R.id.recruitCreateButton);

        specSpinner.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, SPECIALIZATIONS));
        specSpinner.setOnItemSelectedListener(new SimpleItemSelectedListener(position -> preview.setImageResource(CrewVisuals.icon(SPECIALIZATIONS[position]))));
        preview.setImageResource(CrewVisuals.icon(SPECIALIZATIONS[0]));

        create.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Name is required.", Toast.LENGTH_SHORT).show();
                return;
            }
            String spec = SPECIALIZATIONS[specSpinner.getSelectedItemPosition()];
            GameRepository.get().getStorage().addCrew(CrewFactory.create(name, spec));
            nameInput.setText("");
            Toast.makeText(requireContext(), spec + " recruited to Quarters.", Toast.LENGTH_SHORT).show();
        });
    }
}
