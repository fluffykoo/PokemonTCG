package com.example.pokemon_tcg.services.implementations;


import com.example.pokemon_tcg.models.Pokemon;
import com.example.pokemon_tcg.repositories.PokemonRepository;
import com.example.pokemon_tcg.services.IPokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonServiceImpl implements IPokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Override
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }

    @Override
    public Pokemon getPokemonById(String uuid) {
        return pokemonRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Pokemon non trouvé"));
    }

    @Override
    public List<Pokemon> getPokemonByType(String type) {
        return pokemonRepository.findByType(type);
    }

    @Override
    public Pokemon createPokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @Override
    public void deletePokemon(String uuid) {
        pokemonRepository.deleteById(uuid);
    }
}