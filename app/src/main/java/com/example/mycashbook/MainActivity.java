package com.example.mycashbook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView pemasukan, pengeluaran;
    private String username;
    private LineChart lineChart;
    private ArrayList<Cashflow> listCashflow;
    private com.example.mycashbook.DBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username");

        databaseHelper = new com.example.mycashbook.DBHelper(getApplicationContext());
        listCashflow = new ArrayList<>();

        lineChart = (LineChart)findViewById(R.id.line);
        pemasukan = (TextView)findViewById(R.id.total_pemasukan);
        pengeluaran = (TextView)findViewById(R.id.total_pengeluaran);


        List<Entry> lineEntries = getDataSet();
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Pengeluaran");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColor(Color.RED);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleHoleRadius(3);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.DKGRAY);

        List<Entry> lineEntries2 = getDataSet2();
        LineDataSet lineDataSet2 = new LineDataSet(lineEntries2, "Pemasukan");
        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet2.setHighlightEnabled(true);
        lineDataSet2.setLineWidth(2);
        lineDataSet2.setColor(Color.GREEN);
        lineDataSet2.setCircleColor(Color.GREEN);
        lineDataSet2.setCircleRadius(6);
        lineDataSet2.setCircleHoleRadius(3);
        lineDataSet2.setDrawHighlightIndicators(true);
        lineDataSet2.setHighLightColor(Color.GREEN);
        lineDataSet2.setValueTextSize(12);
        lineDataSet2.setValueTextColor(Color.DKGRAY);

        LineData lineData = new LineData(lineDataSet, lineDataSet2);
        lineChart.setDrawMarkers(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(1.0f);
        lineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
        lineChart.setData(lineData);

        total();
    }

    private List<Entry> getDataSet() {
        List<Entry> lineEntries = new ArrayList<Entry>();
        listCashflow.clear();
        listCashflow.addAll(databaseHelper.getAllCashflow(getIntent().getStringExtra("username")));
        System.out.println("size cashflow:" + listCashflow.size());
        for (int i=0; i<listCashflow.size(); i++){
            if (listCashflow.get(i).getJenis().equals("out")) {
                String tgl = listCashflow.get(i).getTgl();
                String[] part = tgl.split("-");

                if (part[1].equals("10")) {
                    lineEntries.add(new Entry(i, Float.parseFloat(listCashflow.get(i).getNominal())));
                    System.out.println("nominal cash:" + listCashflow.get(i).getNominal());
                }
            }
        }
        return lineEntries;
    }

    private List<Entry> getDataSet2() {
        List<Entry> lineEntries = new ArrayList<Entry>();
        listCashflow.clear();
        listCashflow.addAll(databaseHelper.getAllCashflow(getIntent().getStringExtra("username")));
        System.out.println("size cashflow:" + listCashflow.size());
        for (int i=0; i<listCashflow.size(); i++){
            if (listCashflow.get(i).getJenis().equals("in")) {
                String tgl = listCashflow.get(i).getTgl();
                String[] part = tgl.split("-");

                if (part[1].equals("10")) {
                    lineEntries.add(new Entry(i, Float.parseFloat(listCashflow.get(i).getNominal())));
                    System.out.println("nominal cash:" + listCashflow.get(i).getNominal());
                }
            }
        }
        return lineEntries;
    }

    private void total() {
        int in=0 , out = 0;
        List<Entry> lineEntries = new ArrayList<Entry>();
        listCashflow.clear();
        listCashflow.addAll(databaseHelper.getAllCashflow(getIntent().getStringExtra("username")));
        System.out.println("size cashflow:" + listCashflow.size());
        for (int i=0; i<listCashflow.size(); i++){
            if (listCashflow.get(i).getJenis().equals("out")) {
               out += Integer.parseInt(listCashflow.get(i).getNominal());
            }else{
                in += Integer.parseInt(listCashflow.get(i).getNominal());
            }
        }
        pemasukan.setText("Pemasukan: Rp." +in);
        pengeluaran.setText("Pengeluaran: Rp." +out);
    }

    public void toIncome(View v){
        Intent intent = new Intent(getApplicationContext(), PemasukanActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
    public void toOutcome(View v){
        Intent intent = new Intent(getApplicationContext(), com.example.mycashbook.PengeluaranActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
    public void toCashflow(View v){
        Intent intent = new Intent(getApplicationContext(), CashFlowActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
    public void toSetting(View v){
        Intent intent = new Intent(getApplicationContext(), PengaturanActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}
