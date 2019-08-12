package com.rybickim.javaquiz.utils;

public enum QuizDifficulty {
    EASY(5),
    MEDIUM(15),
    HARD(30);

    public int noOfQuestions;

    private QuizDifficulty(int noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }
}
