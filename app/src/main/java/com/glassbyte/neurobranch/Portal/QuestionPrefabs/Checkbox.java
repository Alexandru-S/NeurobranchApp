package com.glassbyte.neurobranch.Portal.QuestionPrefabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.glassbyte.neurobranch.Services.Globals;

import java.util.ArrayList;

/**
 * Created by ed on 10/06/16.
 */
public class Checkbox extends Fragment {

    ArrayList<String> checkboxValues = new ArrayList<>();
    ArrayList<Object> properties = new ArrayList<>();

    RelativeLayout contentLayout;
    RelativeLayout.LayoutParams layoutParams;
    String question, type;
    int questionNumber, totalQuestionAmount;

    ArrayList<CheckBox> checkboxes = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ArrayList<String> checkboxValues = new ArrayList<>();
        ArrayList<String> questionParams = (ArrayList<String>) getProperties().get(getQuestionNumber());

        setQuestion(questionParams.get(0));
        setType(questionParams.get(1));

        for(int i=2; i<questionParams.size(); i++) {
            checkboxValues.add(questionParams.get(i));
        }
        setCheckboxValues(checkboxValues);

        return generateLayout();
    }

    private View generateLayout() {
        ArrayList<Integer> idList = new ArrayList<>();

        contentLayout = new RelativeLayout(this.getActivity());
        contentLayout.setGravity(Gravity.START);
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(Globals.getDp(this.getActivity(), 16), Globals.getDp(this.getActivity(), 24),
                Globals.getDp(this.getActivity(), 24), Globals.getDp(this.getActivity(), 16));
        contentLayout.setLayoutParams(layoutParams);

        TextView questionProgress = new TextView(this.getActivity());
        RelativeLayout.LayoutParams questionProgressParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        questionProgressParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        questionProgressParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        questionProgressParams.setMargins(0, 0, 0, Globals.getDp(this.getActivity(), 16));
        questionProgress.setLayoutParams(questionProgressParams);

        String progression = (getQuestionNumber() + 1) + "/" + getTotalQuestionAmount();
        questionProgress.setText(progression);
        questionProgress.setId(View.generateViewId());
        idList.add(questionProgress.getId());

        contentLayout.addView(questionProgress);

        TextView questionTitle = new TextView(this.getActivity());
        RelativeLayout.LayoutParams questionTitleParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        questionTitleParams.addRule(RelativeLayout.BELOW, questionProgress.getId());
        questionTitleParams.setMargins(0, 0, 0, Globals.getDp(this.getActivity(), 16));
        questionTitle.setLayoutParams(questionTitleParams);
        questionTitle.setText(getQuestion());
        questionTitle.setId(View.generateViewId());
        idList.add(questionTitle.getId());

        contentLayout.addView(questionTitle);

        for(int i=0; i<getCheckboxValues().size(); i++) {
            CheckBox checkbox = new CheckBox(this.getActivity());
            RelativeLayout.LayoutParams checkboxParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            checkboxParams.addRule(RelativeLayout.BELOW, idList.get(i + 1));
            checkboxParams.setMarginStart(Globals.getDp(this.getActivity(), 16));

            checkbox.setLayoutParams(checkboxParams);
            checkbox.setText(getCheckboxValues().get(i));
            checkbox.setId(View.generateViewId());
            idList.add(checkbox.getId());
            checkboxes.add(checkbox);
            contentLayout.addView(checkbox);
        }

        questionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(getAnswers());
            }
        });

        return contentLayout;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getCheckboxValues() {
        return checkboxValues;
    }

    public void setCheckboxValues(ArrayList<String> checkboxValues) {
        this.checkboxValues = checkboxValues;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getTotalQuestionAmount() {
        return totalQuestionAmount;
    }

    public void setTotalQuestionAmount(int totalQuestionAmount) {
        this.totalQuestionAmount = totalQuestionAmount;
    }

    public ArrayList<Object> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Object> properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        for(CheckBox checkBox : checkboxes) {
            if(checkBox.isChecked())
                answers.add(checkBox.getText().toString());
        }
        return answers;
    }
}
