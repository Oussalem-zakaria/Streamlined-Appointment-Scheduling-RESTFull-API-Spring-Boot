package com.ucd.CenterRDV.repository;

import com.ucd.CenterRDV.models.JourTravail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JourTravailRepository extends JpaRepository<JourTravail, Integer> {
    JourTravail findByJour(String jour);
    JourTravail findByJourAndCentreId(String jourName, Integer centreId);
}

