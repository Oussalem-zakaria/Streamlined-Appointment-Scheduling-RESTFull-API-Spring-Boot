package com.ucd.CenterRDV.repository;

import com.ucd.CenterRDV.models.Centre;
import com.ucd.CenterRDV.models.RDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CentreRepository extends JpaRepository<Centre,Integer> {
    Centre findCentreByNom(String name);
    Centre findCentreByRdvsContains(RDV rdv);
}
