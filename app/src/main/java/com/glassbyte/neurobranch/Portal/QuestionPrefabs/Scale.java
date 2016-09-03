package com.glassbyte.neurobranch.Portal.QuestionPrefabs;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.glassbyte.neurobranch.R;

import java.util.ArrayList;

import app.minimize.com.seek_bar_compat.SeekBarCompat;

/**
 * Created by ed on 10/06/16.
 */
@SuppressLint("ValidFragment")
public class Scale extends QuestionFragment {
    private SeekBarCompat seekBar;
    private ArrayList seekValue = new ArrayList();
    private int seekProgression;

    public Scale(ArrayList<Object> properties, int maxIndex, int questionIndex) {
        super(properties, maxIndex, questionIndex);
    }

    @Override
    public void generateAnswers() {
        seekBar = new SeekBarCompat(this.getActivity());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setProgressBarColour(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekProgression = seekBar.getProgress();
            }
        });

        RelativeLayout.LayoutParams seekBarParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        seekBarParams.addRule(RelativeLayout.BELOW, getQuestionTitle_tv().getId());
        seekBar.setLayoutParams(seekBarParams);
        seekBar.setMax(100);
        seekBar.setThumbColor(ContextCompat.getColor(getActivity(), R.color.teal500));

        getContentLayout().addView(seekBar);
    }

    @Override
    public ArrayList<String> getAnswersChosen() {
        seekValue.clear();
        seekValue.add(seekProgression);
        return seekValue;
    }

    @Override
    public ArrayList<Integer> getScoresChosen() {
        return null;
    }

    private void setProgressBarColour(int progress) {
        int colour = (Integer) new ArgbEvaluator().evaluate(((float) progress) / 100,
                ContextCompat.getColor(getActivity(), R.color.teal500),
                ContextCompat.getColor(getActivity(), R.color.red500));

        seekBar.setProgressColor(colour);
        seekBar.setThumbColor(colour);
    }
}