package com.ucd.CenterRDV.services;

import com.ucd.CenterRDV.models.JourTravail;
import com.ucd.CenterRDV.repository.JourTravailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JourService {
    private final JourTravailRepository jourTravailRepository;

    @Autowired
    public JourService(JourTravailRepository jourTravailRepository) {
        this.jourTravailRepository = jourTravailRepository;
    }

    public JourTravail getJourByNameAndCentreId(String jourName, Integer centreId) {
        return jourTravailRepository.findByJourAndCentreId(jourName, centreId);
    }
}
