package com.example.tictactoeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NamesActivity extends AppCompatActivity implements View.OnClickListener {

    EditText player1name;
    EditText player2name;
    Button startBtn;
    public String str_name1, str_name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);
        player1name = findViewById(R.id.edit_text_player1_name);
        player2name = findViewById(R.id.edit_text_player2_name);
        startBtn = findViewById(R.id.start_game_btn);
        startBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_game_btn:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("name1", player1name.getText().toString());
                intent.putExtra("name2", player2name.getText().toString());
                startActivity(intent);
                break;

            default:
                break;
        }
    }


}
