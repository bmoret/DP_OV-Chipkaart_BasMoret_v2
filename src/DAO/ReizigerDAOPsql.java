package DAO;

import Domein.OVChipkaart;
import Domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;
    private AdresDAO adao;
    private OVChipkaartDAO odao;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setAdao(AdresDAO adao) {
        this.adao = adao;
    }

    public void setOdao(OVChipkaartDAO odao) {this.odao = odao;}

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            String q = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum)\n" +
                    "VALUES (?, ?, ?, ?, TO_DATE(?, 'yyyy-mm-dd'));";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, reiziger.getId());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setString(5, "" + reiziger.getGeboortedatum());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String q = "UPDATE reiziger " +
                    "SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = TO_DATE(?, 'yyyy-mm-dd') " +
                    "WHERE reiziger_id=?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(5, reiziger.getId());
            pst.setString(1, reiziger.getVoorletters());
            pst.setString(2, reiziger.getTussenvoegsel());
            pst.setString(3, reiziger.getAchternaam());
            pst.setString(4, "" + reiziger.getGeboortedatum());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String q = "DELETE FROM reiziger WHERE reiziger_id=?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, reiziger.getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) throws Exception {
        try {
            String q = "SELECT * FROM reiziger WHERE reiziger_id=?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Reiziger r1 = new Reiziger(rs.getInt("reiziger_id"),rs.getString("voorletters"),rs.getString("tussenvoegsel")+" ",rs.getString("achternaam"),java.sql.Date.valueOf(rs.getString("geboortedatum")));
                r1.setAdres(adao.findByReiziger(r1));
                r1.setKaarten(odao.findByReiziger(r1));
                if (rs.next()) {
                    System.out.println(rs);
                    System.out.println(rs.getInt("reiziger_id"));
                    throw new Exception("More than one person listed at id");
                }
                return r1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            String q = "SELECT * FROM reiziger WHERE geboortedatum=TO_DATE(?, 'yyyy,mm,dd');";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setString(1, datum);
            ResultSet rs = pst.executeQuery();
            List<Reiziger> lr = new ArrayList<>();
            while (rs.next()) {
                Reiziger r1 = new Reiziger(rs.getInt("reiziger_id"),rs.getString("voorletters"),rs.getString("tussenvoegsel")+" ",rs.getString("achternaam"),java.sql.Date.valueOf(rs.getString("geboortedatum")));
                r1.setAdres(adao.findByReiziger(r1));
                r1.setKaarten(odao.findByReiziger(r1));
                lr.add(r1);
            }
            return lr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            String q = "SELECT * FROM reiziger";
            PreparedStatement pst = conn.prepareStatement(q);
            ResultSet rs = pst.executeQuery();
            List<Reiziger> lr = new ArrayList<>();
            while (rs.next()) {
                Reiziger r1 = new Reiziger(rs.getInt("reiziger_id"),rs.getString("voorletters"),rs.getString("tussenvoegsel")+" ",rs.getString("achternaam"),java.sql.Date.valueOf(rs.getString("geboortedatum")));
                r1.setAdres(adao.findByReiziger(r1));
                r1.setKaarten(odao.findByReiziger(r1));
                lr.add(r1);
            }
            return lr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
