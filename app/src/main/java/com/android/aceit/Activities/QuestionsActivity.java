package com.android.aceit.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aceit.Models.QuestionModel;
import com.android.aceit.Adapters.QuestionsAdapter;
import com.android.aceit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class QuestionsActivity extends AppCompatActivity {
    TextView tv;
    QuestionsAdapter questionsAdapter;
    RecyclerView recyclerView;

    ArrayList<QuestionModel> questionModels;

    Button startLiveQuiz;
    Button startPracticeMode;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            actionBar.setTitle("Questions");
        }

        questionModels = new ArrayList<>();
        recyclerView = findViewById(R.id.rvQuestions);

        startLiveQuiz = (Button) findViewById(R.id.btnStartLiveModeQuiz);
        startPracticeMode = findViewById(R.id.btnStartPracticeQuiz);


        // Retrieve the intent extras
        Intent intent = getIntent();
        // Retrieve the intent extras
        String jsonObjectString = getIntent().getStringExtra("jsonObject");
        JSONObject jsonObject = null;
// Convert the JSON string to a JSONObject
        try {
             jsonObject = new JSONObject(jsonObjectString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create an instance of the Question model and populate it
        QuestionModel question = new QuestionModel();
        ArrayList<QuestionModel> questionList = new ArrayList<>();

        try {
            // Get the name and questions objects from the JSON object
            JSONObject questionsObject = jsonObject.getJSONObject("questions");

            // Iterate over the questions object keys
            Iterator<String> keys = questionsObject.keys();
            while (keys.hasNext()) {
                String questionKey = keys.next();
                JSONObject questionObject = questionsObject.getJSONObject(questionKey);

                // Get the question and answer text from the question object
                String questionText = questionObject.getString("questionText");
                String answerText = questionObject.getString("answerText");
                String qid = questionObject.getString("id");

                // Create a new QuestionModel object and add it to the list
                QuestionModel questionModel = new QuestionModel(questionText,answerText,qid);

                questionList.add(questionModel);
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            questionsAdapter = new QuestionsAdapter(questionList, QuestionsActivity.this);
            recyclerView.setAdapter(questionsAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        startLiveQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionsActivity.this, Quiz.class);
                intent.putExtra("questionList", questionList);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        startPracticeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionsActivity.this, Quiz.class);
                intent.putExtra("questionList", questionList);
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });


// Use the populated questionList as needed




        //to get sharedPreference


        //Toast.makeText(this, "SHredKey"+sharedMainCategoryKey, Toast.LENGTH_SHORT).show();


        //this one works
        Toast.makeText(this, "Questions ", Toast.LENGTH_SHORT).show();















    }


    //to close this activity
    @Override
    public boolean onSupportNavigateUp() {


        finish();
        return false;
    }




}
