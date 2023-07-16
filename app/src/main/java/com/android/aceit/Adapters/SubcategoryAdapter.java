package com.android.aceit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.aceit.Activities.QuestionsActivity;
import com.android.aceit.Activities.SubcategoriesActivity;
import com.android.aceit.Models.ChildData;
import com.android.aceit.R;
import com.android.aceit.MyViewHolder;

import java.util.ArrayList;

public class SubcategoryAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<ChildData> childDataArrayList;
    public Context context;
    String mainkey;
    ArrayList<String> subKeys;
    int count;

    public SubcategoryAdapter(ArrayList<ChildData> childDataArrayList, Context context, String mainkey, ArrayList<String> subKeys) {
        this.childDataArrayList = childDataArrayList;
        this.context = context;
        this.mainkey = mainkey;
        this.subKeys = subKeys;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainrvrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ChildData childData = childDataArrayList.get(position);
        holder.maintv.setText(childData.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasSubCategories = childData.hasSubcategories();
// Create an Intent to redirect to the appropriate activity
                Intent intent;
                if (hasSubCategories) {
                    // Redirect to SubcategoryActivity
                    intent = new Intent(context, SubcategoriesActivity.class);
                } else {
                    // Redirect to QuestionActivity
                    intent = new Intent(context, QuestionsActivity.class);
                }

                // Pass the necessary data as extras in the Intent
                intent.putExtra("name", childData.getName());
                intent.putExtra("key", childData.getKey());
                intent.putExtra("jsonObject", childData.getJsonObject().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);


            }


        });

    }

    @Override
    public int getItemCount() {
        return childDataArrayList.size();
    }
}
