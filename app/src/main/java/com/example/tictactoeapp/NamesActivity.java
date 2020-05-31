package com.example.tictactoeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NamesActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name1;
    EditText name2;
    Button startBtn;
    public String str_name1, str_name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);
        name1 = findViewById(R.id.edit_text_player1_name);
        name2 = findViewById(R.id.edit_text_player2_name);
        startBtn = findViewById(R.id.start_game_btn);
        startBtn.setOnClickListener(this);
        str_name1 = name1.getText().toString();
        str_name2 = name2.getText().toString();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_game_btn:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public String getName1(){
        return str_name1;
    }
    public String getName2(){
        return str_name2;
    }


}
