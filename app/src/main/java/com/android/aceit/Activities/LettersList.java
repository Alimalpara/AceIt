package com.android.aceit.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.aceit.Adapters.FavouriteAdapter;
import com.android.aceit.Adapters.LettersAdapter;
import com.android.aceit.Models.ExpandableItem;
import com.android.aceit.R;

import java.util.ArrayList;

public class LettersList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ExpandableItem> expandableItems;
    LettersAdapter lettersAdapter;
    ActionBar actionBar;
    Button btnLetterListUsePersonalLetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters_list);



        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }
        setRecyclerView();

        initViews();
        btnLetterListUsePersonalLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LettersList.this, CreatePdf.class);
                //passing to activity letterlist then to adapterlitst then to activity
                int itemId = getIntent().getIntExtra("Type_of_letter", 0);
                intent.putExtra("Type_of_letter", itemId); // To set title accordingly
                intent.putExtra("isPersonal",true);
                startActivity(intent);
            }
        });


    }

    public void initViews(){
        btnLetterListUsePersonalLetter = (Button) findViewById(R.id.btnLetterListUsePersonalLetter);
    }

    public void setRecyclerView(){
        expandableItems = new ArrayList<>();
        recyclerView = findViewById(R.id.rvLetters);

        int itemId = getIntent().getIntExtra("Type_of_letter", 0);

        if (itemId == 1) {
            expandableItems = getCoverletters();
            actionBar.setTitle("Cover Letters");
        } else if (itemId == 2) {
            expandableItems = getReferenceLetters();
            actionBar.setTitle("Reference Letters");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lettersAdapter = new LettersAdapter(expandableItems, this,itemId);
        recyclerView.setAdapter(lettersAdapter);

    }

    // Method to retrieve cover letter data
    private ArrayList<ExpandableItem> getCoverletters() {
        ArrayList<ExpandableItem> coverLetterList = new ArrayList<>();

        // Add sample cover letter data
        ExpandableItem coverLetter1 = new ExpandableItem("Cover Letter 1", "Dear {NAME},\n\nI am writing to express my interest in the position of {POSITION} at {COMPANY}. With my strong background in {SKILLS} and {EXPERIENCE}, I believe I would be a valuable asset to your team.\n\nThank you for considering my application. I look forward to the opportunity to discuss how my skills and qualifications align with the needs of {COMPANY}.\n\nSincerely,\n{YOUR NAME}");
        coverLetterList.add(coverLetter1);

        ExpandableItem coverLetter2 = new ExpandableItem("Cover Letter 2", "Dear Hiring Manager,\n\nI am excited to apply for the {POSITION} position at {COMPANY}. My experience in {SKILLS} and {EXPERIENCE} make me a strong candidate for this role.\n\nThank you for considering my application. I am eager to contribute my skills and passion to the success of {COMPANY}.\n\nBest regards,\n{YOUR NAME}");
        coverLetterList.add(coverLetter2);

        ExpandableItem coverLetter3 = new ExpandableItem("Cover Letter 3", "Dear {NAME},\n\nI am writing to apply for the {POSITION} position at {COMPANY}. My skills in {SKILLS} and {EXPERIENCE} make me a perfect fit for this role.\n\nThank you for considering my application. I am excited about the opportunity to contribute to the growth and success of {COMPANY}.\n\nSincerely,\n{YOUR NAME}");
        coverLetterList.add(coverLetter3);

        ExpandableItem coverLetter4 = new ExpandableItem("Cover Letter 4", "Dear Hiring Team,\n\nI am pleased to submit my application for the {POSITION} position at {COMPANY}. With a strong background in {SKILLS} and {EXPERIENCE}, I am confident in my ability to contribute to the success of {COMPANY}.\n\nThank you for considering my application. I look forward to the opportunity to further discuss how my skills and qualifications align with your needs.\n\nBest regards,\n{YOUR NAME}");
        coverLetterList.add(coverLetter4);

        ExpandableItem coverLetter5 = new ExpandableItem("Cover Letter 5", "Dear {NAME},\n\nI am writing to express my interest in the {POSITION} position at {COMPANY}. My experience in {SKILLS} and {EXPERIENCE} has equipped me with the necessary skills to excel in this role.\n\nThank you for considering my application. I am excited about the opportunity to contribute to the continued success of {COMPANY}.\n\nSincerely,\n{YOUR NAME}");
        coverLetterList.add(coverLetter5);

        ExpandableItem coverLetter6 = new ExpandableItem("Cover Letter 6", "Dear {NAME},\n\nI am writing to apply for the {POSITION} role at {COMPANY}. With my expertise in {SKILLS} and {EXPERIENCE}, I am confident in my ability to make a meaningful contribution to {COMPANY}.\n\nThank you for considering my application. I look forward to discussing how my qualifications align with your needs.\n\nSincerely,\n{YOUR NAME}");
        coverLetterList.add(coverLetter6);

        ExpandableItem coverLetter7 = new ExpandableItem("Cover Letter 7", "Dear Hiring Manager,\n\nI am excited to express my interest in the {POSITION} position at {COMPANY}. My background in {SKILLS} and {EXPERIENCE} positions me well to support the objectives of {COMPANY}.\n\nThank you for considering my application. I am eager to contribute to the success of {COMPANY}.\n\nBest regards,\n{YOUR NAME}");
        coverLetterList.add(coverLetter7);

        ExpandableItem coverLetter8 = new ExpandableItem("Cover Letter 8", "Dear {NAME},\n\nI am writing to apply for the {POSITION} role at {COMPANY}. With a strong foundation in {SKILLS} and {EXPERIENCE}, I am confident that I can add value to the team at {COMPANY}.\n\nThank you for considering my application. I am excited about the opportunity to contribute to the growth and success of {COMPANY}.\n\nSincerely,\n{YOUR NAME}");
        coverLetterList.add(coverLetter8);

        ExpandableItem coverLetter9 = new ExpandableItem("Cover Letter 9", "Dear Hiring Team,\n\nI am pleased to submit my application for the {POSITION} position at {COMPANY}. With my proven skills in {SKILLS} and {EXPERIENCE}, I am well-prepared to drive results at {COMPANY}.\n\nThank you for considering my application. I look forward to the chance to discuss my qualifications further.\n\nBest regards,\n{YOUR NAME}");
        coverLetterList.add(coverLetter9);

        ExpandableItem coverLetter10 = new ExpandableItem("Cover Letter 10", "Dear {NAME},\n\nI am writing to express my interest in the {POSITION} position at {COMPANY}. With my background in {SKILLS} and {EXPERIENCE}, I am confident in my ability to contribute to the success of {COMPANY}.\n\nThank you for considering my application. I am excited about the opportunity to join your team and make a positive impact.\n\nSincerely,\n{YOUR NAME}");
        coverLetterList.add(coverLetter10);



        return coverLetterList;
    }

    // Method to retrieve reference letter data
    private ArrayList<ExpandableItem> getReferenceLetters() {
        ArrayList<ExpandableItem> referenceLetterList = new ArrayList<>();



        ExpandableItem referenceLetter1 = new ExpandableItem("Reference Letter 1", "Dear [Reference's Name],\n\nI hope this letter finds you well. I am reaching out to you as I am currently exploring new career opportunities. Your recommendation would be highly valuable in supporting my job application.\n\nDuring our time working together at [Previous Company/Organization], I greatly admired your leadership and expertise. I learned a lot under your guidance, and I believe your endorsement would greatly enhance my chances of securing a new position.\n\nI kindly request your support in providing a positive reference highlighting my [Skills/Experience] and how I contributed to the success of projects we worked on together.\n\nThank you for considering my request. Your support means a lot to me as I embark on this new career chapter.\n\nSincerely,\n[Your Name]");
        referenceLetterList.add(referenceLetter1);

        ExpandableItem referenceLetter2 = new ExpandableItem("Reference Letter 2", "Dear [Reference's Name],\n\nI hope this letter finds you well. I am writing to request your assistance in providing a reference for a job opportunity I am pursuing. Your recommendation would carry significant weight and greatly contribute to my chances of success.\n\nAs we have collaborated closely at [Previous Company/Organization], I highly value your insights into my skills, work ethic, and professionalism. Your firsthand knowledge of my capabilities would serve as a strong endorsement of my suitability for the role.\n\nI kindly request your support in highlighting my [Skills/Experience], ability to [specific achievements], and the positive impact I made on our team and projects. Your recommendation would be immensely valuable in my job search.\n\nThank you for considering my request. I truly appreciate your time and support.\n\nBest regards,\n[Your Name]");
        referenceLetterList.add(referenceLetter2);

        ExpandableItem referenceLetter3 = new ExpandableItem("Reference Letter 3", "Dear [Reference's Name],\n\nI hope this letter finds you well. I am reaching out to request your support as I pursue new career opportunities. Your reference and recommendation would significantly contribute to my job search.\n\nHaving worked together at [Previous Company/Organization], I deeply value your perspective on my skills, work ethic, and professionalism. Your endorsement of my qualifications would greatly enhance my chances of securing a new position.\n\nI kindly request your assistance in highlighting my [Skills/Experience] and the positive contributions I made during our time working together. Your reference would provide valuable insights to potential employers.\n\nThank you for considering my request. I genuinely appreciate your support as I navigate this next chapter of my career.\n\nSincerely,\n[Your Name]");
        referenceLetterList.add(referenceLetter3);

        ExpandableItem referenceLetter4 = new ExpandableItem("Reference Letter 4", "Dear [Reference's Name],\n\nI hope this letter finds you well. I am writing to request your endorsement for an exciting job opportunity I am pursuing. Your reference would be instrumental in supporting my application and highlighting my strengths.\n\nAs we collaborated closely at [Previous Company/Organization], I greatly valued your guidance and mentorship. Your firsthand knowledge of my abilities and dedication to excellence would provide employers with valuable insights into my potential.\n\nI kindly ask for your support in emphasizing my [Skills/Experience] and the positive impact I made within our team. Your reference would significantly contribute to my chances of securing this new role.\n\nThank you for considering my request. Your support means a lot to me as I strive to achieve my professional goals.\n\nBest regards,\n[Your Name]");
        referenceLetterList.add(referenceLetter4);

        ExpandableItem referenceLetter5 = new ExpandableItem("Reference Letter 5", "Dear [Reference's Name],\n\nI hope this letter finds you well. I am reaching out to seek your support as I embark on a new career opportunity. Your recommendation would greatly enhance my chances of success.\n\nDuring our time working together at [Previous Company/Organization], I witnessed your exceptional leadership and firsthand experience of my professional capabilities. Your endorsement would provide valuable insights to prospective employers.\n\nI kindly request your assistance in highlighting my [Skills/Experience] and the positive contributions I made within our team. Your reference would serve as a testament to my abilities and help me secure a new position.\n\nThank you for considering my request. Your support means a lot to me as I pursue new professional horizons.\n\nSincerely,\n[Your Name]");
        referenceLetterList.add(referenceLetter5);

        ExpandableItem referenceLetter6 = new ExpandableItem("Reference Letter 6", "Dear [Reference's Name],\n\nI hope this letter finds you in good health. I am writing to request your recommendation as I explore new career opportunities. Your endorsement would be of great value in strengthening my job applications.\n\nThroughout our professional journey at [Previous Company/Organization], I admired your expertise and leadership. Your insight into my skills and abilities would provide prospective employers with confidence in my qualifications.\n\nI kindly ask for your support in highlighting my [Skills/Experience] and the positive impact I made during our collaboration. Your reference would significantly contribute to my success in securing a new role.\n\nThank you for your time and consideration. Your recommendation would mean a lot to me as I take the next steps in my career.\n\nBest regards,\n[Your Name]");
        referenceLetterList.add(referenceLetter6);

        ExpandableItem referenceLetter7 = new ExpandableItem("Reference Letter 7", "Dear [Reference's Name],\n\nI hope this letter finds you well. I am writing to request your endorsement as I pursue new professional opportunities. Your reference would greatly strengthen my applications and increase my chances of success.\n\nDuring our time working together at [Previous Company/Organization], I greatly appreciated your guidance and mentorship. Your firsthand knowledge of my [Skills/Experience] and work ethic would provide valuable insights to potential employers.\n\nI kindly request your support in highlighting the positive contributions I made to our team and projects. Your reference would contribute significantly to my ability to secure a new role.\n\nThank you for considering my request. I genuinely value your support as I move forward in my career.\n\nSincerely,\n[Your Name]");
        referenceLetterList.add(referenceLetter7);

        ExpandableItem referenceLetter8 = new ExpandableItem("Reference Letter 8", "Dear [Reference's Name],\n\nI hope this letter finds you in good health. I am reaching out to request your support as I explore new professional opportunities. Your reference would greatly enhance my chances of securing a new role.\n\nDuring our time working together at [Previous Company/Organization], I consistently admired your expertise and leadership. Your firsthand experience of my [Skills/Experience] and dedication would provide valuable insights to potential employers.\n\nI kindly ask for your assistance in highlighting the positive impact I made within our team and projects. Your reference would contribute significantly to my ability to secure new career prospects.\n\nThank you for considering my request. Your support means a lot to me as I embark on this new chapter in my professional journey.\n\nBest regards,\n[Your Name]");
        referenceLetterList.add(referenceLetter8);

        ExpandableItem referenceLetter9 = new ExpandableItem("Reference Letter 9", "Dear [Reference's Name],\n\nI hope this letter finds you well. I am writing to seek your support as I pursue new professional opportunities. Your recommendation would greatly enhance my job prospects.\n\nDuring our time working together at [Previous Company/Organization], I consistently witnessed your expertise and dedication. Your firsthand experience of my [Skills/Experience] and work ethic would provide valuable insights to prospective employers.\n\nI kindly request your assistance in highlighting the positive contributions I made within our team and projects. Your reference would significantly contribute to my success in securing a new role.\n\nThank you for considering my request. Your support means a lot to me as I navigate this next phase of my career.\n\nSincerely,\n[Your Name]");
        referenceLetterList.add(referenceLetter9);

        ExpandableItem referenceLetter10 = new ExpandableItem("Reference Letter 10", "Dear [Reference's Name],\n\nI hope this letter finds you well. I am writing to request your endorsement as I pursue new professional opportunities. Your reference would significantly contribute to my job search.\n\nThroughout our time working together at [Previous Company/Organization], I admired your expertise and leadership. Your firsthand experience of my [Skills/Experience] and dedication would provide valuable insights to potential employers.\n\nI kindly ask for your support in highlighting the positive contributions I made within our team and projects. Your reference would be instrumental in strengthening my chances of securing a new role.\n\nThank you for considering my request. Your support means a lot to me as I embark on this new career path.\n\nBest regards,\n[Your Name]");
        referenceLetterList.add(referenceLetter10);





        return referenceLetterList;
    }
    //to close this activity
    @Override
    public boolean onSupportNavigateUp() {


        finish();
        return false;
    }
}