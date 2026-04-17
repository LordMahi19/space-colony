package com.example.spacecolony;

import android.content.Context;

import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.DataManager;
import com.example.spacecolony.model.Location;
import com.example.spacecolony.model.Medbay;
import com.example.spacecolony.model.MissionControl;
import com.example.spacecolony.model.Storage;

import java.util.List;

public class GameRepository {
    private static GameRepository instance;

    private Storage storage;
    private MissionControl missionControl;
    private Medbay medbay;

    private GameRepository() {
        storage = new Storage();
        missionControl = new MissionControl(storage);
        medbay = new Medbay(storage);
    }

    public static GameRepository get() {
        if (instance == null) {
            instance = new GameRepository();
        }
        return instance;
    }

    public Storage getStorage() {
        return storage;
    }

    public MissionControl getMissionControl() {
        return missionControl;
    }

    public List<CrewMember> at(Location location) {
        return storage.getCrewAt(location);
    }

    public void move(List<CrewMember> selected, Location target) {
        for (CrewMember member : selected) {
            storage.moveCrew(member.getId(), target);
        }
    }

    public void train(List<CrewMember> selected) {
        for (CrewMember member : selected) {
            member.train();
        }
        storage.incrementTrainingSessions(selected.size());
    }

    public void recoverFromMedbay(List<CrewMember> selected) {
        medbay.processRecovery(selected);
    }

    public boolean save(Context context) {
        return DataManager.save(context, storage);
    }

    public boolean load(Context context) {
        Storage loaded = DataManager.load(context);
        if (loaded == null) {
            return false;
        }
        storage = loaded;
        missionControl = new MissionControl(storage);
        medbay = new Medbay(storage);
        return true;
    }
}
