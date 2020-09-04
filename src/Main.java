import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Main{
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql:ovchip";
        String user = "student";
        String pass = "password";

        Connection myConn = DriverManager.getConnection(url, user, pass);
        Date gdr1 = java.sql.Date.valueOf("1997-09-08");

        ReizigerDAOPsql rds = new ReizigerDAOPsql(myConn);
        AdresDAOPsql ads = new AdresDAOPsql(myConn);
        rds.setAdao(ads);
        ads.setRdao(rds);
        testReizigerDAO(rds);
        testAdresDAO(ads,rds);

        myConn.close();
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update een bestaande reiziger + vindt reiziger met Id
        Reiziger sietskeNieuweAchternaam = new Reiziger(77, "S", "", "Jansen", java.sql.Date.valueOf(gbdatum));
        try {
            System.out.print("[Test] Achtenaam is eerst " + rdao.findById(77).getAchternaam() + ". Achternaam is na ReizigerDAO.update ");
            rdao.update(sietskeNieuweAchternaam);
            System.out.println(rdao.findById(77).getAchternaam() + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Delete reiziger
        reizigers = rdao.findAll();
        for (Reiziger reiziger : reizigers) {
            if (reiziger.getId() == 77) {
                System.out.print("[Test] Voor het verwijderen zit "+ reiziger.getAchternaam() + " in de database, na het verwijderen ");
            }
        }

        rdao.delete(sietskeNieuweAchternaam);

        reizigers = rdao.findAll();
        Boolean x = true;
        for (Reiziger reiziger : reizigers) {
            if (reiziger.getId() == 77) {
                System.out.println("nog steeds.\n");
                x = false;
            }
        }
        if (x) {
            System.out.println("niet meer.\n");
        }

        // Vindt reiziger met geboortedatum
        System.out.println("[Test] ReizigerDAO.findByGbdatum geeft de volgende reizigers met geboortendatum 03-12-2002:");
        for (Reiziger r : rdao.findByGbdatum("2002-12-03")) {
            System.out.println(r);
        }
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Aanmaken huiseigenaar
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.save(sietske);


        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer deze in de database
        Adres adres1 = new Adres(77, "1111 AB", "11", "Straat","Woonplaats",77);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na ReizigerDAO.save() ");
        adao.save(adres1);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        // Update een bestaande reiziger + vindt reiziger met Id
        Adres adres1update = new Adres(77, "1111 AE", "15", "Dorpstraat","Woonplaats",77);
        try {
            System.out.print("[Test] Adres is eerst " + adao.findByReiziger(sietske) + ". Adres is na AdresDAO.update ");
            adao.update(adres1update);
            System.out.println(adao.findByReiziger(sietske) + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Delete adres
        adressen = adao.findAll();
        for (Adres adres : adressen) {
            if (adres.getAdres_id() == 77) {
                System.out.print("[Test] Voor het verwijderen zit "+ adres + " in de database, na het verwijderen ");
            }
        }

        adao.delete(adres1update);

        adressen = adao.findAll();
        Boolean x = true;
        for (Adres adres : adressen) {
            if (adres.getAdres_id() == 77) {
                System.out.println("nog steeds.\n");
                x = false;
            }
        }
        if (x) {
            System.out.println("niet meer.\n");
        }
    }
}