package com.example.myapplication;

public class Article {
    private int id;
    private String title;
    private String content;
    private String author;
    private String date;

    public Article(int id, String title, String content, String author, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
}

