package com.ucd.CenterRDV.services;

import com.ucd.CenterRDV.models.Centre;
import com.ucd.CenterRDV.models.RDV;
import com.ucd.CenterRDV.models.ApplicationUser;
import com.ucd.CenterRDV.repository.RDVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RdvService {

    private final RDVRepository rdvRepository;

    @Autowired
    public RdvService(RDVRepository rdvRepository) {
        this.rdvRepository = rdvRepository;
    }

    public RDV getRdvById(Integer id){
        return rdvRepository.findRDVById(id);
    }

    public List<RDV> getAllRDVs() {
        return rdvRepository.findAll();
    }

    public List<RDV> getRDVsByUser(ApplicationUser user) {
        return rdvRepository.findByUser(user);
    }

    public List<RDV> getRDVsByCentre(Centre centre){
        return rdvRepository.findByCentre(centre);
    }

    public RDV addRDV(RDV rdv) {
        return rdvRepository.save(rdv);
    }

    public List<RDV> getRDVsByUserIdAndCreneauIdAndJour(Integer userId, Integer creneauId, String jour) {
        List<RDV> rdvs = rdvRepository.findByUser_UserIdAndCreneauId(userId, creneauId);
        return rdvs.stream()
                .filter(rdv -> rdv.getCreneau().getJourTravail().getJour().equals(jour))
                .collect(Collectors.toList());
    }


}
