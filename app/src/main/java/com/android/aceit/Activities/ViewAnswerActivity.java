package com.android.aceit.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aceit.DatabaseHelper;
import com.android.aceit.Models.QuestionModel;
import com.android.aceit.R;
import com.google.android.material.snackbar.Snackbar;

public class ViewAnswerActivity extends AppCompatActivity {
    TextView questiontv, answertv;
    Button AddtoFavouritebtn,updatebutton,share;
    private DatabaseHelper databaseHelper;
    String question,answer,qid;
    ActionBar actionBar;
    boolean isFavorite = false;
    private Menu menu; // Store the menu as a field


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answer);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            actionBar.setTitle("Content");
        }
        //Toast.makeText(this, "View answser activity ", Toast.LENGTH_SHORT).show();

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);



        questiontv = (TextView) findViewById(R.id.tvViewQuestion);
        answertv = (TextView) findViewById(R.id.tvViewAnswer);
        //refernce tp button
        updatebutton = (Button) findViewById(R.id.btnUpdate);







        // Get the question and answer from the intent extras
        question = getIntent().getStringExtra("question");
      answer = getIntent().getStringExtra("answer");
     qid = getIntent().getStringExtra("qid");

        // Set the question and answer text
        questiontv.setText(question);
        answertv.setText(answer);




// Check if the question is in favorites
        isFavorite = databaseHelper.isQuestionInFavorites(qid);




        //update question

        updateFavouriteQuestion();
















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

  public void addToFavouriteMethod(){
      // Check if the question is in favorites
      boolean isFavorite = databaseHelper.isQuestionInFavorites(qid);
      //to update the icon
      // Update the menu item icon based on the current favourite state



      boolean result = false;
      if (isFavorite) {
          // Remove the question from favorites
          result =  databaseHelper.removeFavoriteQuestion(qid);
          if(result){
             // AddtoFavouritebtn.setText("Add to Favorites");
              Snackbar.make(ViewAnswerActivity.this.findViewById(android.R.id.content),
                      "Removed from Favourites", Snackbar.LENGTH_SHORT).show();
              updatebutton.setVisibility(View.GONE);
          }
          else{
              Snackbar.make(ViewAnswerActivity.this.findViewById(android.R.id.content),
                      "Error Deleting", Snackbar.LENGTH_SHORT).show();
              return;

          }




      } else {
          result = databaseHelper.addFavoriteQuestion(qid,question,answer);


          if(result){
              //AddtoFavouritebtn.setText("Remove from Favorites");

              Snackbar.make(ViewAnswerActivity.this.findViewById(android.R.id.content),
                      "Added to Favourites", Snackbar.LENGTH_SHORT).show();
          }else{
              Snackbar.make(ViewAnswerActivity.this.findViewById(android.R.id.content),
                      "Error Adding", Snackbar.LENGTH_SHORT).show();

              return;
          }


      }
      // Update the menu item icon based on the current favourite state
      updateOptionsMenuIcons(!isFavorite);
  }

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
                               Snackbar.make(ViewAnswerActivity.this.findViewById(android.R.id.content),
                                       "Error While Updating", Snackbar.LENGTH_SHORT).show();                           }
                        }
                    });
                }
            }
        }



    }

    private void shareContent(String question, String answer) {
        String shareText = "Question: \n " + question + "\nAnswer: \n" + answer +
                "\n\nShared via *ACE IT*\nComing Soon on Play store";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        // Check if there are any apps available to handle the share intent
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        } else {
            Toast.makeText(this, "No apps available to share", Toast.LENGTH_SHORT).show();
        }
    }


    //menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_answer_menu, menu);
        this.menu = menu; // Store the menu for later access
        updateOptionsMenuIcons(isFavorite);

        return true;
    }
    //to update
    private void updateOptionsMenuIcons(Boolean isFavorite) {
        if (menu == null) return; // Make sure the menu is not null

        MenuItem favouriteMenuItem = menu.findItem(R.id.action_favorite);
        if (isFavorite) {
            favouriteMenuItem.setIcon(R.drawable.ic_baseline_favorite_24);
        } else {
            favouriteMenuItem.setIcon(R.drawable.ic_baseline_favorite_border_24);
        }
    }


    //click events on menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.action_share) {
            // Handle the share action (e.g., open a share dialog)
            shareContent(question,answer);
           // Toast.makeText(this, "share clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.action_favorite) {
            // Handle the favorite action (e.g., add/remove from favorites)
            addToFavouriteMethod();
            //Toast.makeText(this, "Favourite clicked", Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //to close this activity
    @Override
    public boolean onSupportNavigateUp() {


        finish();
        return false;
    }

}


