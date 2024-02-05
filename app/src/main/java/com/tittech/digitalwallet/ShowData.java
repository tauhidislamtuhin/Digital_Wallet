package com.tittech.digitalwallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SimpleTimeZone;

public class ShowData extends AppCompatActivity {
    ListView listView;
    TextView tvTitle;
    DatabaseHelper dbHelper;
    ArrayList<HashMap<String,String>> arrayList;
    HashMap<String,String>hashMap;
    public static boolean EXPENSE = true;

    TextView tvReason;
    TextView tvAmount;
    TextView tvUpdate;
    TextView tvDelete;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        listView = findViewById(R.id.listView);
        tvTitle = findViewById(R.id.tvTitle);
        dbHelper = new DatabaseHelper(this);

        if (EXPENSE==true)tvTitle.setText("Showing All Expense");
        else tvTitle.setText("Showing All Income");


        loadData();


    }

    public void loadData(){
        Cursor cursor = null;
        if (EXPENSE==true)cursor = dbHelper.showAllExpense();
        else cursor = dbHelper.showAllIncome();



        if (cursor!=null && cursor.getCount()>0){
            arrayList = new ArrayList<>();

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                double amount = cursor.getDouble(1);
                String reason = cursor.getString(2);
                String time = cursor.getString(3);

                hashMap = new HashMap<>();
                hashMap.put("id",""+id);
                hashMap.put("amount",""+amount);
                hashMap.put("reason",reason);
                hashMap.put("time",time);
                arrayList.add(hashMap);
            }

            listView.setAdapter(new MyAdapter());

        }
        else {
            tvTitle.append("\nNo Data Found");
        }
    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("MissingInflatedId")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View myView = inflater.inflate(R.layout.show_data_item,parent,false);

           tvReason = myView.findViewById(R.id.tvReason);
           tvAmount = myView.findViewById(R.id.tvamount);
           tvUpdate = myView.findViewById(R.id.tvUpdate);
           tvDelete = myView.findViewById(R.id.tvDelete);
           image = myView.findViewById(R.id.image);
           TextView tvTime = myView.findViewById(R.id.tvTime);
           if (EXPENSE==false)image.setImageResource(R.drawable.income);


            hashMap = arrayList.get(position);
            String id = hashMap.get("id");
            String reason = hashMap.get("reason");
            String amount = hashMap.get("amount");
            String time = hashMap.get("time");


            tvReason.setText(reason);
            tvAmount.setText("BDT "+amount);
            tvTime.setText(time);

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (EXPENSE==true) dbHelper.deleteByIdExpense(id);
                    else dbHelper.deleteByIdIncome(id);

                    loadData();

                }
            });

            tvUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ShowData.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.update, null);
                    dialogBuilder.setView(dialogView);

                    EditText edAmount =dialogView.findViewById(R.id.edAmount);
                    EditText edReason = dialogView.findViewById(R.id.edReason);
                    edAmount.setText(amount);
                    edReason.setText(reason);

                    dialogBuilder.setTitle("Update Your Record");
                    dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String amountStr = edAmount.getText().toString();
                            double dAmount = Double.parseDouble(amountStr);
                            String reasonStr = edReason.getText().toString();
                            boolean isUpdate = false;

                            if(EXPENSE==true){
                                isUpdate = dbHelper.updateExpense(id,dAmount,reasonStr);
                            }else {
                                isUpdate = dbHelper.updateIncome(id,dAmount,reasonStr);
                            }

                            if (isUpdate==true){
                                Toast.makeText(ShowData.this, "Update", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ShowData.this, "Not Update", Toast.LENGTH_SHORT).show();
                            }

                            loadData();

                        }
                    });
                    dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //pass
                        }
                    });

                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                }

            });


            return myView;
        }
    }




    @SuppressLint("MissingInflatedId")
    private void update() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update, null);
        dialogBuilder.setView(dialogView);

        EditText edAmount =dialogView.findViewById(R.id.edAmount);
        EditText edReason = dialogView.findViewById(R.id.edReason);
        edAmount.setText((CharSequence) tvAmount);
        edReason.setText((CharSequence) tvAmount);

        dialogBuilder.setTitle("Update Your Record");
        dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String amountStr = edAmount.getText().toString();
                double dAmount = Double.parseDouble(amountStr);
                String reasonStr = edReason.getText().toString();

                boolean isUpdate = dbHelper.updateExpense(hashMap.get("id"),dAmount,reasonStr);
                if (isUpdate==true){
                    Toast.makeText(ShowData.this, "Update", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ShowData.this, "Not Update", Toast.LENGTH_SHORT).show();
                }

                loadData();


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }



}