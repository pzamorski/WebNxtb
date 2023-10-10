package com.webnxtb.DataBase;

import com.webnxtb.DataBase.hibernate.EncjaDAO;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "konfiguracje")
public class Konfiguracja extends EncjaDAO  {

    public Konfiguracja() {
        super(Konfiguracja.class);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nazwa_konfiguracji")
    private String nazwaKonfiguracji;

    @Column(name = "uzytkownik_xtb")
    private String uzytkownikXtb;

    @Column(name = "haslo_xtb")
    private String hasloXtb;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "period_code")
    private String periodCode;

    @Column(name = "liczba_warstw")
    private int liczbaWarstw;

    @Column(name = "ilosc_neuronow")
    private String iloscNeuronow; // Możesz użyć String do przechowywania listy ilości neuronów

    @Column(name = "czestotliwosc_wyswietlania_wynikow")
    private String czestotliwoscWyswietlaniaWynikow;

    @Column(name = "status")
    private String status;

    // Getters i setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwaKonfiguracji() {
        return nazwaKonfiguracji;
    }

    public void setNazwaKonfiguracji(String nazwaKonfiguracji) {
        this.nazwaKonfiguracji = nazwaKonfiguracji;
    }

    public String getUzytkownikXtb() {
        return uzytkownikXtb;
    }

    public void setUzytkownikXtb(String uzytkownikXtb) {
        this.uzytkownikXtb = uzytkownikXtb;
    }

    public String getHasloXtb() {
        return hasloXtb;
    }

    public void setHasloXtb(String hasloXtb) {
        this.hasloXtb = hasloXtb;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPeriodCode() {
        return periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public int getLiczbaWarstw() {
        return liczbaWarstw;
    }

    public void setLiczbaWarstw(int liczbaWarstw) {
        this.liczbaWarstw = liczbaWarstw;
    }

    public String getIloscNeuronow() {
        return iloscNeuronow;
    }

    public void setIloscNeuronow(String iloscNeuronow) {
        this.iloscNeuronow = iloscNeuronow;
    }

    public String getCzestotliwoscWyswietlaniaWynikow() {
        return czestotliwoscWyswietlaniaWynikow;
    }

    public void setCzestotliwoscWyswietlaniaWynikow(String czestotliwoscWyswietlaniaWynikow) {
        this.czestotliwoscWyswietlaniaWynikow = czestotliwoscWyswietlaniaWynikow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Konfiguracja{" +
                "id=" + id +
                ", nazwaKonfiguracji='" + nazwaKonfiguracji + '\'' +
                ", uzytkownikXtb='" + uzytkownikXtb + '\'' +
                ", hasloXtb='" + hasloXtb + '\'' +
                ", symbol='" + symbol + '\'' +
                ", periodCode='" + periodCode + '\'' +
                ", liczbaWarstw=" + liczbaWarstw +
                ", iloscNeuronow='" + iloscNeuronow + '\'' +
                ", czestotliwoscWyswietlaniaWynikow='" + czestotliwoscWyswietlaniaWynikow + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


    @Override
    public int getID() {
        return this.getId();
    }
}
