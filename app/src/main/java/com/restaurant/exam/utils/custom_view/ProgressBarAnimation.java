package com.restaurant.exam.utils.custom_view;

import android.annotation.SuppressLint;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private TextView textViewProgress;
    private TextView textViewUnProgress;
    private float from;
    private float  to;

    public ProgressBarAnimation(TextView textViewProgress,
                                TextView textViewUnProgress,
                                ProgressBar progressBar,
                                float from,
                                float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
        this.textViewProgress = textViewProgress;
        this.textViewUnProgress = textViewUnProgress;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
        textViewProgress.setText((int) value + "%");
        textViewUnProgress.setText((100 - (int) value) + "%");
    }

}