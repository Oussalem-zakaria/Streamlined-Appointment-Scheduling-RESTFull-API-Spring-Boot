package com.ucd.CenterRDV.repository;

import com.ucd.CenterRDV.models.Centre;
import com.ucd.CenterRDV.models.Creneau;
import com.ucd.CenterRDV.models.RDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CreneauRepository extends JpaRepository<Creneau, Integer> {
    Optional<Creneau> findCreneauByCentreSante(Centre centre);

    Creneau findCreneauByRdvsContains(RDV rdv);

    @Query("SELECT c FROM Creneau c WHERE c.id NOT IN (SELECT r.creneau.id FROM RDV r)")
    List<Creneau> findAvailableCreneaux();

    Creneau findCreneauById(Integer id);

}
