package com.ucd.CenterRDV.controllers;

import com.ucd.CenterRDV.dto.RdvDto;
import com.ucd.CenterRDV.models.*;
import com.ucd.CenterRDV.services.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rdvs")
public class RDVController {
    private final RdvService rdvService;
    private final UserService userService;
    private final JourService jourService;
    private final EmailService emailService;
    private final CentreService centreService;
    private final CreneauService creneauService;

    @Autowired
    public RDVController(RdvService rdvService, UserService userService,JourService jourService,EmailService emailService,CentreService centreService,CreneauService creneauService) {
        this.rdvService = rdvService;
        this.userService = userService;
        this.jourService = jourService;
        this.emailService = emailService;
        this.centreService = centreService;
        this.creneauService = creneauService;
    }

    @GetMapping
    public List<RDV> getAllRDVs() {
        return rdvService.getAllRDVs();
    }

    @GetMapping("/{userId}")
    public List<RDV> getRdvsByUserId(@PathVariable Integer userId) {
        ApplicationUser user = userService.getUserById(userId);
        return rdvService.getRDVsByUser(user);
    }

    @GetMapping("/rdv/{Id}")
    public RDV getRdvById(@PathVariable Integer Id) {
        return rdvService.getRdvById(Id);
    }

//    @PostMapping
//    public RDV addRDV(@RequestBody @Valid RdvDto dto) {
//        RDV rdv = toRdv(dto);
//        return rdvService.addRDV(rdv);
//    }

    @PostMapping
    public ResponseEntity<RdvDto> addRDV(@RequestBody @Valid RdvDto dto) throws MessagingException, IOException {
        // Récupérer tous les rendez-vous de l'utilisateur pour le créneau sélectionné et le jour spécifié
        List<RDV> userRdvsInCreneauAndJour = rdvService.getRDVsByUserIdAndCreneauIdAndJour(dto.userId(), dto.creneauId(), dto.jour());

        // Vérifier si l'utilisateur a déjà un rendez-vous dans ce créneau le même jour
        if (!userRdvsInCreneauAndJour.isEmpty()) {
            // Refuser la prise de rendez-vous car il existe déjà un rendez-vous dans ce créneau le même jour
            return ResponseEntity.badRequest().body(
                    null
            );
        }

        // Si l'utilisateur n'a pas de rendez-vous dans ce créneau le même jour, continuer avec la prise de rendez-vous
        RDV rdv = toRdv(dto);
        rdvService.addRDV(rdv);
        ApplicationUser user = userService.getUserById(dto.userId());
        Centre centre = centreService.getCenterById(dto.centerId());
        Creneau creneau = creneauService.getCreneauById(dto.creneauId());

        emailService.sendEmailFromTemplate(user.getUsername(),user.getFirstName()+" "+user.getLastName(),dto.date().toString(),centre.getNom(),creneau);
        return ResponseEntity.ok(
                dto
        );
    }

    private RDV toRdv(RdvDto dto) {
        RDV rdv = new RDV();
        rdv.setStatut(dto.statut());
        rdv.setDate(dto.date());
        ApplicationUser user = new ApplicationUser();
        user.setUserId(dto.userId());
        Centre centre = new Centre();
        centre.setId(dto.centerId());
        Creneau creneau = new Creneau();
        creneau.setId(dto.creneauId());
        rdv.setUser(user);
        rdv.setCentre(centre);
        // Nouvelle ligne pour spécifier le jour
        JourTravail jour = jourService.getJourByNameAndCentreId(dto.jour(), dto.centerId());

        creneau.setJourTravail(jour);
        rdv.setCreneau(creneau);

        return rdv;
    }
}
