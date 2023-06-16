package com.example.signup_login_realtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView profileName, profileEmail, profileTelepon, userName, changePassword;
    TextView titleName; //titleUsername

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName = findViewById(R.id.titleName1);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileTelepon = findViewById(R.id.profileTelepon);
        titleName = findViewById(R.id.titleName);
        //titleUsername = findViewById(R.id.titleUsername);

        showUserData();
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
}
