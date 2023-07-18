package com.android.aceit.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.aceit.R;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;

public class CreatePdf extends AppCompatActivity {

    String content;
    int type_of_letter;
    ActionBar actionBar;

    //mainviews

    ScrollView svCreateCoverLetter,svCreateReferenceletter,svCreatePersonalLetter;

    //cover letter
    private EditText etCreateCoverLetterName, etCreateCoverLetterPosition, etCreateCoverLetterCompany, etCreateCoverLetterSkills, etCreateCoverLetterExperience,etCreateCoverLetterHiringManagerName;
    Button btnGenerateCoverLetter;

    //reference letter
    private EditText etCreateReferenceLetterReferenceName, etCreateReferenceLetterCompanyName,
            etCreateReferenceLetterSkillsExperience, etCreateReferenceLetterYourName;

    //personal letter
    Button btnGenerateReferenceLetter;
    boolean personalletter;
    int typeFromMain;
   EditText etCreatePersonalLetter;
   Button btnCreatePersonalLeter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);

        initViews();
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        }


        //for cover and ref from rv
        content = getIntent().getStringExtra("CONTENT");
        type_of_letter = getIntent().getIntExtra("type_of_letter", 0);
        typeFromMain = getIntent().getIntExtra("Type_of_letter", 0);
        if(type_of_letter>0){
            typeOfLetterMethod(type_of_letter);
        }








        //now type from the main Letterclass to set it for the personal letter
        isPersonalLetter();



        if (content != null && content.contains("{NAME}")) {
            // The template contains the "NAME" variable
            // Get user input for the hiring manager name
            etCreateCoverLetterHiringManagerName.setVisibility(View.VISIBLE);
        }

        btnGenerateCoverLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAsPDF(type_of_letter,getUIInputForCoverLetterandReturnString());
            }
        });
        btnGenerateReferenceLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAsPDF(type_of_letter,getUIInputForReferenceLetterandReturnString());
            }
        });

        ///for personal
        btnCreatePersonalLeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personal = etCreatePersonalLetter.getText().toString();
                saveAsPDF(typeFromMain,personal);

            }
        });

    }

    public void initViews(){
        //mainviews;
        svCreateCoverLetter = (ScrollView) findViewById(R.id.spCreateCoverLetter);
        svCreateReferenceletter = (ScrollView) findViewById(R.id.spCreateReferenceLetter);

        // Initialize EditText fields for cover letters
        etCreateCoverLetterName = findViewById(R.id.etCreateCoverLetterName);
        etCreateCoverLetterPosition = findViewById(R.id.etCreateCoverLetterPosition);
        etCreateCoverLetterCompany = findViewById(R.id.etCreateCoverLetterCompany);
        etCreateCoverLetterSkills = findViewById(R.id.etCreateCoverLetterSkills);
        etCreateCoverLetterExperience = findViewById(R.id.etCreateCoverLetterExperience);
        etCreateCoverLetterHiringManagerName = (EditText) findViewById(R.id.etCreateCoverLetterHiringManagerName);
        btnGenerateCoverLetter = (Button) findViewById(R.id.btnCreateCOverLetterMain);

        //reference letter
        etCreateReferenceLetterReferenceName = findViewById(R.id.etCreateReferenceLetterPersonalName);
        etCreateReferenceLetterCompanyName = findViewById(R.id.etCreateReferenceLetterCompanyName);
        etCreateReferenceLetterSkillsExperience = findViewById(R.id.etCreateReferenceLetterSkillsExperience);
        etCreateReferenceLetterYourName = findViewById(R.id.etCreateReferenceLetterYourName);

        btnGenerateReferenceLetter = (Button) findViewById(R.id.btnGenerateRefernceLetter);
        
        //personal letter
        svCreatePersonalLetter = (ScrollView) findViewById(R.id.spCreatePersonalLetter);
        etCreatePersonalLetter = (EditText) findViewById(R.id.etCreatePersonalLetter);
        btnCreatePersonalLeter  = (Button) findViewById(R.id.btnCreatePersonalLeter);

    }

    //type of quiz main logic
    public void typeOfLetterMethod(int type){
        if(type==1){
            svCreateCoverLetter.setVisibility(View.VISIBLE);
            svCreateReferenceletter.setVisibility(View.GONE);

            actionBar.setTitle("Cover Letter");
        }else if(type==2){

            svCreateReferenceletter.setVisibility(View.VISIBLE);
            svCreateCoverLetter.setVisibility(View.GONE);

            actionBar.setTitle("Reference Letter");
        }
    }
    private void saveAsPDF(int letterType, String content) {
        // Determine the folder name and file prefix based on the letter type
        String folderName = "";
        String filePrefix = "";
        if (letterType == 1) {
            folderName = "Cover Letters";
            filePrefix = "cover_letter";
        } else if (letterType == 2) {
            folderName = "Reference Letters";
            filePrefix = "reference_letter";
        }

        // Create a file name for the PDF file
        String fileName = filePrefix + ".pdf";

        // Get the directory where you want to save the file
        File documentsDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "ACE IT");
        if (!documentsDirectory.exists()) {
            if (!documentsDirectory.mkdirs()) {
                // Show an error message if the directory cannot be created
                Toast.makeText(CreatePdf.this, "Failed to create directory", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Create the subfolder within ACE IT based on the letter type
        File subFolder = new File(documentsDirectory, folderName);
        if (!subFolder.exists()) {
            if (!subFolder.mkdirs()) {
                // Show an error message if the subfolder cannot be created
                Toast.makeText(CreatePdf.this, "Failed to create subfolder", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Check if a letter with the same name already exists
        File file = new File(subFolder, fileName);
        int sequenceNumber = 1;
        while (file.exists()) {
            // Generate a new file name with a sequence number
            String sequenceFileName = filePrefix + "(" + sequenceNumber + ").pdf";
            file = new File(subFolder, sequenceFileName);
            sequenceNumber++;
        }

        try {
            // Create a PdfWriter
            PdfWriter writer = new PdfWriter(file);

            // Create a PdfDocument
            PdfDocument pdfDocument = new PdfDocument(writer);

            // Create a Document
            Document document = new Document(pdfDocument);
// Create a PdfFont with the desired font and size
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            float fontSize = 14; // Specify the desired font size
            // Add the letter content to the Document
            Paragraph paragraph = new Paragraph(content).setFont(font).setFontSize(fontSize);
            document.add(paragraph);

            // Close the Document
            document.close();

            // Show a success message to the user
            Toast.makeText(CreatePdf.this, "Letter saved as PDF successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Show an error message to the user if the file could not be saved
            Toast.makeText(CreatePdf.this, "Failed to save letter as PDF", Toast.LENGTH_SHORT).show();
        }
    }

    //Method for COver letter to get ui input and setting it to save as pdf
    private String getUIInputForCoverLetterandReturnString() {
        // Get user input
        String fullName = etCreateCoverLetterName.getText().toString();
        String position = etCreateCoverLetterPosition.getText().toString();
        String company = etCreateCoverLetterCompany.getText().toString();
        String skills = etCreateCoverLetterSkills.getText().toString();
        String experience = etCreateCoverLetterExperience.getText().toString();

        // Check for "NAME" variable
        String content = getIntent().getStringExtra("CONTENT");

        if (content.contains("{NAME}")) {
            // The template contains the "NAME" variable
            // Get user input for the hiring manager name


            String hiringManagerName = etCreateCoverLetterHiringManagerName.getText().toString();

            // Replace variables in the template
            content = content.replace("{NAME}", hiringManagerName);
        }

        // Replace other variables in the template
        content = content.replace("{YOUR NAME}", fullName);
        content = content.replace("{POSITION}", position);
        content = content.replace("{COMPANY}", company);
        content = content.replace("{SKILLS}", skills);
        content = content.replace("{EXPERIENCE}", experience);

        return content;
    }

    //Method for Reference letter to get ui input and setting it to save as pdf
    private String getUIInputForReferenceLetterandReturnString() {
        String referenceName = etCreateReferenceLetterReferenceName.getText().toString().trim();
        String companyName = etCreateReferenceLetterCompanyName.getText().toString().trim();
        String skillsExperience = etCreateReferenceLetterSkillsExperience.getText().toString().trim();
        String yourName = etCreateReferenceLetterYourName.getText().toString().trim();

        String content = getIntent().getStringExtra("CONTENT");


        // Replace the variables with user input in the template content
        String referenceLetterContent = content
                .replace("[Reference's Name]", referenceName)
                .replace("[Previous Company/Organization]", companyName)
                .replace("[Skills/Experience]", skillsExperience)
                .replace("[Your Name]", yourName);

        return referenceLetterContent;
    }

    //to check if personal and set accordingly
    public void isPersonalLetter(){
        typeFromMain = getIntent().getIntExtra("Type_of_letter", 0);
        personalletter = getIntent().getBooleanExtra("isPersonal",false);
        if(personalletter){
            if(typeFromMain==1){
                actionBar.setTitle("Cover letter");
                btnCreatePersonalLeter.setText("Generate Cover Letter");
            }else if( typeFromMain == 2){
                actionBar.setTitle("Reference letter");
                btnCreatePersonalLeter.setText("Generate Reference Letter");
            }
            svCreatePersonalLetter.setVisibility(View.VISIBLE);
            svCreateCoverLetter.setVisibility(View.GONE);
            svCreateReferenceletter.setVisibility(View.GONE);


            Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
    }

    //to close this activity
    @Override
    public boolean onSupportNavigateUp() {


        finish();
        return false;
    }
}

        // Get the letter content from the EditText





  /* String multiLineText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\n" +
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\\n\" +\n" +
                "                \"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\\n\" +\n" +
                "                \"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\\n\" +\n" +
                "                \"Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";*/
 /*private void saveAsPDF(int letterType, String content) {
        // Determine the folder name and file prefix based on the letter type
        String folderName = "";
        String filePrefix = "";
        if (letterType == 1) {
            folderName = "coverletters";
            filePrefix = "cover_letter";
        } else if (letterType == 2) {
            folderName = "referencelatters";
            filePrefix = "reference_letter";
        }

        // Create a file name for the PDF file
        String fileName = filePrefix + ".pdf";

        // Get the directory where you want to save the file
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), folderName);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                // Show an error message if the directory cannot be created
                Toast.makeText(CreatePdf.this, "Failed to create directory", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Check if a letter with the same name already exists
        File file = new File(directory, fileName);
        int sequenceNumber = 1;
        while (file.exists()) {
            // Generate a new file name with a sequence number
            String sequenceFileName = filePrefix + "(" + sequenceNumber + ").pdf";
            file = new File(directory, sequenceFileName);
            sequenceNumber++;
        }

        try {
            // Create a PdfWriter
            PdfWriter writer = new PdfWriter(file);

            // Create a PdfDocument
            PdfDocument pdfDocument = new PdfDocument(writer);

            // Create a Document
            Document document = new Document(pdfDocument);

            // Add the letter content to the Document
            Paragraph paragraph = new Paragraph(content);
            document.add(paragraph);

            // Close the Document
            document.close();

            // Show a success message to the user
            Toast.makeText(CreatePdf.this, "Letter saved as PDF successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Show an error message to the user if the file could not be saved
            Toast.makeText(CreatePdf.this, "Failed to save letter as PDF", Toast.LENGTH_SHORT).show();
        }
    }*/