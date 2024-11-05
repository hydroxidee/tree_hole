package com.example.treehole;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Comment {
    private String username;
    private String timestamp;
    private String commentText;
    private HashMap<String, Object> commentHash;

    public Comment(String username, String timestamp, String commentText){
        this.username = username;
        this.timestamp = timestamp;
        this.commentText = commentText;

        commentHash = new HashMap<String,Object>();
        commentHash.put("username",username);
        commentHash.put("timestamp", timestamp);
        commentHash.put("text", commentText);
    }

    public String getUsername(){return username;}
    public String getTimestamp(){return timestamp;}
    public String getCommentText(){return commentText;}

    public void setUsername(String username){this.username = username;}

    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
    public void setCommentText(String commentText){this.commentText = commentText;}
    public HashMap<String, Object> getCommentHash() { return commentHash; }
}
