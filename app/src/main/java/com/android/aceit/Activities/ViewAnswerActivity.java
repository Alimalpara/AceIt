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
import com.android.aceit.R;

public class ViewAnswerActivity extends AppCompatActivity {
    TextView questiontv, answertv;
    Button AddtoFavouritebtn;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answer);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);



        questiontv = (TextView) findViewById(R.id.tvViewQuestion);
        answertv = (TextView) findViewById(R.id.tvViewAnswer);

        // Get the question and answer from the intent extras
        String question = getIntent().getStringExtra("question");
        String answer = getIntent().getStringExtra("answer");
        String qid = getIntent().getStringExtra("qid");

        // Set the question and answer text
        questiontv.setText(question);
        answertv.setText(answer);




// Check if the question is in favorites
        boolean isFavorite = isQuestionInFavorites(qid);

        //refernce tp button
        AddtoFavouritebtn = findViewById(R.id.btnaddtoFavourite);

        //initial set
        initialSet(isFavorite);








        AddtoFavouritebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //databaseHelper.addFavoriteQuestion(qid,question,answer);
               /* Boolean test = isQuestionInFavorites(qid);
                Toast.makeText(ViewAnswerActivity.this, "test" + test, Toast.LENGTH_SHORT).show();*/


                // Check if the question is in favorites
                boolean isFavorite = isQuestionInFavorites(qid);
                boolean result = false;
                if (isFavorite) {
                    // Remove the question from favorites
                   result =  databaseHelper.removeFavoriteQuestion(qid);
                   if(result){
                       AddtoFavouritebtn.setText("Add to Favorites");
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



}


