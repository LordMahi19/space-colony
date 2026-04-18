package com.example.spacecolony.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Lightweight custom bar chart for displaying per-crew statistics.
 * Draws horizontal bars with labels, animated fill, and value text.
 */
public class BarChartView extends View {

    private static final float ROW_HEIGHT_DP = 38f;
    private static final float BAR_HEIGHT_DP = 18f;
    private static final float LABEL_WIDTH_DP = 90f;
    private static final float CORNER_DP = 5f;

    private final List<BarEntry> entries = new ArrayList<>();
    private final Paint barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint bgBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF barRect = new RectF();
    private float animProgress = 1f;

    public BarChartView(Context context) {
        super(context);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        float density = getResources().getDisplayMetrics().density;

        labelPaint.setTextSize(12f * density);
        labelPaint.setColor(0xB3C4D4E8); // text_secondary
        labelPaint.setTextAlign(Paint.Align.LEFT);

        valuePaint.setTextSize(11f * density);
        valuePaint.setColor(0xFFF0F4FF); // text_primary
        valuePaint.setTextAlign(Paint.Align.LEFT);

        barPaint.setStyle(Paint.Style.FILL);

        bgBarPaint.setStyle(Paint.Style.FILL);
        bgBarPaint.setColor(0x15FFFFFF);
    }

    /**
     * Set the chart data and trigger an animation.
     */
    public void setEntries(List<BarEntry> newEntries) {
        entries.clear();
        entries.addAll(newEntries);
        requestLayout();
        animateIn();
    }

    private void animateIn() {
        animProgress = 0f;
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(700);
        anim.setInterpolator(new DecelerateInterpolator(1.5f));
        anim.addUpdateListener(a -> {
            animProgress = (float) a.getAnimatedValue();
            invalidate();
        });
        anim.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        float density = getResources().getDisplayMetrics().density;
        int height = (int) (entries.size() * ROW_HEIGHT_DP * density);
        setMeasuredDimension(width, Math.max(height, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (entries.isEmpty()) {
            return;
        }
        float density = getResources().getDisplayMetrics().density;
        float rowHeight = ROW_HEIGHT_DP * density;
        float barHeight = BAR_HEIGHT_DP * density;
        float labelWidth = LABEL_WIDTH_DP * density;
        float barStart = labelWidth + 8f * density;
        float corner = CORNER_DP * density;
        float valueMargin = 6f * density;
        float maxBarWidth = getWidth() - barStart - 36f * density;

        float maxValue = 0;
        for (BarEntry e : entries) {
            maxValue = Math.max(maxValue, e.value);
        }
        if (maxValue == 0) {
            maxValue = 1;
        }

        Paint.FontMetrics labelFm = labelPaint.getFontMetrics();
        Paint.FontMetrics valueFm = valuePaint.getFontMetrics();

        for (int i = 0; i < entries.size(); i++) {
            BarEntry entry = entries.get(i);
            float rowTop = i * rowHeight;
            float centerY = rowTop + rowHeight / 2f;
            float barTop = centerY - barHeight / 2f;

            // Draw label (truncate if needed)
            float labelBaseline = centerY - (labelFm.ascent + labelFm.descent) / 2f;
            String label = entry.label;
            float measured = labelPaint.measureText(label);
            if (measured > labelWidth) {
                while (label.length() > 1 && labelPaint.measureText(label + "\u2026") > labelWidth) {
                    label = label.substring(0, label.length() - 1);
                }
                label = label + "\u2026";
            }
            canvas.drawText(label, 0, labelBaseline, labelPaint);

            // Draw background bar (ghost track)
            barRect.set(barStart, barTop, barStart + maxBarWidth, barTop + barHeight);
            canvas.drawRoundRect(barRect, corner, corner, bgBarPaint);

            // Draw filled bar
            float barWidth = (entry.value / maxValue) * maxBarWidth * animProgress;
            if (barWidth > 0) {
                barPaint.setColor(entry.color);
                barPaint.setAlpha(210);
                barRect.set(barStart, barTop, barStart + barWidth, barTop + barHeight);
                canvas.drawRoundRect(barRect, corner, corner, barPaint);
            }

            // Draw value text (fade in halfway through animation)
            if (animProgress > 0.3f) {
                float alpha = Math.min(1f, (animProgress - 0.3f) / 0.4f);
                valuePaint.setAlpha((int) (255 * alpha));
                float valueBaseline = centerY - (valueFm.ascent + valueFm.descent) / 2f;
                canvas.drawText(String.valueOf((int) entry.value),
                        barStart + barWidth + valueMargin, valueBaseline, valuePaint);
            }
        }
    }

    /**
     * A single bar entry with a label, numeric value, and ARGB color.
     */
    public static class BarEntry {
        public final String label;
        public final float value;
        public final int color;

        public BarEntry(String label, float value, int color) {
            this.label = label;
            this.value = value;
            this.color = color;
        }
    }
}
