package com.example.treehole;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserInfo {
    private static FirebaseDatabase root;
    private static DatabaseReference reference;

    static HashMap<String, Object> notifs;

    static String username = "";
    static String firstName = "";
    static HashMap<String, Integer> roleTypes;

    // Communities
    static boolean followingAcademic = false;
    static boolean followingLife = false;
    static boolean followingEvent = false;

    static {
        initializeNotifs();
    }

    // Set a mock DatabaseReference instance for testing
    public static void setDatabaseReference(DatabaseReference mockDatabaseReference) {
        reference = mockDatabaseReference;
    }

    // Set a mock FirebaseDatabase instance for testing
    public static void setFirebaseDatabase(FirebaseDatabase mockFirebaseDatabase) {
        root = mockFirebaseDatabase;
        reference = mockFirebaseDatabase.getReference();
    }

    // Ensure Firebase is lazily initialized for production
    private static void ensureFirebaseInitialized() {
        if (reference == null) { // Add this check
            if (root == null) {
                root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
            }
            reference = root.getReference();
        }
    }

    // Initialize the notifications map
    public static void initializeNotifs() {
        notifs = new HashMap<>();
        notifs.put("academic", 0);
        notifs.put("life", 0);
        notifs.put("event", 0);
    }

    // Store the username
    public static void SetUser(String name) {
        username = name;
    }

    // Get the username
    public static String GetUser() {
        return username;
    }

    // Set the user's first name
    public static void setFirstName(String name) {
        firstName = name;
    }

    // Get the user's first name
    public static String getFirstName() {
        return firstName;
    }

    // Get the role index for dropdown menu
    public static int getRoleIndex(String role) {
        if (roleTypes == null) {
            initializeRoles();
        }
        return roleTypes.get(role);
    }

    // Initialize roles and their indices
    private static void initializeRoles() {
        roleTypes = new HashMap<>();
        roleTypes.put("Undergrad Student", 0);
        roleTypes.put("Graduate Student", 1);
        roleTypes.put("Faculty", 2);
        roleTypes.put("Staff", 3);
    }

    // Follow the Academic community
    public static void followAcademic() {
        ensureFirebaseInitialized();
        followingAcademic = true;
        notifs.put("academic", 1);
        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    // Unfollow the Academic community
    public static void unfollowAcademic() {
        ensureFirebaseInitialized();
        followingAcademic = false;
        notifs.put("academic", 0);
        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    // Check if the user is following the Academic community
    public static boolean isFollowingAcademic() {
        return followingAcademic;
    }

    // Follow the Life community
    public static void followLife() {
        ensureFirebaseInitialized();
        followingLife = true;
        notifs.put("life", 1);
        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    // Unfollow the Life community
    public static void unfollowLife() {
        ensureFirebaseInitialized();
        followingLife = false;
        notifs.put("life", 0);
        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    // Check if the user is following the Life community
    public static boolean isFollowingLife() {
        return followingLife;
    }

    // Follow the Event community
    public static void followEvent() {
        ensureFirebaseInitialized();
        followingEvent = true;
        notifs.put("event", 1);
        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    // Unfollow the Event community
    public static void unfollowEvent() {
        ensureFirebaseInitialized();
        followingEvent = false;
        notifs.put("event", 0);
        reference.child("users").child(GetUser()).child("notifs").updateChildren(notifs);
    }

    // Check if the user is following the Event community
    public static boolean isFollowingEvent() {
        return followingEvent;
    }
}