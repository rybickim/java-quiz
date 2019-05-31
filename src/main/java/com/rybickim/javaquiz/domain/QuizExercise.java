package com.rybickim.javaquiz.domain;

public class QuizExercise {

    private String question;
    private String answer;

    public QuizExercise(String question, String answer) {
        this.question = question;
        this.answer = answer;
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

    @Override
    public String toString() {
        return "QuizExercise{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}