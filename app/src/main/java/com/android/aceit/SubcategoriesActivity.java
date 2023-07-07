package com.android.aceit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubcategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubcategoryAdapter adapter;
    private ArrayList<ChildData> childDataArrayList;
    ArrayList<Subject> subjects;
    ArrayList<String> subKeys;
    String mainCategory, categoryKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);


        subjects = new ArrayList<>();

        String jsonObjectString = getIntent().getStringExtra("jsonObject");
       /* JSONObject jsonObject = null;
// Convert the JSON string to a JSONObject
        try {
            jsonObject = new JSONObject(jsonObjectString);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        assert jsonObject != null;*/
        savetoRV(jsonObjectString);


    }


    //new method
    public void savetoRV(String jsonData) {
        childDataArrayList = new ArrayList<>();
        try {


            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject subcategoriesObject = jsonObject.getJSONObject("subcategories");

            Iterator<String> keys = subcategoriesObject.keys();
            while (keys.hasNext()) {
                String subcategoryKey = keys.next();
                JSONObject subcategoryObject = subcategoriesObject.getJSONObject(subcategoryKey);

                // Perform any desired operations with the subcategory object
                // For example, get the name of the subcategory
                String subcategoryName = subcategoryObject.getString("name");
                Boolean isSubCategory = hasSubcategoriesRecursive(subcategoryObject);
                ChildData childData = new ChildData(subcategoryName,subcategoryKey,isSubCategory,subcategoryObject);
                childDataArrayList.add(childData);

                // ...

                // Toast.makeText(this, "Child Node: " + childKey + ", Name: " + childName, Toast.LENGTH_SHORT).show();

                // Perform any desired operations with the child node
            }
            recyclerView = findViewById(R.id.rvSubcategories);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SubcategoryAdapter(childDataArrayList, SubcategoriesActivity.this,mainCategory,subKeys);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            Log.e("MyApp", "JSONException occurred", e);
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Recursive method to check for subcategories
    private boolean hasSubcategoriesRecursive(JSONObject subjectObject) {
        if (subjectObject.has("subcategories")) {
            return true;
        }
        return false;
    }




}

