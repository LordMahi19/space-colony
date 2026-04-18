package com.example.spacecolony;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.spacecolony.ui.HomeFragment;
import com.example.spacecolony.ui.MedbayFragment;
import com.example.spacecolony.ui.MissionFragment;
import com.example.spacecolony.ui.QuartersFragment;
import com.example.spacecolony.ui.RecruitFragment;
import com.example.spacecolony.ui.SimulatorFragment;
import com.example.spacecolony.ui.StatsFragment;

public class MainActivity extends AppCompatActivity {

    private final Fragment homeFragment = new HomeFragment();
    private final Fragment recruitFragment = new RecruitFragment();
    private final Fragment quartersFragment = new QuartersFragment();
    private final Fragment simulatorFragment = new SimulatorFragment();
    private final Fragment missionFragment = new MissionFragment();
    private final Fragment medbayFragment = new MedbayFragment();
    private final Fragment statsFragment = new StatsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.rootLayout);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        bindNavigation(R.id.navHome, homeFragment);
        bindNavigation(R.id.navRecruit, recruitFragment);
        bindNavigation(R.id.navQuarters, quartersFragment);
        bindNavigation(R.id.navSimulator, simulatorFragment);
        bindNavigation(R.id.navMission, missionFragment);
        bindNavigation(R.id.navMedbay, medbayFragment);
        bindNavigation(R.id.navStats, statsFragment);

        findViewById(R.id.navSave).setOnClickListener(v -> {
            boolean ok = GameRepository.get().save(this);
            Toast.makeText(this, ok ? "Game saved." : "Save failed.", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.navLoad).setOnClickListener(v -> {
            boolean ok = GameRepository.get().load(this);
            Toast.makeText(this, ok ? "Game loaded." : "No saved game found.", Toast.LENGTH_SHORT).show();
            if (ok) {
                open(new HomeFragment());
            }
        });

        if (savedInstanceState == null) {
            open(homeFragment);
        }
    }

    private void bindNavigation(int buttonId, Fragment destination) {
        findViewById(buttonId).setOnClickListener(v -> open(destination));
    }

    private void open(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
