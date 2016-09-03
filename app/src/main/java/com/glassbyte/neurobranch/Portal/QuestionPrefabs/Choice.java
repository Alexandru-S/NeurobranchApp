package com.glassbyte.neurobranch.Portal.QuestionPrefabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.glassbyte.neurobranch.Services.Globals;

import java.util.ArrayList;

/**
 * Created by ed on 10/06/16.
 */
@SuppressLint("ValidFragment")
public class Choice extends QuestionFragment {
    private RadioGroup radioGroup;
    private ArrayList<RadioButton> radioButtons = new ArrayList<>();
    private ArrayList<String> answerChosen = new ArrayList<>();
    private ArrayList<Integer> scoreChosen = new ArrayList<>();

    public Choice(ArrayList<Object> properties, int maxIndex, int questionIndex) {
        super(properties, maxIndex, questionIndex);
    }

    @Override
    public void generateAnswers() {
        radioGroup = new RadioGroup(this.getActivity());
        RelativeLayout.LayoutParams radioGroupParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioGroupParams.addRule(RelativeLayout.BELOW, getQuestionTitle_tv().getId());
        radioGroupParams.setMargins(0, 0, 0, Globals.getDp(this.getActivity(), 16));
        radioGroup.setLayoutParams(radioGroupParams);
        radioGroup.setId(View.generateViewId());
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        getIdList().add(radioGroup.getId());

        for(int i=0; i<getAnswers().size(); i++) {
            RadioButton radioButton = new RadioButton(this.getActivity());
            radioButton.setId(View.generateViewId());
            radioButton.setText(getAnswers().get(i));
            radioGroup.addView(radioButton);
            radioButtons.add(radioButton);
        }
        getContentLayout().addView(radioGroup);
    }

    @Override
    public ArrayList<String> getAnswersChosen() {
        if(radioGroup.getCheckedRadioButtonId() > -1) {
            int index = radioButtons.indexOf(radioGroup.getCheckedRadioButtonId());
            String checkedItem = getAnswers().get(radioButtons.indexOf(radioGroup.getCheckedRadioButtonId()));
            for (int i = 0; i < radioButtons.size(); i++) {
                answerChosen.add(i, null);
            }
            answerChosen.set(index, checkedItem);
        }
        return getAnswers();
    }

    @Override
    public ArrayList<Integer> getScoresChosen() {
        for (RadioButton radioButton : radioButtons) {
            //if (radioButton.isChecked())
                //scoreChosen.add(getScores().get(radioGroup.));
        }

        return scoreChosen;
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public ArrayList<RadioButton> getRadioButtons() {
        return radioButtons;
    }

    public ArrayList<String> getAnswerChosen() {
        return answerChosen;
    }
}
