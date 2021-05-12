package com.vella.calculatorassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {
    private TextView historyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyTextView = findViewById(R.id.historyTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("History",MODE_PRIVATE);
        String result = sharedPreferences.getString("historyList",null);
        if (result != null){
            historyTextView.setText(result);
        }
    }
}