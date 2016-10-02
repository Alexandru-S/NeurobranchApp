package com.glassbyte.neurobranch.Portal.QuestionPrefabs;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.glassbyte.neurobranch.Services.Globals;

import java.util.ArrayList;

/**
 * Created by ed on 03/09/2016
 */
@SuppressLint("ValidFragment")
public class Checkbox extends QuestionFragment {
    private ArrayList<CheckBox> checkboxes = new ArrayList<>();
    private ArrayList<String> answersChosen = new ArrayList<>();
    private ArrayList<Integer> scoresChosen = new ArrayList<>();

    public Checkbox(ArrayList<Object> properties, int maxIndex, int questionIndex, boolean isEligibility) {
        super(properties, maxIndex, questionIndex, isEligibility);
    }

    @Override
    public void generateAnswers() {
        for(int i=0; i<getAnswers().size(); i++) {
            CheckBox checkbox = new CheckBox(this.getActivity());
            RelativeLayout.LayoutParams checkboxParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            checkboxParams.addRule(RelativeLayout.BELOW, getIdList().get(i + 1));
            checkboxParams.setMarginStart(Globals.getDp(this.getActivity(), 16));

            checkbox.setLayoutParams(checkboxParams);
            checkbox.setText(getAnswers().get(i));
            checkbox.setId(View.generateViewId());
            getIdList().add(checkbox.getId());
            getCheckboxes().add(checkbox);
            getContentLayout().addView(checkbox);
        }
    }

    @Override
    public ArrayList<String> getAnswersChosen() {
        answersChosen.clear();
        for (int i=0; i< checkboxes.size(); i++) {
            answersChosen.add(i, "");
        }

        for(CheckBox checkBox : getCheckboxes()) {
            if (checkBox.isChecked())
                answersChosen.set(getCheckboxes().indexOf(checkBox), checkBox.getText().toString());

        }
        return answersChosen;
    }

    @Override
    public ArrayList<Integer> getScoresChosen() {
        scoresChosen.clear();
        for(CheckBox checkBox : getCheckboxes()) {
            if (checkBox.isChecked())
                scoresChosen.add(getScores().get(checkboxes.indexOf(checkBox)));

        }
        return scoresChosen;
    }

    public ArrayList<CheckBox> getCheckboxes() {
        return checkboxes;
    }

}
