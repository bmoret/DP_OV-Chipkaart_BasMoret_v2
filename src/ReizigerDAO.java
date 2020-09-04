import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger reiziger) throws SQLException;
    public boolean update(Reiziger reiziger) throws SQLException;
    public boolean delete(Reiziger reiziger);
    public Reiziger findById(int id) throws Exception;
    public List<Reiziger> findByGbdatum(String datum);
    public List<Reiziger> findAll();
}
