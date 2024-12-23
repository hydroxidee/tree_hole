package com.example.treehole;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Post {
    private String username;
    private String timestamp;
    private String postText;
    private String postTitle;
    private String communityType;
    private List<Comment> commentList;
    private HashMap<String, Object> commentHash;
    private HashMap<String, Object> postHash;

    public Post(String username, String timestamp, String postText, String postTitle,String communityType) {
        // make sure there are no null inputs
//        if (username == null || timestamp == null || postText == null || postTitle == null || communityType == null) {
//            throw new IllegalArgumentException("Please fill in all fields.");
//        }

        this.username = (username != null) ? username : "Anonymous";
        this.timestamp = (timestamp != null) ? timestamp : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        this.postText = (postText != null) ? postText : "";
        this.postTitle = (postTitle != null) ? postTitle : "Untitled"; // Default to "Untitled" if title is null
        this.communityType = (communityType != null) ? communityType : "General"; // Default to "General" if communityType is null

//        this.username = username;
//        this.timestamp = timestamp;
//        this.postText = postText;
//        this.postTitle = postTitle;
//        this.communityType = communityType;

        postHash = new HashMap<>();

        postHash.put("username", username);
        postHash.put("timestamp", timestamp);
        postHash.put("title",postTitle);
        postHash.put("text", postText);
        postHash.put("community", communityType);

        this.commentList = new ArrayList<>();
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

    public Date getParsedTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return format.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Comment> getComments() {
        return commentList;
    }

    public String getCommunityType() {
        return communityType;
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    // Setters (optional, if you need to modify fields after creation)
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPostTitle(String title){
        this.postTitle = title;
    }
    public void setCommentList(List<Comment> list){this.commentList = list;}
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void addCommunityType(String type) {
        this.communityType = type;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getText() {
        return postText;
    }

    public HashMap<String, Object> getPostHash() { return postHash; }
}