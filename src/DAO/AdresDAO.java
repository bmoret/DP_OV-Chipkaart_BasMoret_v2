package DAO;

import Domein.Adres;
import Domein.Reiziger;

import java.util.List;

public interface AdresDAO {
    public boolean save (Adres adres);
    public boolean update (Adres adres);
    public boolean delete (Adres adres);
    public Adres findByReiziger (Reiziger reiziger) throws Exception;
    public List<Adres> findAll();
}
