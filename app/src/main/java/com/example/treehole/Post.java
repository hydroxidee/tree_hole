package com.example.treehole;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Post {
    private String username;
    private String timestamp;
    private String postText;

    public Post(String username, String timestamp, String postText) {
        this.username = username;
        this.timestamp = timestamp;
        this.postText = postText;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPostText() {
        return postText;
    }
    public Date getParsedTimestamp(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return format.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Setters (optional, if you need to modify fields after creation)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getText() {
        return postText;
    }
}
