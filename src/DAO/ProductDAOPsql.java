package DAO;

import DAOInterfaces.OVChipkaartDAO;
import DAOInterfaces.ProductDAO;
import Domein.OVChipkaart;
import Domein.Product;

import java.sql.*;
import java.util.ArrayList;
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
        try {
            String q = "DELETE FROM product " +
                    "WHERE product_nummer = ?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, product.getNummer());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    // Ik heb deze en de findAll() niet goed geïmplementeerd.

    // Op dit moment vind findByNummer() geen kaarten die bij het product horen.
    // Dit komt omdat findByNummer() gebruikt wordt door OVChipkaartDAOPsql om de producten op een kaart te zetten.

    // Ik heb heel lang geprobeerd het op een betere manier te implementeren maar na eindeloze stack overflows
    // moet ik helaas zo inleveren. Ik hoop dat ik er maandag op terug kan komen om het toch beter te doen.
    @Override
    public Product findByNummer(int productNummer) throws Exception {
        try {
            String q = "SELECT * FROM product " +
                    "WHERE product_nummer = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, productNummer);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Product p1 = new Product(rs.getInt("product_nummer"),rs.getString("naam"),
                        rs.getString("beschrijving"),rs.getDouble("prijs"));
                if (rs.next()) {
                    throw new Exception("More than one product listed at id: "+rs.getInt("product_nummer"));
                }
                return p1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    // Ik heb dit en findByNummer() niet goed geïmplementeerd.

    // Op dit moment haalt de findAll() alle producten op en roep daarna de findAll() van OVChipkaart aan.
    // Hier word er per kaart gekeken welk product er op staat. Als een kaart een product bevat wordt deze
    // toe gevoegd aan het product.
    // Dit zorgt voor het dubbel ophalen van bepaalde producten wat niet heel logisch klinkt.

    // Ik heb heel lang geprobeerd het op een betere manier te implementeren maar na eindeloze stack overflows
    // moet ik helaas zo inleveren. Ik hoop dat ik er maandag op terug kan komen om het toch beter te doen.
    @Override
    public List<Product> findAll() {
        try {
            String q = "SELECT * FROM product";
            PreparedStatement pst = conn.prepareStatement(q);
            ResultSet rs = pst.executeQuery();
            List<Product> producten = new ArrayList<>();
            while (rs.next()) {
                Product p1 = new Product(rs.getInt("product_nummer"),rs.getString("naam"),
                        rs.getString("beschrijving"),rs.getDouble("prijs"));
                producten.add(p1);
            }
            List<OVChipkaart> kaarten = odao.findAll();
            for (OVChipkaart kaart : kaarten) {
                for (Product product : kaart.getProducten()) {
                    Product found = producten.stream().filter(e -> e.getNummer() == product.getNummer()).findFirst().orElse(null);
                    if (found != null) {
                        found.addOvChipkaart(kaart);
                    }
                }
            }
            return producten;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
