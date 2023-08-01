package com.android.aceit.Activities;


/**
 * The SplashScreen activity is responsible for fetching and saving JSON data to the device.
 * It checks for internet connectivity and fetches the JSON data from the Firebase Database if available.
 * If internet is not available or the Firebase data is empty, it falls back to using local JSON data.
 * The JSON data is saved to a file on the device for further processing.
 * In case of exceptions during the saving process or when fetching data, appropriate error messages are displayed.
 */

// 1. Set the content view to the SplashScreen layout
// 2. Initialize the splash button and set its click listener to start the MainActivity
// 3. Call the saveJSontoDevice method to save JSON data to the device

// Method: saveJSontoDevice()
// 1. Check for internet connectivity using the ConnectivityManager
// 2. If internet connection is available:
//    a. Fetch the JSON data from the Firebase Database using a ValueEventListener
//    b. If the Firebase data exists and is not empty:
//       - Convert the data into a JSON string using Gson (Method: toJson())
//       - Save the JSON string to a file on the device (Method: saveJsonDataToFile())
//       - Show a success toast message
//    c. If the Firebase data is empty or not available:
//       - Use fallback JSON data obtained from the assets folder (Method: getFallbackJsonData())
//       - Save the fallback JSON data to a file on the device (Method: saveJsonDataToFile())
//       - Show an appropriate toast message
//    d. Handle exceptions during the saving process and fallback to saving fallback JSON data if needed (Method: saveFallbackJsonToDevice())
// 3. If no internet connection is available:
//    - Use fallback JSON data obtained from the assets folder (Method: getFallbackJsonData())
//    - Save the fallback JSON data to a file on the device (Method: saveJsonDataToFile())
//    - Show an appropriate toast message

// Method: saveFallbackJsonToDevice()
// 1. Attempt to save the JSON data to a file on the device (Method: saveJsonDataToFile())
// 2. If an exception occurs during the saving process, display an error toast message

// Method: getFallbackJsonData()
// 1. Open the fallback.json file in the assets folder
// 2. Read the content of the file and convert it into a JSON string
// 3. Return the fallback JSON data
// 4. Handle exceptions during the reading process and display an error toast message

// Method: saveJsonDataToFile()
// 1. Attempt to save the JSON data to a file on the device
// 2. If an exception occurs during the saving process, display an error toast message

// Method: showSnackbar()
// 1. Display a Snackbar with the given message
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.aceit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private Button splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splash = findViewById(R.id.btnsplash);

        // Check if the fallback.json file exists in internal storage
        if (!isFallbackJsonFileExists()) {
            // Copy the fallback.json file from assets to internal storage on first launch
            //Toast.makeText(this, "Copying fallback json", Toast.LENGTH_SHORT).show();
            copyFallbackJsonToStorage();
        }

        splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        });

        saveJSontoDevice();
    }

    public void saveJSontoDevice() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
           // Toast.makeText(this, "Internet is available", Toast.LENGTH_SHORT).show();
            // Internet connection is available
            databaseReference = FirebaseDatabase.getInstance().getReference().child("subjects");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String jsonData; // JSON data to be saved

                    if (dataSnapshot.exists()) {
                        //Toast.makeText(SplashScreen.this, "Online data is available", Toast.LENGTH_SHORT).show();
                        Map<String, Object> dataMap = (Map<String, Object>) dataSnapshot.getValue();
                        if (dataMap != null) {
                           // Toast.makeText(SplashScreen.this, "Online data is not empty", Toast.LENGTH_SHORT).show();
                            Gson gson = new Gson();
                            jsonData = gson.toJson(dataMap);

                            // Update the fallback JSON with the online JSON
                            updateFallbackJsonData(jsonData);
                        } else {
                           // Toast.makeText(SplashScreen.this, "Online data is empty", Toast.LENGTH_SHORT).show();

                            // Use fallback JSON if online JSON is empty or null
                            jsonData = getFallbackJsonData();
                        }
                    } else {
                       // Toast.makeText(SplashScreen.this, "Online data is not available", Toast.LENGTH_SHORT).show();

                        // Use fallback JSON if online JSON is not available
                        jsonData = getFallbackJsonData();
                    }

                    // Save JSON data to file
                    saveJsonDataToFile(jsonData);

                    // File saved successfully
                   // Toast.makeText(SplashScreen.this, "Data is saved successfully.", Toast.LENGTH_SHORT).show();

                    // Call the method to handle further processing with the saved JSON data
                    // handleSavedJsonData(jsonData);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FirebaseDatabase", "Failed to read value.", databaseError.toException());
                    //Toast.makeText(SplashScreen.this, "Failed to fetch data from database.", Toast.LENGTH_SHORT).show();

                    // Use fallback JSON if available
                    String jsonData = getFallbackJsonData();

                    // Save fallback JSON data to file
                    saveJsonDataToFile(jsonData);

                    // Fallback JSON data saved successfully
                   // Toast.makeText(SplashScreen.this, "Fallback data is saved successfully.", Toast.LENGTH_SHORT).show();

                    // Call the method to handle further processing with the saved JSON data
                    // handleSavedJsonData(jsonData);
                }
            });
        } else {
           // Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            // No internet connection
            String fallbackJsonData = getFallbackJsonData();

            // Save fallback JSON data to file
            saveJsonDataToFile(fallbackJsonData);

            // Fallback JSON data saved successfully
           // Toast.makeText(SplashScreen.this, "Fallback data is saved successfully.", Toast.LENGTH_SHORT).show();

            // Call the method to handle further processing with the saved JSON data
            // handleSavedJsonData(fallbackJsonData);
        }
    }

    private void saveJsonDataToFile(String jsonData) {
        try {
            String fileName = "data.json";
            File file = new File(getApplicationContext().getFilesDir(), fileName);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonData.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            // Handle the exception
           /// Toast.makeText(SplashScreen.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void updateFallbackJsonData(String updatedJsonData) {
        try {
            File file = new File(getApplicationContext().getFilesDir(), "fallback.json");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            String currentFallbackJsonData = stringBuilder.toString();

            if (!currentFallbackJsonData.equals(updatedJsonData)) {
                // Only update the fallback JSON if it has changed
              //  Toast.makeText(this, "updating the fallback", Toast.LENGTH_SHORT).show();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(updatedJsonData.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            // Handle the exception
           // Toast.makeText(SplashScreen.this, "Failed to update fallback data.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String getFallbackJsonData() {
        try {
            File file = new File(getApplicationContext().getFilesDir(), "fallback.json");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isFallbackJsonFileExists() {
        File file = new File(getApplicationContext().getFilesDir(), "fallback.json");
        return file.exists();
    }

    private void copyFallbackJsonToStorage() {
        try {
            InputStream inputStream = getAssets().open("fallback.json");
            File outputFile = new File(getApplicationContext().getFilesDir(), "fallback.json");

            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//working

   /* public void saveJSontoDevice() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(this, "internt yes ", Toast.LENGTH_SHORT).show();
            // Internet connection is available
            databaseReference = FirebaseDatabase.getInstance().getReference().child("subjects");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String jsonData; // JSON data to be saved

                    if (dataSnapshot.exists()) {
                        Toast.makeText(SplashScreen.this, "snapshot  yes ", Toast.LENGTH_SHORT).show();
                        Map<String, Object> dataMap = (Map<String, Object>) dataSnapshot.getValue();
                        if (dataMap != null) {
                            Toast.makeText(SplashScreen.this, "Online", Toast.LENGTH_SHORT).show();
                            Gson gson = new Gson();
                            jsonData = gson.toJson(dataMap);
                        } else {
                            Toast.makeText(SplashScreen.this, "Offline: Empty Online Data", Toast.LENGTH_SHORT).show();

                            // Use fallback JSON if online JSON is empty or null
                            jsonData = getFallbackJsonData();
                        }
                    } else {
                        Toast.makeText(SplashScreen.this, "Offline: No Online Data", Toast.LENGTH_SHORT).show();

                        // Use fallback JSON if online JSON is not available
                        jsonData = getFallbackJsonData();
                    }

                    try {
                        String fileName = "data.json";
                        File file = new File(getApplicationContext().getFilesDir(), fileName);

                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(jsonData);
                        fileWriter.flush();
                        fileWriter.close();

                        // File saved successfully
                        Toast.makeText(SplashScreen.this, "Data is saved successfully.", Toast.LENGTH_SHORT).show();

                        // Call the method to handle further processing with the saved JSON data

                    } catch (IOException e) {
                        // Handle the exception
                        Toast.makeText(SplashScreen.this, "Failed to save data exec.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                        // Use fallback JSON if saving fails
                        jsonData = getFallbackJsonData();

                        saveFallbackJsonToDevice(jsonData);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FirebaseDatabase", "Failed to read value.", databaseError.toException());
                    Toast.makeText(SplashScreen.this, "Failed to fetch data from database.", Toast.LENGTH_SHORT).show();

                    // Use fallback JSON if available
                    String jsonData = getFallbackJsonData();

                    saveFallbackJsonToDevice(jsonData);
                }
            });
        } else {
            Toast.makeText(this, "internet no", Toast.LENGTH_SHORT).show();
            // No internet connection
            String fallbackJsonData = getFallbackJsonData();

            saveFallbackJsonToDevice(fallbackJsonData);
        }
    }

    private void saveFallbackJsonToDevice(String jsonData) {
        try {
            String fileName = "data.json";
            File file = new File(getApplicationContext().getFilesDir(), fileName);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonData);
            fileWriter.flush();
            fileWriter.close();

            // File saved successfully
            Toast.makeText(SplashScreen.this, "Fallback data is saved successfully.", Toast.LENGTH_SHORT).show();

            // Call the method to handle further processing with the saved JSON data
        } catch (Exception ex) {
            // Handle the exception
            Toast.makeText(SplashScreen.this, "Failed to save fallback data." +ex.toString(), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
//new





    private String getFallbackJsonData() {
        // Assuming "fallback.json" is stored in the "assets" folder

        try {
            // Get the AssetManager instance
            AssetManager assetManager = getAssets();

            // Open the fallback.json file
            InputStream inputStream = assetManager.open("fallback.json");

            // Read the content of the file
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // Get the JSON string from the file
            String fallbackJsonData = stringBuilder.toString();

            // Process the fallback JSON data as required
            // For example, you can parse the JSON and use it as fallback data

            // Close the input stream and buffer reader
            bufferedReader.close();
            inputStream.close();
            Toast.makeText(this, "SUccesssfull in opening the assest", Toast.LENGTH_SHORT).show();

            return fallbackJsonData;
        } catch (Exception e) {
            // Handle the exception if the file cannot be read
            Toast.makeText(this, "unSUccesssfull in opening the assest", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return null;
    }*/