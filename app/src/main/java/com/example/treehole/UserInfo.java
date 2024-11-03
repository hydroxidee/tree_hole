package com.example.treehole;

import java.util.HashMap;

//class stores the username (first part of usc email) so that it can be used once the user logs in
public class UserInfo {
    static String username = "";
    static HashMap<String, Integer> roleTypes;

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
}
