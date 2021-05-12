package com.vella.calculatorassignments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private TextView inputTextView;
    private Boolean isLastNumeric = false;
    private Boolean isLastOperator = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputTextView = findViewById(R.id.tvInput);

        sharedPreferences = getSharedPreferences("History",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_history:
                navigateToHistoryActivity();
                break;
        }
        return true;
    }

    private void navigateToHistoryActivity() {
        Intent historyActivityIntent = new Intent(this,HistoryActivity.class);
        startActivity(historyActivityIntent);
    }

    public void onDigit(View view) {
        String text = ((Button) view).getText().toString();
        inputTextView.append(text);
        isLastNumeric = true;
    }

    public void onOperator(View view) {
        if (isLastNumeric && !isLastOperator){
            inputTextView.append(((Button)view).getText().toString());
            isLastNumeric = false;
            isLastOperator = true;
        }
    }

    public void onEqual(View view) {
        isLastOperator = false;
        if (isLastNumeric){
            String value = inputTextView.getText().toString();
            String prefix = "";
            try {
                if (value.startsWith("-")){
                    prefix = "-";
                    value = value.substring(1);
                }
                if (value.contains("*")){
                    multiplication(value,prefix);
                }else if (value.contains("+")){
                    addition(value,prefix);
                }else if (value.contains("/")){
                   division(value,prefix);
                }else if (value.contains("-")){
                    substraction(value,prefix);
                }
            }catch (Exception e){

            }
        }
    }

    private void substraction(String value, String prefix) {
        String[] splitValue = value.split("-");
        String s1 = splitValue[0];
        String s2 = splitValue[1];
        if(!prefix.isEmpty()){
            s1 = prefix + s1;
        }
        double result = Double.parseDouble(s1) - Double.parseDouble(s2);
        inputTextView.setText(String.valueOf(result));
        storeResult(value+"="+result);
    }

    private void division(String value, String prefix) {
        String[] splitValue = value.split("/");
        String s1 = splitValue[0];
        String s2 = splitValue[1];
        if(!prefix.isEmpty()){
            s1 = prefix + s1;
        }
        double result = Double.parseDouble(s1) / Double.parseDouble(s2);
        inputTextView.setText(String.valueOf(result));
        storeResult(value+"="+result);
    }

    private void addition(String value, String prefix) {
        String[] splitValue = value.split("\\+");
        String s1 = splitValue[0];
        String s2 = splitValue[1];
        if(!prefix.isEmpty()){
            s1 = prefix + s1;
        }
        double result = Double.parseDouble(s1) + Double.parseDouble(s2);
        inputTextView.setText(String.valueOf(result));
        storeResult(value+"="+result);
    }

    private void multiplication(String value, String prefix) {
        String[] splitValue = value.split("\\*");
        String s1 = splitValue[0];
        String s2 = splitValue[1];
        if(!prefix.isEmpty()){
            s1 = prefix + s1;
        }
        double result = Double.parseDouble(s1) * Double.parseDouble(s2);
        inputTextView.setText(String.valueOf(result));
        storeResult(value+"="+result);
    }

    private void storeResult(String result) {
        String oldResult = sharedPreferences.getString("historyList",null);
        if (oldResult != null){
            result = result + "\n" +oldResult;
        }
        editor.putString("historyList", result);
        editor.commit();
    }

    public void onClear(View view) {
        inputTextView.setText("");
        isLastNumeric = false;
        isLastOperator = false;
    }

    @Override
    protected void onDestroy() {
        editor.clear();
        editor.commit();
        super.onDestroy();
    }
}