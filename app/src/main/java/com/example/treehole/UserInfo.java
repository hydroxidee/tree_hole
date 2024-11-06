package com.example.treehole;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

//class stores the username (first part of usc email) so that it can be used once the user logs in
public class UserInfo {
    private static FirebaseDatabase root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/"); ;
    private static DatabaseReference reference = root.getReference();

    static HashMap<String, Object> notifs;

    static String username = "";
    static String firstName = "";
    static HashMap<String, Integer> roleTypes;
    //hello
    //communities
    static boolean followingAcademic = false;
    static boolean followingLife = false;
    static boolean followingEvent = false;

    // stores username
    public static void SetUser(String name)
    {
        username = name;
    }

    // gets username
    public static String GetUser()
    {
        return username;
    }

    public static void setFirstName(String name) { firstName = name; }

    public static String getFirstName() { return firstName; }

    // returns the index of the different roles (for dropdown menu)
    public static int getRoleIndex(String role)
    {
        if(roleTypes == null)
        {
            initializeRoles();
        }
        return roleTypes.get(role);
    }

    //initializes roles and role indexes
    private static void initializeRoles()
    {
        roleTypes = new HashMap<>();
        roleTypes.put("Undergrad Student", 0);
        roleTypes.put("Graduate Student", 1);
        roleTypes.put("Faculty", 2);
        roleTypes.put("Staff", 3);
    }

    //set following academic
    public static void followAcademic(){
        followingAcademic=true;

        notifs.put("academic", 1);

        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    public static void unfollowAcademic(){

        followingAcademic=false;

        notifs.put("academic", 0);

        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    public static boolean isFollowingAcademic()
    {

        return followingAcademic;
    }

    //set following life
    public static void followLife(){

        followingLife=true;

        notifs.put("life", 1);

        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    public static void unfollowLife(){
        followingLife=false;

        notifs.put("life", 0);

        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    public static boolean isFollowingLife()
    {
        return followingLife;
    }

    //set following event
    public static void followEvent(){
        followingEvent=true;

        notifs.put("event", 1);

        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    public static void unfollowEvent(){
        followingEvent=false;

        notifs.put("event", 0);

        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    public static boolean isFollowingEvent()
    {
        return followingEvent;
    }

    public static void initializeNotifs()
    {
        notifs = new HashMap<>();

        notifs.put("academic", 0);
        notifs.put("life", 0);
        notifs.put("event", 0);
    }
}
