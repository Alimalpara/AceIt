package com.android.aceit.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.aceit.Models.ChildData;
import com.android.aceit.R;
import com.android.aceit.Adapters.SubcategoryAdapter;
import com.android.aceit.Models.Subject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class SubcategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubcategoryAdapter adapter;
    private ArrayList<ChildData> childDataArrayList;
    ArrayList<Subject> subjects;
    ArrayList<String> subKeys;
    String mainCategory, categoryKey;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            actionBar.setTitle("Subcategories");
        }

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


    //to close this activity
    @Override
    public boolean onSupportNavigateUp() {


        finish();
        return false;
    }

}

