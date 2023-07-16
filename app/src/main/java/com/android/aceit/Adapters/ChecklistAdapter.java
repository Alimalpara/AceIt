package com.android.aceit.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.aceit.DatabaseHelper;
import com.android.aceit.Models.ChecklistItem;
import com.android.aceit.MyViewHolder;
import com.android.aceit.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ChecklistAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    ArrayList<ChecklistItem> checklistItems ;
    boolean isEditMode = false;
DatabaseHelper databaseHelper;



    public ChecklistAdapter(Context context, ArrayList<ChecklistItem> checklistItems) {
        this.context = context;
        this.checklistItems = checklistItems;
        databaseHelper = new DatabaseHelper(context);


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checklistrvrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int adapterPosition = holder.getAdapterPosition();
        ChecklistItem item = checklistItems.get(adapterPosition);

        holder.checklist_textview.setText(checklistItems.get(adapterPosition).getText());


       /* holder.checklist_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    holder.checklist_textview.setPaintFlags(holder.checklist_textview.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.checklist_textview.setText(checklist.get(adapterPosition).getText());
                    holder.checklist_textview.setPaintFlags(holder.checklist_textview.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }

            }

        });*/
        // Set the checkbox state based on the isChecked property
        holder.checklist_checkBox.setChecked(item.isChecked());

        // Set the strike-through text if the item is checked
        if (item.isChecked()) {
            holder.checklist_textview.setPaintFlags(holder.checklist_textview.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.checklist_textview.setPaintFlags(holder.checklist_textview.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // Set the checkbox listener
        holder.checklist_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the isChecked property of the checklist item
                item.setChecked(isChecked);
                // Update the item in the database
                databaseHelper.updateChecklistItemCheckedStatus(item.getId(), isChecked);

                // Update the strike-through text based on the checkbox state
                if (isChecked) {
                    //Toast.makeText(context, "Modified check ", Toast.LENGTH_SHORT).show();
                    holder.checklist_textview.setPaintFlags(holder.checklist_textview.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                   // Toast.makeText(context, "Modified uncheck ", Toast.LENGTH_SHORT).show();
                    holder.checklist_textview.setPaintFlags(holder.checklist_textview.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.checkList_imagebtn.setVisibility(View.VISIBLE);
                return false;
            }
        });
        holder.checklist_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemText = checklistItems.get(adapterPosition).getText();
                openBottomSheetDialog(itemText,item.getId(),holder);
            }
        });




        holder.checkList_imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              boolean isDeleted =   databaseHelper.deleteChecklistItem(item.getId());

                if (isDeleted) {
                    // Remove the item from the data source
                    checklistItems.remove(holder.getAdapterPosition());
                    // Notify the adapter of the item removal
                    notifyItemRemoved(holder.getAdapterPosition());
                    // Notify the adapter of the range of items that have changed after the deletion
                    notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());

                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT).show();
                }

                holder.checkList_imagebtn.setVisibility(View.GONE);
            }
        });

    }




    @Override
    public int getItemCount() {
        return checklistItems.size();
    }

    private void openBottomSheetDialog(String itemText, long id, MyViewHolder holder) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, com.google.android.material.R.style.Theme_Design_BottomSheetDialog);
        View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_checklist, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        EditText editText = bottomSheetView.findViewById(R.id.etBottomsheet);
        editText.setText(itemText);

        Button button = bottomSheetView.findViewById(R.id.btnBottomSheet);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedText = editText.getText().toString();

                // Check if the edited text is different from the current text
                if (!editedText.equals(checklistItems.get(holder.getAdapterPosition()).getText())) {
                    // Perform the action with the edited text
                    boolean inserted = databaseHelper.updateChecklistItem(id, editedText);
                    if (inserted) {
                        // Update the text of the clicked TextView
                        checklistItems.get(holder.getAdapterPosition()).setText(editedText);
                        holder.checklist_textview.setText(editedText);
                        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                        // Notify the adapter of the data change
                        notifyItemChanged(holder.getAdapterPosition());
                    }
                } else {
                    Toast.makeText(context, "Text unchanged", Toast.LENGTH_SHORT).show();
                }

                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    public void updateItem(int position, ChecklistItem newItem) {
        checklistItems.set(position, newItem);
        notifyItemChanged(position);
    }

    public void updateItemById(long itemId, ChecklistItem newItem) {
        int position = getPositionById(itemId);
        if (position != -1) {
            updateItem(position, newItem);
        }
    }

    public void removeItem(int position) {
        checklistItems.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItemById(long itemId) {
        int position = getPositionById(itemId);
        if (position != -1) {
            removeItem(position);
        }
    }
    public int getPositionById(long itemId) {
        for (int i = 0; i < checklistItems.size(); i++) {
            if (checklistItems.get(i).getId() == itemId) {
                return i;
            }
        }
        return -1; // Item not found
    }
    public void setData(ArrayList<ChecklistItem> data) {
        checklistItems = data;
        notifyDataSetChanged();
    }

    // ...
}


