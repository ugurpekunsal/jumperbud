package com.jumperbud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jumperbud.admin.AdminActivity;
import com.jumperbud.loginandregister.LoginActivity;
import com.jumperbud.models.User;

public class HomeScreenActivity extends AppCompatActivity {

    private ImageButton logout, profile, statistics, exercise, settings, feedback;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private boolean isAdmin;
    private TextView textViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        logout = (ImageButton) findViewById(R.id.button_logout);
        profile = (ImageButton) findViewById(R.id.button_profile);
        statistics = (ImageButton) findViewById(R.id.button_statistics);
        exercise = (ImageButton) findViewById(R.id.button_exercise);
        settings = (ImageButton) findViewById(R.id.button_settings);
        feedback = (ImageButton) findViewById(R.id.button_feedback);
        textViewProfile = (TextView) findViewById(R.id.textViewProfile);

        isAdmin = false;

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeScreenActivity.this, LoginActivity.class));
            }
        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    if (userProfile.admin) {
                        isAdmin = true;
                        textViewProfile.setText("Admin Panel");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeScreenActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdmin) {
                    startActivity(new Intent(HomeScreenActivity.this, AdminActivity.class));
                }
                else {
                    startActivity(new Intent(HomeScreenActivity.this, ProfileActivity.class));
                }
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenActivity.this, StatisticsActivity.class));
            }
        });

        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenActivity.this, ExerciseActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenActivity.this, SettingsActivity.class));
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenActivity.this, FeedbackActivity.class));
            }
        });
    }
}