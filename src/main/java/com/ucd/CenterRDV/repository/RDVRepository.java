package com.ucd.CenterRDV.repository;

import com.ucd.CenterRDV.models.ApplicationUser;
import com.ucd.CenterRDV.models.Centre;
import com.ucd.CenterRDV.models.RDV;
import com.ucd.CenterRDV.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RDVRepository extends JpaRepository<RDV,Integer> {
    List<RDV> findByUser(ApplicationUser user);
    List<RDV> findByCentre(Centre centre);
    RDV findRDVById(Integer id);
    List<RDV> findByUser_UserIdAndCreneauId(Integer userId,Integer creneauId);

}
