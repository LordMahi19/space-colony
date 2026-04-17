package com.example.spacecolony;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.spacecolony.ui.HomeFragment;
import com.example.spacecolony.ui.MedbayFragment;
import com.example.spacecolony.ui.MissionFragment;
import com.example.spacecolony.ui.QuartersFragment;
import com.example.spacecolony.ui.RecruitFragment;
import com.example.spacecolony.ui.SimulatorFragment;
import com.example.spacecolony.ui.StatsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindNavigation(R.id.navHome, new HomeFragment());
        bindNavigation(R.id.navRecruit, new RecruitFragment());
        bindNavigation(R.id.navQuarters, new QuartersFragment());
        bindNavigation(R.id.navSimulator, new SimulatorFragment());
        bindNavigation(R.id.navMission, new MissionFragment());
        bindNavigation(R.id.navMedbay, new MedbayFragment());
        bindNavigation(R.id.navStats, new StatsFragment());

        Button save = findViewById(R.id.navSave);
        Button load = findViewById(R.id.navLoad);
        save.setOnClickListener(v -> {
            boolean ok = GameRepository.get().save(this);
            Toast.makeText(this, ok ? "Game saved." : "Save failed.", Toast.LENGTH_SHORT).show();
        });
        load.setOnClickListener(v -> {
            boolean ok = GameRepository.get().load(this);
            Toast.makeText(this, ok ? "Game loaded." : "No saved game found.", Toast.LENGTH_SHORT).show();
            if (ok) {
                open(new HomeFragment());
            }
        });

        if (savedInstanceState == null) {
            open(new HomeFragment());
        }
    }

    private void bindNavigation(int buttonId, Fragment destination) {
        findViewById(buttonId).setOnClickListener(v -> open(destination));
    }

    private void open(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}