package com.example.tictactoeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SingleActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];
    String[][] field = new String[3][3];
    private boolean player1Turn = true;

    private int roundCount;

    private int whichBot;

    private int player1Points;
    private int player2Points;
    private int draw;
    boolean isFromHard = false;
    boolean isEnd = false;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView textViewDraw;
    Button buttonResetField;
    Button buttonResetGame;


    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        textViewPlayer1 = findViewById(R.id.text_view_player1);
        textViewPlayer2 = findViewById(R.id.text_view_player2);
        textViewDraw = findViewById(R.id.text_view_draw);
        buttonResetField = findViewById(R.id.button_reset);
        buttonResetGame = findViewById(R.id.button_reset_game);

        whichBot = getIntent().getIntExtra("whichBot", 0);

        textViewPlayer1.setTextColor(getResources().getColor(R.color.colorX));



        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }


        if(isLandscape()){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setTextSize(30);
                }
            }
        }

        buttonResetField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetField();
            }
        });

        buttonResetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if(whichBot == 1){
            ((Button) v).setTextColor(getResources().getColor(R.color.colorAppBackgroundGreen));
        }
        else if(whichBot == 2){
            ((Button) v).setTextColor(getResources().getColor(R.color.colorAppBackgroundYellow));
        }
        else if(whichBot == 3){
            ((Button) v).setTextColor(getResources().getColor(R.color.colorAppBackgroundRed));
        }
        ((Button) v).setText("X");
        textViewPlayer1.setTextColor(getResources().getColor(R.color.colorO));
        textViewPlayer2.setTextColor(getResources().getColor(R.color.colorX));

        roundCount++;
        checkForWin();

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
                return;
            }
        }
        disableButtons();
        handler.postDelayed(new Runnable() {
            public void run() {
                // действие будет выполнено через 2с
                if(whichBot == 1){
                    botEasy();
                }
                else if(whichBot == 2){
                    botMedium();
                }
                else if(whichBot == 3){
                    botHard();
                }
                roundCount++;
                player1Turn=false;
                textViewPlayer1.setTextColor(getResources().getColor(R.color.colorX));
                textViewPlayer2.setTextColor(getResources().getColor(R.color.colorO));
                enableButtons();
                if (checkForWin()) {
                    if (player1Turn) {
                        player1Wins();
                    } else {
                        player2Wins();
                    }
                } else if (roundCount == 10) {
                    draw();
                } else {
                    player1Turn = true;
                }
            }
        }, 500);



    }
    private void disableButtons(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
    private void enableButtons(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(true);
            }
        }
    }

    private boolean checkForWin() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //Checking for horizontals
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        //Checking for verticals
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        //Checking for MainDiagonal
        for (int i = 0; i < 3; i++) {
            if (field[0][0].equals(field[1][1])
                    && field[0][0].equals(field[2][2])
                    && !field[0][0].equals("")) {
                return true;
            }
        }

        //Checking for SideDiagonal
        for (int i = 0; i < 3; i++) {
            if (field[0][2].equals(field[1][1])
                    && field[0][2].equals(field[2][0])
                    && !field[0][2].equals("")) {
                return true;
            }
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        textViewPlayer1.setTextColor(getResources().getColor(R.color.colorPrimary));
        textViewPlayer2.setTextColor(getResources().getColor(R.color.colorO));
        textViewDraw.setTextColor(getResources().getColor(R.color.colorO));
        Toast.makeText(this, "You won!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();


    }

    private void player2Wins() {
        player2Points++;
        textViewPlayer1.setTextColor(getResources().getColor(R.color.colorO));
        textViewPlayer2.setTextColor(getResources().getColor(R.color.colorPrimary));
        textViewDraw.setTextColor(getResources().getColor(R.color.colorO));
        Toast.makeText(this,  "Bot won!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        draw++;
        textViewPlayer1.setTextColor(getResources().getColor(R.color.colorO));
        textViewPlayer2.setTextColor(getResources().getColor(R.color.colorO));
        textViewDraw.setTextColor(getResources().getColor(R.color.colorPrimary));
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("You: " + player1Points);
        textViewPlayer2.setText("Bot: "  + player2Points);
        textViewDraw.setText("Draw: " + draw);

    }

    private void resetBoard() {

        disableButtons();

        handler.postDelayed(new Runnable() {
            public void run() {
                // действие будет выполнено через 2с
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].setText("");
                    }
                }
                enableButtons();
                textViewPlayer1.setTextColor(getResources().getColor(R.color.colorX));
                textViewPlayer2.setTextColor(getResources().getColor(R.color.colorO));
                textViewDraw.setTextColor(getResources().getColor(R.color.colorO));
            }
        }, 2000);


        roundCount = 0;
        player1Turn = true;
    }
    private void resetField() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        textViewPlayer1.setTextColor(getResources().getColor(R.color.colorX));
        textViewPlayer2.setTextColor(getResources().getColor(R.color.colorO));
        textViewDraw.setTextColor(getResources().getColor(R.color.colorO));
        roundCount = 0;
        player1Turn = true;
    }
    private void resetGame() {
        resetField();
        player1Points = 0;
        player2Points = 0;
        draw=0;
        updatePointsText();
    }

    @Override //цей метод спрацьовує при повороті екрана і ми можемо певні дані зберегти в цьому об'єкті по ключовим словам
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putInt("draw", draw);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override //цей метод дозволяє передати справжнім змінним ті дані які були скопійовані при зміні орієнтації
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        draw = savedInstanceState.getInt("draw");
    }

    private boolean isLandscape(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void randomBot() {
        while (roundCount!= 9){
            int i = (int) (Math.random() * 3);
            int j = (int) (Math.random() * 3);
            if (field[i][j].equals("")) {
                buttons[i][j].setText("O");
                buttons[i][j].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
        }
    }

    private void botEasy(){
        for (int i = 0; i < 3; i++) { //Checking for horizontals
            if (field[i][0].equals("O") && field[i][1].equals("O") && field[i][2].equals("")) {
                buttons[i][2].setText("O");
                buttons[i][2].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
            if (field[i][0].equals("O") && field[i][2].equals("O") && field[i][1].equals("")) {
                buttons[i][1].setText("O");
                buttons[i][1].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
            if (field[i][1].equals("O") && field[i][2].equals("O") && field[i][0].equals("")) {
                buttons[i][0].setText("O");
                buttons[i][0].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
        }
        for (int i = 0; i < 3; i++) { //Checking for verticals
            if (field[0][i].equals("O") && field[1][i].equals("O") && field[2][i].equals("")) {
                buttons[2][i].setText("O");
                buttons[2][i].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
            if (field[0][i].equals("O") && field[2][i].equals("O") && field[1][i].equals("")) {
                buttons[1][i].setText("O");
                buttons[1][i].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
            if (field[1][i].equals("O") && field[2][i].equals("O") && field[0][i].equals("")) {
                buttons[0][i].setText("O");
                buttons[0][i].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
        }
        //Checking for MainDiagonal
        if (field[0][0].equals("O") && field[1][1].equals("O") && field[2][2].equals("")) {
            buttons[2][2].setText("O");
            buttons[2][2].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }
        if (field[0][0].equals("O") && field[2][2].equals("O") && field[1][1].equals("")) {
            buttons[1][1].setText("O");
            buttons[1][1].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }
        if (field[1][1].equals("O") && field[2][2].equals("O") && field[0][0].equals("")) {
            buttons[0][0].setText("O");
            buttons[0][0].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }

        //Checking for SideDiagonal
        if (field[0][2].equals("O") && field[1][1].equals("O") && field[2][0].equals("")) {
            buttons[2][0].setText("O");
            buttons[2][0].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }
        if (field[0][2].equals("O") && field[2][2].equals("O") && field[1][1].equals("")) {
            buttons[1][1].setText("O");
            buttons[1][1].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }
        if (field[1][1].equals("O") && field[2][0].equals("O") && field[0][2].equals("")) {
            buttons[0][2].setText("O");
            buttons[0][2].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }

        isEnd = true;
        if(isFromHard){
            return;
        }
        randomBot();
    }





    private void botMedium(){
        for (int i = 0; i < 3; i++) { //Checking for horizontals
            if (field[i][0].equals(field[i][1]) && !field[i][1].equals("") && field[i][2].equals("")) {
                buttons[i][2].setText("O");
                buttons[i][2].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
            if (field[i][0].equals(field[i][2]) && !field[i][2].equals("") && field[i][1].equals("")) {
                buttons[i][1].setText("O");
                buttons[i][1].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
            if (field[i][1].equals(field[i][2]) && !field[i][2].equals("") && field[i][0].equals("")) {
                buttons[i][0].setText("O");
                buttons[i][0].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
        }
        for (int i = 0; i < 3; i++) { //Checking for verticals
            if (field[0][i].equals(field[1][i]) && !field[1][i].equals("") && field[2][i].equals("")) {
                buttons[2][i].setText("O");
                buttons[2][i].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
            if (field[0][i].equals(field[2][1]) && !field[2][i].equals("") && field[1][i].equals("")) {
                buttons[1][i].setText("O");
                buttons[1][i].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
            if (field[1][i].equals(field[2][i]) && !field[2][i].equals("") && field[0][i].equals("")) {
                buttons[0][i].setText("O");
                buttons[0][i].setTextColor(getResources().getColor(R.color.colorO));
                return;
            }
        }
        //Checking for MainDiagonal
        if (field[0][0].equals(field[1][1]) && !field[1][1].equals("") && field[2][2].equals("")) {
            buttons[2][2].setText("O");
            buttons[2][2].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }
        if (field[0][0].equals(field[2][2]) && !field[2][2].equals("") && field[1][1].equals("")) {
            buttons[1][1].setText("O");
            buttons[1][1].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }
        if (field[1][1].equals(field[2][2]) && !field[2][2].equals("") && field[0][0].equals("")) {
            buttons[0][0].setText("O");
            buttons[0][0].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }


        //Checking for SideDiagonal
        if (field[0][2].equals(field[1][1]) && !field[1][1].equals("") && field[2][0].equals("")) {
            buttons[2][0].setText("O");
            buttons[2][0].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }
        if (field[0][2].equals(field[2][2]) && !field[2][2].equals("") && field[1][1].equals("")) {
            buttons[1][1].setText("O");
            buttons[1][1].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }
        if (field[1][1].equals(field[2][0]) && !field[2][0].equals("") && field[0][2].equals("")) {
            buttons[0][2].setText("O");
            buttons[0][2].setTextColor(getResources().getColor(R.color.colorO));
            return;
        }
        randomBot();

    }

    private void botHard() {
        isFromHard = true;
        botEasy();
        if(isEnd) {
            botMedium();
        }
        isEnd = false;
        isFromHard = false;
    }






}


