package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DifficultyActivity extends AppCompatActivity implements View.OnClickListener {

    Button startBtn;
    RadioGroup radioGroup;
    RadioButton rb_easy;
    RadioButton rb_medium;
    RadioButton rb_hard;
    LinearLayout back;
    TextView textView;

    int bot = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        startBtn = findViewById(R.id.start_game_btn);
        startBtn.setOnClickListener(this);
        radioGroup = findViewById(R.id.radioGroup);
        rb_easy = findViewById(R.id.radio_btn_easy);
        rb_medium = findViewById(R.id.radio_btn_medium);
        rb_hard = findViewById(R.id.radio_btn_hard);
        textView = findViewById(R.id.text_view_names);
        back = findViewById(R.id.back);

        back.setBackgroundColor(getResources().getColor(R.color.colorAppBackgroundGreen));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_btn_easy:
                        bot = 1;
                        textView.setTextColor(getResources().getColor(R.color.WHITE));
                        rb_easy.setTextColor(getResources().getColor(R.color.WHITE));
                        rb_medium.setTextColor(getResources().getColor(R.color.WHITE));
                        rb_hard.setTextColor(getResources().getColor(R.color.WHITE));
                        back.setBackgroundColor(getResources().getColor(R.color.colorAppBackgroundGreen));

                        break;
                    case R.id.radio_btn_medium:
                        back.setBackgroundColor(getResources().getColor(R.color.colorAppBackgroundYellow));
                        textView.setTextColor(getResources().getColor(R.color.BLACK));
                        rb_easy.setTextColor(getResources().getColor(R.color.BLACK));
                        rb_medium.setTextColor(getResources().getColor(R.color.BLACK));
                        rb_hard.setTextColor(getResources().getColor(R.color.BLACK));
                        bot = 2;

                        break;
                    case R.id.radio_btn_hard:
                        back.setBackgroundColor(getResources().getColor(R.color.colorAppBackgroundRed));
                        bot = 3;
                        textView.setTextColor(getResources().getColor(R.color.WHITE));
                        rb_easy.setTextColor(getResources().getColor(R.color.WHITE));
                        rb_medium.setTextColor(getResources().getColor(R.color.WHITE));
                        rb_hard.setTextColor(getResources().getColor(R.color.WHITE));

                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.start_game_btn:
                Intent intent2 = new Intent(this, SingleActivity.class);
                intent2.putExtra("whichBot", bot);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
