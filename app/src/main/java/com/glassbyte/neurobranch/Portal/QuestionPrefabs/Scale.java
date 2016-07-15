package com.glassbyte.neurobranch.Portal.QuestionPrefabs;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.Globals;

import java.util.ArrayList;

import app.minimize.com.seek_bar_compat.SeekBarCompat;

/**
 * Created by ed on 10/06/16.
 */
public class Scale extends android.support.v4.app.Fragment {

    RelativeLayout relativeLayout;
    RelativeLayout.LayoutParams layoutParams;
    SeekBarCompat seekBar;

    String title, type;
    int response, questionNumber, totalQuestions;

    ArrayList<Integer> idList = new ArrayList<>();
    ArrayList<Object> properties = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ArrayList<String> questionParams = (ArrayList<String>) getProperties().get(getQuestionNumber());

        setTitle(questionParams.get(0));
        setType(questionParams.get(1));

        return generateLayout();
    }

    private View generateLayout() {
        relativeLayout = new RelativeLayout(this.getActivity());
        relativeLayout.setGravity(Gravity.START);
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(Globals.getDp(this.getActivity(), 16), Globals.getDp(this.getActivity(), 24),
                Globals.getDp(this.getActivity(), 24), Globals.getDp(this.getActivity(), 16));
        relativeLayout.setLayoutParams(layoutParams);

        //progression through the epoch
        TextView questionProgress = new TextView(this.getActivity());
        RelativeLayout.LayoutParams questionProgressParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        questionProgressParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        questionProgressParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        questionProgressParams.setMargins(0, 0, 0, Globals.getDp(this.getActivity(), 16));
        questionProgress.setLayoutParams(questionProgressParams);

        String progression = (getQuestionNumber() + 1) + "/" + getTotalQuestions();
        questionProgress.setText(progression);
        questionProgress.setId(View.generateViewId());
        idList.add(questionProgress.getId());

        relativeLayout.addView(questionProgress);

        //title of the current question
        TextView questionTitle = new TextView(this.getActivity());
        RelativeLayout.LayoutParams questionTitleParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        questionTitleParams.addRule(RelativeLayout.BELOW, questionProgress.getId());
        questionTitleParams.setMargins(0, 0, 0, Globals.getDp(this.getActivity(), 16));
        questionTitle.setLayoutParams(questionTitleParams);
        questionTitle.setText(getTitle());
        questionTitle.setId(View.generateViewId());
        idList.add(questionTitle.getId());

        relativeLayout.addView(questionTitle);

        //scale
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
                setResponse(seekBar.getProgress());
            }
        });

        RelativeLayout.LayoutParams seekBarParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        seekBarParams.addRule(RelativeLayout.BELOW, questionTitle.getId());
        seekBar.setLayoutParams(seekBarParams);
        seekBar.setMax(100);
        seekBar.setThumbColor(ContextCompat.getColor(getActivity(), R.color.teal500));

        relativeLayout.addView(seekBar);

        return relativeLayout;
    }

    private void setProgressBarColour(int progress) {
        int red500 = ContextCompat.getColor(getActivity(), R.color.red500);
        int teal500 = ContextCompat.getColor(getActivity(), R.color.teal500);
        int colour = (Integer) new ArgbEvaluator().evaluate(((float) progress) / 100, teal500, red500);

        seekBar.setProgressColor(colour);
        seekBar.setThumbColor(colour);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public ArrayList<Object> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Object> properties) {
        this.properties = properties;
    }
}
