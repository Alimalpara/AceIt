package com.android.aceit.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aceit.DatabaseHelper;
import com.android.aceit.Models.QuestionModel;
import com.android.aceit.R;

public class ViewAnswerActivity extends AppCompatActivity {
    TextView questiontv, answertv;
    Button AddtoFavouritebtn,updatebutton;
    private DatabaseHelper databaseHelper;
    String question,answer,qid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answer);

        Toast.makeText(this, "View answser activity ", Toast.LENGTH_SHORT).show();

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);



        questiontv = (TextView) findViewById(R.id.tvViewQuestion);
        answertv = (TextView) findViewById(R.id.tvViewAnswer);
        //refernce tp button
        AddtoFavouritebtn = findViewById(R.id.btnaddtoFavourite);
        updatebutton = (Button) findViewById(R.id.btnUpdate);



        // Get the question and answer from the intent extras
        question = getIntent().getStringExtra("question");
      answer = getIntent().getStringExtra("answer");
     qid = getIntent().getStringExtra("qid");

        // Set the question and answer text
        questiontv.setText(question);
        answertv.setText(answer);




// Check if the question is in favorites
        boolean isFavorite = databaseHelper.isQuestionInFavorites(qid);


        //initial set
        initialSet(isFavorite);

        //update question

        updateFavouriteQuestion();








        AddtoFavouritebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //databaseHelper.addFavoriteQuestion(qid,question,answer);
               /* Boolean test = isQuestionInFavorites(qid);
                Toast.makeText(ViewAnswerActivity.this, "test" + test, Toast.LENGTH_SHORT).show();*/


                // Check if the question is in favorites
                boolean isFavorite = databaseHelper.isQuestionInFavorites(qid);
                boolean result = false;
                if (isFavorite) {
                    // Remove the question from favorites
                   result =  databaseHelper.removeFavoriteQuestion(qid);
                   if(result){
                       AddtoFavouritebtn.setText("Add to Favorites");
                       updatebutton.setVisibility(View.GONE);
                   }
                   else{
                       Toast.makeText(ViewAnswerActivity.this, " Error deleting", Toast.LENGTH_SHORT).show();

                   }




                } else {
                   result = databaseHelper.addFavoriteQuestion(qid,question,answer);


                    if(result){
                        AddtoFavouritebtn.setText("Remove from Favorites");
                    }
                    else{
                        Toast.makeText(ViewAnswerActivity.this, " Error Adding", Toast.LENGTH_SHORT).show();

                    }


            }
        }



    });
    }

    private void initialSet(Boolean isFavorite){
        // Set the initial button text based on the question's favorite status
        if (isFavorite) {
            AddtoFavouritebtn.setText("Remove from Favorites");
        } else {
            AddtoFavouritebtn.setText("Add to Favorites");
        }
    }

    //local method
/*
    private boolean isQuestionInFavorites(String questionId) {
        String tableName = "favorite_questions";
        String columnName = "question_id";

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{questionId});

        boolean isInFavorites = false;
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isInFavorites = (count > 0);
        }

        if (cursor != null) {
            cursor.close();
        }
        databaseHelper.close();

        return isInFavorites;
    }
*/


    public void updateFavouriteQuestion(){
        boolean isfavourite = databaseHelper.isQuestionInFavorites(qid);

        if (isfavourite) {
            QuestionModel favoriteQuestion = databaseHelper.getQuestionFromFavorites(qid);
            if (favoriteQuestion != null) {
                String favoriteQuestionText = favoriteQuestion.getQuestion();
                String favoriteAnswerText = favoriteQuestion.getAnswer();

                if (!question.equals(favoriteQuestionText) || !answer.equals(favoriteAnswerText)) {
                    // Question is different from the favorite in the database
                    // Show the update button and set OnClickListener
                    updatebutton.setVisibility(View.VISIBLE);
                    updatebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Call the method to update the favorite question in the database
                           boolean isupdated=  databaseHelper.updateFavoriteQuestion(qid, question, answer);
                           if(isupdated){
                               updatebutton.setVisibility(View.GONE);
                           }else{
                               Toast.makeText(ViewAnswerActivity.this, "Error while updating", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                }
            }
        }



    }

}


