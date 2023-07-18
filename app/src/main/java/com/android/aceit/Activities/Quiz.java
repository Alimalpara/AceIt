package com.android.aceit.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.aceit.Models.QuestionModel;
import com.android.aceit.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;




public class Quiz extends AppCompatActivity {

    TextView timer, question;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 5000;
    private int currentQuestionIndex = 0;
    private ArrayList<QuestionModel> questionList;
    private boolean quizCompleted = false;

    private TextToSpeech textToSpeech;
    private boolean isSpeaking = false;
    Handler handler ;
    private long initialTimeInMillis = 5000;
    int type ;// Initial time for each question

    //mainlayouts
    ConstraintLayout mainIncludePractice, mainIncludeLive,liveInitial,liveQuiz,
            practiceInitial,practiceQuiz;

    Button startLiveModeQuiz, startPracticeModeQuiz, practiceNextQuestion, practicePreviousQuestion;

    TextView practiceSetQuestion;
    ActionBar actionBar;


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
                liveQuiz.setVisibility(View.VISIBLE);
                liveInitial.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startTimerQuiz(3);
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
                    practiceInitial.setVisibility(View.GONE);
                    practiceQuiz.setVisibility(View.VISIBLE);
                    practicePreviousQuestion.setVisibility(View.INVISIBLE);
                    practiceSetQuestion.setText("shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n" +
                            "" +
                            "shofgoshgsohgsjfghjsdfhgbkdjfg dhgodhgd\noshosofhosifhosdbvodbodbodhb\nosijhgsofhjgosdgdsfogdojgodsjg\nsjgosgojsfgjsdfgosdjgdsjgdsfjgi\nsojhgosfgodjfog\n");
                }else{
                    Toast.makeText(Quiz.this, "No Questions to display", Toast.LENGTH_SHORT).show();
                }

            }
        });

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

        practicePreviousQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Quiz.this, "clicked", Toast.LENGTH_SHORT).show();
                // Check if there are previous questions
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--; // Decrement the index to move to the previous question
                    setQuestionToTextView(true); // Set the question to the TextView
                }
                updateButtonVisibility(); // Update the visibility of previous and next buttons
            }
        });


    }
    //type of quiz main logic
    public void typeofQuiz(int type){
        if(type==1){
            Toast.makeText(this, "Live mode", Toast.LENGTH_SHORT).show();
            mainIncludeLive.setVisibility(View.VISIBLE);
            liveInitial.setVisibility(View.VISIBLE);
            liveQuiz.setVisibility(View.GONE);
            mainIncludePractice.setVisibility(View.GONE);
            actionBar.setTitle("Live Mode");
        }else if(type==2){
            Toast.makeText(this, "Practice mode", Toast.LENGTH_SHORT).show();
            mainIncludeLive.setVisibility(View.GONE);
            mainIncludePractice.setVisibility(View.VISIBLE);
            practiceInitial.setVisibility(View.VISIBLE);
            practiceQuiz.setVisibility(View.GONE);
            actionBar.setTitle("Practice Mode");
        }
    }

    public void initViews(){
        //main layouts inside the quiz

        timer = findViewById(R.id.tvtimerQuiz);
        question = findViewById(R.id.tvQuizQuestion);
        mainIncludeLive = (ConstraintLayout) findViewById(R.id.ihResumeSubmitLayout);
        mainIncludePractice = (ConstraintLayout) findViewById(R.id.practiceQuiz);

        //layouts
        liveInitial = (ConstraintLayout) findViewById(R.id.cvLiveModeStart);
        liveQuiz = (ConstraintLayout) findViewById(R.id.cvLiveModeQuiz);

        //practice mode
        practiceInitial = (ConstraintLayout) findViewById(R.id.cvPracticeModeStart);
        practiceQuiz = (ConstraintLayout) findViewById(R.id.cvPracticeModeQuiz);


        //mainbuttons to start the quiz
        startLiveModeQuiz = (Button) findViewById(R.id.btnStartLiveMode);
        startPracticeModeQuiz = (Button) findViewById(R.id.btnStartPracticeMode);

        //textview of practice

        practiceSetQuestion = (TextView) findViewById(R.id.tvPracticeModeQuestion);

        //practice buttons
        practiceNextQuestion = (Button) findViewById(R.id.btnPracticeNextQuestion);
        practicePreviousQuestion = (Button) findViewById(R.id.btnPracticePreviousQuestion);

    }

    //following methods for practice mode
    // Method to set the current question to the TextView
    private void setQuestionToTextView(boolean slideToLeft) {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questionList.size()) {
            QuestionModel currentQuestion = questionList.get(currentQuestionIndex);
            practiceSetQuestion.setText(currentQuestion.getQuestion());

            Animation slideAnimation = AnimationUtils.loadAnimation(this, slideToLeft ? R.anim.slide_in_left : R.anim.slide_in_right);
            practiceSetQuestion.startAnimation(slideAnimation);
        }
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
                    Toast.makeText(this, "Questions are mire than arrray", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }




    private void displayQuestion() {
        if (currentQuestionIndex < questionList.size()) {

            if(currentQuestionIndex==0){
                QuestionModel currentQuestion = questionList.get(currentQuestionIndex);

                //to seet animatipon
                // Apply fade-in animation to the question view
                Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
                question.startAnimation(fadeInAnimation);

                questionAnimation();
                question.setText(currentQuestion.getQuestion());
                startCountdown();
                currentQuestionIndex++;

            }else{
                Toast.makeText(this, "Setting a new question", Toast.LENGTH_SHORT).show();
                // Pause for 2 seconds before setting the next question
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        QuestionModel currentQuestion = questionList.get(currentQuestionIndex);
                        questionAnimation();
                        question.setText(currentQuestion.getQuestion());
                        currentQuestionIndex++;
                        startCountdown();

                    }
                }, 2000); // Adjust the pause duration as needed (e.g., 2000 milliseconds = 2 seconds)
            }


        } else {
            // No more questions to display, handle the logic here
            Toast.makeText(this, "No more questions to display", Toast.LENGTH_SHORT).show();
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
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();


            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 5000;
                displayQuestion(); // Set the next question
            }
        };



        countDownTimer.start();
        timerAnimation();
    }


    private void endQuiz() {

        Snackbar.make(question, "Quiz is over", Snackbar.LENGTH_LONG).show();
        Toast.makeText(this, "TImer stioooed", Toast.LENGTH_SHORT).show();
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
