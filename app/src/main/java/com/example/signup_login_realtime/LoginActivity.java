package com.example.signup_login_realtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText loginTelepon, loginPassword;
    Button loginButton;
    TextView signupRedirectText;
    CheckBox showpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTelepon = findViewById(R.id.login_Telepon);
        loginPassword = findViewById(R.id.login_Password);
        signupRedirectText = findViewById(R.id.signUpRedirectText);
        loginButton = findViewById(R.id.login_Button);


        showpwd = findViewById(R.id.loginshowPwd);
        showpwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b) {
                    loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUsername() && validatePassword()) {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }


    public boolean validateUsername() {
        String val = loginTelepon.getText().toString().trim();
        if (!isValidtelepon(val)) {
            loginTelepon.setError("Invalid phone format");
            return false;
        } else {
            loginTelepon.setError(null);
            return true;
        }

    }

    public boolean isValidtelepon(String loginTelepon) {
        if (loginTelepon.length() < 11 || loginTelepon.length() >= 14) {
            return false;
        }
        String regex = "^[0-9]+$";
        return loginTelepon.matches(regex);
    }


    public boolean validatePassword() {
        String val = loginPassword.getText().toString().trim();
        if (!isValidloginPassword(val)) {
            loginPassword.setError("Password must be at least 6 characters long and contain alphanumeric characters");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public boolean isValidloginPassword(String password) {
        if (password.length() < 6) {
            return false;
        }
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).*$";
        return password.matches(regex);
    }

    public void checkUser() {
        String userTelepon = loginTelepon.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("telepon").equalTo(userTelepon);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    loginTelepon.setError(null);
                    String passwordFromDB = snapshot.child(userTelepon).child("password").getValue(String.class);
                    String nameFromDB = snapshot.child(userTelepon).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(userTelepon).child("email").getValue(String.class);
                    String usernameFromDB = snapshot.child(userTelepon).child("username").getValue(String.class);

                    if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                        loginPassword.setError(null);
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                        finish();
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginTelepon.setError("User does not exist");
                    loginTelepon.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
