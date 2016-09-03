package com.glassbyte.neurobranch.Portal.QuestionPrefabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.glassbyte.neurobranch.MainActivity;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Unimplemented.Drawing;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Unimplemented.Multimedia;
import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.DataObjects.Attributes;
import com.glassbyte.neurobranch.Services.DataObjects.JSON;
import com.glassbyte.neurobranch.Services.DataObjects.Question;
import com.glassbyte.neurobranch.Services.DataObjects.Response;
import com.glassbyte.neurobranch.Services.Enums.Preferences;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Helpers.Manager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ed on 04/07/2016
 */
public class EpochHolder extends AppCompatActivity {
    ArrayList<Object> properties = new ArrayList<>();
    ArrayList<QuestionFragment> fragments = new ArrayList<>();

    ViewPager viewPager;
    String trialId, questionId, candidateId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle details = getIntent().getExtras();
        setTrialId(details.getString("TRIAL_ID"));
        Toast.makeText(getApplicationContext(), getTrialId(), Toast.LENGTH_SHORT).show();

        //check the trialid to see if an eligibility form is requirgted
        //make a call to see if the user exists in the requested list
        //if so, the eligibility form must be shown to the user and completed
        //this also means that a field for enabling usage of the score is needed
        //on completion, if the form has a high enough score, the user is switched from the requested list to the verified list
        //else they are removed from the requested list

        try {
            properties = JSON.parseQuestions(new HTTPRequest.ReceiveJSON(this, new URL(
                    Globals.retrieveTrialQuestions(getTrialId())), getTrialId(), getQuestionId(), getCandidateId()).execute().get());
        } catch (InterruptedException | ExecutionException | MalformedURLException e) {
            e.printStackTrace();
        } finally {
            ArrayList<Question> questions = new ArrayList<>();
            for (int i = 0; i < properties.size(); i++) {
                ArrayList<String> questionParams = (ArrayList<String>) properties.get(i);
                String title = questionParams.get(0);
                String type = questionParams.get(1);

                ArrayList<String> questionElements = new ArrayList<>();

                for (int j = 2; j < questionParams.size(); j++) {
                    questionElements.add(questionParams.get(j));
                }

                Question question = new Question(title, questionElements,
                        Attributes.getQuestionType(type), i, false);
                questions.add(question);
            }

            for(Question question : questions) {
                if(question.getType() == Attributes.QuestionType.checkbox) {
                    fragments.add(new Checkbox(properties, questions.size(), questions.indexOf(question)));
                } else if(question.getType() == Attributes.QuestionType.choice) {
                    fragments.add(new Choice(properties, questions.size(), questions.indexOf(question)));
                } else if(question.getType() == Attributes.QuestionType.scale) {
                    fragments.add(new Scale(properties, questions.size(), questions.indexOf(question)));
                } else if(question.getType() == Attributes.QuestionType.section) {
                    fragments.add(new Section(properties, questions.size(), questions.indexOf(question)));
                }
            }

            super.onCreate(savedInstanceState);
            setContentView(R.layout.question_holder);

            final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
            if(fragments.size() > 1) floatingActionButton.hide();
            else floatingActionButton.show();

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(EpochHolder.this)
                            .setTitle("Respond to Trial")
                            .setMessage("By clicking okay, you'll respond to the trial with the answers you've entered. This cannot be changed once clicked.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    for(QuestionFragment fragment : fragments) {
                                        Response response = new Response(Response.generateResponse(
                                                getTrialId(),
                                                getQuestionId(),
                                                Manager.getInstance().getPreference(Preferences.id, getApplicationContext()),
                                                fragment),
                                                Attributes.ResponseType.trial_response);
                                        System.out.println(response.getQuestionResponse().toString());
                                        //new HTTPRequest.PostTrialResponse(response).execute();
                                    }

                                    String toastMessage = fragments.size() > 1 ? "Responses being sent" : "Response being sent";
                                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
                                    EpochHolder.this.finish();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            });

            viewPager = (ViewPager) findViewById(R.id.question_holder);
            viewPager.setAdapter(new SlideAdapter(getSupportFragmentManager()));
            viewPager.setOffscreenPageLimit(fragments.size());
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(position == fragments.size() - 1) {
                        floatingActionButton.show();
                    } else {
                        floatingActionButton.hide();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Exiting questions area")
                    .setMessage("By clicking okay you will exit the questions area and lose any input data entered.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EpochHolder.this.finish();
                            startActivity(new Intent(EpochHolder.this, MainActivity.class));
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public String getTrialId() {
        return trialId;
    }

    public void setTrialId(String trialId) {
        this.trialId = trialId;
    }

    public String getQuestionId() { return questionId; }

    public void setQuestionId(String questionId) { this.questionId = questionId; }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    private class SlideAdapter extends FragmentStatePagerAdapter {

        public SlideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
