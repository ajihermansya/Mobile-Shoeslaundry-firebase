package com.example.signup_login_realtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView profileName, profileEmail, profileTelepon, userName, changePassword;
    TextView titleName; //titleUsername
    Button editprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName = findViewById(R.id.titleName1);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileTelepon = findViewById(R.id.profileTelepon);
        titleName = findViewById(R.id.titleName);
        editprofile = findViewById(R.id.editButton);
        //titleUsername = findViewById(R.id.titleUsername);

        showUserData();

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passUserData();
            }
        });
    }

    public void showUserData() {
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        String emailUser = intent.getStringExtra("email");
        //String usernameUser = intent.getStringExtra("username");
        String teleponUser = intent.getStringExtra("telepon");
        String nameuser1 = intent.getStringExtra("username");


        titleName.setText(nameUser);
        //titleUsername.setText(usernameUser);
        profileName.setText(nameUser);
        profileEmail.setText(emailUser);
        profileTelepon.setText(teleponUser);
        userName.setText(nameuser1);
    }
    public void passUserData() {
        String userTelepon = profileTelepon.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("telepon").equalTo(userTelepon);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String passwordFromDB = snapshot.child(userTelepon).child("password").getValue(String.class);
                    String nameFromDB = snapshot.child(userTelepon).child("name").getValue(String.class);
                    String teleponFromDB = snapshot.child(userTelepon).child("telepon").getValue(String.class);
                    String emailFromDB = snapshot.child(userTelepon).child("email").getValue(String.class);
                    String usernameFromDB = snapshot.child(userTelepon).child("username").getValue(String.class);

                    Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);

                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("telepon", teleponFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("username", usernameFromDB);
                    intent.putExtra("password", passwordFromDB);

                    startActivity(intent);
                   // finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
