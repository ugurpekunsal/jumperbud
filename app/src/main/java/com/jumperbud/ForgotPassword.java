package com.jumperbud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText loginEmail;
    private Button button_reset_password;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        loginEmail = (EditText) findViewById(R.id.loginEmail);
        button_reset_password = (Button) findViewById(R.id.button_reset_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        button_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = loginEmail.getText().toString().trim();

        if (email.isEmpty()) {
            loginEmail.setError("Email is required!");
            loginEmail.requestFocus();
            return;
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("Please provide a valid email!");
            loginEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ForgotPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                }
            }
        });
        progressBar.setVisibility(View.GONE);
    }
}