package com.example.treehole;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AddChangeUserScreen extends AppCompatActivity {
    private FirebaseDatabase root;
    private DatabaseReference reference;
    //hello
    // to get images
    ImageView imageView;
    Uri imageUri;

    private int type;
    String imageURL = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_change_user_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        root = FirebaseDatabase.getInstance("https://treehole-database-default-rtdb.firebaseio.com/");
        reference = root.getReference();

        type = getIntent().getIntExtra("type", 0);
        TextView title = findViewById(R.id.titleName);
        Button button = findViewById(R.id.signUpButton);

        // make it a sign up screen
        if(type == 0)
        {
            title.setText("Sign Up");
            button.setText("Sign Up");
        }
        //make it an edit profile screen
        else if(type == 1)
        {
            title.setText("Edit Profile");
            button.setText("Save");

            EditText firstInput = findViewById(R.id.firstNameInput);
            EditText lastInput = findViewById(R.id.lastNameInput);
            EditText userInput = findViewById(R.id.userInput);
            EditText passInput = findViewById(R.id.passInput);
            EditText bioInput = findViewById(R.id.bioInput);
            EditText numIDInput = findViewById(R.id.numIDInput);
            @SuppressLint("CutPasteId") Spinner roleTypeInput = findViewById(R.id.roleTypes);

            DatabaseReference userRef = reference.child("users").child(UserInfo.GetUser());

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        HashMap<String, String> info = (HashMap<String, String>) dataSnapshot.getValue();
                        firstInput.setText(info.get("first"));
                        lastInput.setText(info.get("last"));
                        userInput.setText(info.get("username") + "@usc.edu");
                        passInput.setText(info.get("password"));
                        if(!Objects.requireNonNull(info.get("bio")).isEmpty())
                        {
                            bioInput.setText(info.get("bio"));
                        }
                        numIDInput.setText(info.get("ID"));
                        roleTypeInput.setSelection(UserInfo.getRoleIndex(roleTypeInput.getSelectedItem().toString()));

                        imageURL = info.get("photo");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "loadPost:onCancelled", error.toException());
                }
            };
            userRef.addListenerForSingleValueEvent(eventListener);
        }


        // Sets up Spinner
        @SuppressLint("CutPasteId") Spinner spinner = findViewById(R.id.roleTypes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    public void onSignUpClick(View view) {
        //retrieves all of the user information
        EditText firstInput = findViewById(R.id.firstNameInput);
        String first = firstInput.getText().toString();
        EditText lastInput = findViewById(R.id.lastNameInput);
        String last = lastInput.getText().toString();
        EditText userInput = findViewById(R.id.userInput);
        String user = userInput.getText().toString();
        EditText passInput = findViewById(R.id.passInput);
        String pass = passInput.getText().toString();
        EditText bioInput = findViewById(R.id.bioInput);
        String bio = bioInput.getText().toString();
        EditText numIDInput = findViewById(R.id.numIDInput);
        String numID = numIDInput.getText().toString();
        Spinner roleTypeInput = findViewById(R.id.roleTypes);
        String roleType = roleTypeInput.getSelectedItem().toString();

        TextView error = findViewById(R.id.errorMessage);

        // validates all the input information
        boolean valid = true;
        if(first.isEmpty() || first.length() > 20)
        {
            valid = false;
            error.setText("** First name must be empty or exceed 20 characters");
        }
        else if(last.isEmpty() || last.length() > 20)
        {
            valid = false;
            error.setText("** Last name must be empty or exceed 20 characters");
        }
        else if(user.isEmpty() || !user.matches("^[a-zA-Z0-9._%+-]+@usc\\.edu$"))
        {
            valid = false;
            error.setText("** Email must be a valid USC email");
        }
        else if(pass.isEmpty() || !pass.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"))
        {
            valid = false;
            error.setText("** Password must be a minimum of 8 letters and numbers");
        }
        else if(bio.length() > 300)
        {
            valid = false;
            error.setText("** Bio must not exceed 300 characters");
        }
        else if(numID.isEmpty() || !numID.matches("\\d{10}"))
        {
            valid = false;
            error.setText("** ID Number must be a valid ID Number");
        }
        else if(type == 0 && imageURL.isEmpty())
        {
            valid = false;
            error.setText("** Please add a Profile Photo");
        }


        //created new user if validation checks all pass
        if(valid)
        {
            error.setText("");

            // creates user info map
            String shortEmail = user.substring(0, user.indexOf("@"));
            HashMap<String, Object> info = new HashMap<>();
            HashMap<String, Object> notifs = new HashMap<>();

            info.put("first", first);
            info.put("last", last);
            info.put("username", shortEmail);
            info.put("password", pass);
            info.put("bio", bio);
            info.put("ID", numID);
            info.put("role", roleType);
            info.put("photo", imageURL);

            notifs.put("academic", 0);
            notifs.put("life", 0);
            notifs.put("event", 0);

            info.put("notifs", notifs);


            DatabaseReference userRef = reference.child("users").child(shortEmail);
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Boolean connected = dataSnapshot.getValue(Boolean.class);
                    if (connected != null && connected) {
                        TextView success = findViewById(R.id.pictureSuccess);
                        success.setText("** System Down, please wait and try again");
                    }
                    //sign in - create new user
                    if(type == 0 && !dataSnapshot.exists()) {
                        //create new user
                        makeNewUser(shortEmail, info);
                    }
                    //sign in - user exists
                    else if (type == 0 && dataSnapshot.exists())
                    {
                        userExists();
                    }
                    //edit profile - save new info
                    else if(type == 1)
                    {
                        UpdateUser(shortEmail, info);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "loadPost:onCancelled", error.toException());
                }
            };
            userRef.addListenerForSingleValueEvent(eventListener);

        }
    }

    // creates new user and navigates back to sign in screen to prompt sign in
    private void makeNewUser(String shortEmail, HashMap<String, Object> info){
        reference.child("users").child(shortEmail).setValue(info);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(AddChangeUserScreen.this, MainActivity.class);
            intent.putExtra("update", 1);
            startActivity(intent);
        }, 0);
    }

    // updates user info then goes back to profile page
    private void UpdateUser(String shortEmail, HashMap<String, Object> info){
        reference.child("users").child(shortEmail).updateChildren(info);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(AddChangeUserScreen.this, ProfilePage.class);
            intent.putExtra("type", 1);
            startActivity(intent);
        }, 0);
    }

    // navigates back to sign in screen and notifies that the user already exists
    private void userExists()
    {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(AddChangeUserScreen.this, MainActivity.class);
            intent.putExtra("update", 0);
            startActivity(intent);
        }, 0);
    }

    //chick "Upload Photo" Button, prompt to open gallery
    public void onUploadPhotoClick(View view)
    {
        openGallery();
    }

    // open's phone gallery
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //saves gotten photo image url
        if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
            imageUri = data.getData();
            imageURL = PhotoUtils.UriToBase64(this, imageUri);

            TextView success = findViewById(R.id.pictureSuccess);
            success.setText("** Uploaded");
        }
    }

}