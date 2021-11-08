package com.example.mycashbook;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CashFlowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private com.example.mycashbook.CashflowRecyclerAdapter cashflowRecyclerAdapter;
    private com.example.mycashbook.DBHelper databaseHelper;
    private List<com.example.mycashbook.Cashflow> listCashflow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCashflow);

        listCashflow = new ArrayList<>();
        cashflowRecyclerAdapter = new com.example.mycashbook.CashflowRecyclerAdapter(listCashflow);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cashflowRecyclerAdapter);
        databaseHelper = new com.example.mycashbook.DBHelper(getApplicationContext());

        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listCashflow.clear();
                listCashflow.addAll(databaseHelper.getAllCashflow(getIntent().getStringExtra("username")));
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                cashflowRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    public void toHomeee(View view) {
        Intent intent = new Intent(getApplicationContext(), com.example.mycashbook.MainActivity.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(intent);
        finish();
    }
}
