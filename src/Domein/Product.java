package Domein;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int nummer;
    private String naam, beschrijving;
    private Double prijs;
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Product(int nummer, String naam, String beschrijving, Double prijs) {
        this.nummer = nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public Double getPrijs() {
        return prijs;
    }

    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public void addOvChipkaart(OVChipkaart kaart) {
        if (!ovChipkaarten.contains(kaart)) {
            ovChipkaarten.add(kaart);
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder("{" + naam + ": €" + prijs + "}");
        for (OVChipkaart kaart : ovChipkaarten) {
            string.append(kaart.getKaartNummer());
            string.append(" ");
        }
        return string.toString();
    }
}
