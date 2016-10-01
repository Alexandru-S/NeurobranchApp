package com.glassbyte.neurobranch.Portal.QuestionPrefabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.glassbyte.neurobranch.Services.Globals;

import java.util.ArrayList;

/**
 * Created by ed on 10/06/16.
 */
@SuppressLint("ValidFragment")
public class Text extends QuestionFragment {
    private EditText editText;
    private ArrayList<String> answerChosen = new ArrayList<>();

    public Text(ArrayList<Object> properties, int maxIndex, int questionIndex) {
        super(properties, maxIndex, questionIndex);
    }

    @Override
    public void generateAnswers() {
        editText = new EditText(this.getActivity());
        RelativeLayout.LayoutParams editTextParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, Globals.getDp(this.getActivity(), 256));
        editTextParams.addRule(RelativeLayout.BELOW, getQuestionTitle_tv().getId());
        editText.setId(View.generateViewId());
        editText.setLayoutParams(editTextParams);

        editText.setSingleLine(false);
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setLines(5);
        editText.setMaxLines(10);
        editText.setVerticalScrollBarEnabled(true);
        editText.setMovementMethod(ScrollingMovementMethod.getInstance());
        editText.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

        getContentLayout().addView(editText);
    }

    @Override
    public ArrayList<String> getAnswersChosen() {
        answerChosen.add(editText.getText().toString());
        return answerChosen;
    }

    @Override
    public ArrayList<Integer> getScoresChosen() {
        return null;
    }
}