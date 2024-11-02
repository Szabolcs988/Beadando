package org.example.szoftverleltar;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface KapcsolatRepository extends CrudRepository<Kapcsolat, Integer> {
    @Query("SELECT k FROM Kapcsolat k ORDER BY k.bekuldes_ideje desc")
    List<Kapcsolat> findAllByOrderByBekuldes_ideje();
}