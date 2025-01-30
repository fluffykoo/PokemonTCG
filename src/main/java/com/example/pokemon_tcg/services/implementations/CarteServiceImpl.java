package com.example.pokemon_tcg.services.implementations;

import com.example.pokemon_tcg.models.Carte;
import com.example.pokemon_tcg.repositories.CarteRepository;
import com.example.pokemon_tcg.services.ICarteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarteServiceImpl implements ICarteService {
    private final CarteRepository carteRepository;

    public CarteServiceImpl(CarteRepository carteRepository) {
        this.carteRepository = carteRepository;
    }

    @Override
    public Carte createCarte(Carte carte) {
        return carteRepository.save(carte);
    }

    @Override
    public List<Carte> getAllCartes() {
        return carteRepository.findAll();
    }

    @Override
    public Carte getCarteById(String uuid) {
        return carteRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Carte not found"));
    }

    @Override
    public Carte updateCarte(Carte carte) {
        return carteRepository.save(carte);
    }

    @Override
    public boolean deleteCarte(String uuid) {
        if (carteRepository.existsById(uuid)) {
            carteRepository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    public Carte findByPokemonId(String pokemonId) {
        return carteRepository.findByPokemon_Uuid(pokemonId);
    }
}