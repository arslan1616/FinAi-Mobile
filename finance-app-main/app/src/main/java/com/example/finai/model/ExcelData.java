package com.example.finai.model;

public class ExcelData {
    private String hisse;
    private String netKar;
    private String ozkaynakDegisim;
    private String ortalamaDegisim;
    private String durum;

    public ExcelData(String hisse, String netKar, String ozkaynakDegisim, String ortalamaDegisim, String durum) {
        this.hisse = hisse;
        this.netKar = netKar;
        this.ozkaynakDegisim = ozkaynakDegisim;
        this.ortalamaDegisim = ortalamaDegisim;
        this.durum = durum;
    }

    public String getHisse() {
        return hisse;
    }

    public void setHisse(String hisse) {
        this.hisse = hisse;
    }

    public String getNetKar() {
        return netKar;
    }

    public void setNetKar(String netKar) {
        this.netKar = netKar;
    }

    public String getOzkaynakDegisim() {
        return ozkaynakDegisim;
    }

    public void setOzkaynakDegisim(String ozkaynakDegisim) {
        this.ozkaynakDegisim = ozkaynakDegisim;
    }

    public String getOrtalamaDegisim() {
        return ortalamaDegisim;
    }

    public void setOrtalamaDegisim(String ortalamaDegisim) {
        this.ortalamaDegisim = ortalamaDegisim;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}