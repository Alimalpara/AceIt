package com.android.aceit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewAnswerActivity extends AppCompatActivity {
    TextView questiontv, answertv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answer);

        questiontv = (TextView) findViewById(R.id.tvViewQuestion);
        answertv = (TextView) findViewById(R.id.tvViewAnswer);

        // Get the question and answer from the intent extras
        String question = getIntent().getStringExtra("question");
        String answer = getIntent().getStringExtra("answer");

        // Find the TextViews in the layout


        // Set the question and answer text
        questiontv.setText(question);
        answertv.setText(answer);

    }
}