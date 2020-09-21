package DAO;

import Domein.OVChipkaart;
import Domein.Product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private Connection conn;
    private OVChipkaartDAO odao;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setOdao(OVChipkaartDAO odao) {
        this.odao = odao;
    }

    @Override
    public boolean save(Product product) {
        try {
            String q = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) \n" +
                    "VALUES (?, ?, ?, ?);";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, product.getNummer());
            pst.setString(2, product.getNaam());
            pst.setString(3, product.getBeschrijving());
            pst.setDouble(4, product.getPrijs());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Product product) {
        try {
            String q = "UPDATE product " +
                    "SET naam = ?, beschrijving = ?, prijs = ? \n" +
                    "WHERE product_nummer = ?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(4, product.getNummer());
            pst.setString(1, product.getNaam());
            pst.setString(2, product.getBeschrijving());
            pst.setDouble(3, product.getPrijs());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Product product) {
        return false;
    }

    @Override
    public Product findByNummer(int productNummer) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }
}
