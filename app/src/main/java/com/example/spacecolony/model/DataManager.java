package com.example.spacecolony.model;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager {
    private static final String SAVE_FILE = "space_colony_save.bin";

    public static boolean save(Context context, Storage storage) {
        try (FileOutputStream fos = context.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(storage);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static Storage load(Context context) {
        try (FileInputStream fis = context.openFileInput(SAVE_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Storage loaded = (Storage) ois.readObject();
            int highestId = 0;
            for (Integer id : loaded.getCrewByIdMap().keySet()) {
                highestId = Math.max(highestId, id);
            }
            CrewMember.setNextId(highestId + 1);
            return loaded;
        } catch (Exception ignored) {
            return null;
        }
    }
}
