package org.example.repository;

import org.example.model.FasciaFedelta;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia Repository per l'entità FasciaFedeltà.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave primaria di tipo String.
 */

public interface FasciaFedeltaRepository extends JpaRepository<FasciaFedelta, String>{
    
}
