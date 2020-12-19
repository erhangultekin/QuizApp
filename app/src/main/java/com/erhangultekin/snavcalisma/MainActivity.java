package com.erhangultekin.snavcalisma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView scoreTextView;
    Button cheatButton;
    ImageView nextButton;
    ImageView previousButton;
    Button trueButton;
    Button falseButton;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    int currentIndex=0;
    int dogru=0;
    int yanlis=0;
    int score=0;
    Button resetButton;
    SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView=findViewById(R.id.textView);
        scoreTextView=findViewById(R.id.scoreTextView);
        resetButton=findViewById(R.id.resetButton);

        previousButton=findViewById(R.id.previousButton); //calisiyor
        nextButton=findViewById(R.id.nextButton); // calisiyor
        cheatButton=findViewById(R.id.cheatButton);
        trueButton=findViewById(R.id.trueButton);
        falseButton=findViewById(R.id.falseButton);

        Question question1=new Question("Ankara Türkiye'nin Başkentidir",true);
        Question question2=new Question("Su 50C'de kaynar",false);
        Question question3=new Question("Messi Arjantinlidir",true);
        Question question4=new Question("Erhan Mega Seksi Biridir",false);
        Question question5=new Question("Rafael Nadal'ın doğduğu yer İspanyadır",true);

        questionArrayList.add(question1);
        questionArrayList.add(question2);
        questionArrayList.add(question3);
        questionArrayList.add(question4);
        questionArrayList.add(question5);

        boolean [] answerArray=new boolean[questionArrayList.size()];

        sharedPreferences =this.getSharedPreferences("com.erhangultekin.snavcalisma", Context.MODE_PRIVATE);
        int storedScore= sharedPreferences.getInt("storedScore",0);



        String questionx = questionArrayList.get(currentIndex).getQuestion();
        textView.setText(questionx);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("SCORE BOARD");
                if (storedScore==0){
                    alert.setMessage("Your Best Score :");
                }else {
                    alert.setMessage("Your Best Score :"+storedScore);
                }

                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dogru=0;
                        yanlis=0;
                        score=0;
                        currentIndex=0;
                        textView.setText(questionArrayList.get(currentIndex).getQuestion());
                        scoreTextView.setText("Score :"+score);
                        trueButton.setEnabled(true);
                        falseButton.setEnabled(true);
                        for(int j=0;j<answerArray.length;j++){
                            answerArray[j]=false;
                        }
                    }
                });
                alert.show();





            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex<questionArrayList.size()-1){
                    currentIndex++;
                    String questionx = questionArrayList.get(currentIndex).getQuestion();
                    textView.setText(questionx);
                }else{
                    currentIndex=0;
                    String questionx = questionArrayList.get(currentIndex).getQuestion();
                    textView.setText(questionx);
                }
                if(answerArray[currentIndex]!=true){
                    trueButton.setEnabled(true);
                    falseButton.setEnabled(true);
                }else {
                    falseButton.setEnabled(false);
                    trueButton.setEnabled(false);
                }


            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex>0){
                    currentIndex--;
                    String questionx = questionArrayList.get(currentIndex).getQuestion();
                    textView.setText(questionx);
                }else {
                    currentIndex=questionArrayList.size()-1;
                    String questionx = questionArrayList.get(currentIndex).getQuestion();
                    textView.setText(questionx);

                }
                if(answerArray[currentIndex]!=true){
                    trueButton.setEnabled(true);
                    falseButton.setEnabled(true);
                }else {
                    falseButton.setEnabled(false);
                    trueButton.setEnabled(false);
                }


            }
        });
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (score!=0){
                    if(score>storedScore){
                        int userScore=(score);
                        sharedPreferences.edit().putInt("storedScore",userScore).apply();
                    }
                }
                answerArray[currentIndex]=true;
                if(questionArrayList.get(currentIndex).isAnswer()==true){
                    dogru++;
                    score+=10;
                    Toast.makeText(MainActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                }else{
                    yanlis++;
                    Toast.makeText(MainActivity.this,"Incorrect",Toast.LENGTH_SHORT).show();

                }
                falseButton.setEnabled(false);
                trueButton.setEnabled(false);
                scoreTextView.setText("Score :"+score);            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerArray[currentIndex]=true;
                if(questionArrayList.get(currentIndex).isAnswer()==false){
                    score+=10;
                    dogru++;
                    Toast.makeText(MainActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                }else{
                    yanlis++;
                    Toast.makeText(MainActivity.this,"Incorrect",Toast.LENGTH_SHORT).show();
                }
                falseButton.setEnabled(false);
                trueButton.setEnabled(false);
                scoreTextView.setText("Score :"+score);
            }
        });
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(MainActivity.this,cheat_detail.class);
                intent.putExtra("answer",questionArrayList.get(currentIndex).isAnswer());
                intent.putExtra("question",questionArrayList.get(currentIndex).getQuestion());
                startActivity(intent);
                falseButton.setEnabled(false);
                trueButton.setEnabled(false);
            }
        });

    }

}