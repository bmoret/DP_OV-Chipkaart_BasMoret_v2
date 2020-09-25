package DAOInterfaces;

import Domein.Product;
import java.util.List;

public interface ProductDAO {
    public boolean save (Product product);
    public boolean update (Product product);
    public boolean delete (Product product);
    public Product findByNummer (int productNummer) throws Exception;
    public List<Product> findAll ();
}
