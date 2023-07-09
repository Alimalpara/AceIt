package com.android.aceit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class QuestionsActivity extends AppCompatActivity {
    TextView tv;
    QuestionsAdapter questionsAdapter;
    RecyclerView recyclerView;

    ArrayList<QuestionModel> questionModels;

    Button startQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        questionModels = new ArrayList<>();
        recyclerView = findViewById(R.id.rvQuestions);

        startQuiz = (Button) findViewById(R.id.btnStartQuiz);


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

        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionsActivity.this, Quiz.class);
                intent.putExtra("questionList", questionList);
                startActivity(intent);
            }
        });

// Use the populated questionList as needed




        //to get sharedPreference


        //Toast.makeText(this, "SHredKey"+sharedMainCategoryKey, Toast.LENGTH_SHORT).show();


        //this one works
        Toast.makeText(this, "Questions ", Toast.LENGTH_SHORT).show();















    }







}
