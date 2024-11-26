package com.example.treehole;

import static junit.framework.TestCase.assertEquals;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class PostCreationTests {

    public static final String USERNAME = erikal;
    @Test
    public void testPostWithValidDetails() {
        // Test Case 6: Post with Valid Details
        String username = USERNAME;
        String timestamp = "2024-11-25 10:00:00";
        String postText = "Test - valid details";
        String postTitle = "All valid details";
        String communityType = "Academic";

        Post validPost = new Post(username, timestamp, postText, postTitle, communityType);

        // Assertions
        assertEquals(username, validPost.getUsername());
        assertEquals(timestamp, validPost.getTimestamp());
        assertEquals(postText, validPost.getPostText());
        assertEquals(postTitle, validPost.getPostTitle());
        assertEquals(communityType, validPost.getCommunityType());
    }

    @Test
    public void testPostAnonymously() {
        // Test Case 7: Post Anonymously
        String username = "Anonymous";
        String timestamp = "2024-11-25 10:05:00";
        String postText = "Test - Anonymous";
        String postTitle = "Anonymous Post";
        String communityType = "Academic";

        Post anonymousPost = new Post(username, timestamp, postText, postTitle, communityType);

        // Assertions
        assertEquals("Anonymous", anonymousPost.getUsername());
        assertEquals(postText, anonymousPost.getPostText());
        assertEquals(postTitle, anonymousPost.getPostTitle());
    }

    @Test
    public void testPostWithMissingTitle() {
        // Test Case 8: Post with Missing Title
        String username = USERNAME;
        String timestamp = "2024-11-25 10:10:00";
        String postText = "Test - no title";
        String postTitle = null; // Missing title
        String communityType = "General";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Post(username, timestamp, postText, postTitle, communityType);
        });

        // Verify the exception message
        assertEquals("Post title cannot be null or empty", exception.getMessage());
    }
}
