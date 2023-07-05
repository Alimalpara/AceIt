package com.android.aceit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {
    TextView tv;
    QuestionsAdapter questionsAdapter;
    RecyclerView recyclerView;

    ArrayList<QuestionModel> questionModels;
    String sharedMainCategoryKey;
    String sharedSubMainCategoryKey;
    String sharedSubSubMainCategoryKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        questionModels = new ArrayList<>();
        recyclerView = findViewById(R.id.rvQuestions);


        //to get sharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
       sharedMainCategoryKey = sharedPreferences.getString("mainCategoryKey","null");
       //to get second level key
        sharedSubMainCategoryKey = sharedPreferences.getString("subMainCategoryKey","null");
        //to get third level key
        sharedSubSubMainCategoryKey = sharedPreferences.getString("subMainCategoryKey","null");

        //Toast.makeText(this, "SHredKey"+sharedMainCategoryKey, Toast.LENGTH_SHORT).show();


        //this one works

        String categoryKey = getIntent().getStringExtra("categoryKey");
        String mainKey = getIntent().getStringExtra("mainkey");
        ArrayList<String> subkeys = getIntent().getStringArrayListExtra("subKeyArray");




        if(subkeys!=null && subkeys.contains(categoryKey)){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("subjects").child(sharedMainCategoryKey).child("subcategories").child(categoryKey);
            getdata(databaseReference);

        }else{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("subjects").child(sharedMainCategoryKey);
            getdata(databaseReference);
        }







    }
    public void getdata(DatabaseReference databaseReference){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("subcategories")) {
                    // If subcategories exist
                    DataSnapshot subcategoriesSnapshot = dataSnapshot.child("subcategories");
                    for (DataSnapshot subcategorySnapshot : subcategoriesSnapshot.getChildren()) {
                        for (DataSnapshot questionSnapshot : subcategorySnapshot.child("questions").getChildren()) {
                            String question = questionSnapshot.child("questionText").getValue(String.class);
                            String answer = questionSnapshot.child("answerText").getValue(String.class);
                            // Create a QuestionModel object and add it to your list
                            QuestionModel questionModel = new QuestionModel();
                            questionModel.setQuestion(question);
                            questionModel.setAnswer(answer);
                            questionModels.add(questionModel);
                        }
                    }
                } else {
                    // If no subcategories exist, retrieve questions and answers directly under the category
                    for (DataSnapshot questionSnapshot : dataSnapshot.child("questions").getChildren()) {
                        String question = questionSnapshot.child("questionText").getValue(String.class);
                        String answer = questionSnapshot.child("answerText").getValue(String.class);
                        // Create a QuestionModel object and add it to your list
                        QuestionModel questionModel = new QuestionModel();
                        questionModel.setQuestion(question);
                        questionModel.setAnswer(answer);
                        questionModels.add(questionModel);


                    }
                }

                // Perform any necessary operations with the questionModels list
                // For example, you can update your RecyclerView adapter here
                // adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                questionsAdapter = new QuestionsAdapter(questionModels, QuestionsActivity.this);
                recyclerView.setAdapter(questionsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void extramethod(){
        String categoryKey = getIntent().getStringExtra("categoryKey");
        ArrayList<QuestionModel> questionModels = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("subjects");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean categoryKeyFound = false;

                // Check if the categoryKey is a direct child under "subjects"
                if (dataSnapshot.hasChild(categoryKey)) {
                    DataSnapshot categorySnapshot = dataSnapshot.child(categoryKey);
                    if (categorySnapshot.hasChild("subcategories")) {
                        // Redirect to subcategory activity
                        Intent intent = new Intent(QuestionsActivity.this, SubcategoriesActivity.class);
                        intent.putExtra("categoryKey", categoryKey);
                        startActivity(intent);
                        finish();
                        return;
                    } else {
                        // Populate question models
                        for (DataSnapshot questionSnapshot : categorySnapshot.child("questions").getChildren()) {
                            String question = questionSnapshot.child("questionText").getValue(String.class);
                            String answer = questionSnapshot.child("answerText").getValue(String.class);
                            // Create a QuestionModel object and add it to your list
                            QuestionModel questionModel = new QuestionModel();
                            questionModel.setQuestion(question);
                            questionModel.setAnswer(answer);
                            questionModels.add(questionModel);
                        }
                        // Set categoryKeyFound to true
                        categoryKeyFound = true;
                    }
                }

                // If the categoryKey is not a direct child under "subjects" or no subcategories exist under the category
                if (!categoryKeyFound) {
                    // Iterate through the subjects and their subcategories
                    for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                        if (subjectSnapshot.hasChild("subcategories")) {
                            DataSnapshot subcategoriesSnapshot = subjectSnapshot.child("subcategories");
                            for (DataSnapshot subcategorySnapshot : subcategoriesSnapshot.getChildren()) {
                                if (subcategorySnapshot.getKey().equals(categoryKey)) {
                                    // Redirect to subcategory activity
                                    Intent intent = new Intent(QuestionsActivity.this, SubcategoriesActivity.class);
                                    intent.putExtra("categoryKey", categoryKey);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            }
                        }
                    }
                }

                // Display questions

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        questionsAdapter = new QuestionsAdapter(questionModels, QuestionsActivity.this);
        recyclerView.setAdapter(questionsAdapter);


    }
}
