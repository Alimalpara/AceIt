package com.android.aceit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SubcategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubcategoryAdapter adapter;
    private ArrayList<String> subcategoryList;
    ArrayList<Subject> subjects;
    ArrayList<String> subKeys;
    String mainCategory, categoryKey;

    private DatabaseReference databaseReference;
    String sharedMainCategoryKey,sharedSubMainCategoryKey,sharedSubSubMainCategoryKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);

        recyclerView = findViewById(R.id.rvSubcategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjects = new ArrayList<>();
       /* try {
            File file = new File(getApplicationContext().getFilesDir(), "data.json");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonData.append(line);
            }

            // Now you have the JSON data as a string
            String jsonString = jsonData.toString();

            // Perform operations on the JSON data as needed

            // Close the input streams
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //to get sharedPreference for mainCategoryKey
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        sharedMainCategoryKey = sharedPreferences.getString("mainCategoryKey","null");
        sharedSubMainCategoryKey = sharedPreferences.getString("subMainCategoryKey","null");
        sharedSubSubMainCategoryKey = sharedPreferences.getString("subsubMainCategoryKey","null");


        // Retrieve the category name from the intent
        categoryKey = getIntent().getStringExtra("categoryKey");

        mainCategory = categoryKey;
        Boolean  subPath = getIntent().getBooleanExtra("isSubPath",false);


        if(subPath){
            databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("subjects").child(sharedMainCategoryKey).child("subcategories").child(sharedSubMainCategoryKey).child("subcategories");
            getData(databaseReference);
        }else{
            databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("subjects").child(sharedMainCategoryKey).child("subcategories");
            getData(databaseReference);
        }














        // Initialize Firebase database reference
// Initialize Firebase database reference




        // Initialize subcategory list
        subcategoryList = new ArrayList<>();
        subKeys = new ArrayList<>();





    }

    public void getData(DatabaseReference databaseReference){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot subcategorySnapshot : dataSnapshot.getChildren()) {
                    String subcategoryName = subcategorySnapshot.child("name").getValue(String.class);
                    subcategoryList.add(subcategoryName);
                    String subjectKey = subcategorySnapshot.getKey();
                    String subjectName = subcategorySnapshot.child("name").getValue(String.class);
                    boolean hasSubcategories = hasSubcategoriesRecursive(subcategorySnapshot);
                    Subject subject = new Subject( subjectName,subjectKey, hasSubcategories);

                    subjects.add(subject);
                    subKeys.add(subjectKey);
                }

                // Set up the adapter with the subcategory list
                adapter = new SubcategoryAdapter(subjects, SubcategoriesActivity.this,mainCategory,subKeys);
                recyclerView.setAdapter(adapter);
                Toast.makeText(SubcategoriesActivity.this, "Got data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SubcategoriesActivity.this, "Failed to fetch subcategories", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Recursive method to check for subcategories
    private boolean hasSubcategoriesRecursive(DataSnapshot subjectSnapshot) {
        if (subjectSnapshot.hasChild("subcategories")) {
            return true;
        } else {
            for (DataSnapshot childSnapshot : subjectSnapshot.getChildren()) {
                boolean hasSubcategories = hasSubcategoriesRecursive(childSnapshot);
                if (hasSubcategories) {
                    return true;
                }
            }
        }
        return false;
    }
}

