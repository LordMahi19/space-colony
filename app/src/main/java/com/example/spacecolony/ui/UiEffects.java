package com.example.spacecolony.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;

public final class UiEffects {
    private UiEffects() {
    }

    public static void staggerChildren(@NonNull View container, long baseDelayMs) {
        if (!(container instanceof android.view.ViewGroup)) {
            return;
        }
        android.view.ViewGroup group = (android.view.ViewGroup) container;
        int childCount = group.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = group.getChildAt(i);
            child.setAlpha(0f);
            child.setTranslationY(28f);
            child.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(420)
                    .setStartDelay(baseDelayMs + i * 55L)
                    .setInterpolator(new DecelerateInterpolator(1.4f))
                    .start();
        }
    }

    public static void pulse(@NonNull View view) {
        view.setScaleX(1f);
        view.setScaleY(1f);
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator up = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.06f);
        ObjectAnimator upY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.06f);
        ObjectAnimator down = ObjectAnimator.ofFloat(view, View.SCALE_X, 1.06f, 1f);
        ObjectAnimator downY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.06f, 1f);
        set.playTogether(up, upY);
        set.setDuration(140);
        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(down, downY);
        set2.setDuration(180);
        AnimatorSet chain = new AnimatorSet();
        chain.playSequentially(set, set2);
        chain.setInterpolator(new OvershootInterpolator(0.6f));
        chain.start();
    }

    public static void fadeIn(@NonNull View view, long delayMs) {
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(300).setStartDelay(delayMs).start();
    }
}
