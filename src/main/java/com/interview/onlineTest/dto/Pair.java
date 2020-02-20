package com.interview.onlineTest.dto;


public class Pair {
    private int queryId;
    private String text;
    private String answer;

    public Pair(int queryId, String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    public Pair() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQueryId() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
