package Domein;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private int kaartNummer, klasse;
    private double saldo;
    private Date geldigTot;
    private Reiziger reiziger;
    private List<Product> producten = new ArrayList<>();

    public OVChipkaart (int kaartNummer, double saldo, int klasse, Date geldigTot, Reiziger reiziger) throws Exception {
        this.kaartNummer = kaartNummer;
        this.saldo = saldo;
        if (klasse ==1 || klasse == 2) {
            this.klasse = klasse;
        } else {
            throw new Exception("Klasse moet gelijk zijn aan 1 of 2");
        }
        this.geldigTot = geldigTot;
        this.reiziger = reiziger;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    public void addProduct(Product product){
        if (!producten.contains(product)) {
            producten.add(product);
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("OV chipkaart: #" + kaartNummer + ", geldig tot: " + geldigTot + ", saldo: " + saldo + ", klasse: " + klasse + ", producten: ");
        for (Product product : producten) {
            string.append(product);
        }
        return string.toString();

    }
}
