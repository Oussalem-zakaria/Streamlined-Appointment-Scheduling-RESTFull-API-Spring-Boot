package com.ucd.CenterRDV.controllers;

import com.ucd.CenterRDV.dto.CreneauDto;
import com.ucd.CenterRDV.models.Creneau;
import com.ucd.CenterRDV.models.RDV;
import com.ucd.CenterRDV.services.CreneauService;
import com.ucd.CenterRDV.services.RdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creneaux")
public class CreneauController {

    private final CreneauService creneauService;
    private final RdvService rdvService;

    @Autowired
    public CreneauController(CreneauService creneauService, RdvService rdvService) {
        this.creneauService = creneauService;
        this.rdvService = rdvService;
    }

    @GetMapping
    public List<Creneau> getAllCreneaux() {
        return creneauService.getAllCreneaux();
    }

    @GetMapping("/rdv/{id}")
    public Creneau getCreneauByRdv(@PathVariable Integer id){
        RDV rdv = rdvService.getRdvById(id);
        return creneauService.getCreneauByRdv(rdv);
    }

    @PostMapping
    public CreneauDto addCreneau(@RequestBody CreneauDto creneau) {
        return creneauService.addCreneau(creneau);
    }

}
