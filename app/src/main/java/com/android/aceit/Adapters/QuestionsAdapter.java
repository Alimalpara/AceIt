package com.android.aceit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.aceit.Activities.ViewAnswerActivity;
import com.android.aceit.Models.QuestionModel;
import com.android.aceit.R;
import com.android.aceit.MyViewHolder;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<QuestionModel> questionModels;
    Context context;

    public QuestionsAdapter(ArrayList<QuestionModel> questionModels, Context context) {
        this.questionModels = questionModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mainrvrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      QuestionModel questionModel = questionModels.get(position);
      String question = questionModel.getQuestion()+" " +questionModel.getId();
      holder.maintv.setText(question);
       // Toast.makeText(context, "Quest "+questionModel.getQuestion(), Toast.LENGTH_SHORT).show();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ViewAnswerActivity.class);

                // Pass the question and answer as extra data
                intent.putExtra("question", questionModel.getQuestion());
                intent.putExtra("answer", questionModel.getAnswer());
                intent.putExtra("qid",questionModel.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line

                // Start the next activity
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return questionModels.size();
    }
}
