package com.example.treehole;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Post {
    private String username;
    private String timestamp;
    private String postText;
    private List<Comment> postComments;

    // Constructor with comments
    public Post(String username, String timestamp, String postText, List<Comment> comments) {
        this.username = username;
        this.timestamp = timestamp;
        this.postText = postText;
        this.postComments = comments != null ? comments : new ArrayList<>();
    }

    // Constructor without comments
    public Post(String username, String timestamp, String postText) {
        this(username, timestamp, postText, new ArrayList<>());
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

    public List<Comment> getComments() {
        return postComments;
    }

    public Date getParsedTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return format.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to add a comment
    public void addComment(Comment comment) {
        postComments.add(comment);
    }

    // Setters (optional, if you need to modify fields after creation)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setPostComments(List<Comment> comments) {
        this.postComments = comments;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getText() {
        return postText;
    }
}
