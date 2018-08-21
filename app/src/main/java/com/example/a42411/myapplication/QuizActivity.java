package com.example.a42411.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;

public class QuizActivity extends AppCompatActivity  {
    private TextView quiz_content;
    private  EditText user_ans;
    private Button back_button;
    private Button submit_button;
    private TextView tv_timer;
    private int counter = 0;
    private String input;
    int flag;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        tv_timer = (TextView)findViewById(R.id.tv_timer);
//        TimerActivity timerActivity = new TimerActivity();
//        timerActivity.

        Intent i =getIntent();
        final String snippet = i.getStringExtra("q");
        final String snippetAnswer = i.getStringExtra("a");
        flag = i.getIntExtra("flag",-2);
        if(snippet!=null){
            new CountDownTimer(120_000,1_000){
                public void onTick(long l){
                    tv_timer.setText(l/1000+" Sec");
                    //counter++;
                }
                public void onFinish(){
                    tv_timer.setText("Finish!");
                }
            }.start();

        }

        quiz_content = (TextView)findViewById(R.id.quiz_content);
        quiz_content.setText(snippet);
        user_ans = (EditText)findViewById(R.id.user_ans);
        back_button = (Button)findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        submit_button = (Button)findViewById(R.id.submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = user_ans.getText().toString();
                Log.e("INPUT:  ",input);
                Log.e("Ans:  ",snippetAnswer);
                if(!(input.equals(""))){
                    if(input.equalsIgnoreCase(snippetAnswer)){
                        Toast.makeText(QuizActivity.this,"Correct!!",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(QuizActivity.this,MainsActivity.class);
                        i.putExtra("flag",flag);
                        startActivity(i);
                    }else {
                        Toast.makeText(QuizActivity.this,"Wrong Answer!!",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(QuizActivity.this,MainsActivity.class);
//                        i.putExtra("flag",flag);
//                        startActivity(i);
                    }

                }else {
                    Toast.makeText(QuizActivity.this,"Whoops,your answer must not be null!",Toast.LENGTH_SHORT).show();
                    Log.i("Test","Toast is ok!");
                }
            }
        });
    }
}
