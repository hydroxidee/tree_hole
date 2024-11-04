package com.example.treehole;

import java.util.HashMap;

//class stores the username (first part of usc email) so that it can be used once the user logs in
public class UserInfo {
    static String username = "";
    static HashMap<String, Integer> roleTypes;

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
    }

    public static void unfollowAcademic(){
        followingAcademic=false;
    }

    public static boolean isFollowingAcademic()
    {
        return followingAcademic;
    }

    //set following life
    public static void followLife(){
        followingLife=true;
    }

    public static void unfollowLife(){
        followingLife=false;
    }

    public static boolean isFollowingLife()
    {
        return followingLife;
    }

    //set following event
    public static void followEvent(){
        followingEvent=true;
    }

    public static void unfollowEvent(){
        followingEvent=true;
    }

    public static boolean isFollowingEvent()
    {
        return followingEvent;
    }

}
