package com.example.pokemon_tcg.services.implementations;

import com.example.pokemon_tcg.constants.TypePokemon;
import com.example.pokemon_tcg.models.Pokemon;
import com.example.pokemon_tcg.repositories.PokemonRepository;
import com.example.pokemon_tcg.services.IPokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonServiceImpl implements IPokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Override
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }

    @Override
    public List<Pokemon> getAllPokemon(TypePokemon type) {
        return (type == null) ? pokemonRepository.findAll() : pokemonRepository.findAllByType(type);
    }

    @Override
    public Pokemon getPokemonById(String uuid) {
        return pokemonRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Pokemon non trouvé"));
    }

    @Override
    public Pokemon createPokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @Override
    public boolean deletePokemon(String uuid) {
        Optional<Pokemon> pokemon = pokemonRepository.findById(uuid);
        if (pokemon.isPresent()) {
            pokemonRepository.deleteById(uuid);
            return true;
        }
        return false;
    }
}