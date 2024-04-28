package com.ucd.CenterRDV.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Creneau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "heure_debut")
    private LocalTime heureDebut;

    @Column(name = "heure_fin")
    private LocalTime heureFin;

    @ManyToOne
    @JoinColumn(
            name = "centre_id"
    )
    @JsonBackReference
    private Centre centreSante;

    @OneToMany(
            mappedBy = "creneau",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private Set<RDV> rdvs;

    @ManyToOne
    @JoinColumn(name = "jour_travail_id")
    @JsonBackReference
    private JourTravail jourTravail;

}
