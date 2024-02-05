package com.tittech.digitalwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvFinalBalance,tvTotalExpense,tvAddExpense,tvShowAllDataExpense,tvTotalIncome,tvAddIncome,tvShowAllDataIncome;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFinalBalance = findViewById(R.id.tvFinalBalance);
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        tvAddExpense = findViewById(R.id.tvAddExpense);
        tvShowAllDataExpense = findViewById(R.id.tvShowAllDataExpense);
        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        tvAddIncome = findViewById(R.id.tvAddIncome);
        tvShowAllDataIncome = findViewById(R.id.tvShowAllDataIncome);
        dbHelper = new DatabaseHelper(this);

        tvAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData.EXPENSE=true;
                startActivity(new Intent(MainActivity.this,AddData.class));

            }
        });

        tvAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData.EXPENSE=false;
                startActivity(new Intent(MainActivity.this,AddData.class));

            }
        });

        tvShowAllDataExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowData.EXPENSE= true;
                startActivity(new Intent(MainActivity.this, ShowData.class));
            }
        });

        tvShowAllDataIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowData.EXPENSE = false;
                startActivity(new Intent(MainActivity.this, ShowData.class));
            }
        });




    }

    //=================================================UpdateUI
    public void updateUI(){
        tvTotalExpense.setText("BDT "+dbHelper.calculateTotalExpense());
        tvTotalIncome.setText("BDT "+dbHelper.calculateTotalIncome());

        double balance = dbHelper.calculateTotalIncome()-dbHelper.calculateTotalExpense();
        tvFinalBalance.setText("BDT "+balance);

    }
    //=================================================UpdateUI
    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateUI();
    }


}