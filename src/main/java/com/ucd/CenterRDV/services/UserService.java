package com.ucd.CenterRDV.services;

import com.ucd.CenterRDV.models.ApplicationUser;
import com.ucd.CenterRDV.models.Role;
import com.ucd.CenterRDV.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder encoder;
    private UserRepository userRepository;

    public UserService(PasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");
        return userRepository.findByUserName(username).orElseThrow(() -> new  UsernameNotFoundException("user is not valid!"));
    }

    public List<ApplicationUser> getAllUsers() {
        return userRepository.findAll();
    }

    public ApplicationUser getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    public ApplicationUser createUser(ApplicationUser user) {
        // Encoder le mot de passe avant de l'enregistrer dans la base de données
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

//    public ApplicationUser updateUser(Integer userId, ApplicationUser newUser) {
//        ApplicationUser existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
//
//        // Mettre à jour les propriétés du nouvel utilisateur
//        existingUser.setFirstName(newUser.getFirstName());
//        existingUser.setLastName(newUser.getLastName());
//        existingUser.setPhoneNumber(newUser.getPhoneNumber());
//        existingUser.setAuthorities(newUser.getAuthorities());
//
//        // Enregistrer les modifications dans la base de données
//        return userRepository.save(existingUser);
//    }

//    public void deleteUser(Integer userId) {
//        // Vérifier si l'utilisateur existe
//        Optional<ApplicationUser> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            // Supprimer l'utilisateur s'il existe
//            userRepository.deleteById(userId);
//        } else {
//            throw new IllegalArgumentException("User not found with id: " + userId);
//        }
//    }
}
