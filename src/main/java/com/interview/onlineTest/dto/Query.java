package com.interview.onlineTest.dto;

import java.util.List;

public class Query {

    private int id;
    private String text;
    private int type;
    private int right_answer;
    private List<String> answers;

    public Query() {
    }

    public Query(int id, String text, int type, int right_answer, List<String> answers) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.right_answer = right_answer;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(int right_answer) {
        this.right_answer = right_answer;
    }

}
