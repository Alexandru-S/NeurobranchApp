package com.glassbyte.neurobranch.Portal.QuestionPrefabs;

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
public class TextSection extends android.support.v4.app.Fragment {
    RelativeLayout contentLayout;
    RelativeLayout.LayoutParams layoutParams;

    int questionNumber, totalQuestionAmount;
    String title, type;

    EditText editText;

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

        contentLayout.addView(questionProgress);

        TextView questionTitle = new TextView(this.getActivity());
        RelativeLayout.LayoutParams questionTitleParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        questionTitleParams.addRule(RelativeLayout.BELOW, questionProgress.getId());
        questionTitleParams.setMargins(0, 0, 0, Globals.getDp(this.getActivity(), 16));
        questionTitle.setLayoutParams(questionTitleParams);
        questionTitle.setText(getTitle());
        questionTitle.setId(View.generateViewId());

        contentLayout.addView(questionTitle);

        editText = new EditText(this.getActivity());
        RelativeLayout.LayoutParams editTextParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, Globals.getDp(this.getActivity(), 256));
        editTextParams.addRule(RelativeLayout.BELOW, questionTitle.getId());
        editText.setId(View.generateViewId());
        editText.setLayoutParams(editTextParams);
        editText.setHint("Enter text here...");

        editText.setSingleLine(false);
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setLines(5);
        editText.setMaxLines(10);
        editText.setVerticalScrollBarEnabled(true);
        editText.setMovementMethod(ScrollingMovementMethod.getInstance());
        editText.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

        contentLayout.addView(editText);

        questionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(getTextContent());
            }
        });

        return contentLayout;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getTextContent() {
        return editText.getText().toString();
    }
}
