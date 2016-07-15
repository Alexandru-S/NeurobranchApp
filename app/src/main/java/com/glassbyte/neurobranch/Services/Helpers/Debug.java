package com.glassbyte.neurobranch.Services.Helpers;

import com.glassbyte.neurobranch.Services.DataObjects.Attributes;
import com.glassbyte.neurobranch.Services.DataObjects.Epoch;
import com.glassbyte.neurobranch.Services.DataObjects.Question;
import com.glassbyte.neurobranch.Services.Globals;

import java.util.ArrayList;

/**
 * Created by ed on 06/07/2016.
 */
public class Debug {
    public static void debugPrintEpoch(ArrayList<Object> properties) {
        ArrayList<Question> questions = new ArrayList<>();
        for (int i = 1; i < properties.size(); i++) {
            ArrayList<String> questionParams = (ArrayList<String>) properties.get(i);
            String title = questionParams.get(0);
            String type = questionParams.get(0);
            ArrayList<String> questionElements = new ArrayList<>();

            for (int j = 2; j < questionParams.size(); j++) {
                questionElements.add(questionParams.get(j));
            }
            Question question = new Question(title, questionElements, Attributes.getQuestionType(type), i, false);
            questions.add(question);
        }

        Epoch epoch = new Epoch(questions, 0, 0, "Debug Research Group");
        System.out.println(epoch.getQuestions().size() + " questions in epoch id " + epoch.getResearcherGroup());
        for (int i = 0; i < epoch.getQuestions().size(); i++) {
            Question question = epoch.getQuestions().get(i);
            System.out.println("Q" + question.getId() + ": " + question.getTitle() + " with type " + question.getType());
            if (!question.getAnswers().isEmpty()) {
                for (int j = 0; j < question.getAnswers().size(); j++) {
                    System.out.println("A" + j + 1 + ": " + question.getAnswers().get(j));
                }
            }
        }
    }

    public static void debugViewQuestions(Epoch epoch) {
        System.out.println(epoch.getQuestions().size() + " questions in epoch id " + epoch.getResearcherGroup());
        for (int i = 0; i < epoch.getQuestions().size(); i++) {
            Question question = epoch.getQuestions().get(i);
            System.out.println("Q" + question.getId() + ": " + question.getTitle() + " with type " + question.getType());
            if (!question.getAnswers().isEmpty()) {
                for (int j = 0; j < question.getAnswers().size(); j++) {
                    System.out.println("A" + j + ": " + question.getAnswers().get(j));
                }
            }
        }
    }
}
