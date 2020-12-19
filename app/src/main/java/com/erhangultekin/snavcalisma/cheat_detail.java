package com.erhangultekin.snavcalisma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class cheat_detail extends AppCompatActivity {
    TextView cheatTextView;
    Button cheatAnswerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat_detail);

        cheatTextView=findViewById(R.id.cheatTextView);
        cheatAnswerButton=findViewById(R.id.cheatAnswerButton);

        Intent intent=getIntent();
        String question=intent.getStringExtra("question");
        boolean answer=intent.getBooleanExtra("answer",true);


        Toast.makeText(cheat_detail.this,"CHEATING",Toast.LENGTH_LONG).show();

        cheatAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheatTextView.setText("Question :"+question+" Answer :"+answer);

            }
        });


    }
}