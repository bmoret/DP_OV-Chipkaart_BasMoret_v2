package DAO;

import Domein.OVChipkaart;
import Domein.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    public boolean save (OVChipkaart kaart);
    public boolean update (OVChipkaart kaart);
    public boolean delete (OVChipkaart kaart);
    public OVChipkaart findByKaartNummer (OVChipkaart kaart);
    public List<OVChipkaart> findByReiziger(Reiziger reiziger);

}
