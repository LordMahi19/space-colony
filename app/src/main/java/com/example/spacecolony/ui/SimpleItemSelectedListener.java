package com.example.spacecolony.ui;

import android.view.View;
import android.widget.AdapterView;

import java.util.function.IntConsumer;

public class SimpleItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private final IntConsumer callback;

    public SimpleItemSelectedListener(IntConsumer callback) {
        this.callback = callback;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        callback.accept(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
