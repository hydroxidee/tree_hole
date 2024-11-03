package com.example.treehole;

import java.util.HashMap;

public class UserInfo {
    static String username = "";
    static HashMap<String, Integer> roleTypes;

    public static void SetUser(String name)
    {
        username = name;
    }

    public static String GetUser()
    {
        return username;
    }

    public static int getRoleIndex(String role)
    {
        if(roleTypes == null)
        {
            initializeRoles();
        }
        return roleTypes.get(role);
    }

    private static void initializeRoles()
    {
        roleTypes = new HashMap<>();
        roleTypes.put("Undergrad Student", 0);
        roleTypes.put("Graduate Student", 1);
        roleTypes.put("Faculty", 2);
        roleTypes.put("Staff", 3);
    }
}
