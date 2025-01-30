package com.example.pokemon_tcg.services.implementations;

import com.example.pokemon_tcg.models.Carte;
import com.example.pokemon_tcg.models.Dresseur;
import com.example.pokemon_tcg.models.HistoriqueEchange;
import com.example.pokemon_tcg.repositories.CarteRepository;
import com.example.pokemon_tcg.repositories.DresseurRepository;
import com.example.pokemon_tcg.repositories.EchangeRepository;
import com.example.pokemon_tcg.services.IDresseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
class DresseurServiceImpl implements IDresseurService {
    @Autowired
    private DresseurRepository dresseurRepository;

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private EchangeRepository echangeRepository;

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

    @Override
    public boolean exchangeCard(String dresseurFromId, String dresseurToId, String carteId) {
        Optional<Dresseur> dresseurFromOpt = dresseurRepository.findById(dresseurFromId);
        Optional<Dresseur> dresseurToOpt = dresseurRepository.findById(dresseurToId);
        Optional<Carte> carteOpt = carteRepository.findById(carteId);

        if (dresseurFromOpt.isPresent() && dresseurToOpt.isPresent() && carteOpt.isPresent()) {
            Dresseur dresseurFrom = dresseurFromOpt.get();
            Dresseur dresseurTo = dresseurToOpt.get();
            Carte carte = carteOpt.get();

            if (dresseurFrom.getCarteList().contains(carte)) {
                dresseurFrom.getCarteList().remove(carte);
                dresseurTo.getCarteList().add(carte);
                carte.setDresseur(dresseurTo);

                dresseurRepository.save(dresseurFrom);
                dresseurRepository.save(dresseurTo);
                carteRepository.save(carte);

                HistoriqueEchange history = new HistoriqueEchange();
                history.setDresseurFrom(dresseurFrom);
                history.setDresseurTo(dresseurTo);
                history.setCarte(carte);
                history.setExchangeDate(LocalDateTime.now());
                echangeRepository.save(history);

                return true;
            }
        }
        return false;
    }
}