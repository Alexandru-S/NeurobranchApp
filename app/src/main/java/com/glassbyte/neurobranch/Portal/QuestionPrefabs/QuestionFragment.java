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

import com.glassbyte.neurobranch.Services.DataObjects.Attributes;
import com.glassbyte.neurobranch.Services.Globals;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ed on 03/09/2016
 */
public abstract class QuestionFragment extends Fragment {
    private ArrayList<String> answers = new ArrayList<>();
    private ArrayList<Integer> scores = new ArrayList<>();

    private ArrayList<Object> properties = new ArrayList<>();
    private ArrayList<Integer> idList = new ArrayList<>();
    private ArrayList<String> questionParams;

    private RelativeLayout contentLayout;
    private RelativeLayout.LayoutParams layoutParams;
    private String questionTitle, questionType;
    private int questionIndex, maxIndex;

    private TextView questionTitle_tv, questionProgress_tv;

    public QuestionFragment(ArrayList<Object> properties, int maxIndex, int questionIndex, boolean isEligibility) {
        questionParams = (ArrayList<String>) properties.get(questionIndex);
        this.questionTitle = questionParams.get(0);
        this.questionType = questionParams.get(1);
        if (questionParams.size() > 2) {
            for (int i = 2; i < questionParams.size(); i += isEligibility ? 2 : 1) {
                this.answers.add(questionParams.get(i));
                if(isEligibility) this.scores.add(Integer.valueOf(questionParams.get(i + 1)));
            }
        }

        this.maxIndex = maxIndex;
        this.questionIndex = questionIndex;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return generateView();
    }

    public View generateView() {
        generateLayout();
        generateProgress();
        generateTitle();
        if (hasAnswers()) generateAnswers();

        return contentLayout;
    }

    public void generateLayout() {
        contentLayout = new RelativeLayout(this.getActivity());
        contentLayout.setGravity(Gravity.START);
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(Globals.getDp(this.getActivity(), 16), Globals.getDp(this.getActivity(), 24),
                Globals.getDp(this.getActivity(), 24), Globals.getDp(this.getActivity(), 16));
        contentLayout.setLayoutParams(layoutParams);
    }

    public void generateProgress() {
        questionProgress_tv = new TextView(this.getActivity());
        RelativeLayout.LayoutParams questionProgressParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        questionProgressParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        questionProgressParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        questionProgressParams.setMargins(0, 0, 0, Globals.getDp(this.getActivity(), 16));
        questionProgress_tv.setLayoutParams(questionProgressParams);

        String progression = (getQuestionIndex() + 1) + "/" + getMaxIndex();
        questionProgress_tv.setText(progression);
        questionProgress_tv.setId(View.generateViewId());
        idList.add(questionProgress_tv.getId());

        contentLayout.addView(questionProgress_tv);
    }

    public void generateTitle() {
        questionTitle_tv= new TextView(this.getActivity());
        RelativeLayout.LayoutParams questionTitleParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        questionTitleParams.addRule(RelativeLayout.BELOW, questionProgress_tv.getId());
        questionTitleParams.setMargins(0, 0, 0, Globals.getDp(this.getActivity(), 16));
        questionTitle_tv.setLayoutParams(questionTitleParams);
        questionTitle_tv.setText(getQuestionTitle());
        questionTitle_tv.setId(View.generateViewId());
        idList.add(questionTitle_tv.getId());

        contentLayout.addView(questionTitle_tv);
    }

    public abstract void generateAnswers();

    public abstract ArrayList<String> getAnswersChosen();

    public abstract ArrayList<Integer> getScoresChosen();

    public int getScoreSum() {
        Integer sum = 0;
        for(Integer integer : getScoresChosen()) {
            sum += integer;
        }

        return sum;
    }

    public boolean hasAnswers() {
        return getQuestionType().equals(Attributes.QuestionType.scale.name()) ||
                getQuestionType().equals(Attributes.QuestionType.text.name()) ||
                getQuestionParams().size() > 2;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<Object> getProperties() {
        return properties;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public String getQuestionType() {
        return questionType;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public ArrayList<Integer> getIdList() {
        return idList;
    }

    public ArrayList<String> getQuestionParams() {
        return questionParams;
    }

    public RelativeLayout getContentLayout() {
        return contentLayout;
    }

    public RelativeLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public TextView getQuestionTitle_tv() {
        return questionTitle_tv;
    }

    public TextView getQuestionProgress_tv() {
        return questionProgress_tv;
    }
}
