package com.android.aceit;

import java.io.Serializable;

public class QuestionModel implements Serializable {
    String question;
    String answer;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuestionModel(String question, String answer, String id) {
        this.question = question;
        this.answer = answer;
        this.id = id;
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
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
