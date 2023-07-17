package com.android.aceit.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
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
    private long initialTimeInMillis = 5000; // Initial time for each question



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        timer = findViewById(R.id.tvtimerQuiz);
        question = findViewById(R.id.tvQuizQuestion);
        handler = new Handler();





        setQuestionQuiz(3);

    }





    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer.setText(timeFormatted);
    }

    private void setQuestionQuiz(int numQuestions) {
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

            Toast.makeText(this, "ran times " + currentQuestionIndex, Toast.LENGTH_SHORT).show();
            QuestionModel currentQuestion = questionList.get(currentQuestionIndex);
            question.setText(currentQuestion.getQuestion());

           /* //to speak current wuestion
            speakQuestion(currentQuestion.getQuestion());*/


            currentQuestionIndex++;
        } else {
            // No more questions to display, handle the logic here
            Toast.makeText(this, "No more wuestion to display ", Toast.LENGTH_SHORT).show();
            quizCompleted = true;
            countDownTimer.cancel();

            timeLeftInMillis = 0; // Reset the timer duration

            updateTimerText(); // Update the timer text one last time
            endQuiz(); // Call the method to handle quiz completion

        }
    }

    private void startQuiz() {
        // Display the first question
// Delay before setting the next question
        displayQuestion();



        // Start the countdown timer
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                // Restart the quiz with a new question if quiz is not completed
                if (!quizCompleted) {
                    timeLeftInMillis = 5000; // Reset the timer duration

                    Toast.makeText(Quiz.this, "Delayeed" , Toast.LENGTH_SHORT).show();
                    // Pause for 2 seconds before setting the next question
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startQuiz(); // Set the next question
                        }
                    }, 2000); // Adjust the pause duration as needed (e.g., 2000 milliseconds = 2 seconds)
                } else {
                    Snackbar.make(question, "Quiz is over", Snackbar.LENGTH_LONG).show();
                    endQuiz();
                }
            }
        };

        // Start the countdown timer
        countDownTimer.start();
    }




    private void endQuiz() {

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



}
