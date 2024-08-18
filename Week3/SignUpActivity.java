package com.example.imagepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class SignUpActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private ProgressBar pbLogin;
    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSignUp;
    private TextView btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();
        pbLogin = findViewById(R.id.pb_login);
        editTextEmail = findViewById(R.id.et_login_email);
        editTextUsername = findViewById(R.id.et_login_username);
        editTextPassword = findViewById(R.id.et_login_password);
        editTextConfirmPassword = findViewById(R.id.et_login_confirm_password);
        buttonSignUp = findViewById(R.id.btn_register);
        btnlogin = findViewById(R.id.login_account);


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered values
                String email = editTextEmail.getText().toString();
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();


                // Validate the fields
                if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignUpActivity.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(SignUpActivity.this, "Enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "Enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    // Passwords do not match
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pbLogin.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null && !user.isEmailVerified()) {
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> emailTask) {
                                                        if (emailTask.isSuccessful()) {
                                                            Toast.makeText(SignUpActivity.this, "Verification email sent. Please verify your email!", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(SignUpActivity.this, "Failed to send verification email. Please try again.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Registration successful. Please login.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Failed to register! Check your credentials", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                // All fields are valid, perform signup
                // Add your signup logic here

//                // For this example, we just display a success message
//                Toast.makeText(SignUpActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
//
//                // You may also navigate to another activity after successful signup
//                // For example, you can use an Intent to start the MainActivity
//                 Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                 startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add code to handle the Sign Up button click, e.g., navigate to the Sign Up activity
                //Toast.makeText(SignUpActivity.this, "Login Up Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}