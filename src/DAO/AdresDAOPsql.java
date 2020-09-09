package DAO;

import Domein.Adres;
import Domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{
    private Connection conn;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            String q = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id)\n" +
                    "VALUES (?,?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, adres.getAdres_id());
            pst.setString(2, adres.getPostcode());
            pst.setString(3, adres.getHuisnummer());
            pst.setString(4, adres.getStraat());
            pst.setString(5, adres.getWoonplaats());
            pst.setInt(6, adres.getReiziger().getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            String q = "UPDATE adres " +
                    "SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ?" +
                    "WHERE adres_id=?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(6, adres.getAdres_id());
            pst.setString(1, adres.getPostcode());
            pst.setString(2, adres.getHuisnummer());
            pst.setString(3, adres.getStraat());
            pst.setString(4, adres.getWoonplaats());
            pst.setInt(5, adres.getReiziger().getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String q = "DELETE FROM adres WHERE adres_id=?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, adres.getAdres_id());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws Exception {
        try {
            String q = "SELECT * FROM adres WHERE reiziger_id = ?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1,reiziger.getId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Adres a1 = new Adres(rs.getInt("adres_id"),rs.getString("postcode"),rs.getString("huisnummer"),rs.getString("straat"),rs.getString("woonplaats"),reiziger);

                if (rs.next()) {
                    System.out.println(rs);
                    System.out.println(rs.getInt("reiziger_id"));
                    throw new Exception("More than one adress listed at id");
                }
                return a1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        try {
            String q = "SELECT * FROM adres;";
            PreparedStatement pst = conn.prepareStatement(q);
            ResultSet rs = pst.executeQuery();
            List<Adres> la = new ArrayList<>();
            while (rs.next()) {
                Adres a1 = new Adres(rs.getInt("adres_id"),rs.getString("postcode"),rs.getString("huisnummer"),rs.getString("straat"),rs.getString("woonplaats"),rdao.findById(rs.getInt("reiziger_id")));
                la.add(a1);
            }
            return la;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
