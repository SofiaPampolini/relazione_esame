package org.example.repository;

import java.util.List;

import org.example.model.TariffaPosto;
import org.example.model.TariffaPostoId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia Repository per l'entità TariffaPosto.
 * Estende JpaRepository fornendo operazioni CRUD automatiche con chiave primaria composta TariffaPostoId.
 */

public interface TariffaPostoRepository extends JpaRepository<TariffaPosto, TariffaPostoId>{
    List<TariffaPosto> findByCodiceTeatro(String codiceTeatro);
} 
