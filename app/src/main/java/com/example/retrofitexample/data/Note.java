package com.example.retrofitexample.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eldor Turgunov on 11.07.2022.
 * Retrofit Example
 * eldorturgunov777@gmail.com
 */
public class Note {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("userId")
    private int userId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public Note() {
    }

    public Note(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Note(int id, String title, String body, int userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
}

