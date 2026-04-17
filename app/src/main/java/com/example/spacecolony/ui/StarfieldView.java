package com.example.spacecolony.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import java.util.Random;

/**
 * Subtle animated starfield for atmosphere. Lightweight: dots + slow drift.
 */
public class StarfieldView extends View {
    private static final int STAR_COUNT = 130;

    private final Paint starPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Random random = new Random();
    private final float[] x = new float[STAR_COUNT];
    private final float[] y = new float[STAR_COUNT];
    private final float[] radius = new float[STAR_COUNT];
    private final float[] vy = new float[STAR_COUNT];
    private final float[] vx = new float[STAR_COUNT];
    private final int[] alpha = new int[STAR_COUNT];
    private int widthPx;
    private int heightPx;
    private ValueAnimator animator;

    public StarfieldView(Context context) {
        super(context);
        init();
    }

    public StarfieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StarfieldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        starPaint.setStyle(Paint.Style.FILL);
        starPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        widthPx = Math.max(1, w);
        heightPx = Math.max(1, h);
        seedStars();
    }

    private void seedStars() {
        for (int i = 0; i < STAR_COUNT; i++) {
            x[i] = random.nextFloat() * widthPx;
            y[i] = random.nextFloat() * heightPx;
            radius[i] = 0.5f + random.nextFloat() * 1.6f;
            vy[i] = 0.12f + random.nextFloat() * 0.45f;
            vx[i] = (random.nextFloat() - 0.5f) * 0.25f;
            alpha[i] = 35 + random.nextInt(190);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0f, 1f);
            animator.setDuration(32_000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(a -> {
                tick();
                invalidate();
            });
            animator.start();
        }
    }

    private void tick() {
        if (widthPx <= 0 || heightPx <= 0) {
            return;
        }
        for (int i = 0; i < STAR_COUNT; i++) {
            y[i] += vy[i];
            x[i] += vx[i];
            if (y[i] > heightPx + 8) {
                y[i] = -6f;
                x[i] = random.nextFloat() * widthPx;
            }
            if (x[i] < -8) {
                x[i] = widthPx + 6f;
            }
            if (x[i] > widthPx + 8) {
                x[i] = -6f;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (widthPx <= 0 || heightPx <= 0) {
            return;
        }
        for (int i = 0; i < STAR_COUNT; i++) {
            starPaint.setAlpha(alpha[i]);
            canvas.drawCircle(x[i], y[i], radius[i], starPaint);
        }
    }
}
