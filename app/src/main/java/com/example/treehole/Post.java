package com.example.treehole;

public class Post {
    private String username;
    private String timestamp;
    private String postText;
    private String postTitle;

    public Post(String username, String timestamp, String PostTitle, String postText) {
        this.username = username;
        this.timestamp = timestamp;
        this.postText = postText;
        this.postTitle = PostTitle;
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
    public String getPostTitle(){return postTitle;}

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

}

