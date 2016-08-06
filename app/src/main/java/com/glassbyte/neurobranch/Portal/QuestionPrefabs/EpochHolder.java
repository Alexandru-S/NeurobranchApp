package com.glassbyte.neurobranch.Portal.QuestionPrefabs;

import android.content.DialogInterface;
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

import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.DataObjects.Attributes;
import com.glassbyte.neurobranch.Services.DataObjects.Epoch;
import com.glassbyte.neurobranch.Services.DataObjects.JSON;
import com.glassbyte.neurobranch.Services.DataObjects.Question;
import com.glassbyte.neurobranch.Services.DataObjects.Response;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ed on 04/07/2016.
 */
public class EpochHolder extends AppCompatActivity {
    Epoch epoch;
    ArrayList<Object> properties = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();

    ViewPager viewPager;

    public EpochHolder(){}

    public EpochHolder(Epoch epoch) {
        this.epoch = epoch;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            properties = JSON.parseQuestions(new HTTPRequest.ReceiveJSON(this,
                    new URL(Globals.GET_QUESTIONS_ADDRESS)).execute().get());
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
                    Checkbox checkbox = new Checkbox();
                    checkbox.setProperties(properties);
                    checkbox.setQuestionNumber(questions.indexOf(question));
                    checkbox.setTotalQuestionAmount(questions.size());
                    fragments.add(checkbox);
                } else if(question.getType() == Attributes.QuestionType.choice) {
                    TextChoice choice = new TextChoice();
                    choice.setProperties(properties);
                    choice.setQuestionNumber(questions.indexOf(question));
                    choice.setTotalQuestions(questions.size());
                    fragments.add(choice);
                } else if(question.getType() == Attributes.QuestionType.drawing) {
                    Drawing drawing = new Drawing();
                    //drawing.setProperties(properties);
                    fragments.add(drawing);
                } else if(question.getType() == Attributes.QuestionType.multimedia) {
                    Multimedia multimedia = new Multimedia();
                    //multimedia.setQuestionNumber(questions.indexOf(question));
                    //multimedia.setProperties(properties);
                    fragments.add(multimedia);
                } else if(question.getType() == Attributes.QuestionType.scale) {
                    Scale scale = new Scale();
                    scale.setProperties(properties);
                    scale.setQuestionNumber(questions.indexOf(question));
                    scale.setTotalQuestions(questions.size());
                    fragments.add(scale);
                } else if(question.getType() == Attributes.QuestionType.section) {
                    TextSection section = new TextSection();
                    section.setProperties(properties);
                    section.setQuestionNumber(questions.indexOf(question));
                    section.setTotalQuestionAmount(questions.size());
                    fragments.add(section);
                }
            }

            super.onCreate(savedInstanceState);
            setContentView(R.layout.question_holder);

            final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
            floatingActionButton.hide();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(EpochHolder.this)
                            .setTitle("Respond to Trial")
                            .setMessage("By clicking okay, you'll respond to the trial with the answers you've entered. This cannot be changed once clicked.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    for(Fragment fragment : fragments) {
                                        Response response = new Response(Response.generateResponse(
                                                "*questionid", "*candidateid", fragment),
                                                Attributes.ResponseType.trial_response);
                                        new HTTPRequest.PostTrialResponse(response).execute();
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
