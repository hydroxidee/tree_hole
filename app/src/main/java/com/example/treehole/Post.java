package com.example.treehole;

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
