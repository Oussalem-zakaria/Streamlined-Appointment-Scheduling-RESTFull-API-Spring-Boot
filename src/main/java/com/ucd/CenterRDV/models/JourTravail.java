package com.ucd.CenterRDV.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Set;

@Entity
@Data
public class JourTravail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String jour;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "centre_id")
    @JsonBackReference
    private Centre centre;

    @OneToMany(mappedBy = "jourTravail")
    @JsonManagedReference
    private Set<Creneau> creneaux;

//    private String heureOuverture;
//    private String heureFermeture;
}
