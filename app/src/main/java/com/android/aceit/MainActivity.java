package com.android.aceit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    TextView tv;
    RecyclerView rv;
    ArrayList<Subject> subjects;
    MainviewAdapter mainviewAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> keys ;
    private boolean isSaveCompleted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rvmain);

       /* //saveJSontoDevice();
        if(saveJSontoDevice()){
            getJSonFromDevice();
        }else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }*/

        // Call saveJSontoDevice() to fetch data from Firebase and save it to a local file
        //saveJSontoDevice();

        savetoRV(getJSonFromDevice());
       // saveJSontoDevice();








        subjects = new ArrayList<>();

        //sharedprefernce to pass keys
         keys = new ArrayList<>();
        // Get the SharedPreferences instance
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



    }



    public void saveJSontoDevice() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("subjects");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> dataMap = (Map<String, Object>) dataSnapshot.getValue();
                if (dataMap != null) {
                    Gson gson = new Gson();
                    String jsonData = gson.toJson(dataMap);

                    try {
                        String fileName = "data.json";
                        File file = new File(getApplicationContext().getFilesDir(), fileName);

                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(jsonData);
                        fileWriter.flush();
                        fileWriter.close();

                        // File saved successfully
                        Toast.makeText(MainActivity.this, "Data is saved successfully.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        // Handle the exception
                        Toast.makeText(MainActivity.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No data available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseDatabase", "Failed to read value.", databaseError.toException());
                Toast.makeText(MainActivity.this, "Failed to fetch data from database.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public String getJSonFromDevice(){
        Toast.makeText(this, "Inside getjson ", Toast.LENGTH_SHORT).show();

        String jsonString ;
          try {
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
            jsonString = jsonData.toString();

            // Perform operations on the JSON data as needed

            // Close the input streams
            br.close();
            isr.close();
            fis.close();

              return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void rvMethod(){
        // Update the RecyclerView with the new data
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainviewAdapter = new MainviewAdapter(getApplicationContext(), subjects);
        rv.setAdapter(mainviewAdapter);
    }


    //new method
    public void savetoRV(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {

                String childKey = keys.next();
                JSONObject childObject = jsonObject.getJSONObject(childKey);
                String childName = childObject.getString("name");
                
                Toast.makeText(this, "Child Node: " + childKey + ", Name: " + childName, Toast.LENGTH_SHORT).show();

                // Perform any desired operations with the child node
            }
        } catch (JSONException e) {
            Log.e("MyApp", "JSONException occurred", e);
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
