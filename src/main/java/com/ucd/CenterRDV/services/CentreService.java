package com.ucd.CenterRDV.services;

import com.ucd.CenterRDV.dto.CenterRespDto;
import com.ucd.CenterRDV.models.Centre;
import com.ucd.CenterRDV.models.JourTravail;
import com.ucd.CenterRDV.models.RDV;
import com.ucd.CenterRDV.repository.CentreRepository;
import com.ucd.CenterRDV.repository.JourTravailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CentreService {
    private final CentreRepository centreRepository;
    private final JourTravailRepository jourTravailRepository;

    @Autowired
    public CentreService(CentreRepository centreRepository, JourTravailRepository jourTravailRepository) {
        this.centreRepository = centreRepository;
        this.jourTravailRepository = jourTravailRepository;
    }

    // Récupérer tous les centres
    public List<Centre> getAllCentres() {
        return centreRepository.findAll();
    }

    public Centre getCenterById(Integer id) {
        return centreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Center not found with id: " + id));

    }

    public Centre getCenterByName(String name) {
        return centreRepository.findCentreByNom(name);
    }

    public Centre getCentreByRdv(RDV rdv) {
        return centreRepository.findCentreByRdvsContains(rdv);
    }

    // Ajouter un centre
    public CenterRespDto addCentre(CenterRespDto centre) {
        Centre c = toCenter(centre);
        centreRepository.save(c);

        return centre;
    }

//    private Centre toCenter(CenterRespDto centerRespDto) {
//        Centre centre = new Centre();
//        centre.setNom(centerRespDto.name());
//        centre.setAdresse(centerRespDto.adresse());
//        centre.setPhoneNumber(centerRespDto.phoneNumber());
//
//        Set<JourTravail> joursTravail = new HashSet<>();
//        List<String> joursTravailNames = centerRespDto.joursTravail();
//        if (joursTravailNames != null) {
//            for (String jourTravailName : joursTravailNames) {
//                JourTravail jourTravail = jourTravailRepository.findByJour(jourTravailName);
//                if (jourTravail == null) {
//                    jourTravail = new JourTravail();
//                    jourTravail.setJour(jourTravailName);
//                    jourTravailRepository.save(jourTravail);
//                }
//                joursTravail.add(jourTravail);
//            }
//        }
//        centre.setJoursTravail(joursTravail);
//
//        return centre;
//    }

    private Centre toCenter(CenterRespDto centerRespDto) {
        Centre centre = new Centre();
        centre.setNom(centerRespDto.name());
        centre.setAdresse(centerRespDto.adresse());
        centre.setPhoneNumber(centerRespDto.phoneNumber());

        List<String> joursTravailNames = centerRespDto.joursTravail();

        // Si des jours de travail sont spécifiés dans le DTO
        if (joursTravailNames != null && !joursTravailNames.isEmpty()) {
            Set<JourTravail> joursTravail = new HashSet<>();

            // Pour chaque nom de jour, rechercher l'entité JourTravail correspondante
            for (String jourTravailName : joursTravailNames) {
                JourTravail jourTravail = jourTravailRepository.findByJour(jourTravailName);

                // Si le jour de travail n'existe pas en base de données, le créer
                if (jourTravail == null) {
                    jourTravail = new JourTravail();
                    jourTravail.setJour(jourTravailName);
                    jourTravail.setCentre(centre); // Associer le jour de travail au centre
                    jourTravailRepository.save(jourTravail);
                }

                joursTravail.add(jourTravail);
            }

            // Associer les jours de travail au centre
            centre.setJoursTravail(joursTravail);
        }

        return centre;
    }

}
