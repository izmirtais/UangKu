package com.example.mycashbook;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CashflowRecyclerAdapter extends RecyclerView.Adapter<com.example.mycashbook.CashflowRecyclerAdapter.CashflowViewHolder> {
    private List<com.example.mycashbook.Cashflow> listCashflow;

    public CashflowRecyclerAdapter(List<com.example.mycashbook.Cashflow> listCashflow) {
        this.listCashflow = listCashflow;
    }
    @Override
    public CashflowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cashflow_recycler, parent, false);
        return new CashflowViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(CashflowViewHolder holder, int position) {
        holder.keterangan.setText(listCashflow.get(position).getKeterangan());
        holder.tanggal.setText(listCashflow.get(position).getTgl());
        if (listCashflow.get(position).getJenis().equals("in")){
            holder.nominal.setText("[+] Rp. "+ listCashflow.get(position).getNominal());
            holder.jenis.setImageResource(R.drawable.arrow_left);
        }else{
            holder.nominal.setText("[-] Rp. "+ listCashflow.get(position).getNominal());
            holder.jenis.setImageResource(R.drawable.arrow_right);
        }
    }
    @Override
    public int getItemCount() {
        Log.v(com.example.mycashbook.CashflowRecyclerAdapter.class.getSimpleName(),""+listCashflow.size());
        return listCashflow.size();
    }
    /**
     * ViewHolder class
     */
    public class CashflowViewHolder extends RecyclerView.ViewHolder {
        public TextView nominal;
        public TextView keterangan;
        public TextView tanggal;
        public ImageView jenis;
        public CashflowViewHolder(View view) {
            super(view);
            nominal = (TextView) view.findViewById(R.id.cashflow_nominal);
            keterangan = (TextView) view.findViewById(R.id.cashflow_keterangan);
            tanggal = (TextView) view.findViewById(R.id.cashflow_tgl);
            jenis = (ImageView) view.findViewById(R.id.cashflow_ikon);
        }
    }
}
