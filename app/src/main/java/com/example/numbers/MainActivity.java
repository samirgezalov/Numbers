package com.example.numbers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> randomizedList;
    ArrayList<Integer> sortedList;


    TextView timeLeftText;
    ProgressBar progressBar;
    int timeLeft;
    int counterCorrectAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public synchronized void onTick(long millisUntilFinished) {
            timeLeftText.setText(timeLeft + "");
            progressBar.setProgress(60 - timeLeft);

            if (timeLeft > 0) {
                timeLeft--;
                Log.i("timer", timeLeft + "");
            }

            if (timeLeft <= 0) {
                Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_LONG).show();
                Log.i("timer", "TIME TO STOP");
                timer.cancel();
            }
        }


        @Override
        public void onFinish() {

            progressBar = null;
            timeLeftText = null;
            randomizedList = null;
            GridView gridView = null;

            Log.i("timer", "onFinish worked");

        }
    };

    public void startGame(View view) {

        progressBar = findViewById(R.id.progressBar);
        timeLeftText = findViewById(R.id.timeLeft);
        randomizedList = new ArrayList<>();
        timer.cancel();

        timeLeft = 60;
        progressBar.setProgress(60 - timeLeft);


        while (randomizedList.size() < 16) {

            boolean isEqualToPrevious = false;
            int j = (int) (Math.random() * 100);

            for (int k = 0; k < randomizedList.size(); k++) {
                if (randomizedList.get(k) == j) {
                    isEqualToPrevious = true;
                    break;
                }
            }
            if (!isEqualToPrevious) {
                randomizedList.add(j);
            }
        }

        sortedList = new ArrayList<>(randomizedList);
        Collections.sort(sortedList);

        GridView gridView = (GridView) findViewById(R.id.gridView);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, R.layout.buttonview, randomizedList);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int counterCorrectAnswer = 0;

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        ((Button) v).getText()+"", Toast.LENGTH_SHORT).show();

                int number = Integer.parseInt(((TextView) v).getText().toString());

                if ((sortedList.get(counterCorrectAnswer) == number)) {
                    Log.i("click", "good");
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();

                    counterCorrectAnswer++;
                    if (counterCorrectAnswer == 16) {
                        Toast.makeText(getApplicationContext(), "YOU WIN", Toast.LENGTH_SHORT).show();
                        timer.cancel();
                    }
                } else {
                    timeLeft -= 3;
                }
                Log.i("click", ((TextView) v).getText().toString());


            }
        });


        Log.i("timer", timeLeft + "");

        timer.start();


    }


    public void onClickBt(View view) {
        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

        int number = Integer.parseInt(((TextView) view).getText().toString());

        if ((sortedList.get(counterCorrectAnswer) == number)) {
            Log.i("click", "good");
            Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();

            counterCorrectAnswer++;
            if (counterCorrectAnswer == 16) {
                Toast.makeText(getApplicationContext(), "YOU WIN", Toast.LENGTH_SHORT).show();
                timer.cancel();
            }
        } else {
            timeLeft -= 3;
        }
        Log.i("click", ((TextView) view).getText().toString());

    }
}
