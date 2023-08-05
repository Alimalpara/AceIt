package com.android.aceit.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aceit.Models.QuestionModel;
import com.android.aceit.R;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;


public class Quiz extends AppCompatActivity {

    TextView timer, question;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 0000;
    private int currentQuestionIndex = 0;
    private ArrayList<QuestionModel> questionList;
    private boolean quizCompleted = false;

    private TextToSpeech textToSpeech;
    private boolean isSpeaking = false;
    Handler handler ;
    private long timeForTheQuestion = 150000;
    int type ;// Initial time for each question

    //mainlayouts
    ConstraintLayout
            //main included
            mainIncludePractice, mainIncludeLive,
    //live mdde
            init_live_quiz_layout,live_quiz_layout_main,
    //practice moede
            init_pracice_quiz_layout,practice_quiz_layout_main;

    Button startLiveModeQuiz, startPracticeModeQuiz, practiceNextQuestion, practicePreviousQuestion;

    TextView practiceSetQuestion;
    ActionBar actionBar;

    //bg image for quiz
    ImageView bgQuiz;

    //for hint
    CardView cvHintMainPracticeMode,cvHintExpandPracticeMode;
    TextView tvPracticeHintExapand;
    ImageView expandIv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
       actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }

        handler = new Handler();

        type = getIntent().getIntExtra("type",0);

        initViews();
        typeofQuiz(type);







        startLiveModeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                live_quiz_layout_main.setVisibility(View.VISIBLE);
                init_live_quiz_layout.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startTimerQuiz(5);
                    }
                }, 3000);

            }
        });

//practice mode click listeners
        startPracticeModeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                if (intent != null) {
                    questionList = (ArrayList<QuestionModel>) getIntent().getSerializableExtra("questionList");
                    init_pracice_quiz_layout.setVisibility(View.GONE);
                    practice_quiz_layout_main.setVisibility(View.VISIBLE);
                    practicePreviousQuestion.setVisibility(View.INVISIBLE);
                    //to set initial question
                    setQuestionToTextView(false);
                   /* practiceSetQuestion.setText("shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n");*/
                }else{
                    Snackbar.make(Quiz.this.findViewById(android.R.id.content),
                            "No Questions to display", Snackbar.LENGTH_SHORT).show();

                }

            }
        });

        //next utton for xhanging question
        practiceNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if there are more questions
                if (currentQuestionIndex < questionList.size() - 1) {
                    currentQuestionIndex++; // Increment the index to move to the next question
                    setQuestionToTextView(false); // Set the question to the TextView
                }
                updateButtonVisibility(); // Update the visibility of previous and next buttons
            }
        });

        //precvious button for previous questions
        practicePreviousQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Quiz.this, "clicked", Toast.LENGTH_SHORT).show();
                // Check if there are previous questions
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--; // Decrement the index to move to the previous question
                    setQuestionToTextView(true); // Set the question to the TextView
                }
                updateButtonVisibility(); // Update the visibility of previous and next buttons
            }
        });

        //hint click event
// Set click listener for the main hint CardView
        cvHintMainPracticeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHintVisibility();
            }
        });

    }
    //type of quiz main logic
    public void typeofQuiz(int type){
        if(type==1){
            //Toast.makeText(this, "Live mode", Toast.LENGTH_SHORT).show();
            mainIncludeLive.setVisibility(View.VISIBLE);
            init_live_quiz_layout.setVisibility(View.VISIBLE);
            live_quiz_layout_main.setVisibility(View.GONE);
            mainIncludePractice.setVisibility(View.GONE);
            bgQuiz.setVisibility(View.GONE);
            actionBar.setTitle("Live Mode");
        }else if(type==2){
            //Toast.makeText(this, "Practice mode", Toast.LENGTH_SHORT).show();
            mainIncludeLive.setVisibility(View.GONE);
            mainIncludePractice.setVisibility(View.VISIBLE);
            init_pracice_quiz_layout.setVisibility(View.VISIBLE);
            practice_quiz_layout_main.setVisibility(View.GONE);
            actionBar.setTitle("Practice Mode");
        }
    }

    public void initViews(){
        //main layouts inside the quiz

        timer = findViewById(R.id.tvtimerQuiz);
        question = findViewById(R.id.tvQuizQuestion);
        mainIncludeLive = (ConstraintLayout) findViewById(R.id.layoutIncludedForLivequiz);
        mainIncludePractice = (ConstraintLayout) findViewById(R.id.layoutIncludedForPracticequiz);

        //layouts
        init_live_quiz_layout = (ConstraintLayout) findViewById(R.id.cvLiveModeStart);
        live_quiz_layout_main = (ConstraintLayout) findViewById(R.id.cvLiveModeQuiz);

        //practice mode
        init_pracice_quiz_layout = (ConstraintLayout) findViewById(R.id.cvPracticeModeStart);
        practice_quiz_layout_main = (ConstraintLayout) findViewById(R.id.cvPracticeModeQuiz);


        //mainbuttons to start the quiz
        startLiveModeQuiz = (Button) findViewById(R.id.btnStartLiveMode);
        startPracticeModeQuiz = (Button) findViewById(R.id.btnStartPracticeMode);

        //textview of practice

        practiceSetQuestion = (TextView) findViewById(R.id.tvPracticeModeQuestion);

        //practice buttons
        practiceNextQuestion = (Button) findViewById(R.id.btnPracticeNextQuestion);
        practicePreviousQuestion = (Button) findViewById(R.id.btnPracticePreviousQuestion);

        // quiz image view
        bgQuiz = (ImageView) findViewById(R.id.ivLiveModeQuizBg);

        //for hint
        cvHintMainPracticeMode = findViewById(R.id.cvHintMainPracticeMode);
        cvHintExpandPracticeMode = findViewById(R.id.cvHintExpandPracticeMode);
        tvPracticeHintExapand = findViewById(R.id.tvPracticeHintExapand);
        expandIv = (ImageView) findViewById(R.id.ivExapndandCollapsePracticeMode);
        cvHintMainPracticeMode.setVisibility(View.VISIBLE);

    }

    //following methods for practice mode
    // Method to set the current question to the TextView
    private void setQuestionToTextView(boolean slideToLeft) {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questionList.size()) {
            QuestionModel currentQuestion = questionList.get(currentQuestionIndex);
            practiceSetQuestion.setText(currentQuestion.getQuestion());

            //to set hint
            // Extract a hint from the answer and set it as a hint for the TextView
            String answer = currentQuestion.getAnswer();
            String hint = extractWordsAsHint(answer); // You can reuse the extractFirstFewWords method from the previous response
            tvPracticeHintExapand.setText(hint);
            Animation slideAnimation = AnimationUtils.loadAnimation(this, slideToLeft ? R.anim.slide_in_left_quiz: R.anim.slide_in_right_quiz);
            practiceSetQuestion.startAnimation(slideAnimation);
            cvHintExpandPracticeMode.setVisibility(View.GONE);
            expandIv.setRotation(0);

        }
    }

    //to toggle the views for hint
    private void toggleHintVisibility() {
        int newVisibility;
        if (cvHintExpandPracticeMode.getVisibility() == View.VISIBLE) {
            newVisibility = View.GONE;
            expandIv.setRotation(0);
        } else {
            newVisibility = View.VISIBLE;
            expandIv.setRotation(180);
        }
        cvHintExpandPracticeMode.setVisibility(newVisibility);

    }


    //to extract few words
    // Method to extract the first few words from a string
   /* private String extractFirstFewWords(String text) {
        int wordsToExtract = 10; // Change this as needed
        String[] words = text.split(" ");
        StringBuilder hint = new StringBuilder();

        for (int i = 0; i < wordsToExtract && i < words.length; i++) {
            hint.append(words[i]).append(" ");
        }

        return hint.toString().trim();
    }
*/
    private String extractWordsAsHint(String text) {
        String[] words = text.split(" ");
        StringBuilder hint = new StringBuilder();

        for (String word : words) {
            // Remove punctuation and trim the word
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").trim();

            // Check if the cleaned word has a length greater than 4 characters
            if (cleanWord.length() > 4) {
                hint.append(cleanWord).append(", ");
            }
        }

        if (hint.length() > 0) {
            // Remove the trailing comma and space
            hint.delete(hint.length() - 2, hint.length());
        }

        return hint.toString();
    }


    // Method to update the visibility of previous and next buttons
    private void updateButtonVisibility() {
        if (currentQuestionIndex == 0) {
            practicePreviousQuestion.setVisibility(View.INVISIBLE);
        } else {
            practicePreviousQuestion.setVisibility(View.VISIBLE);
        }

        if (currentQuestionIndex == questionList.size() - 1) {
            practiceNextQuestion.setVisibility(View.INVISIBLE);
        } else {
            practiceNextQuestion.setVisibility(View.VISIBLE);
        }
    }


    //following methods for live quiz
    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer.setText(timeFormatted);
    }

    private void startTimerQuiz(int numQuestions) {
        Intent intent = getIntent();
        if (intent != null) {
            questionList = (ArrayList<QuestionModel>) getIntent().getSerializableExtra("questionList");
            if (questionList != null && questionList.size() > 0) {
                Collections.shuffle(questionList);

                // Limit the number of questions
                if (numQuestions < questionList.size()) {
                    questionList.subList(numQuestions, questionList.size()).clear();
                    startQuiz();
                }else{
                    Snackbar.make(findViewById(android.R.id.content),
                            "Questions are more than array", Snackbar.LENGTH_SHORT).show();


                }
            }
        }
    }




    private void displayQuestion() {
        if (currentQuestionIndex < questionList.size()) {

            if(currentQuestionIndex==0){
                QuestionModel currentQuestion = questionList.get(currentQuestionIndex);
                bgQuiz.setVisibility(View.VISIBLE);

                //to seet animatipon
                // Apply fade-in animation to the question view
                Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
                question.startAnimation(fadeInAnimation);

                questionAnimation();
                question.setText(currentQuestion.getQuestion());
                startCountdown();
                currentQuestionIndex++;

            }else{
                ///this is when the new question i s coming up

                //Toast.makeText(this, "Get ready for next question", Toast.LENGTH_SHORT).show();
                question.setText("Get ready for next question");
                bgQuiz.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
                // Pause for 2 seconds before setting the next question
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timer.setVisibility(View.VISIBLE);
                        QuestionModel currentQuestion = questionList.get(currentQuestionIndex);
                        questionAnimation();
                        question.setText(currentQuestion.getQuestion());
                        currentQuestionIndex++;
                        startCountdown();

                        bgQuiz.setVisibility(View.VISIBLE);

                    }
                }, 2000); // Adjust the pause duration as needed (e.g., 2000 milliseconds = 2 seconds)
            }


        } else {
            // No more questions to display, handle the logic here
           // Toast.makeText(this, "No more questions to display", Toast.LENGTH_SHORT).show();
            quizCompleted = true;
            countDownTimer.cancel();
            timeLeftInMillis = 0; // Reset the timer duration
            updateTimerText(); // Update the timer text one last time
            endQuiz(); // Call the method to handle quiz completion

        }
    }

    private void startQuiz() {
        displayQuestion(); // Start the quiz by displaying the first question
    }

    private void startCountdown() {
       /* here two varaible are used first is timeForTheQuestion
        the second is timeLeftInMillis.
        so when thwe quiz is launched the timeforthequestoin is used to set the timer of every question
                and the timeLeftInMillis is used to reset the timer and update the timer text by
                decreasing each second and also is used to reset the final timer value after the quiz is over.*/
        countDownTimer = new CountDownTimer(timeForTheQuestion, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();


            }

            @Override
            public void onFinish() {
                timeLeftInMillis = timeForTheQuestion;
                displayQuestion(); // Set the next question
            }
        };



        countDownTimer.start();
        timerAnimation();
    }


    private void endQuiz() {

        Snackbar.make(question, "Session is over", Snackbar.LENGTH_LONG).show();
        bgQuiz.setImageResource(R.drawable.quizcompleted);
        //bgQuiz.setVisibility(View.GONE);
    timer.setVisibility(View.GONE);
    question.setText("Live mode Completed");
        //Toast.makeText(this, "TImer stioooed", Toast.LENGTH_SHORT).show();
        // Quiz ended, handle the logic here
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        /*if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
       /* if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }*/
    }

    public void questionAnimation(){
        Animation fadeInAnimation = AnimationUtils.loadAnimation(Quiz.this, R.anim.fade_in);
        question.startAnimation(fadeInAnimation);}
    public void timerAnimation(){
        // Apply scale animation to timer TextView
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        timer.startAnimation(scaleAnimation);

    }


    //to close this activity
    @Override
    public boolean onSupportNavigateUp() {


        finish();
        return false;
    }


}
