package com.example.signup_login_realtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupUsername, signupEmail, signupPassword, signupTelepon;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    CheckBox showpwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_Name);
        signupUsername = findViewById(R.id.signup_username);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_Button);
        signupTelepon = findViewById(R.id.signup_Telepon);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        showpwd = findViewById(R.id.showPwd);
        showpwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b) {
                    signupPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    signupPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = signupName.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String username = signupUsername.getText().toString().trim();
                String telepon = signupTelepon.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();

                if (validateFields(name, email, username, password, telepon)) {
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");

                    HelperClass helperClass = new HelperClass(name, email, username, password, telepon);
                    reference.child(telepon).setValue(helperClass);

                    Toast.makeText(SignupActivity.this, "You have signed up successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean validateFields(String name, String email, String username, String password, String telepon) {
        if (!isValidname(name)) {
            signupName.setError("Name cannot be empty string characters");
            return false;
        }
        if (username.length() < 6 ) {
            signupUsername.setError("Username be long 6 characters");
            return false;
        }
        if (!isValidphone(telepon)) {
            signupTelepon.setError("Invalid phone format");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Invalid email format");
            return false;
        }

        if (!isValidpassword(password)) {
            signupPassword.setError("Password must be at least 6 characters long and contain alphanumeric characters");
            return false;
        }

        return true;
    }

    public boolean isValidpassword(String password) {
        if (password.length() < 6) {
            return false;
        }
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).*$";
        return password.matches(regex);
    }

    public boolean isValidname(String password) {
        String regex = "^[a-zA-Z]+$";
        return password.matches(regex);
    }

    public boolean isValidphone(String telepon) {
        if (telepon.length() < 11 || telepon.length() >= 14) {
            return false;
        }
        String regex = "^[0-9]+$";
        return telepon.matches(regex);
    }


}
