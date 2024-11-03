package com.example.treehole;

public class UserInfo {
    static String username = "";

    public static void SetUser(String name)
    {
        username = name;
    }

    public static String GetUser()
    {
        return username;
    }
}
