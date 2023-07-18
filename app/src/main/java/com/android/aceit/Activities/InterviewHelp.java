package com.android.aceit.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.aceit.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class InterviewHelp extends AppCompatActivity {
    Button scheduleInterview;
    private EditText etResumeName, etResumeSpecificGoals, etResumeTargetedIndustry, etResumeAdditionlContext, etResumeJobDescription;
    private Button btnResumeSubmitFinal, btnInterviewScheduleFinal;
    int type;
    ActionBar actionBar;
    ScrollView resumeSubmit, interviewSchedule;

    private EditText etInterviewScheduleFullName, etInterviewScheduleContactDetails, etInterviewScheduleJobPosition,
            etInterviewScheduleAvailability, etInterviewScheduleAdditionalNotes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_help);
        initViews();
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }
        typeofHelp(type);


        btnResumeSubmitFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from each EditText
                String resumeName = etResumeName.getText().toString();
                String specificGoals = etResumeSpecificGoals.getText().toString();
                String targetedIndustry = etResumeTargetedIndustry.getText().toString();
                String additionalContext = etResumeAdditionlContext.getText().toString();
                String jobDescription = etResumeJobDescription.getText().toString();

                // Create the template with variables replaced by EditText values
                String template = " Name: %s\n" +
                        "Targeted Industry: %s\n" +
                        "Specific Goals: %s\n" +
                        "Job Description: %s" +
                        "Additional Context: %s\n" +
                        "\n\nPlease attach your resume in .pdf or .doc file";

                String emailBody = String.format(template, resumeName, targetedIndustry,specificGoals, jobDescription,  additionalContext);


                // Send the email with the template as the email body
                sendResumeCheck(emailBody);
            }
        });
        btnInterviewScheduleFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etInterviewScheduleFullName.getText().toString().trim();
                String contactDetails = etInterviewScheduleContactDetails.getText().toString().trim();
                String jobPosition = etInterviewScheduleJobPosition.getText().toString().trim();
                String availability = etInterviewScheduleAvailability.getText().toString().trim();
                String additionalNotes = etInterviewScheduleAdditionalNotes.getText().toString().trim();

                // Get user's timezone
                TimeZone timeZone = TimeZone.getDefault();
                String timeZoneID = timeZone.getID();

// Get current time
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                String currentTimeString = timeFormat.format(currentTime);

// Combine the timezone and current time into a single string
                String timeZoneAndTime = "Timezone: " + timeZoneID + "\n" +
                        "Current Time: " + currentTimeString;

                String template = "Full Name: %s\n" +
                        "Contact Details: %s\n" +
                        "Job Position: %s\n" +
                        "Availability: %s\n" +
                        "Time Zone and Time : %s\n" +
                        "Additional Notes: %s\n";

                String emailBody = String.format(template, fullName, contactDetails, jobPosition,
                        availability, timeZoneAndTime, additionalNotes);

                sendInterviewMail(emailBody);
            }
        });
    }
    //type of quiz main logic
    public void typeofHelp(int type){
        if(type==1){
            interviewSchedule.setVisibility(View.VISIBLE);
            resumeSubmit.setVisibility(View.GONE);

            actionBar.setTitle("Interview Schedule");
        }else if(type==2){

            resumeSubmit.setVisibility(View.VISIBLE);
            interviewSchedule.setVisibility(View.GONE);

            actionBar.setTitle("Resume Check");
        }
    }

  /*  public void inputValidation(String emailBody){


                String resumeName = etResumeName.getText().toString().trim();
                String specificGoals = etResumeSpecificGoals.getText().toString().trim();
                String targetedIndustry = etResumeTargetedIndustry.getText().toString().trim();
                String additionalContext = etResumeAdditionlContext.getText().toString().trim();
                String jobDescription = etResumeJobDescription.getText().toString().trim();

                if (resumeName.isEmpty()) {
                    etResumeName.setError("Please enter Resume Name");
                    etResumeName.requestFocus();
                } else if (specificGoals.isEmpty()) {
                    etResumeSpecificGoals.setError("Please enter Specific Goals");
                    etResumeSpecificGoals.requestFocus();
                } else if (targetedIndustry.isEmpty()) {
                    etResumeTargetedIndustry.setError("Please enter Targeted Industry");
                    etResumeTargetedIndustry.requestFocus();
                } else if (additionalContext.isEmpty()) {
                    etResumeAdditionlContext.setError("Please enter Additional Context");
                    etResumeAdditionlContext.requestFocus();
                } else if (jobDescription.isEmpty()) {
                    etResumeJobDescription.setError("Please enter Job Description");
                    etResumeJobDescription.requestFocus();
                } else {
                    // All inputs are valid, proceed with sending the email
                    sendResumeCheck(emailBody);
                }
            }*/







    public void sendResumeCheck(String emailBody){
       /* Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");*/
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));

        String[] recipientEmails = {"alimalpara@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipientEmails);

        String subject = "Resume Review Request";
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);


        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

       /* Uri attachmentUri = ...; // Uri of the file you want to attach
        emailIntent.putExtra(Intent.EXTRA_STREAM, attachmentUri);*/

        startActivity(Intent.createChooser(emailIntent, "Send Email"));

    }

    public void sendInterviewMail(String emailBody){
       /* Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");*/
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));

        String[] recipientEmails = {"alimalpara@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipientEmails);

        String subject = "Interview Scheduling Request";
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);


        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

       /* Uri attachmentUri = ...; // Uri of the file you want to attach
        emailIntent.putExtra(Intent.EXTRA_STREAM, attachmentUri);*/

        startActivity(Intent.createChooser(emailIntent, "Send Email"));

    }
    public void initViews(){
        type = getIntent().getIntExtra("helptype", 0);
        //resume
        etResumeName = findViewById(R.id.etResumeName);
        etResumeSpecificGoals = findViewById(R.id.etResumeSpecificGoals);
        etResumeTargetedIndustry = findViewById(R.id.etResumeTargetedIndustry);
        etResumeAdditionlContext = findViewById(R.id.etResumeAdditionlContext);
        etResumeJobDescription = findViewById(R.id.etResumeJobDescription);
        btnResumeSubmitFinal = findViewById(R.id.btnResumeSubmitFinal);

        //layouts main
        resumeSubmit = findViewById(R.id.ihResumeSubmitLayout);
        interviewSchedule = findViewById(R.id.ihInterviewScheduleLayout);

        //interview
        etInterviewScheduleFullName = findViewById(R.id.etInterviewScheduleFullName);
        etInterviewScheduleContactDetails = findViewById(R.id.etInterviewScheduleContactDetails);
        etInterviewScheduleJobPosition = findViewById(R.id.etInterviewScheduleJobPosition);
        etInterviewScheduleAvailability = findViewById(R.id.etInterviewScheduleAvailability);

        etInterviewScheduleAdditionalNotes = findViewById(R.id.etInterviewScheduleAdditionalNotes);
        btnInterviewScheduleFinal = findViewById(R.id.btnscheduleInterview);




    }
    //to close this activity
    @Override
    public boolean onSupportNavigateUp() {


        finish();
        return false;
    }

}