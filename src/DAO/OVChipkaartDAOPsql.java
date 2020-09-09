package DAO;

import Domein.OVChipkaart;
import Domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;
    private ReizigerDAO rdao;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    @Override
    public boolean save(OVChipkaart kaart) {
        try {
            String q = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)\n" +
                    "VALUES (?, TO_DATE(?, 'yyyy-mm-dd'), ?, ?, ?);";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, kaart.getKaartNummer());
            pst.setString(2, ""+kaart.getGeldigTot());
            pst.setInt(3, kaart.getKlasse());
            pst.setDouble(4, kaart.getSaldo());
            pst.setInt(5, kaart.getReiziger().getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean update(OVChipkaart kaart) {
        try {
            String q = "UPDATE ov_chipkaart "+
                    "SET geldig_tot = TO_DATE(?, 'yyyy-mm-dd'), klasse = ?, saldo = ?, reiziger_id = ? " +
                    "WHERE kaart_nummer = ?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(5, kaart.getKaartNummer());
            pst.setString(1, ""+kaart.getGeldigTot());
            pst.setInt(2, kaart.getKlasse());
            pst.setDouble(3, kaart.getSaldo());
            pst.setInt(4, kaart.getReiziger().getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart kaart) {
        try {
            String q = "DELETE FROM ov_chipkaart WHERE kaart_nummer=?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, kaart.getKaartNummer());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public OVChipkaart findByKaartNummer (OVChipkaart kaart) {
        try {
            String q = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, kaart.getKaartNummer());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                OVChipkaart k1 = new OVChipkaart(rs.getInt("kaart_nummer"),rs.getDouble("saldo"),rs.getInt("klasse"),java.sql.Date.valueOf(rs.getString("geldig_tot")),rdao.findById(rs.getInt("reiziger_id")));
                if (rs.next()) {
                    System.out.println(rs);
                    System.out.println(rs.getInt("reiziger_id"));
                    throw new Exception("More than one card listed at id");
                }
                return k1;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            String q = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1,reiziger.getId());
            ResultSet rs = pst.executeQuery();
            List<OVChipkaart> lo = new ArrayList<>();
            while (rs.next()) {
                OVChipkaart k1 = new OVChipkaart(rs.getInt("kaart_nummer"),rs.getDouble("saldo"),rs.getInt("klasse"),java.sql.Date.valueOf(rs.getString("geldig_tot")),reiziger);
                lo.add(k1);
            }
            if (lo.isEmpty()) {
                return null;
            }
            return lo;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
