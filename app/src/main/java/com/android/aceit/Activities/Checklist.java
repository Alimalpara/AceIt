package com.android.aceit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.aceit.Adapters.ChecklistAdapter;
import com.android.aceit.DatabaseHelper;
import com.android.aceit.Models.ChecklistItem;
import com.android.aceit.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class Checklist extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ChecklistAdapter checklistAdapter;
    ArrayList<String> arrayList;

    private DatabaseHelper dbHelper;
    private ArrayList<ChecklistItem> checklistItems;


    ConstraintLayout bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        dbHelper = new DatabaseHelper(this);





        recyclerView = (RecyclerView) findViewById(R.id.rvChecklistRecycler);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fbbtnChecklist);
        arrayList = new ArrayList<>();

// Add default data to the SQLite database
       // addDefaultData();

        // Retrieve checklist items from the database
        retrieveChecklistItems();








        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checklist_edit_add_Fragment fragment = Checklist_edit_add_Fragment.newInstance("FAB", null);
                //showBottomFragment();
                showBottomSheet();
            }
        });



    }


    private void showBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, com.google.android.material.R.style.Theme_Design_BottomSheetDialog);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_checklist, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        //click event on
        EditText etBottomsheet = bottomSheetView.findViewById(R.id.etBottomsheet);
        Button btnBottomSheet = bottomSheetView.findViewById(R.id.btnBottomSheet);

        btnBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = etBottomsheet.getText().toString();
                // Generate a unique ID using timestamp and date
                Long itemId = generateUniqueItemId();
                ChecklistItem checklistItem= new ChecklistItem(itemId,value,false);
                boolean isInserted = dbHelper.addChecklistItem(itemId, value, false); // Assuming isChecked is initially set to false

                if (isInserted) {
                    // Item inserted successfully, update the RecyclerView

                   checklistItems.add(checklistItem);
                   checklistAdapter.notifyItemInserted(checklistItems.size()-1);

                } else {
                    // Failed to insert the item
                    Toast.makeText(Checklist.this, "Failed to add item to database", Toast.LENGTH_SHORT).show();
                }



                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomSheetDialog.show();
    }

    private void addDefaultData() {
        // Add default checklist items to the database
        dbHelper.addChecklistItem(generateUniqueItemId(), "Item 1", false);
        dbHelper.addChecklistItem(generateUniqueItemId(), "Item 2", true);
        dbHelper.addChecklistItem(generateUniqueItemId(), "Item 3", false);
    }
    private void retrieveChecklistItems() {
        // Retrieve checklist items from the database
        checklistItems = dbHelper.getAllChecklistItems();

        // Retrieve checklist items from the database


        // Set up the RecyclerView adapter with the retrieved checklist items
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        checklistAdapter = new ChecklistAdapter(Checklist.this, checklistItems);
        recyclerView.setAdapter(checklistAdapter);
    }

    private long generateUniqueItemId() {
        long currentTime = System.currentTimeMillis();
        Random random = new Random();
        int randomValue = random.nextInt(10000); // Adjust the range as per your requirement
        return currentTime + randomValue;
    }
    private void refreshAdapter() {
        // Retrieve the latest data from the database
        checklistItems = dbHelper.getAllChecklistItems();

        // Update the adapter with the new data
        checklistAdapter.setData(checklistItems);
        int newItemPosition = checklistItems.size() - 1;
        checklistAdapter.notifyItemInserted(newItemPosition);
    }


}