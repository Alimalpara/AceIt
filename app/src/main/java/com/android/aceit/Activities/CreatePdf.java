package com.android.aceit.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.aceit.R;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class CreatePdf extends AppCompatActivity {
    Button savebtn;
    String content;
    int type_of_letter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);
        savebtn = (Button) findViewById(R.id.btnSavePDf);

        content = getIntent().getStringExtra("CONTENT");
        type_of_letter = getIntent().getIntExtra("type_of_letter", 0);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAsPDF(type_of_letter, content);
            }
        });
    }
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