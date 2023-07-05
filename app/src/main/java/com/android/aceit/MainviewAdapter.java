package com.android.aceit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainviewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private ArrayList<Subject> subjects;



    public MainviewAdapter(Context context, ArrayList<Subject> subjects) {
        this.context = context;
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mainrvrow, parent, false);
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
                    Intent intent = new Intent(context, SubcategoriesActivity.class);
                    intent.putExtra("categoryKey", subject.getKey());
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("mainCategoryKey", subject.getKey());
                    editor.putInt("count",1);
                    Toast.makeText(context, "main Key " + subject.getKey(), Toast.LENGTH_SHORT).show();
                    editor.commit();

                    Toast.makeText(context, "Subcategories", Toast.LENGTH_SHORT).show();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line
                    context.startActivity(intent);
                } else {
                    // Open Questions Activity
                    Intent intent = new Intent(context, QuestionsActivity.class);
                    intent.putExtra("categoryKey", subject.getKey());
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("mainCategoryKey", subject.getKey());

                    Toast.makeText(context, "main Key " + subject.getKey(), Toast.LENGTH_SHORT).show();
                    editor.commit();
                    Toast.makeText(context, "Questions", Toast.LENGTH_SHORT).show();
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
