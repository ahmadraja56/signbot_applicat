package com.example.imagepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar pbLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView buttonSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        pbLogin = findViewById(R.id.pb_login);
        editTextUsername = findViewById(R.id.et_login_email);
        editTextPassword = findViewById(R.id.et_login_password);
        buttonLogin = findViewById(R.id.btn_login);
        buttonSignUp = findViewById(R.id.sign_up);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered username and password

                LoginUser();
//                String username = editTextUsername.getText().toString();
//                String password = editTextPassword.getText().toString();
//
//                // Check if the username and password are valid
//                if (isValidLogin(username, password)) {
//                    // Successful login
//                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    // Add code to navigate to the next screen or perform other actions after successful login
//                } else {
//                    // Invalid login
//                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add code to handle the Sign Up button click, e.g., navigate to the Sign Up activity
                //Toast.makeText(LoginActivity.this, "Sign Up Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }


    private void LoginUser() {
        String email = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextUsername.setError("Email Required!");
            editTextUsername.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextUsername.setError("Invalid Email");
            editTextUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password Required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Min 6 characters required!");
            editTextPassword.requestFocus();
            return;
        }

        pbLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (!user.isEmailVerified()) {
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Please verify your email first!", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Failed to login! Check your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isValidLogin(String username, String password) {
        // This is a simple example. You should implement your own logic for checking credentials.
        // For example, you might check against a server or a local database.
        return username.equals("") && password.equals("");
    }
}