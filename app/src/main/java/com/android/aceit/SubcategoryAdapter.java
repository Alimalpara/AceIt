package com.android.aceit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubcategoryAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<Subject> subjects;
    public Context context;
    String mainkey;
    ArrayList<String> subKeys;
    int count;

    public SubcategoryAdapter(ArrayList<Subject> subjects, Context context, String mainkey, ArrayList<String> subKeys) {
        this.subjects = subjects;
        this.context = context;
        this.mainkey = mainkey;
        this.subKeys = subKeys;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainrvrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Subject subject = subjects.get(position);
        holder.maintv.setText(subject.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subject.hasSubcategories()) {
                    // Open Subcategories Activity
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("subMainCategoryKey", subject.getKey());

                    editor.apply();



                   // editor.putString("subsubMainCategoryKey", subject.getKey());
                    Toast.makeText(context, "Sub SUb Main Key " + subject.getKey(), Toast.LENGTH_SHORT).show();



                    Intent intent = new Intent(context, SubcategoriesActivity.class);
                    intent.putExtra("categoryKey", subject.getKey());

                    Boolean subpathtrue = true;
                    intent.putExtra("isSubPath",subpathtrue);


                    Toast.makeText(context, "Subcategories", Toast.LENGTH_SHORT).show();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line
                    context.startActivity(intent);
                } else {
                    // Open Questions Activity
                    Intent intent = new Intent(context, QuestionsActivity.class);
                    intent.putExtra("categoryKey", subject.getKey());
                    Toast.makeText(context, "Questions", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("subMainCategoryKey", subject.getKey());


                    editor.apply();


                    intent.putExtra("mainkey",mainkey);
                    intent.putStringArrayListExtra("subKeyArray",subKeys);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line
                    context.startActivity(intent);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
