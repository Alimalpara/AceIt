package com.android.aceit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.aceit.Activities.CreatePdf;
import com.android.aceit.Activities.ViewAnswerActivity;
import com.android.aceit.Models.ExpandableItem;
import com.android.aceit.Models.QuestionModel;
import com.android.aceit.R;
import com.android.aceit.MyViewHolder;

import java.util.ArrayList;

public class LettersAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<ExpandableItem> expandableListItems;
    Context context;
    int type_of_letter;

    public LettersAdapter(ArrayList<ExpandableItem> expandableListItems, Context context, int type_of_letter) {
        this.expandableListItems = expandableListItems;
        this.context = context;
        this.type_of_letter = type_of_letter;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_expandable_letters_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       ExpandableItem item = expandableListItems.get(position);

       holder.expand_title.setText(item.getTitle());
       holder.expand_content.setText(item.getContent());


        // Set the initial visibility of the content and button views
        holder.expand_content.setVisibility(View.GONE);
        holder.expand_button.setVisibility(View.GONE);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(context, "clickd", Toast.LENGTH_SHORT).show();
               toggleExpansion(holder.expand_content,holder.expand_button);
           }

       });

       holder.expand_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String content = item.getContent();
               Intent intent = new Intent(v.getContext(), CreatePdf.class);
               intent.putExtra("CONTENT", content);
               intent.putExtra("type_of_letter",type_of_letter);
               v.getContext().startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return expandableListItems.size();
    }
    private void toggleExpansion(View content, View button) {
        if (content.getVisibility() == View.VISIBLE) {
            content.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    }
}
