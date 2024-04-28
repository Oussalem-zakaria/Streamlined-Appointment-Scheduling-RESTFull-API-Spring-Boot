package com.ucd.CenterRDV.services;

import com.ucd.CenterRDV.dto.CreneauDto;
import com.ucd.CenterRDV.models.Centre;
import com.ucd.CenterRDV.models.Creneau;
import com.ucd.CenterRDV.models.JourTravail;
import com.ucd.CenterRDV.models.RDV;
import com.ucd.CenterRDV.repository.CreneauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreneauService {
    private final CreneauRepository creneauRepository;

    @Autowired
    public CreneauService(CreneauRepository creneauRepository) {
        this.creneauRepository = creneauRepository;
    }

    // Récupérer tous les créneaux
    public List<Creneau> getAllCreneaux() {
        return creneauRepository.findAll();
    }

    public List<Creneau> getAvailableCreneaux() {
        return creneauRepository.findAvailableCreneaux();
    }

    public Creneau getCreneauByRdv(RDV rdv){
        return creneauRepository.findCreneauByRdvsContains(rdv);
    }

    public Creneau getCreneauById(Integer id) {
        return creneauRepository.findCreneauById(id);
    }

    // Ajouter un créneau
    public CreneauDto addCreneau(CreneauDto creneau) {
        Creneau c = toCreneau(creneau);
        creneauRepository.save(c);
        return creneau;
    }

    private Creneau toCreneau(CreneauDto creneauDto){
        Creneau creneau = new Creneau();
        creneau.setHeureDebut(creneauDto.heureDebut());
        creneau.setHeureFin(creneauDto.heureFin());
        Centre centre = new Centre();
        centre.setId(creneauDto.centerId());
        creneau.setCentreSante(centre);

        JourTravail jourTravail = new JourTravail();
        jourTravail.setId(creneauDto.jourTravailId());
        creneau.setJourTravail(jourTravail);

        return creneau;
    }
}
