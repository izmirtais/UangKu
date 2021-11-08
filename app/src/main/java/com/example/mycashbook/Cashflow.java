package com.example.mycashbook;

public class Cashflow {
    private int id_cashflow;
    private String jenis;
    private String tgl;
    private String nominal;
    private String keterangan;

    public int getId_cashflow() {
        return id_cashflow;
    }

    public void setId_cashflow(int id_cashflow) {
        this.id_cashflow = id_cashflow;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
