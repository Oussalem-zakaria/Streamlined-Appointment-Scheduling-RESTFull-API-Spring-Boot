package com.ucd.CenterRDV.controllers;

import com.ucd.CenterRDV.dto.CenterRespDto;
import com.ucd.CenterRDV.models.Centre;
import com.ucd.CenterRDV.models.Creneau;
import com.ucd.CenterRDV.models.JourTravail;
import com.ucd.CenterRDV.models.RDV;
import com.ucd.CenterRDV.services.CentreService;
import com.ucd.CenterRDV.services.RdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/centres")
public class CentreController {

    private final CentreService centreService;
    private final RdvService rdvService;

    public CentreController(CentreService centreService,RdvService rdvService) {
        this.centreService = centreService;
        this.rdvService = rdvService;
    }

    @GetMapping
    public List<Centre> getAllCentres() {
        return centreService.getAllCentres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Centre> getCenterById(@PathVariable Integer id) {
        Centre centre = centreService.getCenterById(id);
        return new ResponseEntity<>(centre,HttpStatus.OK);
    }

    @GetMapping("/rdv/{id}")
    public Centre getCenterByRdv(@PathVariable Integer id){
        RDV rdv = rdvService.getRdvById(id);
        return centreService.getCentreByRdv(rdv);
    }

//    @GetMapping("/{id}/creneaux-disponibles")
//    public ResponseEntity<List<Creneau>> getAvailableCreneauxForCentre(@PathVariable Integer id) {
//        Centre centre = centreService.getCenterById(id);
//        List<RDV> rdvs = rdvService.getRDVsByCentre(centre);
//        Set<Creneau> allCreneaux = centre.getCreneaux();
//
//        List<Creneau> availableCreneaux = filterAvailableCreneaux(allCreneaux, rdvs);
//
//        return new ResponseEntity<>(availableCreneaux, HttpStatus.OK);
//    }

    @GetMapping("/{id}/creneaux-disponibles")
    public ResponseEntity<Set<Creneau>> getAvailableCreneauxForCentre(@PathVariable Integer id, @RequestParam String jourSpecifique) {
        Centre centre = centreService.getCenterById(id);
        Set<JourTravail> joursTravail = centre.getJoursTravail();
        List<RDV> rdvs = rdvService.getRDVsByCentre(centre);

        Set<Creneau> availableCreneaux = filterAvailableCreneaux(joursTravail, rdvs, jourSpecifique);

        return new ResponseEntity<>(availableCreneaux, HttpStatus.OK);
    }

//    private List<Creneau> filterAvailableCreneaux(Set<Creneau> allCreneaux, List<RDV> rdvs) {
//        List<Creneau> availableCreneaux = new ArrayList<>();
//
//        for (Creneau creneau : allCreneaux) {
//            boolean isCreneauAvailable = true;
//
//            for (RDV rdv : rdvs) {
//                if (rdv.getCreneau().equals(creneau)) {
//                    isCreneauAvailable = false;
//                    break;
//                }
//            }
//
//            if (isCreneauAvailable) {
//                availableCreneaux.add(creneau);
//            }
//        }
//
//        return availableCreneaux;
//    }

//    @GetMapping("/{id}/creneaux-disponibles/{jour}")
//    public ResponseEntity<List<Creneau>> getAvailableCreneauxForCentreAndJour(@PathVariable Integer id, @PathVariable String jour) {
//        Centre centre = centreService.getCenterById(id);
//
//        // Récupérer les créneaux du jour de travail sélectionné
//        Set<Creneau> allCreneaux = getAllCreneauxForJour(centre, jour);
//
//        List<RDV> rdvs = rdvService.getRDVsByCentre(centre);
//
//        // Filtrer les créneaux disponibles pour le jour de travail sélectionné
//        List<Creneau> availableCreneaux = filterAvailableCreneaux(allCreneaux, rdvs);
//
//        return new ResponseEntity<>(availableCreneaux, HttpStatus.OK);
//    }

//    private Set<Creneau> getAllCreneauxForJour(Centre centre, String jour) {
//        Set<Creneau> allCreneaux = new HashSet<>();
//        Set<JourTravail> joursTravail = centre.getJoursTravail();
//
//        // Parcourir tous les jours de travail du centre
//        for (JourTravail jourTravail : joursTravail) {
//            // Vérifier si le jour de travail correspond au jour sélectionné
//            if (jourTravail.getJour().equalsIgnoreCase(jour)) {
//                // Ajouter les créneaux du jour de travail à la liste des créneaux
//                allCreneaux.addAll(jourTravail.getCreneaux());
//                break; // Arrêter la recherche une fois que le jour de travail est trouvé
//            }
//        }
//
//        return allCreneaux;
//    }

//    private List<Creneau> filterAvailableCreneaux(Set<Creneau> allCreneaux, List<RDV> rdvs) {
//        List<Creneau> availableCreneaux = new ArrayList<>();
//
//        for (Creneau creneau : allCreneaux) {
//            boolean isCreneauAvailable = true;
//
//            for (RDV rdv : rdvs) {
//                if (rdv.getCreneau().equals(creneau)) {
//                    isCreneauAvailable = false;
//                    break;
//                }
//            }
//
//            if (isCreneauAvailable) {
//                availableCreneaux.add(creneau);
//            }
//        }
//
//        return availableCreneaux;
//    }
// Define a Map to store English-French day name translations
    private static final Map<String, String> jourMapping = Map.of(
            "MONDAY", "Lundi",
            "TUESDAY", "Mardi",
            "WEDNESDAY", "Mercredi",
            "THURSDAY", "Jeudi",
            "FRIDAY", "Vendredi",
            "SATURDAY", "Samedi",
            "SUNDAY", "Dimanche"
    );

    private Set<Creneau> filterAvailableCreneaux(Set<JourTravail> joursTravail, List<RDV> rdvs, String jourSpecifique) {
//        List<Creneau> availableCreneaux = new ArrayList<>();
        Set<Creneau> availableCreneaux = new HashSet<>();

        // Heure actuelle du système de l'utilisateur (supposons que vous avez un moyen d'obtenir cette heure)
        LocalTime currentTime = LocalTime.now();

        // Ajout d'un délai d'une heure à l'heure actuelle
        LocalTime currentTimePlusOneHour = currentTime.plusHours(1);

        // Recherche du jour de travail spécifique
        JourTravail jourTravailSpecifique = joursTravail.stream()
                .filter(jt -> jt.getJour().equalsIgnoreCase(jourSpecifique))
                .findFirst()
                .orElse(null);

        if (jourTravailSpecifique != null) {
            // Obtention des créneaux pour le jour spécifique
            Set<Creneau> creneaux = jourTravailSpecifique.getCreneaux();

            // Filtrage des créneaux disponibles pour ce jour spécifique
            for (Creneau creneau : creneaux) {
                boolean isCreneauAvailable = true;
                int rdvCount = 0;

                // Vérifier si l'heure de début du créneau est supérieure à l'heure actuelle plus une heure
                LocalTime creneauStartTime = creneau.getHeureDebut();
                if (creneauStartTime.isBefore(currentTimePlusOneHour)) {
                    isCreneauAvailable = false; // Ignorer les créneaux dont l'heure de début est déjà passée
                } else {
                    // Vérifier le nombre de rendez-vous dans ce créneau
                    for (RDV rdv : rdvs) {
                        if (rdv.getCreneau().equals(creneau)) {
                            rdvCount++;
                            if (rdvCount >= 5) { // Limite de 5 rendez-vous atteinte
                                isCreneauAvailable = false;
                                break;
                            }
                        }
                    }
                }

//                if (isCreneauAvailable) {
//                    availableCreneaux.add(creneau);
//                }
                if (isCreneauAvailable) {
                    availableCreneaux.add(creneau); // Use Set instead of List
                }
            }

            String currentJourFr = jourMapping.get(LocalDate.now().getDayOfWeek().name());

            if(jourTravailSpecifique.getJour().compareToIgnoreCase(currentJourFr) > 0) {
                availableCreneaux.addAll(jourTravailSpecifique.getCreneaux());
            }
        }

        Set<Creneau> sortedAvailableCreneaux = new TreeSet<>(
                (creneau1, creneau2) -> creneau1.getHeureDebut().compareTo(creneau2.getHeureDebut())
        );

        // Add filtered creneaux to the sorted set
        sortedAvailableCreneaux.addAll(availableCreneaux);

        return sortedAvailableCreneaux;
    }


    @PostMapping
    public CenterRespDto addCentre(@RequestBody CenterRespDto centreDto) {
        return centreService.addCentre(centreDto);
    }

}
