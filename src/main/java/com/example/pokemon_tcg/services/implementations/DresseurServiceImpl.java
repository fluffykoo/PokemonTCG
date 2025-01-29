package com.example.pokemon_tcg.services.implementations;

import com.example.pokemon_tcg.models.Dresseur;
import com.example.pokemon_tcg.repositories.DresseurRepository;
import com.example.pokemon_tcg.services.IDresseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DresseurServiceImpl implements IDresseurService {

    @Autowired
    private DresseurRepository dresseurRepository;

    @Override
    public List<Dresseur> getAllDresseurs() {
        return dresseurRepository.findByDeletedAtIsNull();
    }

    @Override
    public Dresseur getDresseurById(String uuid) {
        return dresseurRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Dresseur non trouvé"));
    }

    @Override
    public Dresseur createDresseur(Dresseur dresseur) {
        return dresseurRepository.save(dresseur);
    }

    @Override
    public boolean deleteDresseur(String uuid) {
        Optional<Dresseur> dresseurOpt = dresseurRepository.findById(uuid);
        if (dresseurOpt.isPresent()) {
            Dresseur dresseur = dresseurOpt.get();
            dresseur.setDeletedAt(LocalDateTime.now());  // On ne supprime pas, on marque comme "supprimé"
            dresseurRepository.save(dresseur);
            return true;
        }
        return false;
    }
}