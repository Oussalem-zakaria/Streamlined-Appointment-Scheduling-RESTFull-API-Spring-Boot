package com.ucd.CenterRDV;

import com.ucd.CenterRDV.models.*;
import com.ucd.CenterRDV.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CenterRdvApplication {

    public static void main(String[] args) {
        SpringApplication.run(CenterRdvApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode,
                          RDVRepository rdvRepository, CentreRepository centreRepository, CreneauRepository creneauRepository
    ) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            ApplicationUser admin = new ApplicationUser(
                    1,
                    "admin@ucd.ac.ma",
                    "zakaria",
                    "oussalem",
                    "0608766503",
                    passwordEncode.encode("12345678"),
                    roles
            );

            userRepository.save(admin);

			// Ajouter un centre
			Centre centre = new Centre();
			centre.setNom("C1");
			centre.setAdresse("Ait Baha");

            Centre centre2 = new Centre();
            centre2.setNom("C2");
            centre2.setAdresse("Biougra");
            centreRepository.save(centre2);

			// Ajouter un créneau
            Set<Creneau> creneaus = new HashSet<>();
            Creneau creneau = new Creneau();
            creneau.setHeureDebut(LocalTime.of(8, 0)); // Exemple : 08:00 (8 heures du matin)
            creneau.setHeureFin(LocalTime.of(10, 0)); // Exemple : 10:00 (10 heures du matin)

            creneaus.add(creneau);

            centre.setCreneaux(creneaus);
            centreRepository.save(centre);

            creneau.setCentreSante(centre); // Associer le créneau au centre créé
            creneauRepository.save(creneau);

//            // Ajouter un RDV
//
            RDV rdv = new RDV();
            rdv.setDate(LocalDate.now()); // Date de votre RDV
            rdv.setUser(admin); // Utilisateur associé au RDV
            rdv.setCentre(centre); // Centre de santé associé au RDV
            rdv.setCreneau(creneau); // Créneau associé au RDV
            rdvRepository.save(rdv);
//
            RDV rdv2 = new RDV();
            rdv2.setDate(LocalDate.now()); // Date de votre RDV
            rdv2.setUser(admin);// Utilisateur associé au RDV
            rdv2.setCentre(centre2); // Centre de santé associé au RDV
            rdv2.setCreneau(creneau);
            rdv2.setStatut(true);// Créneau associé au RDV
            rdvRepository.save(rdv2);
        };
    }
}
