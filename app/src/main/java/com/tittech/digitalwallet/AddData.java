package com.tittech.digitalwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddData extends AppCompatActivity {
    TextView tvTitle;
    EditText edAmount,edReason;
    Button button;
    DatabaseHelper dbHelper;
    public static boolean EXPENSE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        tvTitle = findViewById(R.id.tvTitle);
        edAmount = findViewById(R.id.edAmount);
        edReason = findViewById(R.id.edReason);
        button = findViewById(R.id.button);
        dbHelper = new DatabaseHelper(this);

        if (EXPENSE==true) tvTitle.setText("Add Expense");
        if (EXPENSE==false) tvTitle.setText("Add Income");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sAmount = edAmount.getText().toString();
                String reason = edReason.getText().toString();
                double amount = Double.parseDouble(sAmount);
                if (EXPENSE==true){
                    boolean expenseInserted =dbHelper.addExpense(amount,reason);
                    if (expenseInserted ==true){
                        tvTitle.setText("Expense Added");
                        Toast.makeText(AddData.this, "Expense Added", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(AddData.this, "something wrong", Toast.LENGTH_SHORT).show();

                }
                else {
                    boolean expenseInserted =dbHelper.addIncome(amount,reason);;
                    if (expenseInserted ==true){
                        tvTitle.setText("Income Added");
                        Toast.makeText(AddData.this, "Income Added", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(AddData.this, "something wrong", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    //====================================================OnBackPressed

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}