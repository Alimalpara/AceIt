package com.android.aceit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.aceit.Models.ChildData;
import com.android.aceit.Adapters.MainviewAdapter;
import com.android.aceit.R;
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
    Button favourite ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rvmain);
        childDataArrayList = new ArrayList<>();
        initViews();


        //click lst4ent ot redirect
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Favourites.class));
            }
        });



        //two methods combined savetoRv and  passinmg the json data
            savetoRV(getJSonFromDevice());

        }

        public void initViews(){
        favourite = (Button) findViewById(R.id.btnFavoutie);


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



    //new method
    public void savetoRV(String jsonData) {
        try {
            Toast.makeText(this, "Inside rv", Toast.LENGTH_SHORT).show();
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
            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            mainviewAdapter = new MainviewAdapter(getApplicationContext(), childDataArrayList);
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