package com.android.aceit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.aceit.Adapters.FavouriteAdapter;
import com.android.aceit.Adapters.LettersAdapter;
import com.android.aceit.Models.ExpandableItem;
import com.android.aceit.R;

import java.util.ArrayList;

public class LettersList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ExpandableItem> expandableItems;
    LettersAdapter lettersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters_list);

      setRecyclerView();



    }

    public void setRecyclerView(){
        expandableItems = new ArrayList<>();
        recyclerView = findViewById(R.id.rvLetters);

        int itemId = getIntent().getIntExtra("Type_of_letter", 0);

        if (itemId == 1) {
            expandableItems = getCoverletters();
        } else if (itemId == 2) {
            expandableItems = getReferenceLetters();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lettersAdapter = new LettersAdapter(expandableItems, this,itemId);
        recyclerView.setAdapter(lettersAdapter);
    }

    // Method to retrieve cover letter data
    private ArrayList<ExpandableItem> getCoverletters() {
        ArrayList<ExpandableItem> coverLetterList = new ArrayList<>();

        // Add sample cover letter data
        ExpandableItem coverLetter1 = new ExpandableItem("Cover Letter 1", "Sample content for Cover Letter 1");
        ExpandableItem coverLetter2 = new ExpandableItem("Cover Letter 2", "Sample content for Cover Letter 2");
        ExpandableItem coverLetter3 = new ExpandableItem("Cover Letter 3", "Sample content for Cover Letter 3");

        coverLetterList.add(coverLetter1);
        coverLetterList.add(coverLetter2);
        coverLetterList.add(coverLetter3);

        return coverLetterList;
    }

    // Method to retrieve reference letter data
    private ArrayList<ExpandableItem> getReferenceLetters() {
        ArrayList<ExpandableItem> referenceLetterList = new ArrayList<>();

        // Add sample reference letter data
        ExpandableItem referenceLetter1 = new ExpandableItem("Reference Letter 1", "Sample content for Reference Letter 1");
        ExpandableItem referenceLetter2 = new ExpandableItem("Reference Letter 2", "Sample content for Reference Letter 2");
        ExpandableItem referenceLetter3 = new ExpandableItem("Reference Letter 3", "Sample content for Reference Letter 3");

        referenceLetterList.add(referenceLetter1);
        referenceLetterList.add(referenceLetter2);
        referenceLetterList.add(referenceLetter3);

        return referenceLetterList;
    }

}