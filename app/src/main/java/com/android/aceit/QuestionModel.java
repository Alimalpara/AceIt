package com.android.aceit;

import java.io.Serializable;

public class QuestionModel implements Serializable {
    String question;
    String Answer;

    public QuestionModel(String question, String answer) {
        this.question = question;
        Answer = answer;
    }

    public QuestionModel() {

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
