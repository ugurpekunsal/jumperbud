package com.jumperbud.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jumperbud.HomeScreenActivity;
import com.jumperbud.R;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = {1, 6, 0},
    k = 1,
    d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"},
    d2 = {"Lcom/example/jumperbud/LoginAndRegistration;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}
)
public final class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private TextView txt_attempts;
    private Button login, register, forgot_password;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private int attempts = 3;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.loginEmail);
        editTextPassword = (EditText) findViewById(R.id.loginPassword);

        login = (Button) findViewById(R.id.button_login);
        register = (Button) findViewById(R.id.button_register);
        forgot_password = (Button) findViewById(R.id.button_forgot_password);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgot_password.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                userLogin();
                break;
            case R.id.button_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.button_forgot_password:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        txt_attempts = (TextView) findViewById(R.id.txt_attempts);
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.GONE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //if (user.isEmailVerified()) {
                    if (true) {
                        startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials.", Toast.LENGTH_SHORT).show();
                    txt_attempts.setText(attempts + " attempts left before password is reset.");
                    attempts--;
                    if (attempts == 0) {
                        resetPassword();
                    }
                }
            }
        });
        progressBar.setVisibility(View.GONE);
    }

    private void resetPassword() {
        String email = editTextEmail.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                    attempts = 3;
                }
                else {
                    Toast.makeText(LoginActivity.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                }
            }
        });
        progressBar.setVisibility(View.GONE);
    }
}
