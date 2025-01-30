package com.example.pokemon_tcg.repositories;

import com.example.pokemon_tcg.models.HistoriqueEchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EchangeRepository extends JpaRepository<HistoriqueEchange, String> {
}