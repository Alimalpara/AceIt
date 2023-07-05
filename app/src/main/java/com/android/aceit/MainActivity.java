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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aceit.MainviewAdapter;
import com.android.aceit.R;
import com.android.aceit.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    TextView tv;
    RecyclerView rv;
    ArrayList<Subject> subjects;
    MainviewAdapter mainviewAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> keys ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rvmain);

        getJSon();






        subjects = new ArrayList<>();

        //sharedprefernce to pass keys
         keys = new ArrayList<>();
        // Get the SharedPreferences instance
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



    }

    public void getJSon(){

        databaseReference = FirebaseDatabase.getInstance().getReference().child("subjects");
        // Read the data from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Data retrieved successfully
                // Clear the existing subjects list
                //subjects file daat
                String jsonData = dataSnapshot.getValue().toString();
                try {
                    // Choose a suitable file name and location
                    String fileName = "data.json";
                    File file = new File(getApplicationContext().getFilesDir(), fileName);

                    // Write the JSON data to the file
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(jsonData);
                    fileWriter.flush();
                    fileWriter.close();

                    // File saved successfully
                } catch (IOException e) {
                    // Handle the exception
                }

                subjects.clear();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while fetching data
                Log.e("FirebaseDatabase", "Failed to read value.", databaseError.toException());
                Toast.makeText(MainActivity.this, "Failed to fetch data from database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void rvMethod(){
        // Update the RecyclerView with the new data
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainviewAdapter = new MainviewAdapter(getApplicationContext(), subjects);
        rv.setAdapter(mainviewAdapter);
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
