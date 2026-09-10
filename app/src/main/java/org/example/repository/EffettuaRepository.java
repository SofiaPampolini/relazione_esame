package org.example.repository;

import java.util.Optional;

import org.example.model.Effettua;
import org.example.model.EffettuaId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia Repository per l'entità Effettua.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave composta EffettuaId.
 */

public interface EffettuaRepository extends JpaRepository<Effettua, EffettuaId>{
    Optional<Effettua> findByCodicePrenotazione(String codicePrenotazione);
} 
