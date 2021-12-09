package com.jumperbud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextFeedback = (EditText) findViewById(R.id.editTextFeedback);
        Button buttonSendFeedback = (Button) findViewById(R.id.button_send_feedback);

        buttonSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/html");
                i.putExtra(Intent.EXTRA_EMAIL, new String("xyz@gmail.com"));
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback From App");
                i.putExtra(Intent.EXTRA_TEXT, "Name: " + editTextName.getText() + "\nMessage: " + editTextFeedback.getText());
                try {
                    startActivity(Intent.createChooser(i, "Please select Email!"));
                }
                catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(FeedbackActivity.this, "There are no Email Clients", Toast.LENGTH_SHORT);
                }
                finally {
                    Toast.makeText(FeedbackActivity.this, "Thank you for your feedback.\nRedirecting to home page.", Toast.LENGTH_LONG);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            finish();
                        }
                    }, 3000);
                }
            }
        });
    }
}