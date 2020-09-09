package Domein;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> kaarten = new ArrayList<>();

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getKaarten() {
        return kaarten;
    }

    public void setKaarten(List<OVChipkaart> kaarten) {
        if (kaarten != null) {
            this.kaarten = kaarten;
        }
    }

    public void addKaart(OVChipkaart kaart) {
        if (!kaarten.contains(kaart)) {
            kaarten.add(kaart);
        }
    }

    public String getNaam() {
        if (tussenvoegsel.isBlank()||tussenvoegsel.isEmpty()||tussenvoegsel.contains("null")) {
            return voorletters+". "+achternaam;
        }
        return voorletters+". "+tussenvoegsel+" "+achternaam;
    }

    @Override
    public String toString() {
        String fullString = "#" + id +
                ": " + getNaam() +
                " (" + geboortedatum + "), Adres " +
                adres;
        for (OVChipkaart kaart : kaarten) {
            fullString += "; " + kaart;
        }
        return fullString;
    }
}
