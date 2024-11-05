package com.example.treehole;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Comment {
    private String username;
    private String timestamp;
    private String commentText;
    public Comment(String username, String timestamp, String commentText){
        this.username = username;
        this.timestamp = timestamp;
        this.commentText = commentText;
    }

    public String getUsername(){return username;}
    public String getTimestamp(){return timestamp;}
    public String getCommentText(){return commentText;}

    public void setUsername(String username){this.username = username;}

    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
    public void setCommentText(String commentText){this.commentText = commentText;}
}
