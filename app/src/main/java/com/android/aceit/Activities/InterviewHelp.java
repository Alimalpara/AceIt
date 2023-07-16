package com.android.aceit.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.aceit.R;

public class InterviewHelp extends AppCompatActivity {
    Button scheduleInterview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_help);

        scheduleInterview = (Button) findViewById(R.id.btnScheduleInterview);

        scheduleInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendInterviewMail();
            }
        });

    }

    public void sendInterviewMail(){
       /* Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");*/
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));

        String[] recipientEmails = {"alimalpara@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipientEmails);

        String subject = "Testing scheduling process";
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

        String body = "Hey this is name age and type ";
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

       /* Uri attachmentUri = ...; // Uri of the file you want to attach
        emailIntent.putExtra(Intent.EXTRA_STREAM, attachmentUri);*/

        startActivity(Intent.createChooser(emailIntent, "Send Email"));

    }

}