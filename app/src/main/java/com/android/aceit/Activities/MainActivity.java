package com.android.aceit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aceit.Models.ChildData;
import com.android.aceit.Adapters.MainviewAdapter;
import com.android.aceit.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    RecyclerView rv;

    MainviewAdapter mainviewAdapter;

    ArrayList<ChildData> childDataArrayList;
    Button favourite, help,cv_create, ref_create;
    EditText editText, edittext2;
    Button savebtn,btnResumeUpdate,btnScheduleInterviewHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rvmain);
        initViews();
        childDataArrayList = new ArrayList<>();





// Set the OnFocusChangeListener for the EditText



        //click lst4ent ot redirect
        favourite.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Favourites.class)));


        //checklist
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Checklist.class));
            }
        });


        //letter creation
        cv_create.setOnClickListener(v -> {
            // Create an Intent to start the next activity
            Intent intent = new Intent(MainActivity.this, LettersList.class);
            //passing to activity letterlist then to adapterlitst then to activity
            intent.putExtra("Type_of_letter", 1); // Pass the ID as an extra data with the key "ITEM_ID"
            startActivity(intent);

        });
        ref_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LettersList.class);
                intent.putExtra("Type_of_letter", 2); // Pass the ID as an extra data with the key "ITEM_ID"
                startActivity(intent);

            }
        });

        //interview help Inter
        btnScheduleInterviewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InterviewHelp.class);
                intent.putExtra("helptype", 1); // Pass the ID as an extra data with the key "ITEM_ID"
                startActivity(intent);

            }
        });
        btnResumeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InterviewHelp.class);
                intent.putExtra("helptype", 2); // Pass the ID as an extra data with the key "ITEM_ID"
                startActivity(intent);

            }
        });


        //two methods combined savetoRv and  passinmg the json data
        savetoRV(getJSonFromDevice());

    }






        public void initViews(){
        favourite = (Button) findViewById(R.id.btnFavoutie);
        help = (Button) findViewById(R.id.btntoHelp);
        cv_create = (Button) findViewById(R.id.btncvCreate);
        ref_create = (Button) findViewById(R.id.btnReferenceCreate);
            btnScheduleInterviewHome = (Button) findViewById(R.id.btnScheduleInterviewHome);
            btnResumeUpdate = (Button) findViewById(R.id.btnResumeUpdate);



        }



















    public String getJSonFromDevice(){
     //   Toast.makeText(this, "Inside getjson ", Toast.LENGTH_SHORT).show();

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



    //new method
    public void savetoRV(String jsonData) {
        try {
            //Toast.makeText(this, "Inside rv", Toast.LENGTH_SHORT).show();
            JSONObject jsonObject = new JSONObject(jsonData);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {

                String childKey = keys.next();
                JSONObject childObject = jsonObject.getJSONObject(childKey);
                String childName = childObject.getString("name");
                Boolean subcatergory = hasSubcategoriesRecursive(childObject);


                ChildData childData = new ChildData(childName,childKey,subcatergory,childObject);
                childDataArrayList.add(childData);



              //  Toast.makeText(this, "CHild bame" + childName + "has sub "+ subcatergory, Toast.LENGTH_SHORT).show();




                
               // Toast.makeText(this, "Child Node: " + childKey + ", Name: " + childName, Toast.LENGTH_SHORT).show();

                // Perform any desired operations with the child node
            }

            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
            rv.setLayoutManager(gridLayoutManager);
            mainviewAdapter = new MainviewAdapter(MainActivity.this, childDataArrayList);
            rv.setAdapter(mainviewAdapter);
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
