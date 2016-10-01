package com.glassbyte.neurobranch.Portal.QuestionPrefabs.Unimplemented;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glassbyte.neurobranch.Portal.QuestionPrefabs.QuestionFragment;
import com.glassbyte.neurobranch.R;

import java.util.ArrayList;

/**
 * Created by ed on 10/06/16.
 */
@SuppressLint("ValidFragment")
public class Multimedia extends QuestionFragment {
    public Multimedia(ArrayList<Object> properties, int maxIndex, int questionIndex) {
        super(properties, maxIndex, questionIndex);
    }

    @Override
    public void generateAnswers() {

    }

    @Override
    public ArrayList<String> getAnswersChosen() {
        return null;
    }

    @Override
    public ArrayList<Integer> getScoresChosen() {
        return null;
    }
}
