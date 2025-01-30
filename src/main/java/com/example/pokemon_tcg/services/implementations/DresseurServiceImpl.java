package com.example.pokemon_tcg.services.implementations;

import com.example.pokemon_tcg.models.Dresseur;
import com.example.pokemon_tcg.repositories.DresseurRepository;
import com.example.pokemon_tcg.services.IDresseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
class DresseurServiceImpl implements IDresseurService {
    @Autowired
    private DresseurRepository dresseurRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public Dresseur getDresseurByUsername(String username) {
        return dresseurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Dresseur non trouvé"));
    }

    @Override
    public boolean authenticateDresseur(String username, String password) {
        Dresseur dresseur = getDresseurByUsername(username);
        return passwordEncoder.matches(password, dresseur.getPassword()); // Vérification du mot de passe
    }

    @Override
    public List<Dresseur> getAllDresseurs() {
        return dresseurRepository.findByDeletedAtIsNull();
    }

    @Override
    public Dresseur getDresseurById(String uuid) {
        return dresseurRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Dresseur non trouvé"));
    }

    @Override
    public Dresseur createDresseur(Dresseur dresseur) {
        dresseur.setPassword(passwordEncoder.encode(dresseur.getPassword())); // Hashage du mot de passe
        return dresseurRepository.save(dresseur);
    }


    @Override
    public boolean deleteDresseur(String uuid) {
        Optional<Dresseur> dresseurOpt = dresseurRepository.findById(uuid);
        if (dresseurOpt.isPresent()) {
            Dresseur dresseur = dresseurOpt.get();
            dresseur.setDeletedAt(LocalDateTime.now());
            dresseurRepository.save(dresseur);
            return true;
        }
        return false;
    }

    @Override
    public Dresseur updateDresseur(String uuid, Dresseur dresseur) {
        if (!dresseurRepository.existsById(uuid)) {
            throw new RuntimeException("Dresseur non trouvé");
        }
        dresseur.setUuid(uuid);
        return dresseurRepository.save(dresseur);
    }
}