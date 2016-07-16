package com.glassbyte.neurobranch.Portal.QuestionPrefabs;

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
public class TextChoice extends android.support.v4.app.Fragment {

    String title, type;
    ArrayList<String> answers = new ArrayList<>();
    int questionNumber, totalQuestions;

    RelativeLayout contentLayout;
    RelativeLayout.LayoutParams layoutParams;

    RadioGroup radioGroup;
    ArrayList<Object> properties = new ArrayList<>();
    ArrayList<Integer> idList = new ArrayList<>();
    ArrayList<Integer> radioButtons = new ArrayList<>();

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

        for(int i=2; i<questionParams.size(); i++) {
            answers.add(questionParams.get(i));
        }
        setAnswers(answers);

        return generateLayout();
    }

    private View generateLayout() {
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

        String progression = (getQuestionNumber() + 1) + "/" + getTotalQuestions();
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
        questionTitle.setText(getTitle());
        questionTitle.setId(View.generateViewId());
        idList.add(questionTitle.getId());

        contentLayout.addView(questionTitle);

        radioGroup = new RadioGroup(this.getActivity());
        RelativeLayout.LayoutParams radioGroupParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioGroupParams.addRule(RelativeLayout.BELOW, questionTitle.getId());
        radioGroupParams.setMargins(0, 0, 0, Globals.getDp(this.getActivity(), 16));
        radioGroup.setLayoutParams(radioGroupParams);
        radioGroup.setId(View.generateViewId());
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        idList.add(radioGroup.getId());

        for(int i=0; i<getAnswers().size(); i++) {
            RadioButton radioButton = new RadioButton(this.getActivity());
            radioButton.setId(View.generateViewId());
            radioButton.setText(getAnswers().get(i));
            radioGroup.addView(radioButton);
            radioButtons.add(radioButton.getId());
        }

        contentLayout.addView(radioGroup);

        return contentLayout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAnswers() {
        return getCheckedAnswer();
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getCheckedAnswer() {
        if(radioGroup.getCheckedRadioButtonId() > -1) {
            int index = radioButtons.indexOf(radioGroup.getCheckedRadioButtonId());
            String checkedItem = answers.get(radioButtons.indexOf(radioGroup.getCheckedRadioButtonId()));
            for (int i = 0; i < radioButtons.size(); i++) {
                answers.set(i, "");
            }
            answers.set(index, checkedItem);
        }
        return answers;
    }
}
