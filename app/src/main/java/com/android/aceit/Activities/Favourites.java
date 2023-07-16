package com.android.aceit.Activities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.android.aceit.Adapters.FavouriteAdapter;
import com.android.aceit.Adapters.QuestionsAdapter;
import com.android.aceit.DatabaseHelper;
import com.android.aceit.Models.QuestionModel;
import com.android.aceit.R;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    FavouriteAdapter favouriteAdapter;
    ArrayList<QuestionModel> favouritesArrraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);


        recyclerView = (RecyclerView) findViewById(R.id.rvFavourites);

        databaseHelper = new DatabaseHelper(this);

        //initn arraylist
        favouritesArrraylist = new ArrayList<>();


        Toast.makeText(this, "on create", Toast.LENGTH_SHORT).show();

       //setrup rv
        setupRecyclerView();
        // Populate the RecyclerView
        populateRecyclerView();


    }
    //to set rv on create
    private void setupRecyclerView() {
        // Set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Initialize Adapter
        favouriteAdapter = new FavouriteAdapter(favouritesArrraylist, this);

        // Set the Adapter to the RecyclerView
        recyclerView.setAdapter(favouriteAdapter);
    }

    private void populateRecyclerView() {


        //names top pass as column nade in getclomunindex below
        String q_id_columnName = "question_id";
        String q_columnName = "question";
        String a_columnName = "answer";

        // Get the cursor containing favorite questions
        Cursor cursor = databaseHelper.getAllFavoriteQuestions();

        //to check if there is data in cursor if yes this would execute
        if(cursor!=null && cursor.moveToFirst()){

            //here it will get the index of the row of cursor that is there to get the index of columgn
            //so that the data is not change
            //in shorts aa check krse k je row mali eni har column ni index ne store krse variable mu
            int questionIdIndex = cursor.getColumnIndex(q_id_columnName);
            int questionIndex = cursor.getColumnIndex(q_columnName);
            int answerIndex = cursor.getColumnIndex(a_columnName);

            //here it will ce=heck for the indx not -1

            if (questionIdIndex != -1 && questionIndex != -1 && answerIndex != -1) {

                //agar -1 nthi to aa execute thathe ne value ne save krse ne arraylist mu store krse jyr sudh
                //last element na hoy tyr sudhiu ne
                ///jevu j pate etle adapter je oncreate mu se ena update krse
                    do {
                        String questionId = cursor.getString(questionIdIndex);
                        String question = cursor.getString(questionIndex);
                        String answer = cursor.getString(answerIndex);

                        // Create a FavoriteQuestion object and add it to the list
                        QuestionModel favoriteQuestion = new QuestionModel(question, answer, questionId);
                        favouritesArrraylist.add(favoriteQuestion);
                    } while (cursor.moveToNext());

                    //to set the data to initial values
                    favouriteAdapter.notifyDataSetChanged();


            }

            cursor.close();
        }
        //modify the below to display the error message if not
        else{
            //atle etla mate notify kem k last element b remove thy jay to aa execute thahe ne atle add krvu pde
            //to set data to emtpy if everything is removed
            favouriteAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Please add favorites to access them here ", Toast.LENGTH_SHORT).show();
        }



    }



    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(this, "on resume", Toast.LENGTH_SHORT).show();

        // Clear the ArrayList before repopulating
        favouritesArrraylist.clear();

        // Populate the RecyclerView
        populateRecyclerView();
    }


}

