package com.tuit.tuit.repository.model;

public class CurrentQuestion {
    private int questionIndex;

    public CurrentQuestion(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }


}
